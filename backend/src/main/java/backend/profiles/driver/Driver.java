package backend.profiles.driver;


import backend.general.Location;
import backend.profiles.Profile;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;

/**
 * Created by Muhammad Saeed on 2/11/2017.
 */
@Subclass(index = true)
public class Driver extends Profile{

    public String vehicle;
    public double balance=0;
    public int rate;

     public String imageURL;
    @Index
    public boolean idle;

    //default constructor for Entity initialization
    public Driver (){}

    public Driver(String name, String email, String phone, String vehicle, String imageURL) {
        super(name, email, phone);
        this.vehicle = vehicle;
        this.imageURL = imageURL;
    }

    //============


}
