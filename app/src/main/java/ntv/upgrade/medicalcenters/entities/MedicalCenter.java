package ntv.upgrade.medicalcenters.entities;

import android.media.Image;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * This entity holds the basic key attributes of a Medical Center.
 *
 * Created by Paulino Gomez on 1/10/2016.
 */
public class MedicalCenter {

    // Basic Medical Center Info
    private int MCID;
    private String NAME;
    private String EMAIL;
    private String PHONE;
    private LatLng GEO_LOCATION;
    private Image IMAGE;

    // List of accepted ARSs
    private List<Integer> ACCEPTED_ARS = new ArrayList<>();
    // List of specialities
    private List<Integer> SPECIALITIES = new ArrayList<>();

    public MedicalCenter(int MCID, String NAME, String EMAIL,
                         String PHONE, LatLng GEO_LOCATION, Image IMAGE) {
        this.MCID = MCID;
        this.NAME = NAME;
        this.EMAIL = EMAIL;
        this.PHONE = PHONE;
        this.GEO_LOCATION = GEO_LOCATION;
        this.IMAGE = IMAGE;
    }

    // GETTERS

    public int getMCID() {
        return MCID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPHONE() {
        return PHONE;
    }

    public LatLng getGEO_LOCATION() {
        return GEO_LOCATION;
    }

    public Image getIMAGE() {
        return IMAGE;
    }

    public List<Integer> getACCEPTED_ARS() {
        return ACCEPTED_ARS;
    }

    public List<Integer> getSPECIALITIES() {
        return SPECIALITIES;
    }


    // SETTERS
    public void setACCEPTED_ARS(List<Integer> ACCEPTED_ARS) {
        this.ACCEPTED_ARS = ACCEPTED_ARS;
    }

    public void setSPECIALITIES(List<Integer> SPECIALITIES) {
        this.SPECIALITIES = SPECIALITIES;
    }
}
