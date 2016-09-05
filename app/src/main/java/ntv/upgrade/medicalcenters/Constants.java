package ntv.upgrade.medicalcenters;

/**
 * Created by Paulino Gomez on 1/10/2016.
 */
public class Constants {

    public static final int GOOGLE_API_CLIENT_TIMEOUT_SECONDS = 10; // 10 seconds
    public static final String GOOGLE_API_CLIENT_ERROR_MSG =
            "Failed to connect to GoogleApiClient (error code = %d)";
    // Used to size the images in the mobile app so they can animate cleanly from list to detail
    public static final int IMAGE_ANIM_MULTIPLIER = 2;

    // Maps values
    public static final String MAPS_INTENT_URI = "geo:0,0?q=";
}
