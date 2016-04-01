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
    // List of sites
    public static List<MedicalCenter> mMedicalCenters = new ArrayList<>();
    private static GoogleApiClient mGoogleApiClient = null;
    private GoogleSignInAccount mCurrentUserAccount;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
    }

    protected void initSingletons() {
        Log.i(TAG, "Initializing Singleton Classes");
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void setGoogleApiClient(GoogleApiClient aGoogleApiClient) {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = aGoogleApiClient;
        }
    }

    public GoogleSignInAccount getUserAccount() {
        return mCurrentUserAccount;
    }

    public void setUserAccount(GoogleSignInAccount userAccount) {
        mCurrentUserAccount = userAccount;
    }
}
