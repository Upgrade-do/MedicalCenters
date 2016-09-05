package ntv.upgrade.medicalcenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.maps.model.LatLng;

import ntv.upgrade.medicalcenters.models.MedicalCenter;

/**
 * This fragment holds a list of the basic details of medical centers around
 * the device location sorted by distance.
 * <p/>
 * Created by Paulino Gomez on 1/10/2016.
 */
public class ListFragment extends Fragment {

    // For log purposes
    private final String TAG = ListFragment.class.getSimpleName();

    // to communicate with the base activity
    private OnFragmentInteractionListener mListener;

    private FirebaseRecyclerAdapter mAdapter;

    private LatLng mLatestLocation;

    private boolean mItemClicked;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location =
                    intent.getParcelableExtra(FusedLocationProviderApi.KEY_LOCATION_CHANGED);
            if (location != null) {
                mLatestLocation = new LatLng(location.getLatitude(), location.getLongitude());
                // mAdapter.mAttractionList = loadAttractionsFromLocation(mLatestLocation);
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    /**
     * Mandatory empty constructor
     */
    public ListFragment() {
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mItemClicked = false;
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mBroadcastReceiver, UtilityService.getLocationUpdatedIntentFilter());
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        mLatestLocation = Utils.getLocation(getActivity());

        ListRecyclerView recyclerView =
                (ListRecyclerView) rootView.findViewById(R.id.list_recyclerView);
        recyclerView.setEmptyView(rootView.findViewById(android.R.id.empty));
        recyclerView.setHasFixedSize(true);

       // List<MedicalCenter> medicalCenters = loadMedicalCentersFromLocation(mLatestLocation);

        mAdapter = new FirebaseRecyclerAdapter<MedicalCenter, ViewHolder>(MedicalCenter.class, R.layout.list_item, ViewHolder.class, ListMapActivity.mDatabase) {
            @Override
            public void populateViewHolder(ViewHolder viewHolder, MedicalCenter medicalCenter, int position) {
                viewHolder.setImageURL(getContext(), medicalCenter.getImageURL());
                viewHolder.setName(medicalCenter.getName());
                viewHolder.setPhone(medicalCenter.getPhone());
                viewHolder.setEmail(medicalCenter.getEmail());
            }
        };
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * On list item click listener
     */
    interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * This interface allows an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String id);
    }

    /**
     * View Holder of each item on the list
     */
    private static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        // Load a larger size image to make the activity transition to the detail screen smooth
        int mImageSize = 120 * Constants.IMAGE_ANIM_MULTIPLIER;
        // Constructor
        public ViewHolder(View view) {
            super(view);
            mView = view;
        }

        public void setImageURL(Context context, Bitmap imageURL){
            ImageView mImageView = (ImageView) mView.findViewById(R.id.item_image);

            Glide.with(context)
                    .load(imageURL)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.color.lighter_gray)
                    .override(mImageSize, mImageSize)
                    .into(mImageView);
        }
        public void setName(String name){
            TextView mOverlayTextView = (TextView) mView.findViewById(R.id.item_name);
           // mOverlayTextView.setText(name);
        }
        public void setPhone(String phone){
            TextView mOverlayTextView = (TextView) mView.findViewById(R.id.item_phone);
          //  mOverlayTextView.setText(phone);
        }
        public void setMCID(String MCID){

        }
        public void setLatitude(String latitude){
        }
        public void setLongitude(String longitude){
        }
        public void setEmail(String email){
            TextView mOverlayTextView = (TextView) mView.findViewById(R.id.item_email);
          //  mOverlayTextView.setText(email);
        }

    }
}
