package ntv.upgrade.medicalcenters;

import android.content.Context;
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
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import ntv.upgrade.medicalcenters.entities.MedicalCenter;
import ntv.upgrade.medicalcenters.json.JSONReader;
import ntv.upgrade.medicalcenters.json.JSONWriter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    // name of the file to preserve medical centers
    private final String MEDICAL_CENTERS_DATA_FILE_NAME = "medical_centers_data";
    private MedicalCentersApplication mMedicalCentersApplication;

    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            InputStream in = openFileInput(MEDICAL_CENTERS_DATA_FILE_NAME);
            JSONReader reader = new JSONReader();
            mMedicalCentersApplication.mMedicalCenters = reader.readJsonStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        try {
            OutputStream out = openFileOutput(MEDICAL_CENTERS_DATA_FILE_NAME, Context.MODE_PRIVATE);
            JSONWriter writer = new JSONWriter();
            writer.writeJsonStream(out, mMedicalCentersApplication.mMedicalCenters);
        } catch (IOException e) {
            e.printStackTrace();
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
                this, drawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setHeaderContent(navigationView.getHeaderView(0));
        navigationView.setNavigationItemSelectedListener(this);

        mMedicalCentersApplication.mMedicalCenters.add(new MedicalCenter(1,"Plaza de la Salud", "stuff","219818", new LatLng(18.488780, 69.921893),"http://media.cleveland.com/health_impact/photo/ahuja-medical-centerjpg-6769ad690225262a.jpg"));
        mMedicalCentersApplication.mMedicalCenters.add(new MedicalCenter(2,"Hospital Dr. Luis E. Aybar, (Antiguo Morgan)", "stuff","219818", new LatLng(18.494225, -69.890826),"http://mediguide.com/wp-content/uploads/2011/01/world-medical-center.jpg"));
        mMedicalCentersApplication.mMedicalCenters.add(new MedicalCenter(3,"Hospital Central de las Fuerzas Armadas", "stuff","219818", new LatLng(18.480899, -69.921265),"http://www.clker.com/cliparts/v/7/J/7/5/u/entrance-to-the-administration-building-at-the-national-naval-medical-center-in-bethesda-md-md.png"));
        mMedicalCentersApplication.mMedicalCenters.add(new MedicalCenter(4,"Hospital Docente Universitario Doctor Darío Contreras", "stuff","219818", new LatLng(18.485642, -69.863374),"http://classroomclipart.com/images/gallery/Clipart/Architecture/convention-center-clipart-124.jpg"));
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
            case R.id.nav_profile:
                intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_medical_centers:
                intent = new Intent(this, ListMapActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_about:
                int PLACE_PICKER_REQUEST = 1;
              //  displayPlacePicker();
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