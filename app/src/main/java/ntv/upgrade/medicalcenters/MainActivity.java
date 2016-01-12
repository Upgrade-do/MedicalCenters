package ntv.upgrade.medicalcenters;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ntv.upgrade.medicalcenters.entities.MedicalCenter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    protected static List<MedicalCenter> mMedicalCentersList = new ArrayList<>();
    private MedicalCentersApplication mMedicalCentersApplication;

    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (!mMedicalCentersApplication.getGoogleApiClient().isConnected())
            mMedicalCentersApplication.getGoogleApiClient().connect();
    }

    @Override
    protected void onStop() {
        if (mMedicalCentersApplication.getGoogleApiClient() != null &&
                mMedicalCentersApplication.getGoogleApiClient().isConnected()) {
            mMedicalCentersApplication.getGoogleApiClient().disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMedicalCentersApplication = (MedicalCentersApplication) getApplicationContext();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setHeaderContent(navigationView.getHeaderView(0));
        navigationView.setNavigationItemSelectedListener(this);

       /* mMedicalCentersList.add(new Hospital("Plaza de la Salud", new LatLng(18.488780, 69.921893)));
        mMedicalCentersList.add(new Hospital("Hospital Dr. Luis E. Aybar, (Antiguo Morgan)", new LatLng(18.494225, -69.890826)));
        mMedicalCentersList.add(new Hospital("Hospital Central de las Fuerzas Armadas", new LatLng(18.480899, -69.921265)));
        mMedicalCentersList.add(new Hospital("Hospital Docente Universitario Doctor Darío Contreras", new LatLng(18.485642, -69.863374)));
        mMedicalCentersList.add(new Hospital("Hospital Luis Eduardo Aybar", new LatLng(18.494195, -69.890858)));
*/
    }

    private void setHeaderContent(View header) {

        CircleImageView profileImage = (CircleImageView) header.findViewById(R.id.profile_image);
        TextView profileName = (TextView) header.findViewById(R.id.profile_name);
        TextView profileEmail = (TextView) header.findViewById(R.id.profile_email);

        Glide.with(this)
                .load(mMedicalCentersApplication.getUserAccount().getPhotoUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(profileImage);

        profileName.setText(mMedicalCentersApplication.getUserAccount().getDisplayName());
        profileEmail.setText(mMedicalCentersApplication.getUserAccount().getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        switch (id) {

            case R.id.nav_home:
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.nav_medical_centers:
                intent = new Intent(this, ListMapActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
               // intent = new Intent(this, SettingsActivity.class);
               // startActivity(intent);
                break;
            case R.id.nav_about:
                int PLACE_PICKER_REQUEST = 1;
              //  displayPlacePicker();
                break;
            case R.id.nav_sign_out:
                signOut();
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

/*    private void displayPlacePicker() {

        int PLACE_PICKER_REQUEST = 1;
        if (mMedicalCentersApplication.getGoogleApiClient() == null || !mMedicalCentersApplication.getGoogleApiClient().isConnected())
            return;

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(getParent()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.d("PlacesAPI Demo", "GooglePlayServicesRepairableException thrown");
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d("PlacesAPI Demo", "GooglePlayServicesNotAvailableException thrown");
        }
    }*/

    // [START signOut]
    private void signOut() {
        if (mMedicalCentersApplication.getGoogleApiClient().isConnected()) {
            Auth.GoogleSignInApi.signOut(mMedicalCentersApplication.getGoogleApiClient()).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // [START_EXCLUDE]
                            handleSignOutResult(status);
                        }
                    });
        }
    }

    // [START handleSignOutResult]
    private void handleSignOutResult(Status status) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mMedicalCentersApplication.getGoogleApiClient()).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        // updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int PLACE_PICKER_REQUEST = 1;
        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(data, this);

            String toastMsg = String.format("Place: %s", place.getName());
            Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }*/
}