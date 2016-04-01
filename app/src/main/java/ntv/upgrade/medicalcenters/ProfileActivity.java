package ntv.upgrade.medicalcenters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity
        implements View.OnClickListener {

    // TAG
    private static final String TAG = ProfileActivity.class.getSimpleName();

    private MedicalCentersApplication mMedicalCentersApplication;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onStart() {
        super.onStart();
        if (!mMedicalCentersApplication.getGoogleApiClient().isConnected())
            mMedicalCentersApplication.getGoogleApiClient().connect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMedicalCentersApplication = (MedicalCentersApplication) getApplicationContext();
        mGoogleApiClient = mMedicalCentersApplication.getGoogleApiClient();
        loadUserInfo();


        Button signOut = (Button) findViewById(R.id.sign_out_button);
        Button disconnect = (Button) findViewById(R.id.disconnect_button);

        signOut.setOnClickListener(this);
        disconnect.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
        }
    }

    private void loadUserInfo() throws NullPointerException {
        GoogleSignInAccount user = mMedicalCentersApplication.getUserAccount();

        CircleImageView profileImage = (CircleImageView) findViewById(R.id.profile_image);
        getSupportActionBar().setTitle(user.getDisplayName());

        Glide.with(this)
                .load(user.getPhotoUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(profileImage);
    }

    private void signOut() {

        setProgressBarIndeterminateVisibility(true);


        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // [START_EXCLUDE]
                            handleSignOutResult(status);
                        }
                    });
        }
    }

    public void onSignedOut() {
        // We have signed out or disconnected, so should drop out local state.
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
        setProgressBarIndeterminateVisibility(false);
    }

    private void handleSignOutResult(Status status) {
        // mGoogleApiClient.disconnect();
        onSignedOut();
    }

    private void revokeAccess() {
        setProgressBarIndeterminateVisibility(true);

        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }


        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        // updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
}
