package backend.apis;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import java.io.IOException;
import java.util.List;

import backend.deliveryRequests.DeliveryRequest;
import backend.helpers.Constants;
import backend.helpers.FireBaseHelper;
import backend.merchants.Merchant;
import backend.profiles.customer.Customer;
import backend.profiles.driver.Driver;

/**
 * Created by Muhammad on 19/08/2017.
 */
@Api(name = "driverApi",
        version = "v1",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE}
)
public class DriverApi {
    private static DriverApi driverApiInstance;

    public DriverApi() {
    }

    public static DriverApi getApiSingleton() {
        if (driverApiInstance == null) {
            driverApiInstance = new DriverApi();
            return driverApiInstance;
        }
        return driverApiInstance;
    }

    @ApiMethod(name = "driverRefusesDelivery")
    public DeliveryRequest driverRefusesDelivery(@Named("deliveryRequestID") Long deliveryRequestID,
                                                 @Named("driverID") Long driverID) {
        DeliveryRequest deliveryRequest = DeliveryRequest.getDeliveryRequestByID(deliveryRequestID);
        deliveryRequest.addDriverWhoRefused(driverID);
        Driver.getDriverByID(driverID).changeDriverState(true);

        /*
        * redirect to other driver
        * don't call a method here to do that, it might stall the driver who refused app
        * */

        return deliveryRequest;
    }

    @ApiMethod(name = "createDriver")
    public Driver createDriver(@Named("name") String name,
                               @Named("email") String email,
                               @Named("phone") String phone,
                               @Named("vehicle") String vehicle,
                               @Named("imageURL") String imageURL
    ) {
        Driver driver = new Driver(name, email, phone, vehicle, imageURL);
        driver.saveProfile();
        return driver;
    }


    @ApiMethod(name = "driverRefuseDeliveryRequest")
    public DeliveryRequest driverRefuseDeliveryRequest(@Named("deliveryRequestId") Long deliveryRequestId,
                                                       @Named("driverId") Long driverId) {
        DeliveryRequest deliveryRequest = DeliveryRequest.getDeliveryRequestByID(deliveryRequestId);
        deliveryRequest.addDriverWhoRefused(driverId);
        deliveryRequest.save();


        // redirect to another Driver
        final Queue queue = QueueFactory.getQueue("driverQueue");
        queue.add(TaskOptions.Builder.withUrl("//GetTheNearestDriverServlet").
                param("deliveryRequestId", deliveryRequest.toString()));
        return deliveryRequest;

    }

    @ApiMethod(name = "driverAcceptsDeliveryRequest")
    public DeliveryRequest driverAcceptsDeliveryRequest(@Named("deliveryRequestId") Long deliveryRequestId,
                                                        @Named("driverId") Long driverId) {
        DeliveryRequest deliveryRequest = DeliveryRequest.getDeliveryRequestByID(deliveryRequestId);
        deliveryRequest.driverAcceptsOrder = true;
        deliveryRequest.driverId = driverId;
        //some info about time etc
        deliveryRequest.save();
        List<String> merchantRegTokens = Merchant.getMerchantByID(deliveryRequest.merchantId).getRegTokenList();
        List<String> customerRegTokens = Customer.getProfileByID(deliveryRequest.customerId).getRegTokenList();
        try {
            FireBaseHelper.sendNotification(merchantRegTokens, "driver is on the way");
            FireBaseHelper.sendNotification(customerRegTokens, "driver is on the way");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deliveryRequest;
    }

    @ApiMethod(name = "driverConfirmsPickUp")
    public DeliveryRequest driverConfirmsPickUp(@Named("deliveryRequestId") Long deliveryRequestId) {

        DeliveryRequest deliveryRequest = DeliveryRequest.getDeliveryRequestByID(deliveryRequestId);
        deliveryRequest.driverConfirmedPickUP = true;
        deliveryRequest.save();
        return deliveryRequest;
    }

    @ApiMethod(name = "driverDeliveredOrder")
    public DeliveryRequest driverDeliveredOrder(@Named("deliveryRequestId") Long deliveryRequestId) throws IOException {
        DeliveryRequest deliveryRequest = DeliveryRequest.getDeliveryRequestByID(deliveryRequestId);
        deliveryRequest.orderDelivered = true;
        // Todo calculate charge here using distance and timeSpent
        deliveryRequest.charge = 10;
        List<String> merchantRegTokens = Merchant.getMerchantByID(deliveryRequest.merchantId).getRegTokenList();
        List<String> customerRegTokens = Customer.getProfileByID(deliveryRequest.customerId).getRegTokenList();
        FireBaseHelper.sendNotification(customerRegTokens, "charge =" + deliveryRequest.charge);
        //think of more suitable message for the merchant
        FireBaseHelper.sendNotification(merchantRegTokens, "charge =" + deliveryRequest.charge);
        deliveryRequest.save();
        return deliveryRequest;
    }
}
