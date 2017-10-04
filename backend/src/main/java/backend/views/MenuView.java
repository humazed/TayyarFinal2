package backend.views;

import java.util.ArrayList;
import java.util.List;

import backend.general.Viewable;
import backend.merchants.MerchantCategory;
import backend.merchants.Merchant;
/**
 * Created by Muhammad Saeed on 3/24/2017.
 */

public class MenuView implements Viewable{
    public List<MenuElement> menuElements = new ArrayList<>();

    //internal uses
    public MenuView(Merchant merchant) {
        List<MerchantCategory> merchantCategoryList = merchant.getMenuCategories();
        for (MerchantCategory merchantCategory : merchantCategoryList) {
            MenuElement menuElement = new MenuElement(merchantCategory.id, merchantCategory.name,
                    merchantCategory.imageURL);
            this.menuElements.add(menuElement);
        }
    }

    //for the endpoint
    public MenuView(Long merchantID) {
        Merchant merchant = Merchant.getMerchantByID(merchantID);
        new MenuView(merchant);
    }


}
