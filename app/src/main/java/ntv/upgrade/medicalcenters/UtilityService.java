package ntv.upgrade.medicalcenters;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.concurrent.TimeUnit;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;

/**
 * Created by Paulino Gomez on 1/10/2016.
 */
public class UtilityService extends IntentService {

    public static final int GOOGLE_API_CLIENT_TIMEOUT_SECONDS = 10; // 10 seconds
    public static final String GOOGLE_API_CLIENT_ERROR_MSG =
            "Failed to connect to GoogleApiClient (error code = %d)";
    //FOR LOG PURPOSES
    private static final String TAG = UtilityService.class.getSimpleName();
    private static final String ACTION_LOCATION_UPDATED = "location_updated";
    private static final String ACTION_REQUEST_LOCATION = "request_location";
    private static final String ACTION_FAKE_UPDATE = "fake_update";


    public UtilityService() {
        super(TAG);
    }

    public static IntentFilter getLocationUpdatedIntentFilter() {
        return new IntentFilter(UtilityService.ACTION_LOCATION_UPDATED);
    }

    public static void requestLocation(Context context) {
        Intent intent = new Intent(context, UtilityService.class);
        intent.setAction(UtilityService.ACTION_REQUEST_LOCATION);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent != null ? intent.getAction() : null;

        switch (action) {
            case ACTION_REQUEST_LOCATION:
                requestDeviceLocation();
                break;
            case ACTION_LOCATION_UPDATED:
                locationUpdated(intent);
                break;
            case ACTION_FAKE_UPDATE:
                // LatLng currentLocation = MapUtils.getLocation(this);
                //TouristAttractions.getClosestCity(currentLocation);
                break;
        }
    }

    /**
     * Called when a location update is requested
     */
    private void requestDeviceLocation() {
        Log.v(TAG, ACTION_REQUEST_LOCATION);

        if (!MapUtils.checkFineLocationPermission(this)) {
            return;
        }

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();

        // It's OK to use blockingConnect() here as we are running in an
        // IntentService that executes work on a separate (background) thread.
        ConnectionResult connectionResult = googleApiClient.blockingConnect(
                GOOGLE_API_CLIENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);

        if (connectionResult.isSuccess() && googleApiClient.isConnected()) {

            Intent locationUpdatedIntent = new Intent(this, UtilityService.class);
            locationUpdatedIntent.setAction(ACTION_LOCATION_UPDATED);

            // Send last known location out first if available
            Location location = FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                Intent lastLocationIntent = new Intent(locationUpdatedIntent);
                lastLocationIntent.putExtra(
                        FusedLocationProviderApi.KEY_LOCATION_CHANGED, location);
                startService(lastLocationIntent);
            }

            // Request new location
            LocationRequest mLocationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            FusedLocationApi.requestLocationUpdates(
                    googleApiClient, mLocationRequest,
                    PendingIntent.getService(this, 0, locationUpdatedIntent, 0));

            googleApiClient.disconnect();
        } else {
            Log.e(TAG, String.format(GOOGLE_API_CLIENT_ERROR_MSG,
                    connectionResult.getErrorCode()));
        }
    }

    /**
     * Called when the location has been updated
     */
    private void locationUpdated(Intent intent) {
        Log.v(TAG, ACTION_LOCATION_UPDATED);

        // Extra new location
        Location location =
                intent.getParcelableExtra(FusedLocationProviderApi.KEY_LOCATION_CHANGED);

        if (location != null) {
            LatLng latLngLocation = new LatLng(location.getLatitude(), location.getLongitude());

            // Store in a local preference as well
            MapUtils.storeLocation(this, latLngLocation);

            // Send a local broadcast so if an Activity is open it can respond
            // to the updated location
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }
}
