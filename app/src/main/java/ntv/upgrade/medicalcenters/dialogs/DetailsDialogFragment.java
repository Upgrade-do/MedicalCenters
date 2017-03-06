package ntv.upgrade.medicalcenters.dialogs;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ntv.upgrade.medicalcenters.ListMapActivity;
import ntv.upgrade.medicalcenters.R;
import ntv.upgrade.medicalcenters.models.MedicalCenter;

/**
 * Created by Paulino on 10/4/2016.
 */

public class DetailsDialogFragment extends DialogFragment
        implements View.OnClickListener{

    //TAG
    private static final String TAG = DetailsDialogFragment.class.getSimpleName();

    private static final String EXTRA_MEDICAL_CENTER = "medical_center";

    private String mItemName;

    private EditText mComment;
    private MedicalCenter mMedicalCenter;

    private OnCommentsInteractionListener mListener;

    public static DetailsDialogFragment newInstance(String itemName) {
        DetailsDialogFragment fragment = new DetailsDialogFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_MEDICAL_CENTER, itemName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemName = getArguments().getString(EXTRA_MEDICAL_CENTER);
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);

        mMedicalCenter = ListMapActivity.mMedicalCenter;

/*        mComment = (EditText) view.findViewById(R.id.commentToSave);
        ImageButton save = (ImageButton) view.findViewById(R.id.saveCommentButton);
        ImageButton clear = (ImageButton) view.findViewById(R.id.cleanCommentButton);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.preSavedComments);
        recyclerView.setAdapter(new CommentAdapter(this, mItem));

        if (mItem.hasComment()) {
            mComment.setText(mItem.getComment().getCommentText());
        }
        save.setOnClickListener(this);
        clear.setOnClickListener(this);*/

        return view;
    }


    @Override
    public void onClick(View view) {
  /*      switch (view.getId()){
            case R.id.saveCommentButton:
                if (!mComment.getText().toString().equals("")){
                    Comment c = new Comment(
                            mItem.getSectionId(),
                            mItem.getCategoryId(),
                            mItem.getItemId(),
                            mComment.getText().toString());

                    mItem.setComment(c);
                    mListener.commentSaved(mItem.getSectionId());
                }else {
                    mItem.clearComment();
                }

                dismiss();
                break;
            case R.id.cleanCommentButton:
                mComment.setText("");
                mItem.clearComment();
                mListener.commentSaved(mItem.getSectionId());
                break;
        }*/
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCommentsInteractionListener) {
            mListener = (OnCommentsInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface OnCommentsInteractionListener {
        void commentSaved(int section);
    }
}