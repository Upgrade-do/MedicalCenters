package ntv.upgrade.medicalcenters;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.IOException;
import java.util.List;

import ntv.upgrade.medicalcenters.csvtools.Reader;
import ntv.upgrade.medicalcenters.models.MedicalCenter;

/**
 * Created by Paulino Gomez on 1/9/2016.
 */
public class MedicalCentersApplication extends Application {

    private static final String TAG = MedicalCentersApplication.class.getSimpleName();
    // List of sites
    private List<MedicalCenter> mMedicalCenters;
    private GoogleSignInAccount mCurrentUserAccount;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();

        AssetManager assetManager = getAssets();

        try {
            mMedicalCenters = Reader.readAndInsert(assetManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void initSingletons() {
        Log.i(TAG, "Initializing Singleton Classes");
    }

    public GoogleSignInAccount getUserAccount() {
        return mCurrentUserAccount;
    }

    public void setUserAccount(GoogleSignInAccount userAccount) {
        mCurrentUserAccount = userAccount;
    }

    public List<MedicalCenter> getMedicalCenters() {
        return mMedicalCenters;
    }
}
