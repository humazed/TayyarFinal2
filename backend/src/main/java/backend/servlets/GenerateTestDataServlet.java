package backend.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.apis.MerchantApi;
import backend.merchants.Category;
import backend.merchants.Choice;
import backend.merchants.Item;
import backend.merchants.Merchant;
import backend.merchants.Option;
import backend.merchants.pharmacy.Pharmacy;
import backend.merchants.pharmacy.PharmacyItem;
import backend.merchants.restaurant.Restaurant;
import backend.merchants.restaurant.RestaurantItem;
import backend.merchants.superMarket.SuperMarket;
import backend.merchants.superMarket.SuperMarketItem;

/**
 * Created by Muhammad on 19/08/2017.
 */

public class GenerateTestDataServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        createMerchants();
        resp.getWriter().write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    void createMerchants() {
        List<Merchant> merchantList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Restaurant restaurant = new Restaurant(i + " Restaurant " + i, "@", "010", "151aaa");
            Pharmacy pharmacy = new Pharmacy(i + " Pharmacy " + i, "@", "010", "151aaa");
            SuperMarket superMarket = new SuperMarket(i + " SuperMarket " + i, "@", "010", "151aaa");
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
                Category category = new Category(j + " " + String.valueOf((char) ((int) 'a' + j)), "bla", "imageURL");
                category.saveCategory();
                merchant.addCategory(category.id);
                for (int k = 0; k < 6; k++) {
                    Item item;
                    if (merchant instanceof Restaurant) {
                        item = new RestaurantItem(k + " " + String.valueOf((char) ((int) 'a' + j)) + " Restaurant", Math.random() * 200);
                    } else if (merchant instanceof SuperMarket) {
                        item = new SuperMarketItem(k + " " + String.valueOf((char) ((int) 'a' + j)) + " SuperMarket", Math.random() * 200);
                    } else {
                        item = new PharmacyItem(k + " " + String.valueOf((char) ((int) 'a' + j)) + " Pharmacy", Math.random() * 200);
                    }
                    item.saveItem();
                    for (int l = 0; l < 2; l++) {
                        Option option = new Option(item.name + " " + l + " " + String.valueOf((char) ((int) 'a' + j)) + " option", false, "bla bla bla");
                        option.saveOption();
                        item.addOption(option.id);
                        for (int m = 0; m < 2; m++) {
                            Choice choice = new Choice(l + " " + String.valueOf((char) ((int) 'a' + j)) + " Choice", Math.random() * 50, "bla bla");
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
    }
}
