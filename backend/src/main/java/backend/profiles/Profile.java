package backend.profiles;

import backend.general.Location;
import backend.general.Review;
import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad on 25/07/2017.
 */
@Entity
public abstract class Profile {
    @Id
    public Long id;
    public String name;
    //  public String password; check for 3rd party authentication
    public String email;
    public List<String> regTokenList = new ArrayList<>();
    public String phone;
    public String imageURl;
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public List<Key<Review>> reviews = new ArrayList<>();
    public Location currentLocation;
    //default constructor for Entity initialization
    public Profile (){}
    //============

    public void saveProfile() {
        ObjectifyService.ofy().save().entity(this).now();
    }
    //to be used with signUp, updating profile info etc


    public Profile(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void removeRegToken(String regToken){
        this.regTokenList.remove(regToken);
        saveProfile();
    }

}
