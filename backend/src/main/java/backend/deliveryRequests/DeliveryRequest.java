package backend.deliveryRequests;

import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backend.helpers.OfyHelper;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Muhammad on 16/08/2017.
 */
@Entity
public class DeliveryRequest {
    @Id
    public Long id;
    public Long customerId;
    public Long driverId;
    public Long merchantId;
    //location info
    public boolean merchantAcceptsOrder = false;
    public boolean driverAcceptsOrder = false;
    public boolean driverConfirmedPickUP = false;
    public boolean orderDelivered = false;
    public List<Long> driversWhoRefusedIDs = new ArrayList<>();
    public Map<Integer, Long> order = new HashMap<>();
    public String instructions;

    public DeliveryRequest(Long customerId, Long merchantId,
                           Map<Integer, Long> order, String instructions) {
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.order = order;
        this.instructions = instructions;
    }

    public void save() {
        ofy().save().entity(this).now();
    }

    public static DeliveryRequest getDeliveryRequestByID(Long deliveryRequestID) {
        Key<DeliveryRequest> deliveryRequestKey = Key.create(DeliveryRequest.class, deliveryRequestID);
        return ObjectifyService.ofy().load().key(deliveryRequestKey).now();
    }

    public void addDriverWhoRefused(Long id) {
        this.driversWhoRefusedIDs.add(id);
        save();
    }
}
