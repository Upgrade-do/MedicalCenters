package ntv.upgrade.medicalcenters.models;

import android.graphics.Bitmap;

/**
 * This entity holds the basic key attributes of a Medical Center.
 *
 * Created by Paulino Gomez on 1/10/2016.
 */
public class MedicalCenter {

    // Basic Medical Center Info
    private String MCID;
    private String name;
    private String email;
    private String phone;
    private String latitude;
    private String longitude;
    private Bitmap imageURL;

    public MedicalCenter() {
    }

    public MedicalCenter(String MCID, String name, String email, String phone,
                         String latitude, String longitude, Bitmap imageURL) {
        this.MCID = MCID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageURL = imageURL;
    }

    public String getMCID() {
        return MCID;
    }

    public void setMCID(String MCID) {
        this.MCID = MCID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Bitmap getImageURL() {
        return imageURL;
    }

    public void setImageURL(Bitmap imageURL) {
        this.imageURL = imageURL;
    }
}
