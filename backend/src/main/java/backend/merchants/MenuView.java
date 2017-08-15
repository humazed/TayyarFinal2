package backend.merchants;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Saeed on 3/24/2017.
 */

public class MenuView {
    List<MenuElement> menuElements = new ArrayList<>();

    //internal uses
    public MenuView(Merchant merchant) {
        List<Category> categoryList = merchant.getCategories();
        for (Category category : categoryList) {
            this.menuElements.add(new MenuElement(category.id, category.name,
                    category.imageURL));
        }
    }

    //for the endpoint
    public MenuView(Long merchantID) {
        Merchant merchant =  Merchant.getMerchantByID(merchantID);
        new MenuView(merchant);
    }



}
