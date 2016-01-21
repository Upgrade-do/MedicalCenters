package ntv.upgrade.medicalcenters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Shows detail a single Item.
 *
 * Created by Paulino Gomez on 1/15/2016.
 */
public class ListItemDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_MEDICAL_CENTER = "medical_center";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void launch(Activity activity, String medicalCenter, View heroView) {
        Intent intent = getLaunchIntent(activity, medicalCenter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, heroView, heroView.getTransitionName());
            ActivityCompat.startActivity(activity, intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    public static Intent getLaunchIntent(Context context, String medicalCenter) {
        Intent intent = new Intent(context, ListItemDetailsActivity.class);
        intent.putExtra(EXTRA_MEDICAL_CENTER, medicalCenter);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        String medicalCenter = getIntent().getStringExtra(EXTRA_MEDICAL_CENTER);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_item_details, ListItemDetailsFragment.createInstance(medicalCenter))
                    .commit();
        }
    }
}
