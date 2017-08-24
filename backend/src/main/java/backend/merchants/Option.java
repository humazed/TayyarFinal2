package backend.merchants;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Muhammad Saeed on 2/11/2017.
 */
@Entity
@Cache

public class Option {

    @Id
    public Long id;
    public String name;//size , additions etc
    public boolean required;
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public List<Key<Choice>> choices = new ArrayList<>();

    public String description; //ingredients, etc,

    //default constructor for Entity initialization
    public Option() {
    }

    public Option(String name, boolean required, String description) {
        this.name = name;
        this.required = required;
        this.description = description;
    }

    public static Option getOptionByID(Long id) {
        return ofy().load().type(Option.class).id(id).now();
    }
    public void saveOption() {
        ofy().save().entity(this).now();
    }

    public void addChoice(Long choiceID) {
        Key<Choice> choiceKey = Key.create(Choice.class, choiceID);
        this.choices.add(choiceKey);
        ofy().save().entity(this).now();// save changes in this Merchant
    }

    public List<Choice> getChoices() {
        List<Choice> categories = new ArrayList<>();
        for (Key<Choice> choiceKey : this.choices) {
            Choice choice = ofy().load().key(choiceKey).now();
            categories.add(choice);
        }
        /*todo try another approach by passing all keys at once,
         notice that the query, isn't executed until .now()*/
        return categories;
    }
//============
}
