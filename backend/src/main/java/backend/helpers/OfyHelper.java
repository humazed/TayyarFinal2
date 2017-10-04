package backend.helpers;

import backend.TestEntity;
import backend.general.Review;
import backend.merchants.MerchantCategory;
import backend.merchants.Choice;
import backend.merchants.Item;
import backend.merchants.Merchant;
import backend.merchants.dessertsMerchant.DessertsMerchant;
import backend.merchants.inventory.Inventory;
import backend.merchants.pharmacy.Pharmacy;
import backend.merchants.pharmacy.PharmacyItem;
import backend.merchants.Option;
import backend.merchants.restaurant.Restaurant;
import backend.merchants.restaurant.RestaurantItem;
import backend.merchants.specialMerchant.SpecialMerchant;
import backend.merchants.superMarket.SuperMarket;
import backend.merchants.superMarket.SuperMarketItem;
import backend.profiles.Profile;
import backend.profiles.customer.Customer;
import backend.profiles.driver.Driver;

import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Muhammad on 24/07/2017.
 */

public class OfyHelper implements ServletContextListener {
    static {
        System.out.println("aaaaaaaaaaaaaaaaaaaaawala walaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("aaaaaaaaaaaaaaaaaaaaawala walaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("aaaaaaaaaaaaaaaaaaaaawala walaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        ObjectifyService.register(Merchant.class);
        ObjectifyService.register(Restaurant.class);
        ObjectifyService.register(Pharmacy.class);
        ObjectifyService.register(SuperMarket.class);

        ObjectifyService.register(MerchantCategory.class);

        ObjectifyService.register(Item.class);
        ObjectifyService.register(RestaurantItem.class);
        ObjectifyService.register(PharmacyItem.class);
        ObjectifyService.register(SpecialMerchant.class);
        ObjectifyService.register(SuperMarketItem.class);
        ObjectifyService.register(DessertsMerchant.class);

        ObjectifyService.register(Option.class);
        ObjectifyService.register(Choice.class);

        ObjectifyService.register(Review.class);

        ObjectifyService.register(Profile.class);
        ObjectifyService.register(Customer.class);
        ObjectifyService.register(Driver.class);
        ObjectifyService.register(Inventory.class);
        ObjectifyService.register(TestEntity.class);

    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
