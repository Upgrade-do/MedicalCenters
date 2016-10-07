package ntv.upgrade.medicalcenters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import ntv.upgrade.medicalcenters.adapters.ListAdapter;
import ntv.upgrade.medicalcenters.models.MedicalCenter;

/**
 * This fragment holds a list of the basic details of medical centers around
 * the device location sorted by distance.
 * <p/>
 * Created by Paulino Gomez on 1/10/2016.
 */
public class ListFragment extends Fragment implements ListAdapter.onItemChange{

    // For log purposes
    private static final String TAG = ListFragment.class.getSimpleName();

    // Firebase references
    public static DatabaseReference mDatabaseRef;
    public static StorageReference mStorageRef;

    private ListAdapter mAdapter;

    // to communicate with the base activity
    private OnListFragmentInteractionListener mListener;

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
        // Write a message to the database
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        ListRecyclerView recyclerView =
                (ListRecyclerView) rootView.findViewById(R.id.list_recyclerView);
        recyclerView.setEmptyView(rootView.findViewById(android.R.id.empty));
        recyclerView.setHasFixedSize(true);

        mAdapter = new ListAdapter(getContext(), mDatabaseRef, mStorageRef, this);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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

    @Override
    public void onMapClicked(MedicalCenter medicalCenter) {
        Toast.makeText(getContext(), "MAP" + medicalCenter.getMCID(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(MedicalCenter medicalCenter) {
        mListener.onItemClicked(medicalCenter);
    }

    /**
     * This interface allows an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that activity.
     */
    public interface OnListFragmentInteractionListener {
        void onItemClicked(MedicalCenter medicalCenter);
    }
}
