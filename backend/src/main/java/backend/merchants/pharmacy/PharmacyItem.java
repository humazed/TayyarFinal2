package backend.merchants.pharmacy;

import com.googlecode.objectify.annotation.Subclass;

import backend.merchants.Item;

/**
 * Created by Muhammad on 25/07/2017.
 */
//enum type{pills,drops,etc}
@Subclass(index = true)

public class PharmacyItem extends Item {
    boolean requiresPrescription =false;

    public PharmacyItem() {
    }

    public PharmacyItem(String name, double basePrice) {
        super(name, basePrice);
    }
}
