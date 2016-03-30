package ntv.upgrade.medicalcenters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import ntv.upgrade.medicalcenters.models.User;

/**
 * A SignIn screen that lets the user use his/her google account
 * to be identified throughout the app.
 * <p/>
 * To use this class is necessary to create the proper google-services.json file.
 */
public class SignInActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = SignInActivity.class.getSimpleName();
    private static final int REQ_CHOOSE_ACCOUNT = 55333;
    private static final int REQ_SIGN_IN = 9001;
    private static final String SAVED_USER = "user";
    private static final String SAVED_DEEPLINK = "deeplink";
    private MedicalCentersApplication mMedicalCentersApplication;
    private GoogleApiClient mGoogleApiClient;
    private TextView mSignInStatus;
    private ProgressDialog mProgressDialog;
    private User mUser;
    private boolean mIsResolving = false;
    private boolean mSignInClicked = false;
    private ProgressDialog mDialog;
    private Runnable mRunAfterSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //mDialog = new ProgressDialog(this);

        // Views
        mSignInStatus = (TextView) findViewById(R.id.sign_in_status);
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);

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

        assert signInButton != null;
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());

        signInButton.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr =
                Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);

        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            //showProgressDialog();

            setProgressBarIndeterminateVisibility(true);
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {

                    setProgressBarIndeterminateVisibility(false);
                    // hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelable(SAVED_USER, mUser);
        //outState.putParcelable(SAVED_DEEPLINK, mDeepLink);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //onUserRetrieved((User) savedInstanceState.getParcelable(SAVED_USER));
        // mDeepLink = savedInstanceState.getParcelable(SAVED_DEEPLINK);
    }
/*

    @Override
    public void onUserRetrieved(final User user) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StreamFragment frag =
                        (StreamFragment) getFragmentManager().findFragmentByTag(STREAM_FRAG_TAG);
                if (user != null) {
                    mUser = user;
                    ((TextView) findViewById(R.id.user_name)).setText(mUser.googleDisplayName);
                    NetworkImageView profile = ((NetworkImageView) findViewById(R.id.user_profile_pic));
                    profile.setImageUrl(mUser.googlePhotoUrl, mVolley.getImageLoader());
                    findViewById(R.id.signed_in_container).setVisibility(View.VISIBLE);
                    findViewById(R.id.signed_out_container).setVisibility(View.GONE);
                    if (frag != null) {
                        frag.setUser(mUser, MainActivity.this);
                    }
                } else {
                    mUser = null;
                    findViewById(R.id.signed_in_container).setVisibility(View.GONE);
                    findViewById(R.id.signed_out_container).setVisibility(View.VISIBLE);
                    if (frag != null) {
                        frag.setUser(null, MainActivity.this);
                    }
                }
                setProgressBarIndeterminateVisibility(false);

                // Run a queued action
                // NOTE: In some situations, mRunAfterSignIn may be garbage collected while the SignIn
                // process takes place.  Therefore, it is not recommended to use this pattern for
                // critical tasks.
                if (mRunAfterSignIn != null) {
                    mRunAfterSignIn.run();
                    mRunAfterSignIn = null;
                }
            }
        });

    }

*/


    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect.
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed");

        if (mIsResolving) {
            Log.d(TAG, "Already resolving.");
            return;
        }

        // Attempt to resolve the ConnectionResult
        if (connectionResult.hasResolution() && mSignInClicked) {
            mIsResolving = true;
            mSignInClicked = false;

            try {
                connectionResult.startResolutionForResult(this, REQ_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, "Could not resolve.", e);
                mIsResolving = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onClick(View view) {
        beginSignInFlow();
    }

    /**
     * Begin the non-immediate sign in flow
     */
    private void beginSignInFlow() {

        setProgressBarIndeterminateVisibility(true);

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, REQ_SIGN_IN);

        boolean state = true;   //HaikuSession.State state = mHaikuPlusSession.checkSessionState(true);
        mSignInClicked = true;
/*
        if (state*//* == HaikuSession.State.UNAUTHENTICATED*//*) {
            Intent intent = AccountPicker.newChooseAccountIntent(
                    null, null, new String[]{"com.google"},
                    false, null, null, null, null);
            startActivityForResult(intent, REQ_CHOOSE_ACCOUNT);
        } else {
            mGoogleApiClient.connect();
        }
        */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQ_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void showDialog(String msg) {
        mDialog.setMessage(msg);
        mDialog.show();
    }

    private void dismissDialog() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            mMedicalCentersApplication.setUserAccount(result.getSignInAccount());

            mSignInStatus.setText(getString(R.string.signed_in_format,
                    mMedicalCentersApplication.getUserAccount().getDisplayName()));

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void showProgressasDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.signing_in));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hidePfdrogressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

}

