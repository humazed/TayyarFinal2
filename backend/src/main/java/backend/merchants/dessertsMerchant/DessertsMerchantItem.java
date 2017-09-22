package backend.merchants.dessertsMerchant;

import com.googlecode.objectify.annotation.Subclass;

import backend.merchants.Item;

/**
 * Created by Muhammad on 19/08/2017.
 */

@Subclass(index = true)
public class DessertsMerchantItem extends Item{
    //default constructor for Entity initialization
    public DessertsMerchantItem(){}
    //============

    public DessertsMerchantItem(String name, double basePrice) {
        super(name, basePrice);
    }
}
