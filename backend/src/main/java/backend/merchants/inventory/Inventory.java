package backend.merchants.inventory;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Muhammad on 30/09/2017.
 */

@Entity
public class Inventory {
    @Id
    public Long id;
    @Index
    public List<String> merchantCategoriesNames = new ArrayList<>();

    @Index
    public List<String> itemsCategoriesNames = new ArrayList<>();

    //default constructor for Entity initialization
    public Inventory() {
    }
    //============



    public void addMerchantCategory(String category) {
        if (!this.merchantCategoriesNames.contains(category)) {
            this.merchantCategoriesNames.add(category);
            this.save();
        }

    }

    public void addItemCategory(String category) {
        if (!this.itemsCategoriesNames.contains(category)) {
            this.itemsCategoriesNames.add(category);
            this.save();
        }
    }


    public void save() {
        ofy().save().entity(this).now();
    }

    public static Inventory getInventoryCategoriesByID(Long id) {
        return ofy().load().type(Inventory.class).id(id).now();
    }

    public static Inventory getInventoryCategoriesByMerchantCategory(String category) {
        //throws "java.lang.reflect.UndeclaredThrowableException", if name doesn't match any
        return ofy().load().type(Inventory.class).
                filter("merchantCategoriesNames =", category).list().get(0);
    }

    public static Inventory getInventoryCategoriesByItemCategory(String category) {
        //throws "java.lang.reflect.UndeclaredThrowableException", if name doesn't match any
        return ofy().load().type(Inventory.class).
                filter("itemsCategoriesNames =", category).list().get(0);
    }

}
