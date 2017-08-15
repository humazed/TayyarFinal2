package backend.merchants;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

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

}
