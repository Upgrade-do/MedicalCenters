package ntv.upgrade.medicalcenters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Shows detail a single Item.
 *
 * Created by Paulino Gomez on 1/15/2016.
 */
public class ListItemDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_MCID = "mcid";

    public static Intent getLaunchIntent(Context context, String medicalCenter) {
        Intent intent = new Intent(context, ListItemDetailsActivity.class);
        intent.putExtra(EXTRA_MCID, medicalCenter);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        String medicalCenter = getIntent().getStringExtra(EXTRA_MCID);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_item_details, ListItemDetailsFragment.createInstance(medicalCenter))
                    .commit();
        }
    }
}
