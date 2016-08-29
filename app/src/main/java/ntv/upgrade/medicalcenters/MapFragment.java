package ntv.upgrade.medicalcenters;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * Created by Paulino Gomez on 1/10/2016.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    List<LatLng> hole = null;
    // Google map object.
    private MapView mMapView;
    private GoogleMap mMap;
    private Bundle mBundle;
    private CameraPosition mCameraPosition;
    private OnMapFragmentInteractionListener mListener;

    public MapFragment() {
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        MapsInitializer.initialize(getActivity());
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(mBundle);
        setUpMapIfNeeded(view);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Sets up the map if it not already instantiated.
     * If it isn't installed will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     */
    private void setUpMapIfNeeded(View inflatedView) {

        if (mMap == null) {
            mMapView.getMapAsync(MapFragment.this);
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.setMapType(Integer.parseInt(
                Preferences.getPreferredMapStyle(getActivity())));

        //  mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        final List<LatLng> home = new ArrayList<>();
        home.add(new LatLng(18.438510740901954, -69.96936678973725));
        home.add(new LatLng(18.44188982248279, -69.96106267062714));
        home.add(new LatLng(18.43409340755349, -69.96095538226655));
        home.add(new LatLng(18.433360567078857, -69.96241450397065));
        home.add(new LatLng(18.43220022993798, -69.96638417331269));
        home.add(new LatLng(18.43472446212536, -69.96653437701752));
        home.add(new LatLng(18.434704105562403, -69.96724248019746));
        home.add(new LatLng(18.437045094496142, -69.96741414157441));
        home.add(new LatLng(18.436597255601296, -69.96840119449189));
        home.add(new LatLng(18.4371426350097, -69.96964216406923));

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                mCameraPosition = cameraPosition;

                // mMap.clear();
                /*
                hole = new ArrayList<>();
                float p = 360/360;
                float d =0;
                for(int i=0; i < 360; ++i, d+=p){
                    hole.add(SphericalUtil.computeOffset(aki, 50, d));
                }

                mMap.addPolygon(new PolygonOptions()
                        .addAll(home)
                        .addHole(hole)
                        .strokeWidth(0)
                        .fillColor(Color.argb(50, 255, 138, 101)));*/
            }
        });
        LatLng aki = Utils.getLocation(getActivity());
        centerMapOnLocation(aki);

        mMap.addPolygon(new PolygonOptions()
                .addAll(home)
                .strokeWidth(0)
                .strokeColor(Color.rgb(255, 87, 34)));

        setInfoWindows();
        setOnMapClickListener();
        // drawAreasList();
        // drawAttractionsList();
        drawLaZonaPolygon();

        MapsInitializer.initialize(getContext());

    }

    /**
     * Center map on a given LatLng @param geo
     */
    private void centerMapOnLocation(LatLng geo) {
      /*  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(geo.latitude, geo.longitude), 12));*/
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

    private void setOnMapClickListener() {
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng geo) {
            }
        });
    }

   /* public void drawArea(Area area) {


        mMap.addMarker(new MarkerOptions()
                .position(area.getGeo())
                .title(area.getName())
                .snippet(String.valueOf(area.getType()))
                .draggable(false)

        );
        mMap.addCircle(new CircleOptions()
                .center(area.getGeo())
                .radius(ActivityMain.TRIGGER_RADIUS)
                .strokeColor(R.color.colorAccentLight)
                .fillColor(R.color.colorAccent));
    }*/

    public void drawLaZonaPolygon() {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

  /*  public void drawAttraction(Attraction attraction) {


        mMap.addMarker(new MarkerOptions()
                .position(attraction.getGeo())
                .title(attraction.getName())
                .snippet(String.valueOf(attraction.getType()))
                .draggable(false)

        );
    }

    public void drawAreasList() {
        for (Area area : ActivityMain.mAreasArrayList) {
            drawArea(area);
        }
    }

    public void drawAttractionsList() {
        for (Attraction attraction : ActivityMain.mAttractionsArrayList) {
            drawAttraction(attraction);
        }
    }*/


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMapFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}