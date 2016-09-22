package ntv.upgrade.medicalcenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ntv.upgrade.medicalcenters.models.MedicalCenter;
import ntv.upgrade.medicalcenters.models.Place;
import ntv.upgrade.medicalcenters.utils.MapUtils;

/**
 * Created by Paulino Gomez on 1/10/2016.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ValueEventListener {

    // Google map object.
    public static DatabaseReference mDatabaseRef;
    // TAG
    private final String TAG = MapFragment.class.getSimpleName();
    private MapView mMapView;
    private GoogleMap mMap;
    private Bundle mBundle;
    private Location mLastLocation;
    private List<MedicalCenter> mMedicalCenters;
    private SharedPreferences mPreferences;
    // Client used to interact with Google APIs.
    private GoogleApiClient mGoogleApiClient;


    private OnMapFragmentInteractionListener mListener;

    public MapFragment() {
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // MapView
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(mBundle);
        mMapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;

        // Database reference
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("MedicalCenters").getRef();
        mDatabaseRef.addValueEventListener(this);

        // Binding preferences
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mMedicalCenters = new ArrayList<>();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        if (MapUtils.checkFineLocationPermission(getContext())) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                centerMapOnLocation(mLastLocation);
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        // Failed to read value
        Log.w(TAG, "Connection Suspended" + String.valueOf(i));
    }

    /********************************************************************************************
     * FIREBASE DATABASE REFERENCE*
     ********************************************************************************************/
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        if (mMap != null) {

            // whenever data at this location is updated.
            for (DataSnapshot mdSnapshot : dataSnapshot.getChildren()) {

                MedicalCenter md = mdSnapshot.getValue(MedicalCenter.class);
                mMedicalCenters.add(md);

                drawPlace(new Place(
                        mdSnapshot.getKey(),
                        md.getName(),
                        md.getEmail(),
                        md.getLatitude(),
                        md.getLongitude(),
                        md.getPhone()));
            }

        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Failed to read value
        Log.w(TAG, "Failed to read value.", databaseError.toException());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnMapFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mMap == null) {
            mMap = googleMap;
            setUpMap();
        }
    }

    private void setUpMap() {

        int mapStyle = Integer.parseInt(mPreferences.getString(
                getContext().getString(R.string.pref_key_map_style),
                getContext().getString(R.string.pref_default_map_style)));

        mMap.setMapType(mapStyle);

        if (MapUtils.checkFineLocationPermission(getContext())) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);

        setInfoWindows();
        drawArea();
    }

    /**
     * Center map on a given LatLng @param geo
     */
    private void centerMapOnLocation(CameraPosition cameraPosition) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude), 13));
    }

    /**
     * Center map on a given Location @param location
     */
    private void centerMapOnLocation(Location location) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 13));
    }

    private void setInfoWindows() {

        // tempSite = null;

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            public View getInfoWindow(Marker arg0) {

            /*    View view = getLayoutInflater().inflate(R.layout.site_view_layout, null);

                TextView clickToEdit = (TextView) view.findViewById(R.id.textView_click_to_edit);
                clickToEdit.setVisibility(View.VISIBLE);

                TextView siteName = (TextView) view.findViewById(R.id.textView_Site_Name);
                TextView siteCoordinates = (TextView) view.findViewById(R.id.textView_Lat_Lng);
                TextView siteHeight = (TextView) view.findViewById(R.id.textView_height);

                TextView alphaAzimuth = (TextView) view.findViewById(R.id.alphaAzimuth_textView);
                TextView betaAzimuth = (TextView) view.findViewById(R.id.betaAzimuth_textView);
                TextView gammaAzimuth = (TextView) view.findViewById(R.id.gammaAzimuth_textView);

                TextView alphaTilt = (TextView) view.findViewById(R.id.alphaTilt_textView);
                TextView betaTilt = (TextView) view.findViewById(R.id.betaTilt_textView);
                TextView gammaTilt = (TextView) view.findViewById(R.id.gammaTilt_textView);

                tempSite = getSiteByLocation(arg0.getPosition());

                if (tempSite != null) {

                    siteName.setText(tempSite.getName());

                    siteCoordinates.setText(String.format("(%.5f, %.5f)"
                            , tempSite.getGeo().latitude, tempSite.getGeo().longitude));

                    siteHeight.setText(String.format("%.2f mts", tempSite.getHeight()));

                    alphaAzimuth.setText(String.format("%d", tempSite.getAlpha().getAzimuth()));
                    betaAzimuth.setText(String.format("%d", tempSite.getBeta().getAzimuth()));
                    gammaAzimuth.setText(String.format("%d", tempSite.getGamma().getAzimuth()));

                    alphaTilt.setText(String.format("%.1f", tempSite.getAlpha().getTilt()));
                    betaTilt.setText(String.format("%.1f", tempSite.getBeta().getTilt()));
                    gammaTilt.setText(String.format("%.1f", tempSite.getGamma().getTilt()));

                    if (tempSite.isOperational()) {
                        siteName.setBackgroundResource(R.color.colorGreen);
                    } else siteName.setBackgroundResource(R.color.colorRed);
                }
                return view;*/
                return null;
            }

            public View getInfoContents(Marker arg0) {
                return null;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                //  openEditSiteDialogFragment("edit");
            }
        });
    }

    public void drawArea() {
        mMap.addPolygon(new PolygonOptions()
                .add(
                        new LatLng(18.4730365696298, -69.89192656117666),
                        new LatLng(18.46722705984141, -69.88965971147991),
                        new LatLng(18.46885902440289, -69.8851668834618),
                        new LatLng(18.470503814422717, -69.88219244139827),
                        new LatLng(18.471836886544178, -69.88099081169821),
                        new LatLng(18.473302235970095, -69.88118393074728),
                        new LatLng(18.47429948065845, -69.88155943992751),
                        new LatLng(18.477901723826147, -69.88202077987808),
                        new LatLng(18.480425283974153, -69.8829434597792),
                        new LatLng(18.480313352651173, -69.88415581825393),
                        new LatLng(18.480516864093133, -69.8847995484175),
                        new LatLng(18.480404932829963, -69.88520724418777),
                        new LatLng(18.479550182592206, -69.88540036323684),
                        new LatLng(18.47524583985043, -69.8903785434959))
                .strokeColor(Color.rgb(255, 87, 34))
                .fillColor(Color.argb(50, 255, 138, 101)));
    }

    public void drawPlace(Place place) {

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_location);

        mMap.addMarker(new MarkerOptions()
                .position(place.getGEO())
                .title(place.getNAME())
                .icon(icon)
                .draggable(false));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public interface OnMapFragmentInteractionListener {
        List<Place> onGetPlaces();
    }

}