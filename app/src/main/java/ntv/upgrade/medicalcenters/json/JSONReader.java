/*
package ntv.upgrade.medicalcenters.json;

import android.util.JsonToken;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ntv.upgrade.medicalcenters.models.MedicalCenter;

*/
/**
 * This class Reads MedicalCenter objects from JSON string
 *
 * Created by Paulino Gomez on 1/12/2016.
 *//*

public class JSONReader {

    public ArrayList<MedicalCenter> readJsonStream(InputStream in) throws IOException {
        android.util.JsonReader reader = new android.util.JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readJSONArray(reader);
        } finally {
            reader.close();
        }
    }

    private ArrayList<MedicalCenter> readJSONArray(android.util.JsonReader reader) throws IOException {
        ArrayList<MedicalCenter> medicalCenters = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            medicalCenters.add(readJSONObject(reader));
        }
        reader.endArray();
        return medicalCenters;
    }

    private MedicalCenter readJSONObject(android.util.JsonReader reader) throws IOException {

        int MCID = 0;
        String NAME = null;
        String EMAIL = null;
        String PHONE = null;
        List<Double> GEO_LOCATION = null;
        String IMAGE_URL = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String field = reader.nextName();
            if (field.equals("MCID")) {
                MCID = reader.nextInt();
            } else if (field.equals("NAME")) {
                NAME = reader.nextString();
            } else if (field.equals("EMAIL")) {
                EMAIL = reader.nextString();
            } else if (field.equals("PHONE")) {
                PHONE = reader.nextString();
            } else if (field.equals("GEO_LOCATION") && reader.peek() != JsonToken.NULL) {
                GEO_LOCATION = readGeoLocation(reader);
            } else if (field.equals("IMAGE_URL")) {
                IMAGE_URL = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        assert GEO_LOCATION != null;
        LatLng latLng = new LatLng(
                Double.parseDouble(GEO_LOCATION.get(0).toString()),
                Double.parseDouble(GEO_LOCATION.get(1).toString()));

        return new MedicalCenter(MCID, NAME, EMAIL, PHONE, latLng, IMAGE_URL);
    }

    private List<Double> readGeoLocation(android.util.JsonReader reader) throws IOException {
        List<Double> geo = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            geo.add(reader.nextDouble());
        }
        reader.endArray();
        return geo;
    }
}*/
