package ntv.upgrade.medicalcenters.models;

/**
 * Created by xeros on 3/27/2016.
 */
public class User {

    private double googleId;
    private String name;
    private String email;
    private String photoURL;
    private double cityId;
    private String birthDate;
    private double ssn;
    private String blood;
    private String address;
    private String ars;
    private double arsId;

    public User(double googleId, String name, String email, String photoURL, double cityId,
                String birthDate, double ssn, String blood, String address, String ars, double arsId) {
        this.googleId = googleId;
        this.name = name;
        this.email = email;
        this.photoURL = photoURL;

        this.cityId = cityId;
        this.birthDate = birthDate;
        this.ssn = ssn;
        this.blood = blood;
        this.address = address;
        this.ars = ars;
        this.arsId = arsId;
    }

    public String getName() {
        return name;
    }

    public double getGoogleId() {
        return googleId;
    }

    public double getCityId() {
        return cityId;
    }

    public void setCityId(double cityId) {
        this.cityId = cityId;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public double getSSN() {
        return ssn;
    }

    public void setSSN(double ssn) {
        this.ssn = ssn;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArs() {
        return ars;
    }

    public void setArs(String ars) {
        this.ars = ars;
    }

    public double getArsId() {
        return arsId;
    }

    public void setArsId(double arsId) {
        this.arsId = arsId;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photoURL;
    }
}