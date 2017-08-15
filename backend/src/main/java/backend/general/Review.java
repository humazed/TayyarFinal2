package backend.general;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * User: YourPc
 * Date: 3/14/2017
 */

@Entity
public class Review {
    @Id
    public Long id;
    public Long reviewerID;
    public Long revieweeID;
    public String body;
    public int rating;

    //default constructor for Entity initalization
    public Review(){}
    
}
