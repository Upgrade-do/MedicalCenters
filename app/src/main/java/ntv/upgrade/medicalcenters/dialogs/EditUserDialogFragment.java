package ntv.upgrade.medicalcenters.dialogs;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import ntv.upgrade.medicalcenters.R;

/**
 * Created by pgomez on 9/15/2016.
 */
public class EditUserDialogFragment extends DialogFragment implements View.OnClickListener {

    //TAG
    private static final String TAG = EditARSDialogFragment.class.getSimpleName();

    private EditText
            BLOOD_TYPE,
            BIRTH_DATE,
            SSN;

    private EditUserDialogFragment.OnEditUserDialogListener mListener;

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
        View view = inflater.inflate(R.layout.fragment_edit_user, container, false);

        BLOOD_TYPE = (EditText) view.findViewById(R.id.user_blood_type);
        BIRTH_DATE = (EditText) view.findViewById(R.id.user_birth_date);
        SSN = (EditText) view.findViewById(R.id.user_SSN);

        BLOOD_TYPE.setText(getArguments().getString("blood_type"));
        BIRTH_DATE.setText(getArguments().getString("birth_date"));
        SSN.setText(getArguments().getString("social_security"));

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
                BLOOD_TYPE.setText("");
                BIRTH_DATE.setText("");
                SSN.setText("");
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditARSDialogFragment.OnEditARSDialogListener) {
            mListener = (EditUserDialogFragment.OnEditUserDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    public void onSaveClick(View v) {

        if(validateInfo()){
            mListener.onUserSave(
                    BLOOD_TYPE.getText().toString(),
                    BIRTH_DATE.getText().toString(),
                    SSN.getText().toString());
            dismiss();
        }
    }
    private boolean validateInfo(){

        if (TextUtils.isEmpty(BLOOD_TYPE.getText().toString())){
            BLOOD_TYPE.setError(getString(R.string.error_required_field));
            BLOOD_TYPE.requestFocus();
        } else if (TextUtils.isEmpty(BIRTH_DATE.getText().toString())){
            BIRTH_DATE.setError(getString(R.string.error_required_field));
            BIRTH_DATE.requestFocus();
        } else if (TextUtils.isEmpty(SSN.getText().toString())){
            SSN.setError(getString(R.string.error_required_field));
            SSN.requestFocus();
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

    public interface OnEditUserDialogListener {
        void onUserSave(String mBloodType, String mBirthDate, String mSSN);
    }
}
