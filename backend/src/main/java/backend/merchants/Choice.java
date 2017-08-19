package backend.merchants;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Muhammad on 19/08/2017.
 */
@Entity
public class Choice {
    @Id
    Long id;
    public double addedPrice;
    public boolean available;
    /* final price = basePrice+addedPrice

       ex: Kushari

        size options: "required"                     large 20LE, medium 15LE and small 10LE
        additions options: "not required"            meet balls 5LE

            basePrice = 10 LE i.e the minimum of all required Options

                                    and small Option addedPrice  = 0
                                    medium Option addedPrice = 5LE
                                    large  Option addedPrice = 10LE

                                    meet balls option addedPrice =5LE
    */

    //default constructor for Entity initialization
    public Choice() {
    }
    //============

    public static Choice getChoiceByID(Long id) {
        return ofy().load().type(Choice.class).id(id).now();
    }

    public void saveChoice() {
        ofy().save().entity(this).now();
    }

}
