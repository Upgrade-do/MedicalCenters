package ntv.upgrade.medicalcenters.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Paulino on 9/17/2016.
 */

public class Place {

    private int
            PLACE_ID;

    private String
            NAME,
            ADDRESS;

    private double
            LATITUDE,
            LONGITUDE;

    private String
            PHONE;

    public Place() {
    }

    public Place(int PLACE_ID, String NAME, String ADDRESS, double LATITUDE, double LONGITUDE, String PHONE) {
        this.PLACE_ID = PLACE_ID;
        this.NAME = NAME;
        this.ADDRESS = ADDRESS;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.PHONE = PHONE;
    }

    public int getPLACE_ID() {
        return PLACE_ID;
    }

    public void setPLACE_ID(int PLACE_ID) {
        this.PLACE_ID = PLACE_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public double getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public double getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public LatLng getGEO(){
        return new LatLng(getLATITUDE(),getLONGITUDE());
    }
}
