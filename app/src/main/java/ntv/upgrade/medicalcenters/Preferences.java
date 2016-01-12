package ntv.upgrade.medicalcenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Paulino Gomez on 1/10/2016.
 */
public class Preferences {

    public static String getPreferredMapStyle(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(
                context.getString(R.string.pref_key_map_style),
                context.getString(R.string.pref_default_map_style));
    }
}
