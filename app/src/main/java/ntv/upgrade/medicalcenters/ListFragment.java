package ntv.upgrade.medicalcenters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import ntv.upgrade.medicalcenters.models.MedicalCenter;
import ntv.upgrade.medicalcenters.models.Place;

/**
 * This fragment holds a list of the basic details of medical centers around
 * the device location sorted by distance.
 * <p/>
 * Created by Paulino Gomez on 1/10/2016.
 */
public class ListFragment extends Fragment {

    // Used to size the images in the mobile app so they can animate cleanly from list to detail
    public static final int IMAGE_ANIM_MULTIPLIER = 2;
    public static DatabaseReference mDatabaseRef;
    public static StorageReference mStorageRef;
    // For log purposes
    private final String TAG = ListFragment.class.getSimpleName();
    // to communicate with the base activity
    private OnListFragmentInteractionListener mListener;
    private FirebaseRecyclerAdapter mAdapter;

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
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ListRecyclerView recyclerView =
                (ListRecyclerView) rootView.findViewById(R.id.list_recyclerView);
        recyclerView.setEmptyView(rootView.findViewById(android.R.id.empty));
        recyclerView.setHasFixedSize(true);

        // Write a message to the database
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();


        mAdapter = new FirebaseRecyclerAdapter<MedicalCenter, ViewHolder>(
                MedicalCenter.class, R.layout.list_item, ViewHolder.class, mDatabaseRef.child("MedicalCenters").getRef()) {
            @Override
            public void populateViewHolder(ViewHolder viewHolder, MedicalCenter medicalCenter, int position) {
                Place place = new Place(1001, medicalCenter.getName(),
                        medicalCenter.getEmail(),
                        medicalCenter.getLatitude(),
                        medicalCenter.getLongitude(),
                        medicalCenter.getPhone());
                mListener.onPlaceAdded(place);
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
            mListener = (OnListFragmentInteractionListener) getActivity();
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
    public interface OnListFragmentInteractionListener {
        void onPlaceAdded(Place place);
    }
    /**
     * View Holder of each item on the list
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        // Load a larger size image to make the activity transition to the detail screen smooth
        int mImageSize = 120 * IMAGE_ANIM_MULTIPLIER;

        // Constructor
        public ViewHolder(View view) {
            super(view);
            mView = view;
        }

        public void setImageURL(final Context context, final String imageURL) {
            final ImageView mImageView = (ImageView) mView.findViewById(R.id.item_image);
            File localFile = null;
            try {
                localFile = File.createTempFile("images", "jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            mStorageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Glide.with(context)
                                    .load(imageURL)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .placeholder(R.color.lighter_gray)
                                    .override(mImageSize, mImageSize)
                                    .into(mImageView);
                        }
                    });
        }

        public void setName(String name) {
            TextView mOverlayTextView = (TextView) mView.findViewById(R.id.item_name);
            mOverlayTextView.setText(name);
        }

        public void setPhone(String phone) {
            TextView mOverlayTextView = (TextView) mView.findViewById(R.id.item_phone);
            mOverlayTextView.setText(phone);
        }

        public void setMCID(String MCID) {

        }

        public void setLatitude(String latitude) {
        }

        public void setLongitude(String longitude) {
        }

        public void setEmail(String email) {
            TextView mOverlayTextView = (TextView) mView.findViewById(R.id.item_email);
            mOverlayTextView.setText(email);
        }

    }
}
