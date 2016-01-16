package ntv.upgrade.medicalcenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ntv.upgrade.medicalcenters.entities.MedicalCenter;

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

    private ListAdapter mAdapter;

    private LatLng mLatestLocation;

    private int mImageSize;
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

    private static List<MedicalCenter> loadMedicalCentersFromLocation(final LatLng curLatLng) {
        if (ListMapActivity.mMedicalCentersApplication.mMedicalCenters != null) {
            List<MedicalCenter> medicalCenters = ListMapActivity.mMedicalCentersApplication.mMedicalCenters;
            if (curLatLng != null) {
                Collections.sort(medicalCenters,
                        new Comparator<MedicalCenter>() {
                            @Override
                            public int compare(MedicalCenter lhs, MedicalCenter rhs) {
                                double lhsDistance = SphericalUtil.computeDistanceBetween(
                                        lhs.getGEOLOCATION(), curLatLng);
                                double rhsDistance = SphericalUtil.computeDistanceBetween(
                                        rhs.getGEOLOCATION(), curLatLng);
                                return (int) (lhsDistance - rhsDistance);
                            }
                        }
                );
            }
            return medicalCenters;
        }
        return null;
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

        mLatestLocation = Utils.getLocation(getActivity());

        // Load a larger size image to make the activity transition to the detail screen smooth
        mImageSize = getResources().getDimensionPixelSize(R.dimen.image_size)
                * Constants.IMAGE_ANIM_MULTIPLIER;

        List<MedicalCenter> medicalCenters = loadMedicalCentersFromLocation(mLatestLocation);

        mAdapter = new ListAdapter(getActivity(), medicalCenters);

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListRecyclerView recyclerView =
                (ListRecyclerView) view.findViewById(R.id.list_recyclerView);
        recyclerView.setEmptyView(view.findViewById(android.R.id.empty));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        return view;
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
     * Recycler View
     */
    private class ListAdapter extends RecyclerView.Adapter<ViewHolder>
            implements ItemClickListener {

        // This list will be loaded on the fragment
        public List<MedicalCenter> mMedicalCenters;

        // For binding purposes
        private Context mContext;

        // Constructor
        public ListAdapter(Context context, List<MedicalCenter> medicalCenters) {
            super();
            mContext = context;
            mMedicalCenters = medicalCenters;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view, this);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            // holds the item to bind
            MedicalCenter medicalCenter = mMedicalCenters.get(position);

            holder.mTitleTextView.setText(medicalCenter.getNAME());
            holder.mDescriptionTextView.setText(medicalCenter.getNAME());
            Glide.with(mContext)
                    .load(medicalCenter.getIMAGEURL())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.local_hospital_white_96x96)
                    .override(mImageSize, mImageSize)
                    .into(holder.mImageView);

            String distance =
                    Utils.formatDistanceBetween(mLatestLocation, medicalCenter.getGEOLOCATION());
            if (TextUtils.isEmpty(distance)) {
                holder.mOverlayTextView.setVisibility(View.GONE);
            } else {
                holder.mOverlayTextView.setVisibility(View.VISIBLE);
                holder.mOverlayTextView.setText(distance);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return mMedicalCenters == null ? 0 : mMedicalCenters.size();
        }

        @Override
        public void onItemClick(View view, int position) {
            if (!mItemClicked) {
                mItemClicked = true;
                View heroView = view.findViewById(android.R.id.icon);
                ListItemDetailsActivity.launch(
                        getActivity(), mAdapter.mMedicalCenters.get(position).getNAME(), heroView);
            }
        }
    }

    /**
     * View Holder of each item on the list
     */
    private static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // Member attributes
        TextView mTitleTextView;
        TextView mDescriptionTextView;
        TextView mOverlayTextView;
        ImageView mImageView;
        ItemClickListener mItemClickListener;

        // Constructor
        public ViewHolder(View view, ItemClickListener itemClickListener) {
            super(view);
            mTitleTextView = (TextView) view.findViewById(R.id.poi_tittle);
            mDescriptionTextView = (TextView) view.findViewById(R.id.poi_description);
            mOverlayTextView = (TextView) view.findViewById(R.id.poi_overlay_text);
            mImageView = (ImageView) view.findViewById(R.id.poi_image);
            mItemClickListener = itemClickListener;
            view.setOnClickListener(this);
        }

        // OnClick Listener
        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getAdapterPosition());
        }
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
}
