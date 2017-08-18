/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package backend;

import com.google.api.server.spi.Strings;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.cmd.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.server.spi.config.Named;

import backend.deliveryRequests.DeliveryRequest;
import backend.general.MerchantView;
import backend.helpers.CursorHelper;
import backend.helpers.FireBaseHelper;
import backend.merchants.Category;
import backend.merchants.Item;
import backend.merchants.MenuView;
import backend.merchants.Merchant;
import backend.merchants.pharmacy.Pharmacy;
import backend.merchants.pharmacy.PharmacyItem;
import backend.merchants.restaurant.RestaurantItemOption;
import backend.merchants.restaurant.Restaurant;
import backend.merchants.restaurant.RestaurantItem;
import backend.profiles.customer.Customer;
import backend.profiles.driver.Driver;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend",
                ownerName = "backend",
                packagePath = ""
        )
)
public class MyEndpoint {


    /**
     * todo change return types to wrappers after testing
     */

    @ApiMethod(name = "createRestaurant")
    public Restaurant createRestaurant(@Named("name") String name, @Named("email") String email,
                                       @Named("phone") String phone, @Named("imageURL") String imageURL
    ) {
        Restaurant restaurant = new Restaurant(name, email, phone, imageURL);
        restaurant.saveMerchant();
        return restaurant;
    }

    @ApiMethod(name = "createPharmacy")
    public Pharmacy createPharmacy(@Named("name") String name, @Named("email") String email,
                                   @Named("phone") String phone, @Named("imageURL") String imageURL
    ) {
        Pharmacy pharmacy = new Pharmacy(name, email, phone, imageURL);
        pharmacy.saveMerchant();
        return pharmacy;
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

    @ApiMethod(name = "createDriver")
    public Driver createDriver(@Named("name") String name,
                               @Named("email") String email,
                               @Named("phone") String phone,
                               @Named("vehicle") String vehicle,
                               @Named("imageURL") String imageURL
    ) {
        Driver driver = new Driver(name, email, phone, vehicle, imageURL);
        driver.saveProfile();
        return driver;
    }

    @ApiMethod(name = "createCategory")
    public Category createCategory(@Named("name") String name,
                                   @Named("description") String description,
                                   @Named("imageURL") String imageURL
    ) {
        Category category = new Category(name, description, imageURL);
        category.saveCategory();
        return category;
    }

    @ApiMethod(name = "createRestaurantItem")
    public RestaurantItem createRestaurantItem(@Named("name") String name,
                                               @Named("basePrice") double basePrice) {
        RestaurantItem item = new RestaurantItem(name, basePrice);
        item.saveItem();
        return item;
    }

    @ApiMethod(name = "createPharmacyItem")
    public PharmacyItem createPharmacyItem(@Named("name") String name,
                                           @Named("basePrice") double basePrice) {
        PharmacyItem item = new PharmacyItem(name, basePrice);
        item.saveItem();
        return item;
    }


    @ApiMethod(name = "createOption", path = "createOption", httpMethod = ApiMethod.HttpMethod.GET)
    public RestaurantItemOption createOption(@Named("name") String name,
                                             @Named("required") boolean required,
                                             @Named("price") double addedPrice,
                                             @Named("description") String description,
                                             @Named("available") boolean available) {

        RestaurantItemOption option = new RestaurantItemOption(name, required, addedPrice, description, available);
        option.saveOption();
        return option;
    }


    @ApiMethod(name = "addCategoryToMerchant")
    public Merchant addCategoryToMerchant(@Named("merchantID") Long merchantID,
                                          @Named("categoryID") Long categoryID) {
        Merchant merchant = Merchant.getMerchantByID(merchantID);
        merchant.addCategory(categoryID);
        return merchant;
    }


    @ApiMethod(name = "addItemToCategory")
    public Category addItemToCategory(@Named("categoryID") Long categoryID, @Named("itemID") Long itemID) {
        Category category = Category.getCategoryByID(categoryID);
        category.addItem(itemID);
        return category;
    }


    @ApiMethod(name = "addOptionToRestaurantItem")
    public Item addOptionToRestaurantItem(@Named("itemID") Long itemID,
                                          @Named("optionID") Long optionID) {

        RestaurantItem item = (RestaurantItem) RestaurantItem.getItemByID(itemID);
        item.addOptionToThisItem(optionID);
        return item;
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

    @ApiMethod(name = "getListOfMerchantsViews")
    public CollectionResponse<MerchantView> getMerchantsViewsOrderedByPricing(@Named("cursorStr") @Nullable String cursorStr) {

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

    @ApiMethod(name = "getItemsOfCategory")
    public List<Item> getItemsOfCategory(@Named("categoryID") Long categoryID) {
        return Category.getCategoryByID(categoryID).getItems();
    }


    @ApiMethod(name = "sendDeliveryRequest")
    public DeliveryRequest sendDeliveryRequest(@Named("customerId") Long customerId, @Named("merchantId") Long merchantId,
                                               @Named("orderJsonMap") String orderJsonMap,
                                               @Named("instructions") String instructions) throws IOException {

        Map<Integer, Long> orderMap = new Gson().fromJson(
                orderJsonMap, new TypeToken<HashMap<Integer, Long>>() {
                }.getType()
        );

        DeliveryRequest deliveryRequest = new DeliveryRequest(customerId, merchantId, orderMap, instructions);
        deliveryRequest.save();
        FireBaseHelper.sendNotification(getMerchantByID(merchantId).regTokenList, String.valueOf(deliveryRequest.id));
        //the merchant client App parses the delivery request id and calls getDeliveryRequestByID
        return deliveryRequest;
    }

    @ApiMethod(name = "getDeliveryRequestByID")
    public DeliveryRequest getDeliveryRequestByID(@Named("deliveryRequestID") Long deliveryRequestID) {
        return DeliveryRequest.getDeliveryRequestByID(deliveryRequestID);
    }

    @ApiMethod(name = "merchantAcceptsDeliveryRequest")
    public DeliveryRequest merchantAcceptsDeliveryRequest(@Named("deliveryRequestID") Long deliveryRequestID) {
        DeliveryRequest deliveryRequest = DeliveryRequest.getDeliveryRequestByID(deliveryRequestID);
        deliveryRequest.merchantAcceptsOrder = true;
        String city = Merchant.getMerchantByID(deliveryRequest.merchantId).
                location.city;
        /*
        * no city field in the driver class yet
        * this is where you use google maps API
        * */
        Query<Driver> driverQuery = ObjectifyService.ofy().load().type(Driver.class).filter("city =", city)
                .filter("idle =", true);

        List<Driver> driverList = driverQuery.list();
        List<Long> driverIDs = new ArrayList<>();
        //getting list of all active drivers' IDs
        for (Driver driver : driverList) {
            driverIDs.add(driver.id);
        }
        List<Long> driversWhoRefusedIDs = deliveryRequest.driversWhoRefusedIDs;
        //filtering out drivers who refused
        for (Long id : driversWhoRefusedIDs) {
            driverIDs.remove(id);
        }
        try {
            Long driverID = driverIDs.get(0);
            Driver driver = Driver.getDriverByID(driverID);
            deliveryRequest.driverId = driverID;
            deliveryRequest.save();
            FireBaseHelper.sendNotification(driver.regTokenList, String.valueOf(deliveryRequest.id));
            return deliveryRequest;
        } catch (Exception e) {
            return null;
        }
    }

    @ApiMethod(name = "driverRefusesDelivery")
    public DeliveryRequest driverRefusesDelivery(@Named("deliveryRequestID") Long deliveryRequestID,
                                                 @Named("driverID") Long driverID) {
        DeliveryRequest deliveryRequest = DeliveryRequest.getDeliveryRequestByID(deliveryRequestID);
        deliveryRequest.addDriverWhoRefused(driverID);
        Driver.getDriverByID(driverID).changeDriverState(true);

        /*
        * redirect to other driver
        * don't call a method here to do that, it might stall the driver who refused app
        * */
        return deliveryRequest;
    }

    // testing methods
    //===========================================================================
    @ApiMethod(name = "create20Merchants")
    public List<Merchant> create20Merchants() {
        List<Merchant> merchantList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Restaurant merchant = new Restaurant();
            Pharmacy pharmacy = new Pharmacy();
            merchantList.add(merchant);
            merchantList.add(pharmacy);
            merchant.saveMerchant();
        }
        return merchantList;
    }
}
