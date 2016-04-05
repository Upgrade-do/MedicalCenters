package ntv.upgrade.medicalcenters;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

import ntv.upgrade.medicalcenters.models.MedicalCenter;

/**
 * Created by Paulino Gomez on 1/9/2016.
 */
public class MedicalCentersApplication extends Application {

    private static final String TAG = MedicalCentersApplication.class.getSimpleName();

    // List of sites
    private List<MedicalCenter> mMedicalCenters;
    // TODO: 4/4/2016 switch to User model as soon as posible
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

    public GoogleSignInAccount getUserAccount() {
        return mCurrentUserAccount;
    }

    public void setUserAccount(GoogleSignInAccount userAccount) {
        mCurrentUserAccount = userAccount;
    }

    public List<MedicalCenter> getMedicalCenters() {
        return mMedicalCenters;
    }

    public void setMedicalCenters(List<MedicalCenter> mMedicalCenters) {
        this.mMedicalCenters = mMedicalCenters;
    }
}
