package ntv.upgrade.medicalcenters.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xeros on 3/27/2016.
 */
public class User implements Parcelable {

    // parcel keys
    private static final String KEY_GOOGLE_ID = "google_id";
    private static final String KEY_CITY_ID = "city_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_BIRTH_DATE = "birth_date";
    private static final String KEY_SSN = "social_security";
    private static final String KEY_BLOOD = "blood";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_ARS = "ars";
    private static final String KEY_ARS_ID = "ars_id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHOTO = "photo";
    /**
     * Creator required for class implementing the parcelable interface.
     */
    public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            // read the bundle containing key value pairs from the parcel
            Bundle bundle = source.readBundle();
            User user = new User(
                    bundle.getDouble(KEY_GOOGLE_ID),
                    bundle.getString(KEY_NAME),
                    bundle.getString(KEY_EMAIL),
                    bundle.getString(KEY_PHOTO),
                    bundle.getDouble(KEY_CITY_ID),
                    bundle.getString(KEY_BIRTH_DATE),
                    bundle.getDouble(KEY_SSN),
                    bundle.getString(KEY_BLOOD),
                    bundle.getString(KEY_ADDRESS),
                    bundle.getString(KEY_ARS),
                    bundle.getDouble(KEY_ARS_ID));

            return user;
            // instantiate a person using values from the bundle

        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }

    };
    private double googleId;
    private double cityId;
    private String name;
    private String birthDate;
    private double ssn;
    private String blood;
    private String address;
    private String ars;
    private double arsId;
    private String email;
    private String photo;

    /**
     * Empty constructor for array creation
     */
    public User() {
    }

    public User(double googleId, String name, String email, String photo, double cityId,
                String birthDate, double ssn, String blood, String address, String ars, double arsId) {
        this.googleId = googleId;
        this.name = name;
        this.email = email;
        this.photo = photo;

        this.cityId = cityId;
        this.birthDate = birthDate;
        this.ssn = ssn;
        this.blood = blood;
        this.address = address;
        this.ars = ars;
        this.arsId = arsId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // create a bundle for the key value pairs
        Bundle bundle = new Bundle();

        // insert the key value pairs to the bundle
        bundle.putDouble(KEY_GOOGLE_ID, googleId);
        bundle.putDouble(KEY_CITY_ID, cityId);
        bundle.putString(KEY_NAME, name);
        bundle.putString(KEY_BIRTH_DATE, birthDate);
        bundle.putDouble(KEY_SSN, ssn);
        bundle.putString(KEY_BLOOD, blood);
        bundle.putString(KEY_ADDRESS, address);
        bundle.putString(KEY_ARS, ars);
        bundle.putDouble(KEY_ARS_ID, arsId);
        bundle.putString(KEY_EMAIL, email);
        bundle.putString(KEY_PHOTO, photo);

        // write the key value pairs to the parcel
        dest.writeBundle(bundle);
    }
}