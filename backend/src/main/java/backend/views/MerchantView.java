package backend.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import backend.general.Viewable;
import backend.merchants.Merchant;

/**
 * Created by Muhammad Saeed on 3/10/2017.
 */
public class MerchantView implements Viewable{
    public String name;
    public Long merchantID;
    public String imageURL;
    public int pricing;
    public int rating;
    public boolean active;

    public MerchantView(String name, Long merchantID, String imageURL) {
        this.name = name;
        this.merchantID = merchantID;
        this.imageURL = imageURL;
    }

    public MerchantView(Merchant merchant) {
        this.name = merchant.name;
        this.merchantID = merchant.id;
        this.imageURL = merchant.imageURL;
        this.active = merchant.active;
        this.pricing = merchant.pricing;
        this.rating = merchant.rating;
        //delivery estimate
    }
    public static List<MerchantView> getListOfMerchantsViews(List<? extends Merchant> list){
        List<MerchantView> merchantViews = new ArrayList<>();
        for (Merchant merchant : list) {
            merchantViews.add(new MerchantView(merchant));
        }

        return merchantViews;
    }
}
