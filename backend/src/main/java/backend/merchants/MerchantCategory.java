package backend.merchants;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

import backend.general.Viewable;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Muhammad Saeed on 2/11/2017.
 */
@Entity
@Cache
public class MerchantCategory implements Viewable {
    @Id
    public Long id;
    @Index
    public String name;
    public String description;
    public String imageURL;


    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    // can't be used in the client side, to do so use Transformer
    public List<Key<Item>> items = new ArrayList<Key<Item>>();

    //default constructor for Entity initalization
    public MerchantCategory() {
    }

    public MerchantCategory(String name, String description, String imageURL) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
    }

    public void saveCategory() {
        ofy().save().entity(this).now();
    }

    public static MerchantCategory getCategoryByID(Long id) {
        return ofy().load().type(MerchantCategory.class).id(id).now();
    }

    public void addItem(Long itemID) {
        Key<Item> itemKey = Key.create(Item.class, itemID);
        this.items.add(itemKey);//add item key to this category
        ObjectifyService.ofy().save().entity(this).now();// save changes in this category
    }


    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        for (Key<Item> itemKey : this.items) {
            Item item = ofy().load().key(itemKey).now();
            items.add(item);
        }
        /*todo try another approach by passing all keys at once,
         notice that the query, isn't executed until .now()*/
        return items;
    }
}
