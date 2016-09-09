package ntv.upgrade.medicalcenters.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This entity holds the basic key attributes that represents
 * a certified doctor.
 *
 * Created by Paulino Gomez on 1/10/2016.
 */
public class Doctor {

    // Basic Doctors Info
    private int CMD_ID;
    private String FIRST_NAME;
    private String SECOND_NAME;
    private String FIRST_LAST_NAME;
    private String SECOND_LAST_NAME;
    private int JCE_ID;

    // List of accepted ARSs
    private List<Integer> ACCEPTED_ARS = new ArrayList<>();
    // List of specialities
    private List<Integer> SPECIALITIES = new ArrayList<>();

    public Doctor(int CMD_ID, String FIRST_NAME, String SECOND_NAME,
                  String FIRST_LAST_NAME, String SECOND_LAST_NAME, int JCE_ID) {

        this.CMD_ID = CMD_ID;
        this.FIRST_NAME = FIRST_NAME;
        this.SECOND_NAME = SECOND_NAME;
        this.FIRST_LAST_NAME = FIRST_LAST_NAME;
        this.SECOND_LAST_NAME = SECOND_LAST_NAME;
        this.JCE_ID = JCE_ID;
    }

    public int getCMD_ID() {
        return CMD_ID;
    }

    public String getFIRST_NAME() {
        return FIRST_NAME;
    }

    public String getSECOND_NAME() {
        return SECOND_NAME;
    }

    public String getFIRST_LAST_NAME() {
        return FIRST_LAST_NAME;
    }

    public String getSECOND_LASTNAME() {
        return SECOND_LAST_NAME;
    }

    public int getJCE_ID() {
        return JCE_ID;
    }

    public List<Integer> getACCEPTED_ARS() {
        return ACCEPTED_ARS;
    }

    public void setACCEPTED_ARS(List<Integer> ACCEPTED_ARS) {
        this.ACCEPTED_ARS = ACCEPTED_ARS;
    }

    public List<Integer> getSPECIALITIES() {
        return SPECIALITIES;
    }

    public void setSPECIALITIES(List<Integer> SPECIALITIES) {
        this.SPECIALITIES = SPECIALITIES;
    }
}
