package ntv.upgrade.medicalcenters.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntv.upgrade.medicalcenters.R;
import ntv.upgrade.medicalcenters.models.MedicalCenter;

/**
 * Created by Paulino on 10/1/2016.
 */

public class ListAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.list_item)
    View mView;
    @BindView(R.id.item_image)
    ImageView mImageView;
    @BindView(R.id.item_name)
    TextView mName;
    @BindView(R.id.item_email)
    TextView mEmail;
    @BindView(R.id.item_phone)
    TextView mPhone;
    @BindView(R.id.item_map)
    ImageView mMap;

    MedicalCenter mMedicalCenter = null;

    private onItemClickedListener mListener;

    public ListAdapterHolder(View v, onItemClickedListener listener) {
        super(v);
        //butter knife bind
        ButterKnife.bind(this, v);

        mListener = listener;

        mMap.setOnClickListener(this);
        mView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id){
            case R.id.item_map:
                mListener.onMapClicked(mMedicalCenter);
                break;
            case R.id.list_item:
                mListener.onItemClicked(mMedicalCenter);
                break;
        }

    }

    public interface onItemClickedListener {
        void onMapClicked(MedicalCenter medicalCenter);
        void onItemClicked(MedicalCenter medicalCenter);
    }
}