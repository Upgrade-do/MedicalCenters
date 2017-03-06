package ntv.upgrade.medicalcenters.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import ntv.upgrade.medicalcenters.R;
import ntv.upgrade.medicalcenters.models.MedicalCenter;

/**
 *
 * Created by Paulino on 9/28/2016.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapterHolder> implements ListAdapterHolder.onItemClickedListener{


    private static DatabaseReference mDatabaseRef;
    private static StorageReference mStorageRef;
    private final String TAG = ListAdapter.class.getSimpleName();
    private final Map<String, MedicalCenter> ITEM_MAP = new HashMap<>();
    public onItemChange
            mListener;

    private Context
            mContext;

    public ListAdapter(Context context, DatabaseReference dr, StorageReference sr, onItemChange listener) {
        this.mListener = listener;
        this.mContext = context;
        this.mDatabaseRef = dr;
        this.mStorageRef = sr;

        Query query = mDatabaseRef.child("MedicalCenters");
        query.addValueEventListener(new EventsListener());

    }

    private int addOrModify(MedicalCenter medicalCenter){

        for(Map.Entry<String, MedicalCenter> entry : ITEM_MAP.entrySet()) {
            if (medicalCenter.getMCID().equals(entry.getValue().getMCID())){
                entry.setValue(medicalCenter);
                return 1;
            }
        }
        ITEM_MAP.put(String.valueOf(ITEM_MAP.size()), medicalCenter);
        return 0;
    }

    @Override
    public ListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ListAdapterHolder(v, this);
    }

    public MedicalCenter getItemFromPosition(int position) {
        return ITEM_MAP.get(String.valueOf(position));
    }

    @Override
    public void onBindViewHolder(final ListAdapterHolder holder, int position) {

        //get medicalCenter
        MedicalCenter medicalCenter = getItemFromPosition(position);
        holder.mMedicalCenter = medicalCenter;

        mStorageRef.child("MedicalCenters/" + holder.mMedicalCenter.getImageURL())
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(mContext)
                        .load(uri)
                        .placeholder(R.drawable.hospseguro)
                        .into(holder.mImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        holder.mName.setText(holder.mMedicalCenter.getName());
        holder.mPhone.setText(holder.mMedicalCenter.getPhone());
        holder.mEmail.setText(holder.mMedicalCenter.getEmail());
    }

    @Override
    public int getItemCount() {
        return ITEM_MAP.size();
    }

    @Override
    public void onItemClicked(MedicalCenter medicalCenter) {
        mListener.onItemClicked(medicalCenter);
    }

    @Override
    public void onMapClicked(MedicalCenter medicalCenter) {
        mListener.onMapClicked(medicalCenter);
    }

    public interface onItemChange {
        void onMapClicked(MedicalCenter medicalCenter);
        void onItemClicked(MedicalCenter medicalCenter);
    }

    //firebase event listener
    private class EventsListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // whenever data at this location is updated.
            for (DataSnapshot mdSnapshot : dataSnapshot.getChildren()) {

                MedicalCenter medicalCenter = mdSnapshot.getValue(MedicalCenter.class);
                medicalCenter.setMCID(mdSnapshot.getKey());

                addOrModify(medicalCenter);

            }
            notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
