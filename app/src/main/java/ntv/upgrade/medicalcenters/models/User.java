package ntv.upgrade.medicalcenters.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xeros on 3/27/2016.
 */
public class User implements Parcelable {
    // parcel keys
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    /**
     * Creator required for class implementing the parcelable interface.
     */
    public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            // read the bundle containing key value pairs from the parcel
            Bundle bundle = source.readBundle();

            // instantiate a person using values from the bundle
            return new User(
                    bundle.getString(KEY_NAME),
                    bundle.getInt(KEY_AGE));
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }

    };
    private String name;
    private int age;

    /**
     * Empty constructor for array creation
     */
    public User() {
    }

    /**
     * @param name
     * @param age
     */
    public User(String name, int age) {
        this.name = name;
        this.age = age;
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

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
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
        bundle.putString(KEY_NAME, name);
        bundle.putInt(KEY_AGE, age);

        // write the key value pairs to the parcel
        dest.writeBundle(bundle);
    }
}