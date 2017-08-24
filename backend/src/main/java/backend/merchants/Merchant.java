package backend.merchants;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.api.server.spi.config.Named;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.*;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;

import backend.general.Location;
import backend.general.Notifiable;
import backend.general.Review;
import backend.general.Viewable;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Muhammad on 24/07/2017.
 */
@Entity
@Cache
public abstract class Merchant implements Notifiable, Viewable {
    @Id
    public Long id;
    @Index
    public String name;
    public String email;
    public String phone;
    @Index
    public String city;
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public List<Key<Review>> reviews = new ArrayList<Key<Review>>();
    public Location location;
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public List<Key<Category>> categories = new ArrayList<Key<Category>>();
    public String imageURL;
    @Index
    public int pricing;
    @Index
    public int rating;
    @Index
    public boolean active;


    //default constructor for Entity initialization
    public Merchant() {}

    public static Merchant getMerchantByID(Long id) {
        return ofy().load().type(Merchant.class).id(id).now();
    }

    public Merchant(String name, String email, String phone, String imageURL) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.imageURL = imageURL;
    }

    public void saveMerchant() {
        ofy().save().entity(this).now();
    }

    public void addCategory(Long categoryID) {
        Key<Category> categoryKey = Key.create(Category.class, categoryID);
        this.categories.add(categoryKey);
        saveMerchant();// save changes in this Merchant
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        for (Key<Category> categoryKey : this.categories) {
            Category category = ofy().load().key(categoryKey).now();
            categories.add(category);
        }
        /*todo try another approach by passing all keys at once,
         notice that the query, isn't executed until .now()*/
        return categories;
    }
    public static List<Merchant> getMerchantByName(@Named("name") String name) {
        Query<Merchant> query = ObjectifyService.ofy().load().type(Merchant.class).
                filter("name =", name).order("pricing");
        return query.list();
    }

    @Override
    public void addRegToken(String regToken) {
        this.regTokenList.add(regToken);
        saveMerchant();
    }

    @Override
    public void removeRegToken(String regToken) {
        this.regTokenList.remove(regToken);
        saveMerchant();
    }

    @Override
    public List<String> getRegTokenList() {
        return this.regTokenList;
    }
}
