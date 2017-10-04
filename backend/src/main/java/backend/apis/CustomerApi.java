package backend.apis;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.objectify.cmd.Query;

import java.io.IOException;
import java.util.List;
import java.util.logging.Filter;

import backend.deliveryRequests.DeliveryRequest;
import backend.helpers.Constants;
import backend.helpers.CursorHelper;
import backend.helpers.FireBaseHelper;
import backend.helpers.pseudoEnums.MerchantOrderByOptions;
import backend.helpers.pseudoEnums.MerchantTypes;
import backend.merchants.MerchantCategory;
import backend.merchants.Item;
import backend.merchants.Merchant;
import backend.merchants.inventory.Inventory;
import backend.profiles.customer.Customer;
import backend.views.MenuView;
import backend.merchants.MerchantView;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Muhammad on 19/08/2017.
 */
@Api(
        name = "customerApi",
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

    @ApiMethod(name = "getListOfMerchantsViewsOrderedBy")
    public CollectionResponse<MerchantView> getListOfMerchantsViewsOrderedBy(
            @Named("cursorStr") String cursorStr,
            @Named("orderByOption") String orderByOption,
            @Named("merchantType") String merchantType,
            @Named("limitNumber") int limitNumber) {

        Class<? extends Merchant> merchantTypeClass = MerchantTypes.getMerchantTypeClass(merchantType);

        Query<Merchant> query = (Query<Merchant>) ofy().load().type(merchantTypeClass).
                order(MerchantOrderByOptions.getOption(orderByOption)).limit(limitNumber);

        cursorStr = cursorStr.toLowerCase().equals("null") ? null : cursorStr;
        CursorHelper<Merchant> cursorHelper = (CursorHelper<Merchant>) new CursorHelper<>(merchantTypeClass);
        CollectionResponse<Merchant> merchantResponse =
                cursorHelper.queryAtCursor(query, cursorStr);

        List<MerchantView> result = MerchantView
                .getListOfMerchantsViews((List<Merchant>) merchantResponse.getItems());
        CollectionResponse<MerchantView> response = cursorHelper.buildCollectionResponse(result);
        return response;
    }


    @ApiMethod(name = "getMerchantMenuByID")
    public MenuView getMerchantMenuByID(@Named("merchantID") Long merchantID) {
        MenuView menuView = new MenuView(merchantID);
        return menuView;
    }

    @ApiMethod(name = "getItemsOfCategoryByID")
    public List<Item> getItemsOfCategoryByID(@Named("categoryID") Long categoryID) {
        return MerchantCategory.getCategoryByID(categoryID).getItems();
    }

    @ApiMethod(name = "sendDeliveryRequest")
    public DeliveryRequest sendDeliveryRequest(@Named("deliveryRequestJson") String deliveryRequestJson) throws IOException {
        DeliveryRequest deliveryRequest = new Gson().fromJson(
                deliveryRequestJson, new TypeToken<DeliveryRequest>() {
                }.getType());
        deliveryRequest.save();

        FireBaseHelper.sendNotification(Merchant.getMerchantByID(
                deliveryRequest.merchantId).getRegTokenList(),
                String.valueOf(deliveryRequest.id));

        //the merchant client App parses the delivery request id and calls getDeliveryRequestByID
        return deliveryRequest;
    }

    @ApiMethod(name = "getDeliveryRequestsOfCustomer")
    public List<DeliveryRequest> getDeliveryRequestsOfCustomer(@Named("customerID") Long customerID) {
        return ofy().load().type(DeliveryRequest.class).filter("customerId =", customerID).list();
    }


    @ApiMethod(name = "getListOfMerchantsByCategoryNameOrderedBy", path = "getListOfMerchantsByCategoryNameOrderedBy")
    public CollectionResponse<MerchantView> getListOfMerchantsByCategoryNameOrderedBy
            (@Named("categoryName") String categoryName,
             @Named("cursorStr") String cursorStr,
             @Named("orderByOption") String orderByOption,
             @Named("limitNumber") int limitNumber) {

        Query<Merchant> query = ofy().load().type(Merchant.class).
                filter("actualCategories", categoryName).order(orderByOption).limit(limitNumber);

        cursorStr = cursorStr.toLowerCase().equals("null") ? null : cursorStr;

        CursorHelper<Merchant> cursorHelper = new CursorHelper<>(Merchant.class);
        CollectionResponse<Merchant> merchantResponse =
                cursorHelper.queryAtCursor(query, cursorStr);

        List<MerchantView> result = MerchantView
                .getListOfMerchantsViews((List<Merchant>) merchantResponse.getItems());

        CollectionResponse<MerchantView> response = cursorHelper.buildCollectionResponse(result);
        return response;
    }


    @ApiMethod(name = "getListOfItemsByCategoryNameOrderedBy", path = "getListOfItemsByCategoryNameOrderedBy")
    public CollectionResponse<Item> getListOfItemsByCategoryNameOrderedBy
            (@Named("categoryName") String categoryName,
             @Named("cursorStr") String cursorStr,
             @Named("orderByOption") String orderByOption,
             @Named("limitNumber") int limitNumber) {

        Query<Item> query = ofy().load().type(Item.class).
                filter("actualCategories", categoryName).order(orderByOption).limit(limitNumber);

        cursorStr = cursorStr.toLowerCase().equals("null") ? null : cursorStr;

        CursorHelper<Item> cursorHelper = new CursorHelper<>(Item.class);
        CollectionResponse<Item> response =
                cursorHelper.queryAtCursor(query, cursorStr);

        return response;
    }

    @ApiMethod(name = "getInventory")
    public Inventory getInventory() {
        return ofy().load().type(Inventory.class).list().get(0);
    }

    @ApiMethod(name = "getFeedOfTopMerchants")
    public CollectionResponse<MerchantView> getFeedOfTopMerchants(@Named("cursorStr") String cursorStr,
                                                                  @Named("orderByOption") String orderByOption,
                                                                  @Named("limitNumber") int limitNumber) {
        Query<Merchant> query = ofy().load().type(Merchant.class)
                .filter("browsable",true).order(orderByOption).limit(limitNumber);
        cursorStr = cursorStr.toLowerCase().equals("null") ? null : cursorStr;

        CursorHelper<Merchant> cursorHelper = new CursorHelper<>(Merchant.class);
        CollectionResponse<Merchant> merchantResponse =
                cursorHelper.queryAtCursor(query, cursorStr);

        List<MerchantView> result = MerchantView
                .getListOfMerchantsViews((List<Merchant>) merchantResponse.getItems());
        CollectionResponse<MerchantView> response = cursorHelper.buildCollectionResponse(result);
        return response;
    }


    //testing methods
 /*@ApiMethod(name = "getListOfRestaurantsViewsOrderedBy",path = "getListOfRestaurantsViewsOrderedBy")
 public CollectionResponse<MerchantView> getListOfRestaurantsViewsOrderedBy(
         @Named("cursorStr") @Nullable String cursorStr,
         @Named("merchantsOrderBy") MerchantsOrderBy merchantsOrderBy,
         @Named("limitNumber") int limitNumber){
     return getListOfMerchantsViewsOrderedBy(cursorStr,merchantsOrderBy,MerchantTypes.RESTAURANT,limitNumber);
 }*/

    @ApiMethod(name = "createDeliveryRequest")
    public DeliveryRequest createDeliveryRequest(@Named("customerID") Long customerID) {
        DeliveryRequest deliveryRequest = new DeliveryRequest(customerID, 100L, null, "blabla");
        deliveryRequest.save();
        return deliveryRequest;
    }

}
