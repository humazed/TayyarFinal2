package backend.profiles.driver;


import backend.general.Location;
import backend.merchants.Merchant;
import backend.profiles.Profile;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Muhammad Saeed on 2/11/2017.
 */
@Subclass(index = true)
public class Driver extends Profile {

    public String vehicle;
    public double balance = 0;
    public int rate;

    public String imageURL;
    @Index
    public boolean idle;

    //default constructor for Entity initialization
    public Driver() {
    }

    public Driver(String name, String email, String phone, String vehicle, String imageURL) {
        super(name, email, phone);
        this.vehicle = vehicle;
        this.imageURL = imageURL;
    }

    public void changeDriverState(boolean idle) {
        this.idle = idle;
        saveProfile();
    }

    public static Driver getDriverByID(Long id) {
        return ofy().load().type(Driver.class).id(id).now();
    }
    //============


}
