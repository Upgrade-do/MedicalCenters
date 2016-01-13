package ntv.upgrade.medicalcenters;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import ntv.upgrade.medicalcenters.entities.MedicalCenter;

/**
 * Created by Paulino Gomez on 1/9/2016.
 */
public class MedicalCentersApplication extends Application {

    private static final String TAG = MedicalCentersApplication.class.getSimpleName();

    private static GoogleApiClient mGoogleApiClient = null;
    private GoogleSignInAccount mCurrentUserAccount;

    // List of sites
    public static List<MedicalCenter> mMedicalCenters = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
    }

    /**
     * Custom method to initialize Singleton Classes
     */
    protected void initSingletons() {
        Log.i(TAG, "Initializing Singleton Classes");
    }

    /**
     * @return Current user of the application
     */
    public GoogleSignInAccount getUserAccount() {
        return mCurrentUserAccount;
    }

    /**
     * Sets the current user who is currently using the application
     *
     * @param userAccount is the user to be set.
     */
    public void setUserAccount(GoogleSignInAccount userAccount) {
        mCurrentUserAccount = userAccount;
    }

    /**
     * @return Google Api Client used throughout the application
     */
    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    /**
     * Creates the Google Api Client.
     * @param aGoogleApiClient to be used.
     */
    public void setGoogleApiClient(GoogleApiClient aGoogleApiClient) {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = aGoogleApiClient;
        }
    }
}
