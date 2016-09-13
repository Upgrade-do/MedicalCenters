package ntv.upgrade.medicalcenters;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity
        implements View.OnClickListener,
        EditARSDialogFragment.OnEditARSDialogListener {

    // TAG
    private static final String TAG = ProfileActivity.class.getSimpleName();

    private static final String FRAGMENT_EDIT_ARS = "fragment_edit_ars";

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextView mARSName;
    private TextView mARSMemberID;
    private TextView mARSClient;
    private TextView mARSAccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mARSName = (TextView) findViewById(R.id.ars_name);
        mARSMemberID = (TextView) findViewById(R.id.ars_associate_id);
        mARSClient = (TextView) findViewById(R.id.ars_client_name);
        mARSAccountType = (TextView) findViewById(R.id.ars_plan);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            updateUI();
        }

        Button signOut = (Button) findViewById(R.id.sign_out_button);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        assert signOut != null;
        signOut.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void updateUI() throws NullPointerException {

        CircleImageView profileImage = (CircleImageView) findViewById(R.id.profile_image);
        getSupportActionBar().setTitle(mFirebaseUser.getDisplayName());

        if (!mFirebaseUser.isAnonymous()) {
            Glide.with(this)
                    .load(mFirebaseUser.getPhotoUrl().toString())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(profileImage);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            intent = new Intent(this, SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Displays the Create Client Dialog Fragment
     */
    private void onShowEditARSDialog(){
        Log.i(TAG, "Calling Create Client Dialog Fragment");

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DialogFragment prev = (DialogFragment) getFragmentManager().findFragmentByTag(FRAGMENT_EDIT_ARS);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = new EditARSDialogFragment();
        newFragment.show(ft, FRAGMENT_EDIT_ARS);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out_button:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, SignInActivity.class));
                finish();
                break;

            case R.id.edit_ars:
                onShowEditARSDialog();
                break;
        }
    }

    @Override
    public void onARSSave(String arsName, String associateID, String clientName, String plan) {
        Log.i(TAG, "Saving new client");

        mARSName.setText(arsName);
        mARSMemberID.setText(associateID);
        mARSClient.setText(clientName);
        mARSAccountType.setText(plan);
    }
}
