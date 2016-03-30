package ntv.upgrade.medicalcenters;

import java.text.SimpleDateFormat;

/**
 * Created by Paulino Gomez on 1/10/2016.
 */
public class Constants {

    public static final int GOOGLE_API_CLIENT_TIMEOUT_S = 10; // 10 seconds
    public static final String GOOGLE_API_CLIENT_ERROR_MSG =
            "Failed to connect to GoogleApiClient (error code = %d)";
    // Used to size the images in the mobile app so they can animate cleanly from list to detail
    public static final int IMAGE_ANIM_MULTIPLIER = 2;
    // Resize images sent to Wear to 400x400px
    public static final int NOTIFICATION_IMAGE_SIZE = 400;
    // Max # of attractions to show at once
    public static final int MAX_ATTRACTIONS = 4;

    // Maps values
    public static final String MAPS_INTENT_URI = "geo:0,0?q=";
    public static final String MAPS_NAVIGATION_INTENT_URI = "google.navigation:mode=w&q=";
    public static final String[] ACTIONS = {"http://schemas.google.com/AddActivity",
            "http://schemas.google.com/ReviewActivity"};
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("'on' MMM d yyyy");

}
