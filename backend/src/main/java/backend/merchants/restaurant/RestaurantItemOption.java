package backend.merchants.restaurant;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Muhammad Saeed on 2/11/2017.
 */
@Entity
public class RestaurantItemOption {

    @Id
    public Long id;
    public String name;//size , spicy etc
    public boolean required;
    public double addedPrice;

    /* final price = basePrice+addedPrice

       ex: Kushari

        size options: "required"                     large 20LE, medium 15LE and small 10LE
        additions options: "not required"            meet balls 5LE

            basePrice = 10 LE i.e the minimum of all required Options

                                    and small RestaurantItemOption addedPrice  = 0
                                    medium RestaurantItemOption addedPrice = 5LE
                                    large  RestaurantItemOption addedPrice = 10LE

                                    meet balls option addedPrice =5LE
    */

    public String description; //ingredients, etc,

    public boolean available;

    //default constructor for Entity initialization
    public RestaurantItemOption() {
    }

    public RestaurantItemOption(String name, boolean required, double addedPrice, String description, boolean available) {
        this.name = name;
        this.required = required;
        this.addedPrice = addedPrice;
        this.description = description;
        this.available = available;
    }

    public static RestaurantItemOption getOptionByID(Long id) {
        return ofy().load().type(RestaurantItemOption.class).id(id).now();
    }
    public void saveOption() {
        ofy().save().entity(this).now();
    }

//============
}
