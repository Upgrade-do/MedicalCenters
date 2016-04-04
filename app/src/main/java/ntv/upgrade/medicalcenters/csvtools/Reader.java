package ntv.upgrade.medicalcenters.csvtools;

import android.content.res.AssetManager;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ntv.upgrade.medicalcenters.Constants;
import ntv.upgrade.medicalcenters.models.MedicalCenter;

/**
 * Created by paulino on 23/02/15.
 */
public class Reader {

    public static List<MedicalCenter> readAndInsert(AssetManager assetManager) throws UnsupportedEncodingException {

        List<MedicalCenter> mMedicalCenters = new ArrayList<>();
        InputStream is = null;

        try {
            is = assetManager.open(Constants.getDatasourceFileName());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line;
        StringTokenizer st;
        try {

            while ((line = reader.readLine()) != null) {
                st = new StringTokenizer(line, ",");
                MedicalCenter medicalCenter = new MedicalCenter();
                medicalCenter.setMCID(Double.valueOf(st.nextToken()));
                medicalCenter.setName(st.nextToken());
                medicalCenter.setEmail(st.nextToken());
                medicalCenter.setPhone(st.nextToken());
                medicalCenter.setGeo(
                        new LatLng(
                                Double.valueOf(st.nextToken()),
                                Double.valueOf(st.nextToken())));
                medicalCenter.setImageURL(st.nextToken());


                mMedicalCenters.add(medicalCenter);

            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        return mMedicalCenters;
    }
}