package backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

import backend.deliveryRequests.DeliveryRequest;
import backend.helpers.Constants;
import backend.profiles.driver.Driver;

/**
 * Created by Muhammad on 19/08/2017.
 */
@Api(name = "orderAPI",
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
}
