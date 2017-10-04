/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package backend.apis;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import backend.TestEntity;
import backend.deliveryRequests.DeliveryRequest;
import backend.general.Viewable;
import backend.helpers.Constants;
import backend.merchants.MerchantCategory;
import backend.merchants.Choice;
import backend.merchants.Item;
import backend.merchants.Merchant;
import backend.merchants.Option;
import backend.merchants.dessertsMerchant.DessertsMerchant;
import backend.merchants.dessertsMerchant.DessertsMerchantItem;
import backend.merchants.inventory.Inventory;
import backend.merchants.pharmacy.Pharmacy;
import backend.merchants.pharmacy.PharmacyItem;
import backend.merchants.restaurant.Restaurant;
import backend.merchants.restaurant.RestaurantItem;
import backend.merchants.specialMerchant.SpecialMerchant;
import backend.merchants.specialMerchant.SpecialMerchantItem;
import backend.merchants.superMarket.SuperMarket;
import backend.merchants.superMarket.SuperMarketItem;

import static com.googlecode.objectify.ObjectifyService.ofy;

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
    public Merchant getMerchantById(@Named("merchantId") Long merchantId) {
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

    @ApiMethod(name = "createSuperMarket")
    public SuperMarket createSuperMarket(@Named("name") String name, @Named("email") String email,
                                         @Named("phone") String phone, @Named("imageURL") String imageURL
    ) {
        SuperMarket superMarket = new SuperMarket(name, email, phone, imageURL);
        superMarket.saveMerchant();
        return superMarket;
    }

    @ApiMethod(name = "createDessertMerchant")
    public DessertsMerchant createDessertsMerchant(@Named("name") String name, @Named("email") String email,
                                                   @Named("phone") String phone, @Named("imageURL") String imageURL
    ) {
        DessertsMerchant dessertsMerchant = new DessertsMerchant(name, email, phone, imageURL);
        dessertsMerchant.saveMerchant();
        return dessertsMerchant;
    }

    @ApiMethod(name = "createSpecialMerchant")
    public SpecialMerchant createSpecialMerchant(@Named("name") String name, @Named("email") String email,
                                                 @Named("phone") String phone, @Named("imageURL") String imageURL
    ) {
        SpecialMerchant specialMerchant = new SpecialMerchant(name, email, phone, imageURL);
        specialMerchant.saveMerchant();
        return specialMerchant;
    }

    //==================================================

    @ApiMethod(name = "createCategory")
    public MerchantCategory createCategory(@Named("name") String name,
                                           @Named("description") String description,
                                           @Named("imageURL") String imageURL
    ) {
        MerchantCategory merchantCategory = new MerchantCategory(name, description, imageURL);
        merchantCategory.saveCategory();
        return merchantCategory;
    }

    //===========================================
    @ApiMethod(name = "createInventoryCategories")
    public Inventory createInventoryCategories() {
        Inventory inventory = new Inventory();
        inventory.save();
        return inventory;
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

    @ApiMethod(name = "createSuperMarketItem")
    public SuperMarketItem createSuperMarketItem(@Named("name") String name,
                                                 @Named("basePrice") double basePrice) {
        SuperMarketItem item = new SuperMarketItem(name, basePrice);
        item.saveItem();
        return item;
    }

    @ApiMethod(name = "createDessertsMerchantItem")
    public DessertsMerchantItem createDessertsMerchantItem(@Named("name") String name,
                                                           @Named("basePrice") double basePrice) {
        DessertsMerchantItem item = new DessertsMerchantItem(name, basePrice);
        item.saveItem();
        return item;
    }

    @ApiMethod(name = "createSpecialMerchantItem")
    public SpecialMerchantItem createSpecialMerchantItem(@Named("name") String name,
                                                         @Named("basePrice") double basePrice) {
        SpecialMerchantItem item = new SpecialMerchantItem(name, basePrice);
        item.saveItem();
        return item;
    }

    //=============================================

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


    @ApiMethod(name = "addMenuCategoryToMerchant")
    public Merchant addMenuCategoryToMerchant(@Named("merchantID") Long merchantID,
                                              @Named("categoryID") Long categoryID) {
        Merchant merchant = Merchant.getMerchantByID(merchantID);
        merchant.addMenuCategory(categoryID);
        return merchant;
    }

    @ApiMethod(name = "addListOfActualCategoriesToMerchantOrItem")
    public Viewable addListOfActualCategoriesToMerchantOrItem(@Named("merchantOrItemID") Long merchantOrItemID,
                                                        @Named("categoryName") String categoriesNamesCSV,
                                                        @Named("type") String type) {
        List<String> categories = Arrays.asList(categoriesNamesCSV.split(","));
        Viewable merchantOrItem = null;
        if (type.equalsIgnoreCase("m")) {
            Merchant merchant = Merchant.getMerchantByID(merchantOrItemID);
            merchant.addListOfActualCategoriesToMerchant(categories);
            merchantOrItem = merchant;
        }
        else if (type.equalsIgnoreCase("i")){
            Item item = Item.getItemByID(merchantOrItemID);
            item.addListOfActualCategoriesToItem(categories);
            merchantOrItem = item;
        }
        return merchantOrItem;
    }


    @ApiMethod(name = "addCategoryToInventoryCategories")
    public Inventory addCategoryToInventoryCategories(@Named("categoryName") String categoryName,
                                                      @Named("InventoryCategoriesID") Long invCatID,
                                                      @Named("type") String type) {
        Inventory inventory = Inventory.getInventoryCategoriesByID(invCatID);
        if (type.equalsIgnoreCase("m")) {
            inventory.addMerchantCategory(categoryName);
        } else if (type.equalsIgnoreCase("i")) {
            inventory.addItemCategory(categoryName);
        }
        return inventory;
    }

    @ApiMethod(name = "addItemToCategory")
    public MerchantCategory addItemToCategory(@Named("categoryID") Long categoryID, @Named("itemID") Long itemID) {
        MerchantCategory merchantCategory = MerchantCategory.getCategoryByID(categoryID);
        merchantCategory.addItem(itemID);
        return merchantCategory;
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
        return MerchantCategory.getCategoryByID(categoryID).getItems();
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
                param("deliveryRequestId", deliveryRequest.toString()));
        return deliveryRequest;
    }

    public void signUpWithFirebase() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
    }

    // testing methods
    //===========================================================================


    @ApiMethod(name = "createRandomMerchants")
    public List<Merchant> createRandomMerchants() {
        final Queue queue = QueueFactory.getQueue("createMerchantsQueue");
        queue.add(TaskOptions.Builder.withUrl("/GenerateTestDataServlet"));
        return null;
    }

    @ApiMethod(name = "createTestEntity")
    public TestEntity createTestEntity(@Named("name") String name) {
        TestEntity testEntity = new TestEntity(name);
        testEntity.saveTest();
        return testEntity;
    }

    @ApiMethod(name = "getTestEntityByCategories")
    public List<TestEntity> getTestEntityByCategories(@Named("category") String categoryName) {
        List<TestEntity> testEntities = ofy().load().type(TestEntity.class).filter("categories", categoryName).list();
        for (TestEntity entity : testEntities) {
            System.out.println(entity);
        }
        return testEntities;
    }
}