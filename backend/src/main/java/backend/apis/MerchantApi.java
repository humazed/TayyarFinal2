/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package backend.apis;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.List;

import backend.TestEntity;
import backend.deliveryRequests.DeliveryRequest;
import backend.helpers.Constants;
import backend.merchants.Category;
import backend.merchants.Choice;
import backend.merchants.Item;
import backend.merchants.Merchant;
import backend.merchants.Option;
import backend.merchants.pharmacy.Pharmacy;
import backend.merchants.pharmacy.PharmacyItem;
import backend.merchants.restaurant.Restaurant;
import backend.merchants.restaurant.RestaurantItem;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "merchantApi",
        version = "v1",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE}
)
public class MerchantApi {


    /**
     * todo change return types to wrappers after testing
     */

    @ApiMethod(name = "getMerchantById")
    public Merchant getMerchantById(@Named("merchantId") Long merchantId){
        return Merchant.getMerchantByID(merchantId);
    }


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


    @ApiMethod(name = "createOption")
    public Option createOption(@Named("name") String name,
                               @Named("required") boolean required,
                               @Named("description") String description) {

        Option option = new Option(name, required, description);
        option.saveOption();
        return option;
    }

    @ApiMethod(name = "createChoice")
    public Choice createChoice(@Named("name") String name,
                               @Named("addedPrice") double addedPrice,
                               @Named("description") String description) {

        Choice choice = new Choice(name, addedPrice, description);
        choice.saveChoice();
        return choice;
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


    @ApiMethod(name = "addOptionToItem")
    public Item addOptionTotItem(@Named("itemID") Long itemID,
                                 @Named("optionID") Long optionID) {

        Item item = Item.getItemByID(itemID);
        item.addOption(optionID);
        return item;
    }


    @ApiMethod(name = "addChoiceToOption")
    public Option addChoiceToOption(@Named("optionID") Long optionID,
                                    @Named("choiceID") Long choiceID) {

        Option option = Option.getOptionByID(optionID);
        option.addChoice(choiceID);
        return option;
    }


    @ApiMethod(name = "getItemsOfCategoryByID")
    public List<Item> getItemsOfCategoryByID(@Named("categoryID") Long categoryID) {
        return Category.getCategoryByID(categoryID).getItems();
    }


    @ApiMethod(name = "getDeliveryRequestByID")
    public DeliveryRequest getDeliveryRequestByID(@Named("deliveryRequestID") Long deliveryRequestID) {
        return DeliveryRequest.getDeliveryRequestByID(deliveryRequestID);
    }


    @ApiMethod(name = "merchantAcceptsDeliveryRequest")
    public DeliveryRequest merchantAcceptsDeliveryRequest(@Named("deliveryRequestID") Long deliveryRequestID) {

        DeliveryRequest deliveryRequest = DeliveryRequest.getDeliveryRequestByID(deliveryRequestID);
        final Queue queue = QueueFactory.getQueue("driverQueue");
        queue.add(TaskOptions.Builder.withUrl("//GetTheNearestDriverServlet").
                param("deliveryRequestId",deliveryRequest.toString()));
        return deliveryRequest;
    }

    public void signUpWithFirebase() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
    }

    // testing methods
    //===========================================================================


    @ApiMethod(name = "createRandomMerchants")
    public List<Merchant> createRandomMerchants()  {
        final Queue queue = QueueFactory.getQueue("createMerchantsQueue");
        queue.add(TaskOptions.Builder.withUrl("/GenerateTestDataServlet"));
        return null;
    }

    @ApiMethod(name = "createTestEntity")
    public TestEntity createTestEntity(@Named("name") String name){
        Date date1 = new Date();
        System.out.println(date1);
        TestEntity testEntity = new TestEntity(name,date1);
        testEntity.saveTest();
        return testEntity;
    }

}
