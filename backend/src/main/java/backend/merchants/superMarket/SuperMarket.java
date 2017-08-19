package backend.merchants.superMarket;

import com.googlecode.objectify.annotation.Subclass;

import backend.merchants.Merchant;

/**
 * Created by Muhammad on 19/08/2017.
 */
@Subclass(index = true)
public class SuperMarket extends Merchant {

    //default constructor for Entity initialization
    public SuperMarket (){}
    //============

    public SuperMarket(String name, String email, String phone, String imageURL) {
        super(name, email, phone, imageURL);
    }
}
