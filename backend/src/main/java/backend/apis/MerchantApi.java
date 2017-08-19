/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package backend.apis;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.api.server.spi.config.Named;

import backend.deliveryRequests.DeliveryRequest;
import backend.helpers.Constants;
import backend.merchants.Choice;
import backend.merchants.superMarket.SuperMarket;
import backend.merchants.superMarket.SuperMarketItem;
import backend.views.MerchantView;
import backend.helpers.CursorHelper;
import backend.helpers.FireBaseHelper;
import backend.merchants.Category;
import backend.merchants.Item;
import backend.views.MenuView;
import backend.merchants.Merchant;
import backend.merchants.pharmacy.Pharmacy;
import backend.merchants.pharmacy.PharmacyItem;
import backend.merchants.Option;
import backend.merchants.restaurant.Restaurant;
import backend.merchants.restaurant.RestaurantItem;
import backend.profiles.customer.Customer;
import backend.profiles.driver.Driver;

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


    // testing methods
    //===========================================================================
    @ApiMethod(name = "createRandomMerchants")
    public List<Merchant> createRandomMerchants()  {
        List<Merchant> merchantList = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            Restaurant restaurant = new Restaurant(i + " Restaurant " + i, "@", "010", "151aaa");
            Pharmacy pharmacy = new Pharmacy(i + " Pharmacy " + i, "@", "010", "151aaa");
            SuperMarket superMarket = new SuperMarket(i + " SuperMarket "+ i, "@", "010", "151aaa");
            restaurant.saveMerchant();
            pharmacy.saveMerchant();
            superMarket.saveMerchant();
            merchantList.add(restaurant);
            merchantList.add(pharmacy);
            merchantList.add(superMarket);
        }
        for (Merchant merchant : merchantList) {
            merchant.pricing = (int) (Math.random() * 10);
            merchant.addRegToken("regToken Holder");
            for (int j = 0; j < 4; j++) {
                Category category = new Category(j+" "+String.valueOf((char) ((int) 'a' + j)), "bla", "imageURL");
                category.saveCategory();
                merchant.addCategory(category.id);
                for (int k = 0; k < 6; k++) {
                    Item item;
                    if (merchant instanceof Restaurant) {
                        item = new RestaurantItem(k+" "+String.valueOf((char) ((int) 'a' + j))+" Restaurant", Math.random() * 200);
                    }
                    else if (merchant instanceof SuperMarket){
                        item = new SuperMarketItem(k+" "+String.valueOf((char) ((int) 'a' + j))+" SuperMarket", Math.random() * 200);
                    }
                    else {
                        item = new PharmacyItem(k+" "+String.valueOf((char) ((int) 'a' + j))+" Pharmacy", Math.random() * 200);
                    }
                    item.saveItem();
                    for (int l = 0; l <2 ; l++) {
                        Option option = new Option(item.name+" "+l+" "+String.valueOf((char) ((int) 'a' + j))+" option",false,"bla bla bla");
                        option.saveOption();
                        item.addOption(option.id);
                        for (int m = 0; m < 2; m++) {
                            Choice choice = new Choice(l+" "+String.valueOf((char) ((int) 'a' + j))+" Choice",Math.random()*50,"bla bla");
                            choice.saveChoice();
                            option.addChoice(choice.id);
                        }
                        option.saveOption();
                    }
                    item.saveItem();
                    category.addItem(item.id);
                }
                category.saveCategory();
                merchant.saveMerchant();
            }
        }
        return merchantList;
    }
}
