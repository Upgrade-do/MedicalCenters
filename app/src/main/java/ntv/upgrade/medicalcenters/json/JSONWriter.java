/*
package ntv.upgrade.medicalcenters.json;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import ntv.upgrade.medicalcenters.models.MedicalCenter;

*/
/**
 * This class Writes MedicalCenter objects to JSON string
 *
 * Created by Paulino Gomez on 1/12/2016.
 *//*

public class JSONWriter {

    public void writeJsonStream(OutputStream out, List<MedicalCenter> medicalCenters) throws IOException {
        android.util.JsonWriter writer = new android.util.JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writeJSONArray(writer, medicalCenters);
        writer.close();
    }

    private void writeJSONArray(android.util.JsonWriter writer, List<MedicalCenter> medicalCenters) throws IOException {
        writer.beginArray();
        for (MedicalCenter medicalCenter : medicalCenters) {
            writeJSONObject(writer, medicalCenter);
        }
        writer.endArray();
    }

    private void writeJSONObject(android.util.JsonWriter writer, MedicalCenter medicalCenter) throws IOException {
        writer.beginObject();

        writer.name("MCID").value(medicalCenter.getMCID());
        writer.name("NAME").value(medicalCenter.getNAME());
        writer.name("EMAIL").value(medicalCenter.getEMAIL());
        writer.name("PHONE").value(medicalCenter.getPHONE());

        writer.name("GEO_LOCATION");
        writeGeoLocation(writer, medicalCenter.getGEOLOCATION());

        writer.name("IMAGE_URL").value(medicalCenter.getIMAGEURL());

        writer.endObject();
    }

    private void writeGeoLocation(android.util.JsonWriter writer, LatLng latLng) throws IOException {
        writer.beginArray();
        writer.value(latLng.latitude);
        writer.value(latLng.longitude);
        writer.endArray();
    }

}
*/
