package ntv.upgrade.medicalcenters.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Paulino on 9/17/2016.
 */

public class Area {

    private int
            AREA_ID;

    private String
            NAME,
            DESCRIPTION;

    private List<LatLng>
            POINTS;

    public Area() {
    }

    public Area(int AREA_ID, String NAME, String DESCRIPTION, List<LatLng> POINTS) {
        this.AREA_ID = AREA_ID;
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.POINTS = POINTS;
    }

    public int getAREA_ID() {
        return AREA_ID;
    }

    public void setAREA_ID(int AREA_ID) {
        this.AREA_ID = AREA_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public List<LatLng> getPOINTS() {
        return POINTS;
    }

    public void setPOINTS(List<LatLng> POINTS) {
        this.POINTS = POINTS;
    }
}
