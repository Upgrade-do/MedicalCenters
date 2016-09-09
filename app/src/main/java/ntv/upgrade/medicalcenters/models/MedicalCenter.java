package ntv.upgrade.medicalcenters.models;

/**
 * This entity holds the basic key attributes of a Medical Center.
 *
 * Created by Paulino Gomez on 1/10/2016.
 */
public class MedicalCenter {

    // Basic Medical Center Info
    private double MCID;
    private String name;
    private String email;
    private String phone;
    private double latitude;
    private double longitude;
    private String imageURL;

    public MedicalCenter() {
    }

    public MedicalCenter(double MCID, String name, String email, String phone,
                         double latitude, double longitude, String imageURL) {
        this.MCID = MCID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageURL = imageURL;
    }

    public double getMCID() {
        return MCID;
    }

    public void setMCID(double MCID) {
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
