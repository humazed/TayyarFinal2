package backend.general;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.cmd.Query;

import java.util.List;

import backend.profiles.Profile;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Muhammad on 16/08/2017.
 */
@Entity
public class BlackListedProfile {
    @Id
    Long id;
    @Index
    Long profileId;
    @Index
    String wifiMac;
    @Index
    String deviceMac;
    @Index
    String email;
    @Index
    String phone;
    @Index
    List<String> regTokens;
    String whyBlackListed;

    public BlackListedProfile(Profile profile) {
        profileId = profile.id;
        wifiMac = profile.wifiMac;
        deviceMac = profile.deviceMac;
        email = profile.email;
        phone = profile.phone;
        regTokens = profile.regTokenList;
    }

    public static boolean isInBlackList(BlackListedProfile blackListedProfile) {
        Query<BlackListedProfile> query = ofy().load().type(BlackListedProfile.class);
        boolean isIn;
        int count = 0;
        count += query.filter("profileId =", blackListedProfile.profileId).count();
        count += query.filter("wifiMac =", blackListedProfile.wifiMac).count();
        count += query.filter("deviceMac =", blackListedProfile.deviceMac).count();
        count += query.filter("email =", blackListedProfile.email).count();
        count += query.filter("phone =", blackListedProfile.phone).count();
        for (String regToken : blackListedProfile.regTokens) {
            count += query.filter("regToken =", regToken).count();
        }
        isIn = count > 0;
        return isIn;
    }


    public static boolean isInBlackList(Profile profile) {
        BlackListedProfile blackListedProfile = new BlackListedProfile(profile);
        return isInBlackList(blackListedProfile);
    }

    public void putInBlackList(Profile profile, String whyBlackListed) {
        if (isInBlackList(profile))
            return;
        else {
            BlackListedProfile blackListedProfile = new BlackListedProfile(profile);
            blackListedProfile.whyBlackListed= whyBlackListed;
            ofy().save().entity(blackListedProfile).now();
        }
    }

}
