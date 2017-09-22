package backend.merchants.specialMerchant;

import com.googlecode.objectify.annotation.Subclass;

import backend.merchants.Item;

/**
 * Created by Muhammad on 19/08/2017.
 */

@Subclass(index = true)
public class SpecialMerchantItem extends Item{
    //default constructor for Entity initialization
    public SpecialMerchantItem(){}
    //============

    public SpecialMerchantItem(String name, double basePrice) {
        super(name, basePrice);
    }
}
