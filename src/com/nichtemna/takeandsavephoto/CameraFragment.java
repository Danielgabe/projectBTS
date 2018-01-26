package com.nichtemna.takeandsavephoto;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nichtemna.takeandsavephoto.PhotoActivity.TransactionType;
import com.nichtemna.takeandsavephoto.PhotoFragment.CustomComparator;
import com.nichtemna.takeandsavephoto.fragment.CommonUtil;
import com.nichtemna.takeandsavephoto.fragment.DataPhoto;
import com.nichtemna.takeandsavephoto.fragment.StatusAmbilSurvey;
import com.nichtemna.takeandsavephoto.fungsi.DatabaseManager;
import com.nichtemna.takeandsavephoto.fungsi.JSONParser;
import com.nichtemna.takeandsavephoto.fungsi.UserFunctions;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class CameraFragment extends Fragment implements OnClickListener {
    private static final int MEDIA_TYPE_IMAGE = 100;

    float degreeView=0f;
    private Preview mPreview;
    private Camera mCamera;
    private FrameLayout mFrameLayout;
    private ImageButton imb_photo, imb_take_photo, imb_upload;
    private int numberOfCameras;
    private int cameraCurrentlyLocked;
    private int defaultCameraId;
    private boolean isBackCamera = false;
    
    private int serverResponseCode = 0;
    private ProgressDialog dialog = null;
    private String imagepath=null;
    private String upLoadServerUri = "http://bts.devtuwaga.com/api_services/test_upload";
    private ArrayList<File> files = new ArrayList<File>();
    
    private ConnectionDetector cd;
    private Boolean isInternetPresent = false;
    
    private static SensorManager sensorService;
    
 // define the display assembly compass picture
 	private ImageView image;

 	// record the compass picture angle turned
 	private float currentDegree = 0f;

 	// device sensor manager
 	private Sensor mSensorManager;

 	TextView tvHeading;
 	
 	JSONParser jsonParser = new JSONParser();
 	
 	private DatabaseManager dm;
 	private int countNow=0;
 	private Bitmap bitmap = null;
 	private byte[] datanya = null;
 	private FileOutputStream fos = null;
 	private String logMessage = "";
 	
 	private SensorEventListener mySensorEventListener = new SensorEventListener() {

 	    @Override
 	    public void onAccuracyChanged(Sensor sensor, int accuracy) {
 	    }

 		@Override
 		public void onSensorChanged(SensorEvent event) {

 			// get the angle around the z-axis rotated
 			float degree = Math.round(event.values[0]);
 			degreeView = degree + 90f;
 			int retval = Float.compare(degreeView, 360f);
 			if(retval > 0){
 				degreeView -= 360f;
 			}
 			
 			tvHeading.setText(" "+Float.toString(degreeView) + " degrees");
 			 
 			// create a rotation animation (reverse turn degree degrees)
 			RotateAnimation ra = new RotateAnimation(
 					currentDegree, 
 					-degree,
 					Animation.RELATIVE_TO_SELF, 0.5f, 
 					Animation.RELATIVE_TO_SELF,
 					0.5f);

 			// how long the animation will take place
 			ra.setDuration(210);

 			// set the animation after the end of the reservation status
 			ra.setFillAfter(true);

 			// Start the animation
 			image.startAnimation(ra);
 			currentDegree = -degree;

 		}
 	  };

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	dm = new DatabaseManager(getActivity());
    	StatusAmbilSurvey.setStatus("Not_TAKEN");
        setHasOptionsMenu(true);
        numberOfCameras = Camera.getNumberOfCameras();
        findIdOfDefaultCamera();
        super.onCreate(savedInstanceState);
        
        //cd = new ConnectionDetector(getActivity().getApplicationContext());
        
        sensorService = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensorManager = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if (mSensorManager != null) {
          sensorService.registerListener(mySensorEventListener, mSensorManager,
              SensorManager.SENSOR_DELAY_NORMAL);

        } else {
        	Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
            Toast.makeText(getActivity(), "ORIENTATION Sensor not found",Toast.LENGTH_LONG).show();
            getActivity().finish();
          }
    }

    private void findIdOfDefaultCamera() {
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
                defaultCameraId = i;
                isBackCamera = true;
            }
        }
    }

    public static CameraFragment newInstance(TransactionType type) {
        CameraFragment frag = new CameraFragment();
        final Bundle args = new Bundle();
        args.putSerializable(Extras.TRANSACTION_TYPE, type);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	Log.d("Camera","Masuk");
        View view = inflater.inflate(R.layout.fragment_camera, container, false);        
        initViews(view);        
        setListeners();
        return view;
    }

    private void initViews(View view) {
    	image = (ImageView) view.findViewById(R.id.imageViewCompass);
    	tvHeading = (TextView) view.findViewById(R.id.tvHeading);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.frag_cam_frame_surface);
        mPreview = new Preview(getActivity(), (SurfaceView) view.findViewById(R.id.frag_cam_surfaceView));
        mPreview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mFrameLayout.addView(mPreview);
        imb_photo = (ImageButton) view.findViewById(R.id.frag_cam_imb_photo);
        imb_take_photo = (ImageButton) view.findViewById(R.id.frag_cam_imb_take_photo);
        
        //imb_upload = (ImageButton) view.findViewById(R.id.frag_cam_imb_upload);
    }

    private void setListeners() {
        imb_photo.setOnClickListener(this);
        if(!(dm.getCountDataPhotoUloadedUpload(StatusAmbilSurvey.getNomid()) == 3) ||
         		 !(dm.getToDoStatus(StatusAmbilSurvey.getNomid()) == 1)){
        	imb_take_photo.setOnClickListener(this);
        }
        //imb_upload.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        System.gc();
        mCamera = Camera.open(defaultCameraId);
        Camera.Parameters params = mCamera.getParameters();
        Size mSize = null;
        /*List<Size> sizes = params.getSupportedPictureSizes();
        for (Size size : sizes) {
        	logMessage += size.width+" - "+size.height+"\n";
            if ( (size.width > 1024) && (size.width <= 3232)) {
                mSize = size;
                break;
            }
        }
        createLog(logMessage);*/
        params.setPictureSize(2592,1944);
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.setParameters(params);
        mPreview.setmCameraID(defaultCameraId);
        mPreview.setCamera(mCamera);
        
        sensorService = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensorManager = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if (mSensorManager != null) {
          sensorService.registerListener(mySensorEventListener, mSensorManager,
              SensorManager.SENSOR_DELAY_NORMAL);
        } else {
        	Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
            Toast.makeText(getActivity(), "ORIENTATION Sensor not found",Toast.LENGTH_LONG).show();
            getActivity().finish();
          }
    }
    
    @Override
    public void onPause() {
        super.onPause();
        if (mCamera != null) {
            mPreview.setCamera(null);
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_cam_imb_photo:
                goToPhotoFragment(TransactionType.PERMANENT);
                break;
            case R.id.frag_cam_imb_take_photo:
                mCamera.takePicture(null, null, jpegCallback);
                break;
            /*case R.id.frag_cam_imb_upload:
            	this.getImageFileList();
                break;*/
            default:
                break;
        }
    }

    /**
     * Switch fragment to photo preview
     */
    private void goToPhotoFragment(TransactionType type) {
        if (getActivity() instanceof FragmentToggler) {
            ((FragmentToggler) getActivity()).toggleFragments(type);
        }
    }

    /**
     * Callback for taken photo
     */
    PictureCallback jpegCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
        		saveTakenPhoto(data, Float.toString(degreeView));
        		bitmap.recycle();
        		System.gc(); 
        }
    };

    /**
     * Saves taken image to Sdcard picrute derictory
     *
     * @param data - byte[] of taken picture
     */
    private void saveTakenPhoto(byte[] data, String degreenya) {
    	 
    	System.gc();
    	//datanya = data;
        File from = null, to = null;
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        Log.d("saveTakenPhoto", "masuk");
        try {
            sensorService.unregisterListener(mySensorEventListener);
        	BitmapFactory.Options options = new BitmapFactory.Options();
        	 options.inJustDecodeBounds = false;
        	 options.inPreferredConfig = Config.RGB_565;
        	 options.inDither = false;
        	 int countData = data.length; 
        	 System.gc();
               bitmap = BitmapFactory.decodeByteArray(data, 0, countData, options); 
              
               System.gc();
               
                Log.d("saveTakenPhoto", "bit map");
                bitmap = ImageUtils.rescaleBitmap(bitmap);
                Log.d("saveTakenPhoto", "rescale bit map");
                if (isBackCamera) {
                	//bitmap = ImageUtils.rotate(bitmap, 90, false);
                } else {
                	bitmap = ImageUtils.rotate(bitmap, -90, true);
                	System.gc();
                }
        } catch (Exception error) {
            error.printStackTrace();
            System.gc();
        }
        
        if(dm.getCountDataPhotoByNomButton(StatusAmbilSurvey.getNomid(),StatusAmbilSurvey.getButtonnya()) > 0){
        	countNow = dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya()).getSeqNo();
        	countNow += 1;
        }else{
        	countNow = 1;
        } 
        File pictureFile = ImageUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE, degreenya, countNow);

        try {
            fos = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            bitmap.recycle();
            System.gc();
            fos.flush();
            fos.close();
            
            if(dm.getCountDataPhotoByNomButton(StatusAmbilSurvey.getNomid(),StatusAmbilSurvey.getButtonnya()) > 0){
            	dm.updateDataPhotoSeqNoButton(countNow, StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya());
            	if(StatusAmbilSurvey.getActiveCamera().equals("PANORAMIC")){
            		DataPhoto dp = dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya());
            		String listPanoramic = dp.getPanoramic();
            		listPanoramic +=pictureFile.getName()+";";
            		dm.updateDataPhotoByButton("panoramic",StatusAmbilSurvey.getButtonnya(),listPanoramic, StatusAmbilSurvey.getNomid());
            	}
            	else if(StatusAmbilSurvey.getActiveCamera().equals("AKSESPLN")){
            		if(CommonUtil.isNotNullOrEmpty( dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya()).getParam_2())){
            			File toDelete = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + ImageUtils.TAKE_AND_SAVE_PHOTO).toString(),
            					dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya()).getParam_2());
            			toDelete.delete();
            		}
            		DataPhoto dp = dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya());
            		String aksesPln = dp.getParam_2();
            		aksesPln = pictureFile.getName();
            		dm.updateDataPhotoByButton("param_2", StatusAmbilSurvey.getButtonnya(), aksesPln, StatusAmbilSurvey.getNomid());
            	}
            	else if(StatusAmbilSurvey.getActiveCamera().equals("POLEPLN")){
            		if(CommonUtil.isNotNullOrEmpty( dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya()).getParam_3())){
            			File toDelete = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + ImageUtils.TAKE_AND_SAVE_PHOTO).toString(),
            					dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya()).getParam_3());
            			toDelete.delete();
            		}
            		DataPhoto dp = dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya());
            		String polePln = dp.getParam_3();
            		polePln = pictureFile.getName();
            		dm.updateDataPhotoByButton("param_3", StatusAmbilSurvey.getButtonnya(), polePln, StatusAmbilSurvey.getNomid());
            	}
            	else if(StatusAmbilSurvey.getActiveCamera().equals("KABELPLN")){
            		if(CommonUtil.isNotNullOrEmpty( dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya()).getParam_4())){
            			File toDelete = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + ImageUtils.TAKE_AND_SAVE_PHOTO).toString(),
            					dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya()).getParam_4());
            			toDelete.delete();
            		}
            		DataPhoto dp = dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya());
            		String jalurPln = dp.getParam_4();
            		jalurPln = pictureFile.getName();
            		dm.updateDataPhotoByButton("param_4", StatusAmbilSurvey.getButtonnya(), jalurPln, StatusAmbilSurvey.getNomid());
            	}
            	
            }
            
        } catch (FileNotFoundException e) {
            Log.d("tag", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("tag", "Error accessing file: " + e.getMessage());
        } finally {
        	fos = null;
        	Toast.makeText(getActivity(), "File saved " + pictureFile.getName(), Toast.LENGTH_LONG).show();
            goToPhotoFragment(TransactionType.PERMANENT);
        }
    }
    
    private void createLog(String msg){
		FileHandler fh=null;
	    String name;
	    if ( 0 == Environment.getExternalStorageState().compareTo(Environment.MEDIA_MOUNTED))
	        name = Environment.getExternalStorageDirectory().getAbsolutePath();
	    else
	        name = Environment.getDataDirectory().getAbsolutePath();

	    name += "/CameraFragment.log";

	    try {
	        fh = new FileHandler(name, 256*1024, 1, true);
	        fh.setFormatter(new SimpleFormatter());
	        fh.publish(new LogRecord(Level.ALL, " : "+msg));
	    } catch (Exception e) 
	    {
	        Log.e("MyLog", "FileHandler exception", e);
	        return;
	    }finally{
	        if (fh != null) 
	            fh.close();
	    }
	}
}
