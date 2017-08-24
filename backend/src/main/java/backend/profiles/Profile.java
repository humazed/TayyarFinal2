package backend.profiles;

import backend.general.Location;
import backend.general.Notifiable;
import backend.general.Review;
import backend.merchants.Merchant;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Muhammad on 25/07/2017.
 */
@Entity
public abstract class Profile implements Notifiable{
    @Id
    public Long id;
    public String name;
    //  public String password; check for 3rd party authentication
    public String email;
    public String phone;
    public String wifiMac;
    public String deviceMac;
    public String imageURl;
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public List<Key<Review>> reviews = new ArrayList<>();
    public Location currentLocation;

    //default constructor for Entity initialization
    public Profile() {
    }
    //============

    public void saveProfile() {
        ofy().save().entity(this).now();
    }
    //to be used with signUp, updating profile info etc

    public static Profile getProfileByID(Long id) {
        return ofy().load().type(Profile.class).id(id).now();
    }


    public Profile(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public void addRegToken(String regToken) {
        this.regTokenList.add(regToken);
        saveProfile();
    }

    @Override
    public void removeRegToken(String regToken) {
        this.regTokenList.add(regToken);
        saveProfile();
    }

    @Override
    public List<String> getRegTokenList() {
        return this.regTokenList;
    }
}
