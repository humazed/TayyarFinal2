package backend;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Muhammad on 26/08/2017.
 */
@Entity
public class TestEntity {
    @Id
    Long id;
    @Index
    String name;
    @Index
    Date date;
    @Index
    GeoPt geoPt;

    public TestEntity(String name,Date date, GeoPt geoPt) {

        this.name = name;
        this.date = date;
        this.geoPt = geoPt;

    }

    public TestEntity(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public static TestEntity getTestEntityById(Long id){
        return ofy().load().type(TestEntity.class).id(id).now();
    }
    public void saveTest(){
        ofy().save().entity(this).now();
    }
}
