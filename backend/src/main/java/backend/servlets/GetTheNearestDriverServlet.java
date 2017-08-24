package backend.servlets;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.deliveryRequests.DeliveryRequest;
import backend.helpers.FireBaseHelper;
import backend.merchants.Merchant;
import backend.profiles.driver.Driver;

/**
 * Created by Muhammad on 24/08/2017.
 */

public class GetTheNearestDriverServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deliveryRequestId = req.getParameter("deliveryRequestId");
        Long id = Long.parseLong(deliveryRequestId);
        getTheNearestDriver(id);
        System.out.println("done and done");
    }

    DeliveryRequest getTheNearestDriver(Long deliveryRequestID) {
        DeliveryRequest deliveryRequest = DeliveryRequest.getDeliveryRequestByID(deliveryRequestID);
        deliveryRequest.merchantAcceptsOrder = true;
        String city = Merchant.getMerchantByID(deliveryRequest.merchantId).
                location.city;
        /*
        * no city field in the driver class yet
        * this is where you use google maps API
        * */

        // this query might cause problem for filtering two different properties, check query constraints
        Query<Driver> driverQuery = ObjectifyService.ofy().load().type(Driver.class).filter("city =", city)
                .filter("idle =", true);

        List<Driver> driverList = driverQuery.list();
        List<Long> driverIDs = new ArrayList<>();
        //getting list of all active drivers' IDs
        for (Driver driver : driverList) {
            driverIDs.add(driver.id);
        }
        List<Long> driversWhoRefusedIDs = deliveryRequest.driversWhoRefusedIDs;
        //filtering out drivers who refused
        for (Long id : driversWhoRefusedIDs) {
            driverIDs.remove(id);
        }
        try {
            //sort according to some criteria
            Long driverID = driverIDs.get(0);
            Driver driver = Driver.getDriverByID(driverID);
            deliveryRequest.driverId = driverID;
            deliveryRequest.save();
            FireBaseHelper.sendNotification(driver.getRegTokenList(), String.valueOf(deliveryRequest.id));
            return deliveryRequest;
        } catch (Exception e) {
            return null;
        }
    }
}
