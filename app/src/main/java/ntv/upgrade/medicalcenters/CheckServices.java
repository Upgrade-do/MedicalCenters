package ntv.upgrade.medicalcenters;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by Paulino Gomez on 1/10/2016.
 */
public class CheckServices {

    public static boolean isGoogleMapsInstalled(final Activity activity) {
        try {
            activity.getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
