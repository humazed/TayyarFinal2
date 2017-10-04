package backend.merchants.dessertsMerchant;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Subclass;

import backend.merchants.Merchant;

/**
 * Created by Muhammad on 19/08/2017.
 */
@Subclass(index = true)
@Cache
public class DessertsMerchant extends Merchant {
    //default constructor for Entity initialization
    public DessertsMerchant() {
        this.browsable = true;

    }
    //============

    public DessertsMerchant(String name, String email, String phone, String imageURL) {
        super(name, email, phone, imageURL);
        this.browsable = true;
    }
}
