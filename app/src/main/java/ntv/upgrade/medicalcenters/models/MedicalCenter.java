package ntv.upgrade.medicalcenters.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * This entity holds the basic key attributes of a Medical Center.
 *
 * Created by Paulino Gomez on 1/10/2016.
 */
public class MedicalCenter implements Parcelable {

    // parcel keys
    private static final String KEY_MCID = "medical_center_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_LAT = "city_id";
    private static final String KEY_LNG = "birth_date";
    private static final String KEY_IMAGE_URL = "image_url";

    /**
     * Creator required for class implementing the parcelable interface.
     */
    public static final Parcelable.Creator<MedicalCenter> CREATOR = new Creator<MedicalCenter>() {

        @Override
        public MedicalCenter createFromParcel(Parcel source) {
            // read the bundle containing key value pairs from the parcel
            Bundle bundle = source.readBundle();
            MedicalCenter medicalCenter = new MedicalCenter(
                    bundle.getDouble(KEY_MCID),
                    bundle.getString(KEY_NAME),
                    bundle.getString(KEY_EMAIL),
                    bundle.getString(KEY_PHONE),
                    bundle.getDouble(KEY_LAT),
                    bundle.getDouble(KEY_LNG),
                    bundle.getString(KEY_IMAGE_URL));

            return medicalCenter;
            // instantiate a person using values from the bundle

        }

        @Override
        public MedicalCenter[] newArray(int size) {
            return new MedicalCenter[size];
        }

    };

    // Basic Medical Center Info
    private double MCID;
    private String name;
    private String email;
    private String phone;
    private double lat;
    private double lng;
    private String imageURL;

    /**
     * Empty constructor for array creation
     */
    public MedicalCenter() {
    }

    public MedicalCenter(double MCID, String name, String email, String phone,
                         double lat, double lng, String imageURL) {
        this.MCID = MCID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.lat = lat;
        this.lng = lng;
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

    public LatLng getGeo() {
        return new LatLng(lat, lng);
    }

    public void setGeo(LatLng geo) {
        this.lat = geo.latitude;
        this.lng = geo.longitude;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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
        bundle.putDouble(KEY_MCID, MCID);
        bundle.putString(KEY_NAME, name);
        bundle.putString(KEY_EMAIL, email);
        bundle.putString(KEY_PHONE, phone);
        bundle.putDouble(KEY_LAT, lat);
        bundle.putDouble(KEY_LNG, lng);
        bundle.putString(KEY_IMAGE_URL, imageURL);

        // write the key value pairs to the parcel
        dest.writeBundle(bundle);
    }
}
