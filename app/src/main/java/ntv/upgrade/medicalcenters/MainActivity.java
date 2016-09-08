package ntv.upgrade.medicalcenters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import ntv.upgrade.medicalcenters.notifications.NotificationRequest;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnPermissionCallback {

    private static final String TAG = MainActivity.class.getSimpleName();

    // Required Permissions
    private static final String READ_PHONE_STATE = android.Manifest.permission.READ_PHONE_STATE;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;


    private PermissionHelper
            mPermissionHelper;

    private SharedPreferences
            mPreferences;

    private PackageInfo
            mPackageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        try {
            mPackageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            mPermissionHelper = PermissionHelper.getInstance(this);
            mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            setHeaderContent(navigationView.getHeaderView(0));
            mPermissionHelper.request(READ_PHONE_STATE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    private void setHeaderContent(View header) {

        CircleImageView profileImage = (CircleImageView) header.findViewById(R.id.profile_image);
        TextView profileName = (TextView) header.findViewById(R.id.profile_name);

        if (!mFirebaseUser.isAnonymous()) {
            Glide.with(this)
                    .load(mFirebaseUser.getPhotoUrl().toString())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(profileImage);
        }

        profileName.setText(mFirebaseUser.getDisplayName());
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
        Intent intent;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            intent = new Intent(this, SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
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
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private String getUnitID() {
        Log.d(TAG, "Getting device ID. . .");
        return ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    private void getInstallationInfo(){
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(
                getResources().getString(R.string.pref_key_account_id), mFirebaseUser.getEmail());
        preferencesEditor.putString(
                getResources().getString(R.string.pref_key_device_id), getUnitID());
        preferencesEditor.putString(
                getResources().getString(R.string.pref_key_version_id), mPackageInfo.versionName);
        preferencesEditor.putString(
                getResources().getString(R.string.pref_key_build_id), String.valueOf(mPackageInfo.versionCode));
        preferencesEditor.apply();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "Handling permissions request result");
        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionGranted(@NonNull String[] permissionName) {
        Log.d(TAG, "Permission(s) " + Arrays.toString(permissionName) + " Granted");
        getInstallationInfo();
    }

    @Override
    public void onPermissionDeclined(@NonNull String[] permissionName) {
        Log.d(TAG, "Permission(s) " + Arrays.toString(permissionName) + " Declined");
    }

    @Override
    public void onPermissionPreGranted(@NonNull String permissionsName) {
        Log.d(TAG, "Permission( " + permissionsName + " ) preGranted");
        getInstallationInfo();
    }

    @Override
    public void onPermissionNeedExplanation(@NonNull String permissionName) {
        Log.d(TAG, "Permission( " + permissionName + " ) needs Explanation");

        RequestAfterExplanation request = new RequestAfterExplanation(permissionName);

        new NotificationRequest.Builder()
                .setContext(this)
                .setId(NotificationRequest.NOTIFICATION_ID_ALERT)
                .setTittle(permissionName.substring(permissionName.lastIndexOf(".") + 1))
                .setMessage(getResources().getString(R.string.permission_explanation_READ_PHONE_STATE))
                .isCancelable(true)
                .setPositiveAction(request)
                .setIcon(R.drawable.ic_warning)
                .build()
                .post();
    }

    @Override
    public void onPermissionReallyDeclined(@NonNull String permissionName) {
        Log.d(TAG, "Permission " + permissionName + " can only be granted from App info");

        Toast.makeText(this,
                String.format(
                        getResources().getString(R.string.permission_really_declined),
                        permissionName),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNoPermissionNeeded() {
        Log.d(TAG, "Permission(s) not needed");
    }

    private class RequestAfterExplanation implements Runnable {
        private String permissionName;

        private RequestAfterExplanation(String permissionName) {
            this.permissionName = permissionName;
        }

        @Override
        public void run() {
            mPermissionHelper.requestAfterExplanation(permissionName);
        }
    }
}