package com.nichtemna.takeandsavephoto;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import com.nichtemna.takeandsavephoto.fragment.StatusAmbilSurvey;
 
public class ImageUtils {
    private static final int MEDIA_TYPE_IMAGE = 100;
    private static final int IMAGE_MAX_SIZE = 500;
    public static final String TAKE_AND_SAVE_PHOTO = "PIM";

    public static Bitmap rescaleBitmap(Bitmap bitmap) {
    	/*int scale = 1;
        if (bitmap.getHeight() > IMAGE_MAX_SIZE || bitmap.getWidth() > IMAGE_MAX_SIZE) {
            scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE /
                    (double) Math.max(bitmap.getHeight(), bitmap.getWidth())) / Math.log(0.5)));
        }*/
        int scale1 = 1024;
        int scale2 = 768;
        if(bitmap.getWidth() < scale1 && bitmap.getHeight() < scale2){
        	scale1 = bitmap.getWidth();
            scale2 = bitmap.getHeight();
        }
        createLog("ImageUtils-test-resolution : "+bitmap.getWidth()+" - "+bitmap.getHeight());
        return Bitmap.createScaledBitmap(bitmap, scale1, scale2, false);
    }

    public static Bitmap rotate(Bitmap bitmap, int degree, boolean flip) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        if (flip) {
            matrix.preScale(-1, 1);
        }
        matrix.preRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, false);
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(int type, String degree, int count) {
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), TAKE_AND_SAVE_PHOTO);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        File mediaFile = null;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + StatusAmbilSurvey.getUserid()+"_"+StatusAmbilSurvey.getActiveCamera()+"_"+String.valueOf(StatusAmbilSurvey.getNomid())+"_"+StatusAmbilSurvey.getButtonnya()+"_"+timeStamp+"_d-"+degree+"_"+String.valueOf(count)+ ".jpg");
        } else {
            mediaFile = null;
        }
        return mediaFile;
    }
    
    private static void createLog(String msg){
    	FileHandler fh=null;
        String name;
        if ( 0 == Environment.getExternalStorageState().compareTo(Environment.MEDIA_MOUNTED))
            name = Environment.getExternalStorageDirectory().getAbsolutePath();
        else
            name = Environment.getDataDirectory().getAbsolutePath();

        name += "/TakePicture.log";

        try {
            fh = new FileHandler(name, 256*1024, 1, true);
            fh.setFormatter(new SimpleFormatter());
            fh.publish(new LogRecord(Level.ALL, " : "+msg));
        } catch (Exception e) 
        {
            Log.e("TakePicture", "FileHandler exception", e);
            return;
        }finally{
            if (fh != null)
                fh.close();
        }
    }
}
