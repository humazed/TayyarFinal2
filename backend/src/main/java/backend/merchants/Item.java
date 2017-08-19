package backend.merchants;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Muhammad on 24/07/2017.
 */
@Entity
public abstract class Item {
    @Id
    public Long id;
    public String name;
    @Index
    public double basePrice;//without extras, minimum of all required Options like size etc

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public List<Key<Option>> options = new ArrayList<>();
    public String imageURL;
    public String description;
    @Index
    public boolean available = true;

    //default constructor for Entity initalization
    public Item() {
    }

    public Item(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    public static Item getItemByID(Long id) {
        return ofy().load().type(Item.class).id(id).now();
    }

    public void saveItem() {
        ofy().save().entity(this).now();
    }
    
    public void addOption(Long optionID) {
        Key<Option> optionKey = Key.create(Option.class, optionID);
        this.options.add(optionKey);
        ofy().save().entity(this).now();// save changes in this Item
    }
    public List<Option> getOptions() {
        List<Option> categories = new ArrayList<>();
        for (Key<Option> optionKey : this.options) {
            Option option = ofy().load().key(optionKey).now();
            categories.add(option);
        }
        /*todo try another approach by passing all keys at once,
         notice that the query, isn't executed until .now()*/
        return categories;
    }
}
