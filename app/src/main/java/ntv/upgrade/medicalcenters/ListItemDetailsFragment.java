package ntv.upgrade.medicalcenters;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.model.LatLng;

import ntv.upgrade.medicalcenters.models.MedicalCenter;
import ntv.upgrade.medicalcenters.utils.MapUtils;

/**
 *
 * The tourist attraction detail fragment which contains the details of a
 * a single attraction
 *
 * Created by Paulino Gomez on 1/15/2016.
 */
public class ListItemDetailsFragment extends Fragment{

    // Maps values
    public static final String MAPS_INTENT_URI = "geo:0,0?q=";
    // Used to size the images in the mobile app so they can animate cleanly from list to detail
    public static final int IMAGE_ANIM_MULTIPLIER = 2;
    private static final String EXTRA_MEDICAL_CENTER = "medical_center";
    private MedicalCenter mMedicalCenter;

    public ListItemDetailsFragment() {
    }

    public static ListItemDetailsFragment createInstance(String itemName) {
        ListItemDetailsFragment detailFragment = new ListItemDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MEDICAL_CENTER, itemName);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.content_item_details, container, false);
        String itemName = getArguments().getString(EXTRA_MEDICAL_CENTER);
        mMedicalCenter = findMedicalCenterByName(itemName);

        if (mMedicalCenter == null) {
            getActivity().finish();
            return null;
        }

        TextView nameTextView = (TextView) view.findViewById(R.id.tittleTextView);
        TextView distanceTextView = (TextView) view.findViewById(R.id.distanceTextView);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        FloatingActionButton mapFab = (FloatingActionButton) view.findViewById(R.id.mapFab);

        LatLng location = MapUtils.getLocation(getActivity());

        String distance = MapUtils.formatDistanceBetween(location, new LatLng(
                        Double.valueOf(mMedicalCenter.getLatitude()),
                        Double.valueOf(mMedicalCenter.getLongitude())));

        if (TextUtils.isEmpty(distance)) {
            distanceTextView.setVisibility(View.GONE);
        }

        nameTextView.setText(itemName);
        distanceTextView.setText(distance);
        //descTextView.setText(mMedicalCenter.getImageURL());

        int imageSize = getResources().getDimensionPixelSize(R.dimen.image_size) * IMAGE_ANIM_MULTIPLIER;
        Glide.with(getActivity())
                .load(mMedicalCenter.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.color.lighter_gray)
                .override(imageSize, imageSize)
                .into(imageView);

        mapFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(MAPS_INTENT_URI +
                        Uri.encode(mMedicalCenter.getName() + ", " + mMedicalCenter.getMCID())));
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Some small additions to handle "up" navigation correctly
                Intent upIntent = NavUtils.getParentActivityIntent(getActivity());
                upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                // Check if up activity needs to be created (usually when
                // detail screen is opened from a notification or from the
                // Wearable app
                if (NavUtils.shouldUpRecreateTask(getActivity(), upIntent)
                        || getActivity().isTaskRoot()) {

                    // Synthesize parent stack
                    TaskStackBuilder.create(getActivity())
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // On Lollipop+ we finish so to run the nice animation
                    getActivity().finishAfterTransition();
                    return true;
                }

                // Otherwise let the system handle navigating "up"
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Really hacky loop for finding attraction in our static content provider.
     * Obviously would not be used in a production app.
     */
    private MedicalCenter findMedicalCenterByName(String itemName) {

                    return ListMapActivity.mMedicalCenter;

    }
}