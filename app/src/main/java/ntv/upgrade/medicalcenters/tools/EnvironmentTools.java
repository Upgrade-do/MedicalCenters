package ntv.upgrade.medicalcenters.tools;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This Java Class pretends to work with the Android Environment to
 * return if weather or not some tools are usable by the time they're called.
 * <p/>
 * <p/>
 * ****** Ready ******
 * <p/>
 * Created by xrshck on 04/11/14.
 */
public class EnvironmentTools {

    // Returns false if external storage is read only
    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    //Returns true if external storage is available
    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    private static void moveFile(String inputPath, String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;

            // delete the original file
            new File(inputPath + inputFile).delete();


        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

    private static void deleteFile(String inputPath, String inputFile) {
        try {
            // delete the original file
            new File(inputPath + inputFile).delete();

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public static void copyFile(String inputPath, String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

    public static void renameFile(String inputPath, String inputFile, String outputFile) {

        try {
            File from = new File(inputPath + inputFile);
            File to = new File(inputPath + outputFile);
            from.renameTo(to);

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }
}
