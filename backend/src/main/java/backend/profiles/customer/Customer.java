package backend.profiles.customer;

import backend.profiles.Profile;

import com.googlecode.objectify.annotation.Subclass;

/**
 * Created by Muhammad Saeed on 2/9/2017.
 */
@Subclass(index = true)
public class Customer extends Profile {
    public String mainAddress;
    public int numberOfOrders =0;
    public double totalAmountOfMoneySpent =0;

    //default constructor for Entity initialization
    public Customer (){}

    public Customer(String name, String email, String phone, String mainAddress) {
        super(name, email, phone);
        this.mainAddress = mainAddress;
    }
    //============

}

