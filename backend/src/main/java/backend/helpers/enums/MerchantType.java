package backend.helpers.enums;

import backend.merchants.Merchant;
import backend.merchants.pharmacy.Pharmacy;
import backend.merchants.restaurant.Restaurant;
import backend.merchants.superMarket.SuperMarket;
import backend.profiles.Profile;

/**
 * Created by Muhammad on 19/08/2017.
 */

public enum MerchantType {
    RESTAURANT(Restaurant.class),PHARMACY(Pharmacy.class),SUPERMARKET(SuperMarket.class);

    private final Class<? extends Merchant> aClass;

    private MerchantType(Class<? extends Merchant> aClass) {
        this.aClass = aClass;
    }

    public Class<? extends Merchant> getMarked() {
        return aClass;
    }
}
