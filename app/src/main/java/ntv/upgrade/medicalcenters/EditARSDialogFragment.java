package ntv.upgrade.medicalcenters;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by Paulino on 9/12/2016.
 */

public class EditARSDialogFragment extends DialogFragment implements View.OnClickListener {

    //TAG
    private static final String TAG = EditARSDialogFragment.class.getSimpleName();

    private EditText
            ARS_NAME,
            ASSOCIATE_ID,
            CLIENT_NAME,
            PLAN;

    private OnEditARSDialogListener mListener;

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_ars, container, false);

        ARS_NAME = (EditText) view.findViewById(R.id.ars_name);
        ASSOCIATE_ID = (EditText) view.findViewById(R.id.ars_associate_id);
        CLIENT_NAME = (EditText) view.findViewById(R.id.ars_client_name);
        PLAN = (EditText) view.findViewById(R.id.ars_plan);

        ImageButton save = (ImageButton) view.findViewById(R.id.button_save);
        ImageButton clear = (ImageButton) view.findViewById(R.id.button_clean);

        save.setOnClickListener(this);
        clear.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_save:
                onSaveClick(view);
                break;
            case R.id.button_clean:
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEditARSDialogListener) {
            mListener = (OnEditARSDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    public void onSaveClick(View v) {

        if(validateInfo()){
            mListener.onARSSave(
                    ARS_NAME.getText().toString(),
                    ASSOCIATE_ID.getText().toString(),
                    CLIENT_NAME.getText().toString(),
                    PLAN.getText().toString());
            dismiss();
        }
    }
    private boolean validateInfo(){

        if (TextUtils.isEmpty(ARS_NAME.getText().toString())){
            ARS_NAME.setError(getString(R.string.error_required_field));
            ARS_NAME.requestFocus();
        } else if (TextUtils.isEmpty(ASSOCIATE_ID.getText().toString())){
            ASSOCIATE_ID.setError(getString(R.string.error_required_field));
            ASSOCIATE_ID.requestFocus();
        } else if (TextUtils.isEmpty(CLIENT_NAME.getText().toString())){
            CLIENT_NAME.setError(getString(R.string.error_required_field));
            CLIENT_NAME.requestFocus();
        } else if (TextUtils.isEmpty(PLAN.getText().toString())){
            PLAN.setError(getString(R.string.error_required_field));
            PLAN.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface OnEditARSDialogListener {
        void onARSSave(String arsName, String associateID, String clientName, String plan);
    }
}
