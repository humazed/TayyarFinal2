package backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.objectify.cmd.Query;

import java.io.IOException;
import java.util.List;

import backend.deliveryRequests.DeliveryRequest;
import backend.helpers.Constants;
import backend.helpers.CursorHelper;
import backend.helpers.FireBaseHelper;
import backend.merchants.Merchant;
import backend.profiles.customer.Customer;
import backend.views.MenuView;
import backend.views.MerchantView;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Muhammad on 19/08/2017.
 */
@Api(
        name = "customerApiInstance",
        version = "v1",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE}
)
public class CustomerApi {
        private static CustomerApi customerApiInstance;

        public CustomerApi() {
        }

        public static CustomerApi getApiSingleton() {
                if (customerApiInstance == null) {
                        customerApiInstance = new CustomerApi();
                        return customerApiInstance;
                }
                return customerApiInstance;
        }

        @ApiMethod(name = "createCustomer")
        public Customer createCustomer(@Named("name") String name,
                                       @Named("email") String email,
                                       @Named("mainAddress") String mainAddress,
                                       @Named("phone") String phone) {
                Customer customer = new Customer(name, email, phone, mainAddress);
                customer.saveProfile();
                return customer;
        }

        @ApiMethod(name = "getMerchantByName")
        public List<MerchantView> getMerchantByName(@Named("name") String name) {
                return MerchantView.
                        getListOfMerchantsViews(Merchant.getMerchantByName(name));
        }

        @ApiMethod(name = "getMerchantByID")
        public Merchant getMerchantByID(@Named("merchantID") Long merchantID) {
                return Merchant.getMerchantByID(merchantID);
        }

        @ApiMethod(name = "getMerchantViewByID")
        public MerchantView getMerchantViewByID(@Named("merchantID") Long merchantID) {
                return new MerchantView(Merchant.getMerchantByID(merchantID));
        }

        @ApiMethod(name = "getListOfMerchantsViewsOrderedByPricing")
        public CollectionResponse<MerchantView> getListOfMerchantsViewsOrderedByPricing(@Named("cursorStr") @Nullable String cursorStr) {

                Query<Merchant> query = ofy().load().type(Merchant.class).
                        order("pricing").limit(5);
                CursorHelper<Merchant> cursorHelper = new CursorHelper<>(Merchant.class);
                CollectionResponse<Merchant> merchantsResponse =
                        cursorHelper.queryAtCursor(query, cursorStr);
                List<MerchantView> result = MerchantView
                        .getListOfMerchantsViews((List<Merchant>) merchantsResponse.getItems());

                CollectionResponse<MerchantView> response = cursorHelper.buildCollectionResponse(result);
                return response;
        }

        @ApiMethod(name = "getMerchantMenuByID")
        public MenuView getMerchantMenuByID(@Named("merchantID") Long merchantID) {
                MenuView menuView = new MenuView(merchantID);
                return menuView;
        }

        @ApiMethod(name = "sendDeliveryRequest")
        public DeliveryRequest sendDeliveryRequest(@Named("deliveryRequestJson")String  deliveryRequestJson ) throws IOException {
                DeliveryRequest deliveryRequest =  new Gson().fromJson(
                        deliveryRequestJson, new TypeToken<DeliveryRequest>() {
                        }.getType());
                deliveryRequest.save();
                FireBaseHelper.sendNotification(Merchant.getMerchantByID(
                        deliveryRequest.merchantId).regTokenList,
                        String.valueOf(deliveryRequest.id));

                //the merchant client App parses the delivery request id and calls getDeliveryRequestByID
                return deliveryRequest;
        }


}
