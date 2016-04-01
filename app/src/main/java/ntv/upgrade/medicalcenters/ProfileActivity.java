package ntv.upgrade.medicalcenters;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity
        implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // TAG
    private static final String TAG = ProfileActivity.class.getSimpleName();

    private MedicalCentersApplication mMedicalCentersApplication;
    private GoogleApiClient mGoogleApiClient;

    private boolean mIsResolving;
    private boolean mSignInClicked;


    @Override
    protected void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMedicalCentersApplication = (MedicalCentersApplication) getApplicationContext();
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mMedicalCentersApplication.setGoogleApiClient(mGoogleApiClient);

        Button signOut = (Button) findViewById(R.id.sign_out_button);
        Button disconnect = (Button) findViewById(R.id.disconnect_button);

        updateUI();

        assert signOut != null;
        signOut.setOnClickListener(this);
        assert disconnect != null;
        disconnect.setOnClickListener(this);

    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Start onConnectionFailed Method");

        if (mIsResolving) {
            Log.i(TAG, "Already resolving.");
            return;
        }

        // Attempt to resolve the ConnectionResult
        if (connectionResult.hasResolution() && mSignInClicked) {
            Log.i(TAG, "Start resolving connection failed");
            mIsResolving = true;
            mSignInClicked = false;

            try {
                connectionResult.startResolutionForResult(this, Constants.REQ_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, "Could not resolve.", e);
                mIsResolving = false;
                mGoogleApiClient.connect();
            }
        }
    }


    private void updateUI() throws NullPointerException {
        GoogleSignInAccount user = mMedicalCentersApplication.getUserAccount();

        CircleImageView profileImage = (CircleImageView) findViewById(R.id.profile_image);
        getSupportActionBar().setTitle(user.getDisplayName());

        Glide.with(this)
                .load(user.getPhotoUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(profileImage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out_button:
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                handleSignOutResult(status);
                            }
                        });

                break;
            case R.id.disconnect_button:
                Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                handleRevokeAccessResult(status);
                            }
                        });
                break;
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Start onConnected Method");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Start onConnectionSuspended Method");
        // Attempt to reconnect.
        mGoogleApiClient.connect();
    }

    private void handleSignOutResult(Status status) {
        if (status.isSuccess()) {
            mGoogleApiClient.disconnect();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void handleRevokeAccessResult(Status status) {
        if (status.isSuccess()) {
            mGoogleApiClient.disconnect();
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
