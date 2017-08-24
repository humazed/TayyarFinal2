package backend.merchants.pharmacy;

import backend.merchants.Merchant;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Subclass;

/**
 * Created by Muhammad on 24/07/2017.
 */
@Subclass(index = true)
@Cache
public class Pharmacy extends Merchant {

    //default constructor for Entity initalization
    public Pharmacy(){}

    public Pharmacy(String name, String email, String phone, String imageURL) {
        super(name, email, phone, imageURL);
    }
}
