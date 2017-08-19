package backend.views;

import backend.general.Location;
import backend.profiles.Profile;

/**
 * Created by Muhammad on 25/07/2017.
 */

public class ProfileView {
    public Long id;
    public String name;
    public String email;
    public String phone;
    public String imageURl;
    public Location currentLocation;

    public ProfileView(Long id, String name, String email,
                       String phone, String imageURl, Location currentLocation) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.imageURl = imageURl;
        this.currentLocation = currentLocation;
    }

    public ProfileView(Profile profile) {
        this.id = profile.id;
        this.name = profile.name;
        this.email = profile.email;
        this.phone = profile.phone;
        this.imageURl = profile.imageURl;
        this.currentLocation = profile.currentLocation;

    }
}
