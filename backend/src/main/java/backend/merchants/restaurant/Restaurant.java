package backend.merchants.restaurant;

import backend.merchants.Merchant;
import com.googlecode.objectify.annotation.Subclass;

/**
 * Created by Muhammad on 24/07/2017.
 */
@Subclass(index = true)
public class Restaurant extends Merchant {

    //default constructor for Entity initalization
    public Restaurant() {
    }

    public Restaurant(String name, String email, String phone, String imageURL) {
        super(name, email, phone, imageURL);
    }
}
