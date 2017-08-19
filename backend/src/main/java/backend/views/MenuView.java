package backend.views;

import java.util.ArrayList;
import java.util.List;

import backend.merchants.Category;
import backend.merchants.Merchant;
/**
 * Created by Muhammad Saeed on 3/24/2017.
 */

public class MenuView {
    public List<MenuElement> menuElements = new ArrayList<>();

    //internal uses
    public MenuView(Merchant merchant) {
        List<Category> categoryList = merchant.getCategories();
        for (Category category : categoryList) {
            MenuElement menuElement = new MenuElement(category.id, category.name,
                    category.imageURL);
            this.menuElements.add(menuElement);
        }
    }

    //for the endpoint
    public MenuView(Long merchantID) {
        Merchant merchant = Merchant.getMerchantByID(merchantID);
        new MenuView(merchant);
    }


}
