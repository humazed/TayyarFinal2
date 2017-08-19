package backend.merchants.superMarket;

import com.googlecode.objectify.annotation.Subclass;

import backend.merchants.Item;

/**
 * Created by Muhammad on 19/08/2017.
 */

@Subclass(index = true)
public class SuperMarketItem extends Item{
    //default constructor for Entity initialization
    public SuperMarketItem (){}
    //============

    public SuperMarketItem(String name, double basePrice) {
        super(name, basePrice);
    }
}
