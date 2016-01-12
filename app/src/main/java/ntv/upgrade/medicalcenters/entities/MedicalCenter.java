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

    private int id;
    private String name, email, phone;
    private LatLng geo;
    private Image image;
    private List<Integer> arsIDs = new ArrayList<>();
    private List<Integer> specialitieIDs = new ArrayList<>();

    public MedicalCenter(String name, LatLng geo) {
        this.name = name;
        this.geo = geo;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public LatLng getGeo() {
        return geo;
    }

    public void setGeo(LatLng geolocation) {
        this.geo = geolocation;
    }

    public List<Integer> getArsIDs() {
        return arsIDs;
    }

    public void setArsIDs(List<Integer> arsIDs) {
        this.arsIDs = arsIDs;
    }

    public List<Integer> getSpecialitieIDs() {
        return specialitieIDs;
    }

    public void setSpecialitieIDs(List<Integer> specialitieIDs) {
        this.specialitieIDs = specialitieIDs;
    }
}
