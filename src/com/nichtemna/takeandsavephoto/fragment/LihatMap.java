package com.nichtemna.takeandsavephoto.fragment;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;  
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener; 

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;   
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;     
import android.widget.Toast; 

import com.nichtemna.takeandsavephoto.CameraFragment;
import com.nichtemna.takeandsavephoto.PhotoActivity;
import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.UploadPhotos;
import com.nichtemna.takeandsavephoto.fungsi.ConnectionDetector;
import com.nichtemna.takeandsavephoto.fungsi.DatabaseManager;
import com.nichtemna.takeandsavephoto.fungsi.JSONParser;
import com.nichtemna.takeandsavephoto.fungsi.LoginActivity;
import com.nichtemna.takeandsavephoto.fungsi.SessionManager;
import com.nichtemna.takeandsavephoto.fungsi.UserFunctions;
import com.nichtemna.takeandsavephoto.fungsi.nom_list;
import com.nichtemna.takeandsavephoto.fungsi.site_info;
import com.nichtemna.takeandsavephoto.fungsi.to_do;
import com.nichtemna.takeandsavephoto.mapsoffline.AndroidOpenStreetMapViewActivity;
import com.nichtemna.takeandsavephoto.activity.*; 

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;   
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;     
import android.widget.Toast;

import org.osmdroid.views.overlay.MyLocationOverlay;

import com.nichtemna.takeandsavephoto.CameraFragment;
import com.nichtemna.takeandsavephoto.ImageUtils;
import com.nichtemna.takeandsavephoto.PhotoActivity;
import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.UploadPhotos;
import com.nichtemna.takeandsavephoto.fungsi.ConnectionDetector;
import com.nichtemna.takeandsavephoto.fungsi.DatabaseManager;
import com.nichtemna.takeandsavephoto.fungsi.JSONParser;
import com.nichtemna.takeandsavephoto.fungsi.LoginActivity;
import com.nichtemna.takeandsavephoto.fungsi.SessionManager;
import com.nichtemna.takeandsavephoto.fungsi.UserFunctions;
import com.nichtemna.takeandsavephoto.fungsi.site_info;
import com.nichtemna.takeandsavephoto.fungsi.to_do;
import com.nichtemna.takeandsavephoto.mapsoffline.AndroidOpenStreetMapViewActivity;
import com.nichtemna.takeandsavephoto.activity.*; 


public class LihatMap extends Activity {
	private int checkHttpResponseServerError = 0;
	private ProgressDialog progressDialog;
	private Handler progressBarHandler = new Handler();
	private int countAllPhotos = 0; private int progressBarStatus=0;
	private int batasCAN1=0; private int batasCAN2=0; private int batasCAN3=0; 
	private DataPhoto DataPhotoCAN1,DataPhotoCAN2,DataPhotoCAN3;
	List<DataPhoto> listDataNomToUpload = new ArrayList<DataPhoto>();
	List<String> listPhotosToUpload = new ArrayList<String>();
	private String statusProgressBarNow="";
	private ProgressDialog progressBar;
	TextView showSiteId;
	TextView showSiteName;
	TextView showLatitude;
	TextView showLogitude;
	TextView showProvince;
	TextView showCity;
	LocationManager locationManager; 

	SessionManager session;
	DatabaseManager db;
	Boolean isOnline = false;
	ConnectionDetector cd;
	SharedPreferences pref;
	private Button uploadData, surveyNom, kandidat1, kandidat2;
	private static final String url_nom_list = "http://bts.devtuwaga.com/api_services/get_nom_list";
	private static final String url_to_do = "http://bts.devtuwaga.com/api_services/get_data_to_do";
	private static final String url_data_tower = "http://bts.devtuwaga.com/api_services/get_data_tower";
	private static final String url_master_nom = "http://bts.devtuwaga.com/api_services/get_master_nom";
	private static final String url_insert_into_todoList = "http://bts.devtuwaga.com/api_services/insert_todo";

	private MapController myMapController;
	private int nom_id;
	private String userId;      	   
	private MapView myOpenMapView;
	private Boolean isInternetPresent = false;
	private ArrayList<File> files = new ArrayList<File>(); 
	ArrayList<OverlayItem> anotherOverlayItemArray; 
	ArrayList<OverlayItem> anotherOverlayItemArray1;
	ArrayList<OverlayItem> anotherOverlayItemArray2; 
	ArrayList<OverlayItem> anotherOverlayItemArray3;
	ArrayList<OverlayItem> overlayItemArray;
	MyLocationOverlay myLocationOverlay = null;
	private JSONParser jsonParser = new JSONParser();
	private int serverResponseCode = 0;
	private String imagepath=null;
	private List<Integer> listUomId;
	private List<File> listGetFiles = null;
	private String panoramic = "";private String buttonName;
	private String param2 = "";private String param3 = "";private String param4 = "";
	private String fileDirName = ""; private String fileName = "";
	private int countProcess = 0;
	private String logMessage = "";
	private Location lastLoc2;
	
 @Override
	   public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        if (android.os.Build.VERSION.SDK_INT > 9) {
	            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	            StrictMode.setThreadPolicy(policy);
	        }
	        
	        overlayItemArray = new ArrayList<OverlayItem>(); 
	        session = new SessionManager(getApplicationContext());
	        Log.d("Lihat Map", "Masuk");
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        setContentView(R.layout.informasisites3);
	        
	        db = new DatabaseManager(LihatMap.this);
	    	db.createTableSurvey();
	        db.createDataPhoto(); 
	        db.createPhotoUploaded();
	        
	        Intent intent = getIntent();
	        HashMap<String, String> user = session.getUserDetails();		
			String userName = user.get(SessionManager.User_Name);
			String userId = user.get(SessionManager.User_Id);  
			Log.d("User Upload", userId);		
		     //Log.d("Nom Upload", String.valueOf(nomid));
	        nom_id = intent.getIntExtra("nom_id", 0);
			StatusAmbilSurvey.setUserid(userId);
			StatusAmbilSurvey.setNomid(nom_id);
	        
	        myOpenMapView = (MapView)findViewById(R.id.openmapview);
	        myOpenMapView.setTileSource(TileSourceFactory.MAPNIK);
	        myOpenMapView.setBuiltInZoomControls(true);
	        myOpenMapView.setClickable(true);
	        myMapController = myOpenMapView.getController();	        
	        myMapController.setZoom(18);
	        DefaultResourceProxyImpl defaultResourceProxyImpl = new DefaultResourceProxyImpl(this);
	        MyItemizedIconOverlay myItemizedIconOverlay = new MyItemizedIconOverlay(overlayItemArray, null, defaultResourceProxyImpl);
        	myOpenMapView.getOverlays().add(myItemizedIconOverlay);
        	locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        	Location lastLocation 
	        	= locationManager.getLastKnownLocation(
	        			LocationManager.GPS_PROVIDER);
	        if(lastLocation != null){
	        	updateLoc(lastLocation);
	        }
	        
	        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
	        myOpenMapView.getOverlays().add(myScaleBarOverlay);
	      
	        //db = new DatabaseManager(getApplicationContext());
	        nom_list si = db.getSpesific_nom(StatusAmbilSurvey.getNomid());
	        
	        //--- Create Another Overlay for multi marker
	        anotherOverlayItemArray = new ArrayList<OverlayItem>();
	        if(CommonUtil.isNotNullOrEmpty(si.get_latitude()) && CommonUtil.isNotNullOrEmpty(si.get_longitude()))
	        {
	        	anotherOverlayItemArray.add(new OverlayItem(
		        		"Latitude", "Longitude", new GeoPoint(Double.parseDouble(si.get_latitude()), Double.parseDouble(si.get_longitude())))); 
	        }
	        
	        /*
	        ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay 
	        	= new ItemizedIconOverlay<OverlayItem>(
	        			this, anotherOverlayItemArray, myOnItemGestureListener);
	        myOpenMapView.getOverlays().add(anotherItemizedIconOverlay);
	        */
	        //---
	        
	        checkPinMark();
	        
	        //Add MyLocationOverlay
	        myLocationOverlay = new MyLocationOverlay(this, myOpenMapView);
	        myOpenMapView.getOverlays().add(myLocationOverlay);
	        myOpenMapView.postInvalidate();
	        
			//Log.d("StatusAmbilSurvey setNomid ", Integer.toString(StatusAmbilSurvey.getNomid()));
			//Log.d("User Id Parse", "= "+ userId);
			//Log.d("Nom Id Parse", Integer.toString(nom_id));
			//boolean checkSurvey = db.checkSurvey(nom_id,userId);	        
	        getSiteInfo(nom_id);
	        
	        String uriX = ""; 
	        String uriY = "";
	        cd = new ConnectionDetector(this.getApplicationContext());
	        isOnline = cd.isConnectingToInternet();
	        if(isOnline){
	        	uriX = "checklist"; 
	        	}
	        else
	        {
	        	uriX = "cross"; 
	        }
	        int imageResource = getResources().getIdentifier(uriX, "drawable", getPackageName());
	        ImageView checkKoneksi = (ImageView)findViewById(R.id.checkKoneksi);
	        Drawable res = getResources().getDrawable(imageResource);
	        checkKoneksi.setImageDrawable(res);
	        
	        LocationManager manager = (LocationManager)getSystemService(Activity.LOCATION_SERVICE);
	        if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	        	uriY = "cross";
	        }
	        else{uriY = "checklist";} 
	        int imageResourceGps = getResources().getIdentifier(uriY, "drawable", getPackageName());
	        ImageView checkGps = (ImageView)findViewById(R.id.gpsLock);
	        Drawable resGps = getResources().getDrawable(imageResourceGps);
	        checkGps.setImageDrawable(resGps);	        	       
	        
	        //int hasilSurvey = getResources().getIdentifier(uriw, "drawable", getPackageName());
	        //ImageView SurveyCheck = (ImageView)findViewById(R.id.hasilSurvey);
	        //Drawable resSurvey = getResources().getDrawable(hasilSurvey);
	        //SurveyCheck.setImageDrawable(resSurvey);
	        
	        /*Button ambilSurvey = (Button) findViewById(R.id.ambilSurvey);
	        ambilSurvey.setOnClickListener(new TextView.OnClickListener(){
	            public void onClick(View v){
	            	Intent myIntent= new
	            	Intent(v.getContext(), AmbilSurvey.class);
	            	myIntent.putExtra("nom_id", nom_id);	            	
	            	startActivityForResult(myIntent, 0);
	            }
	       	});*/
			/*
			Button showPeta = (Button) findViewById(R.id.showPeta);
	        showPeta.setOnClickListener(new TextView.OnClickListener(){
	            public void onClick(View v){
	            	Intent myIntent= new
	            	Intent(v.getContext(), AndroidOpenStreetMapViewActivity.class);
	            	myIntent.putExtra("nom_id", nom_id);	            	
	            	startActivityForResult(myIntent, 0);
	            }
	       	});
	        */
	        createButtonUpload();
	        createButtonCandidate2();
	        createButtonCandidate1();
	        createButtonSurveyNom();
 }
 OnItemGestureListener<OverlayItem> myOnItemGestureListener
 = new OnItemGestureListener<OverlayItem>(){

		 
		public boolean onItemLongPress(int arg0, OverlayItem arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		 
		public boolean onItemSingleTapUp(int index, OverlayItem item) {
			Toast.makeText(LihatMap.this, 
					item.mDescription + "\n"
					+ item.mTitle + "\n"
					+ item.mGeoPoint.getLatitudeE6() + " : " + item.mGeoPoint.getLongitudeE6(), 
					Toast.LENGTH_LONG).show();
			return true;
		}
 	
 }; 
 
 protected void onResume(){
	 super.onResume();
	 createButtonUpload();
	 createButtonCandidate2();
     createButtonCandidate1();
     createButtonSurveyNom();
     
     checkPinMark();
     
 	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
 	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
 	myLocationOverlay.disableMyLocation();
 	myLocationOverlay.disableCompass();
 }
 
 private void createButtonSurveyNom(){
	 surveyNom = (Button) findViewById(R.id.surveynom);
	 surveyNom.setOnClickListener(new Button.OnClickListener(){
		 public void onClick(View v){
			 if(db.getCountDataPhotoByNomButton(StatusAmbilSurvey.getNomid(),"SURVEY") == 0){
				 db.addDataPhoto(StatusAmbilSurvey.getUserid(), StatusAmbilSurvey.getNomid(), "", "", "", "", "",0,"SURVEY","","","");
			 }
			 //StatusAmbilSurvey.setButtonnya("SURVEY");
				 Intent myIntent= new
				Intent(v.getContext(), AmbilSurvey.class);
				 myIntent.putExtra("nom_id", nom_id);
				 myIntent.putExtra("typenya", "SURVEY");
				 startActivityForResult(myIntent, 0);
	     }
    });
     
     String x = "cross";
     if(db.getCountDataPhotoByNomButton(StatusAmbilSurvey.getNomid(),"SURVEY") > 0){
    	 String uploadStatus = db.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(),"SURVEY").getUploadStatus();
    	 if(uploadStatus.equals("READY") || uploadStatus.equals("UPLOADED") || (db.getToDoStatus(StatusAmbilSurvey.getNomid()) == 1) ){
    		 x = "checklist";
    		 kandidat1.setEnabled(true);
    		 //kandidat2.setEnabled(false);
    		 //uploadData.setEnabled(true);
    	 }else{
    		 x = "cross";
    		 kandidat1.setEnabled(false);
    		 //kandidat2.setEnabled(false);
    		 //uploadData.setEnabled(false);
    	 }
     }else{
    	 kandidat1.setEnabled(false);
		 //kandidat2.setEnabled(false);
		 //uploadData.setEnabled(false);
     }
    	 int hasilUpload = getResources().getIdentifier(x, "drawable", getPackageName());
         ImageView checkUpload = (ImageView)findViewById(R.id.imgSurveyNom);
         Drawable resUpload = getResources().getDrawable(hasilUpload);
         checkUpload.setImageDrawable(resUpload);
 }
 
 private void createButtonCandidate1(){
	 kandidat1 = (Button) findViewById(R.id.ambilCandidate1);
	 kandidat1.setOnClickListener(new Button.OnClickListener(){
		 public void onClick(View v){
			 if(db.getCountDataPhotoByNomButton(StatusAmbilSurvey.getNomid(),"CANDIDATE1") == 0){
				 db.addDataPhoto(StatusAmbilSurvey.getUserid(), StatusAmbilSurvey.getNomid(), "", "", "", "", "",0,"CANDIDATE1","","","");
			 }
			 //StatusAmbilSurvey.setButtonnya("CANDIDATE1");
				 Intent myIntent= new
						 Intent(v.getContext(), AmbilSurvey.class);
				 myIntent.putExtra("nom_id", nom_id);
				 myIntent.putExtra("typenya", "CANDIDATE1");	
				 startActivityForResult(myIntent, 0);
			 
	     }
    });
     
     String x = "cross";
     if(db.getCountDataPhotoByNomButton(StatusAmbilSurvey.getNomid(),"CANDIDATE1") > 0){
    	 String uploadStatus = db.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(),"CANDIDATE1").getUploadStatus();
    	 if(uploadStatus.equals("READY") || uploadStatus.equals("UPLOADED") || (db.getToDoStatus(StatusAmbilSurvey.getNomid()) == 1) ){
    		 x = "checklist";
    		 kandidat2.setEnabled(true);
    	 }else{
    		 x = "cross";
    		 kandidat2.setEnabled(false);
    	 }
     }else{
    	 kandidat2.setEnabled(false);
     }
    	 int hasilUpload = getResources().getIdentifier(x, "drawable", getPackageName());
         ImageView checkUpload = (ImageView)findViewById(R.id.imgUpCandidate1);
         Drawable resUpload = getResources().getDrawable(hasilUpload);
         checkUpload.setImageDrawable(resUpload);
 }
 
 private void createButtonCandidate2(){
	 kandidat2 = (Button) findViewById(R.id.ambilCandidate2);
	 kandidat2.setOnClickListener(new Button.OnClickListener(){
		 public void onClick(View v){
			 if(db.getCountDataPhotoByNomButton(StatusAmbilSurvey.getNomid(),"CANDIDATE2") == 0){
				 db.addDataPhoto(StatusAmbilSurvey.getUserid(), StatusAmbilSurvey.getNomid(), "", "", "", "", "",0,"CANDIDATE2","","","");
			 }
			 //StatusAmbilSurvey.setButtonnya("CANDIDATE2");
			 Intent myIntent= new
	         Intent(v.getContext(), AmbilSurvey.class);
	         myIntent.putExtra("nom_id", nom_id);	  
	         myIntent.putExtra("typenya", "CANDIDATE2");
	         startActivityForResult(myIntent, 0);
	     }
    });
     
     String x = "cross";
     if(db.getCountDataPhotoByNomButton(StatusAmbilSurvey.getNomid(),"CANDIDATE2") > 0){
    	 String uploadStatus = db.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(),"CANDIDATE2").getUploadStatus();
    	 if(uploadStatus.equals("READY") || uploadStatus.equals("UPLOADED") || (db.getToDoStatus(StatusAmbilSurvey.getNomid()) == 1) ){
    		 x = "checklist";
    	 }else{
    		 x = "cross";
    	 }
     }
    	 int hasilUpload = getResources().getIdentifier(x, "drawable", getPackageName());
         ImageView checkUpload = (ImageView)findViewById(R.id.imgUpCandidate2);
         Drawable resUpload = getResources().getDrawable(hasilUpload);
         checkUpload.setImageDrawable(resUpload);
 }
 
 private void createButtonUpload(){
	 uploadData = (Button) findViewById(R.id.uploadData);
     uploadData.setOnClickListener(new Button.OnClickListener(){
         public void onClick(View v){
         	//get Internet status
             isInternetPresent = cd.isConnectingToInternet();

             // check for Internet status
        		if(isInternetPresent){
        			//new uploadProcess().execute();
        			new uploadProcessWithProgressBar().execute();
                    Log.d("Button Onclick","Masuk");
        		}else{
        			Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        		}
         }
    	});
     
     String x = "cross";
     //logMessage += "davin cek after refresh : "+StatusAmbilSurvey.getNomid();
     if(db.getCountDataPhotoReadyUpload(StatusAmbilSurvey.getNomid()) == 3){
    	 x = "cross";
    	 uploadData.setEnabled(true);
     }else if( (db.getCountDataPhotoUloadedUpload(StatusAmbilSurvey.getNomid()) == 3) ||
    		 (db.getToDoStatus(StatusAmbilSurvey.getNomid()) == 1)
    		 ){
    	 x = "checklist";
    	 uploadData.setEnabled(false);
     }else{
    	 if((db.getCountDataPhotoReadyUpload(StatusAmbilSurvey.getNomid()) > 0) && (db.getCountDataPhotoUloadedUpload(StatusAmbilSurvey.getNomid()) > 0)){
    		 x = "cross";
        	 uploadData.setEnabled(true);
    	 }else{
    		 x = "cross";
        	 uploadData.setEnabled(false);
    	 }
    	 
     }
     
    	 int hasilUpload = getResources().getIdentifier(x, "drawable", getPackageName());
         ImageView checkUpload = (ImageView)findViewById(R.id.imgUploadData);
         Drawable resUpload = getResources().getDrawable(hasilUpload);
         checkUpload.setImageDrawable(resUpload);
 }
 
private void getSiteInfo(int nom_id) {
	// TODO Auto-generated method stub

	List<site_info>site_info = db.get_siteInfo(nom_id);
	for (site_info dataNom : site_info) {
		showSiteId = (TextView)findViewById(R.id.showSiteID);
		showSiteId.setText(dataNom.get_site_id());
		
		showSiteName = (TextView)findViewById(R.id.showSiteName);
		showSiteName.setText(dataNom.get_site_name());
		
		showLatitude = (TextView)findViewById(R.id.showLatitude);
		showLatitude.setText(DecimalUtil.toString(Double.valueOf(dataNom.get_latitude())));
		Log.d("davin-test-doank",dataNom.get_latitude());
		showLogitude = (TextView)findViewById(R.id.showLongitude);
		showLogitude.setText(DecimalUtil.toString(Double.valueOf(dataNom.get_longitude())));
		
		showProvince = (TextView)findViewById(R.id.showProvince);
		showProvince.setText(dataNom.get_site_province());
		
		showCity = (TextView)findViewById(R.id.showCity);
		showCity.setText(dataNom.get_city());		
		}
	}

 public void doRefresh(){
	 Intent refresh = new Intent(getApplicationContext(), LihatMap.class);
     startActivity(refresh);
     this.finish();
 }
 
 public List<Integer> getAllSurveyNom(){
	 listUomId = new ArrayList<Integer>();
	 db = new DatabaseManager(this);
	 List<DataPhoto> listDp = db.getListAllSurveyByUser(StatusAmbilSurvey.getUserid());
	 for(DataPhoto dp : listDp){
		 listUomId.add(dp.getNomid());
	 }
	 
	 return listUomId;
 }
 
 @SuppressWarnings("resource")
 public void uploadFile(String sourceFileUri, String name) throws ResponseCodeException {
      
 	  String fileName = sourceFileUri;

      HttpURLConnection conn = null;
      DataOutputStream dos = null;  
      String lineEnd = "\r\n";
      String twoHyphens = "--";
      String boundary = "*****";
      int bytesRead, bytesAvailable, bufferSize;
      byte[] buffer;
      int maxBufferSize = 1 * 2048 * 2048; 
      File sourceFile = new File(sourceFileUri); 
      
      if (!sourceFile.isFile()) {
    	  
 	           //dialog.dismiss(); 
 	           
 	           Log.e("uploadFile", "Source File not exist :"+imagepath);
 	           
 	           this.runOnUiThread(new Runnable() {
 	               public void run() {
 	            	   if (progressDialog.isShowing()) {
 	                		  progressDialog.dismiss();
 	          			}
 	                      Toast.makeText(getApplicationContext(), "Source File not exist : "+imagepath, Toast.LENGTH_SHORT).show();
 	               }
 	           }); 
 	           
 	           //return 0;
       
      }
      else
      {
 	           try { 
 	            	
 	               FileInputStream fileInputStream = new FileInputStream(sourceFile);
 	               URL url = new URL("http://bts.devtuwaga.com/api_services/test_upload");
 	               
 	               conn = (HttpURLConnection) url.openConnection(); 
 	               conn.setDoInput(true); 
 	               conn.setDoOutput(true); 
 	               conn.setUseCaches(false);
 	               conn.setRequestMethod("POST");
 	               conn.setRequestProperty("Connection", "Keep-Alive");
 	               conn.setRequestProperty("ENCTYPE", "multipart/form-data");
 	               conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
 	               conn.setRequestProperty("uploaded_file", fileName);
 	               //conn.setRequestProperty("name", fileName);
 	               logMessage += "davin-masuk-upload : "+fileName+"\n \n";
 	               
 	               dos = new DataOutputStream(conn.getOutputStream());
 	               Log.d("DOS", dos.toString());
 	               dos.writeBytes(twoHyphens + boundary + lineEnd); 
 	               dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
 	            		                     + fileName + "\"" + lineEnd);
 	               
 	               dos.writeBytes(lineEnd);
 	     
 	            
 	               bytesAvailable = fileInputStream.available(); 
 	     
 	               bufferSize = Math.min(bytesAvailable, maxBufferSize);
 	               buffer = new byte[bufferSize];
 	     
 	              
 	               bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
 	                 
 	               while (bytesRead > 0) {
 	            	   
 	                 dos.write(buffer, 0, bufferSize);
 	                 bytesAvailable = fileInputStream.available();
 	                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
 	                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
 	                 
 	                }
 	     
 	               
 	               dos.writeBytes(lineEnd);
 	               dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
 	     
 	              
 	               serverResponseCode = conn.getResponseCode();
 	               String serverResponseMessage = conn.getResponseMessage();
 	                Log.d("Server Response", serverResponseMessage);
 	               Log.i("uploadFile", "HTTP Response is : " 
 	            		   + serverResponseMessage + ": " + serverResponseCode);
 	               logMessage += "davin-response-code : "+serverResponseMessage+" "+serverResponseCode+"\n \n";
 	               
 	               if(serverResponseCode == 200){
 	            	   
 	            	   /*this.runOnUiThread(new Runnable() {
 	                        public void run() {
 	                        	
 	                            Toast.makeText(getApplicationContext(), "Files Upload Completed.", Toast.LENGTH_SHORT).show();
 	                        }
 	                    });     */           
 	               }else{
 	            	   //checkHttpResponseServerError = 1;
 	            	   //throw new ResponseCodeException("Terjadi error pada upload survey. Hubungi administrator.");
 	               }
 	               
 	               fileInputStream.close();
 	               dos.flush();
 	               dos.close();
 	               
 	              db.insertPhotoUploaded(StatusAmbilSurvey.getUserid(), StatusAmbilSurvey.getNomid(),fileName);
 	                
 	          /*} catch (MalformedURLException ex) {
 	        	  
 	              //dialog.dismiss();  
 	              ex.printStackTrace();
 	              
 	              this.runOnUiThread(new Runnable() {
 	                  public void run() {
 	                      Toast.makeText(getApplicationContext(), "Upload Gagal", Toast.LENGTH_SHORT).show();
 	                  }
 	              });
 	              
 	              Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
 	          */} catch (Exception e) {
 	        	 checkHttpResponseServerError = 1;
 	        	 logMessage += "davin-test-exception : "+e.getMessage()+"\n";
 	        	 throw new ResponseCodeException("Terjadi error pada upload survey. Hubungi administrator.");
 	              //dialog.dismiss();  
 	              /*e.printStackTrace();
 	              this.runOnUiThread(new Runnable() {
 	                  public void run() {
 	                	 checkHttpResponseServerError = 1;
 	                	  if (progressDialog.isShowing()) {
 	                		  progressDialog.dismiss();
 	          			}
 	                      Toast.makeText(getApplicationContext(), "Terjadi error pada upload survey. Hubungi administrator.", Toast.LENGTH_SHORT).show();
 	                  }
 	              });
 	              Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e);  */
 	          }
 	          //dialog.dismiss();
 	          //return serverResponseCode; 
 	          
       }  
     }
 
 private void saveParameterPhotos(String userid, int nomid, String pnoramic, String param2, String param3, String param4, String param5, String button, String longnya, String latnya) throws ResponseCodeException{
     try{
     List<NameValuePair> params = new ArrayList<NameValuePair>();
     Log.d("User Upload", userid);
     Log.d("Nom Upload", Integer.toString(StatusAmbilSurvey.getNomid()));
     Log.d("Nom Upload nom_idm", Integer.toString(nom_id));
     Log.d("Nom Upload String.valueOf(nomid)", String.valueOf(nomid));
     
     params.add(new BasicNameValuePair("id", userid));
     params.add(new BasicNameValuePair("nom_id", String.valueOf(nomid)));
     params.add(new BasicNameValuePair("panorama", pnoramic));
     params.add(new BasicNameValuePair("param_2", param2));
     params.add(new BasicNameValuePair("param_3", param3));
     params.add(new BasicNameValuePair("param_4", param4));
     params.add(new BasicNameValuePair("checklis", param5));
     params.add(new BasicNameValuePair("type", button));
     params.add(new BasicNameValuePair("long", longnya));
     params.add(new BasicNameValuePair("lat", latnya));
     Log.d("Param", params.toString());
     JSONObject json = jsonParser.makeHttpRequest("http://bts.devtuwaga.com/api_services/upload_nom_survey", "POST", params);
     //Log.d("XXXX", json.toString());
     //db = new DatabaseManager(getApplicationContext());
     checkHttpResponseServerError = 0;
     }catch(Exception e){
    	 logMessage += "davin-test-exception : "+e.getMessage()+"\n";
     	 throw new ResponseCodeException("Terjadi error pada upload survey. Hubungi administrator.");
     }
     
 }
 
 private void saveParameterPhotos2(DataPhoto dp) throws ResponseCodeException{
     try{
     List<NameValuePair> params = new ArrayList<NameValuePair>();
     
     params.add(new BasicNameValuePair("id", dp.getUserid()));
     params.add(new BasicNameValuePair("nom_id", String.valueOf(dp.getNomid())));
     params.add(new BasicNameValuePair("panorama", dp.getPanoramic()));
     params.add(new BasicNameValuePair("param_2", dp.getParam_2()));
     params.add(new BasicNameValuePair("param_3", dp.getParam_3()));
     params.add(new BasicNameValuePair("param_4", dp.getParam_4()));
     params.add(new BasicNameValuePair("checklis", dp.getParam_5()));
     params.add(new BasicNameValuePair("type", dp.getButtonnya()));
     params.add(new BasicNameValuePair("long", dp.getLongt()));
     params.add(new BasicNameValuePair("lat", dp.getLat()));
     Log.d("Param", params.toString());
     JSONObject json = jsonParser.makeHttpRequest("http://bts.devtuwaga.com/api_services/upload_nom_survey", "POST", params);
     //Log.d("XXXX", json.toString());
     //db = new DatabaseManager(getApplicationContext());
     checkHttpResponseServerError = 0;
     }catch(Exception e){
    	 logMessage += "davin-test-exception : "+e.getMessage()+"\n";
     	 throw new ResponseCodeException("Terjadi error pada upload survey. Hubungi administrator.");
     }
     
 }
 
 private void updateLoc(Location loc){
		GeoPoint locGeoPoint = new GeoPoint(loc.getLatitude(), loc.getLongitude());
		myMapController.setCenter(locGeoPoint);
		
		setOverlayLoc(loc);
		
		myOpenMapView.invalidate();
	}
private void setOverlayLoc(Location overlayloc){
		GeoPoint overlocGeoPoint = new GeoPoint(overlayloc);
		//---
		overlayItemArray.clear();
		
		OverlayItem newMyLocationItem = new OverlayItem(
				"My Location", "My Location", overlocGeoPoint);
		overlayItemArray.add(newMyLocationItem);
		//---
	}

private LocationListener myLocationListener
= new LocationListener(){

	 
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		updateLoc(location);
	}

	 
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	 
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
};


private class MyItemizedIconOverlay extends ItemizedIconOverlay<OverlayItem>{

		public MyItemizedIconOverlay(
				List<OverlayItem> pList,
				org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<OverlayItem> pOnItemGestureListener,
				ResourceProxy pResourceProxy) {
			super(pList, pOnItemGestureListener, pResourceProxy);
			// TODO Auto-generated constructor stub
		}
	 
	 
	 
		@Override
		public void draw(Canvas canvas, MapView mapview, boolean arg2) {
			// TODO Auto-generated method stub
			super.draw(canvas, mapview, arg2);
			
			if(!overlayItemArray.isEmpty()){
				//overlayItemArray have only ONE element only, so I hard code to get(0)
				GeoPoint in = overlayItemArray.get(0).getPoint();
				
				Point out = new Point();
				mapview.getProjection().toPixels(in, out);
				
				Bitmap bm = BitmapFactory.decodeResource(getResources(), 
						R.drawable.my_location);
				canvas.drawBitmap(bm, 
						out.x - bm.getWidth()/2, 	//shift the bitmap center
						out.y - bm.getHeight()/2, 	//shift the bitmap center
						null);
			}
			
			if(!anotherOverlayItemArray.isEmpty())
			{
				GeoPoint in = anotherOverlayItemArray.get(0).getPoint();
				
				Point out = new Point();
				mapview.getProjection().toPixels(in, out);
				
				Bitmap bm = BitmapFactory.decodeResource(getResources(), 
						R.drawable.nom_location);
				canvas.drawBitmap(bm, 
						out.x - bm.getWidth()/2, 	//shift the bitmap center
						out.y - bm.getHeight()/2, 	//shift the bitmap center
						null);
			}
			
			if(!anotherOverlayItemArray1.isEmpty())
			{
				GeoPoint in2 = anotherOverlayItemArray1.get(0).getPoint();
				
				Point out2 = new Point();
				mapview.getProjection().toPixels(in2, out2);
				
				Bitmap bm2 = BitmapFactory.decodeResource(getResources(), 
						R.drawable.candidate_location);
				canvas.drawBitmap(bm2, 
						out2.x - bm2.getWidth()/2, 	//shift the bitmap center
						out2.y - bm2.getHeight()/2, 	//shift the bitmap center
						null);
			}
			
			if(!anotherOverlayItemArray2.isEmpty())
			{
				GeoPoint in2 = anotherOverlayItemArray2.get(0).getPoint();
				
				Point out2 = new Point();
				mapview.getProjection().toPixels(in2, out2);
				
				Bitmap bm2 = BitmapFactory.decodeResource(getResources(), 
						R.drawable.candidate_location);
				canvas.drawBitmap(bm2, 
						out2.x - bm2.getWidth()/2, 	//shift the bitmap center
						out2.y - bm2.getHeight()/2, 	//shift the bitmap center
						null);
			}
			
			if(!anotherOverlayItemArray3.isEmpty())
			{
				GeoPoint in3 = anotherOverlayItemArray3.get(0).getPoint();
				
				Point out3 = new Point();
				mapview.getProjection().toPixels(in3, out3);
				
				Bitmap bm3 = BitmapFactory.decodeResource(getResources(), 
						R.drawable.candidate_location);
				canvas.drawBitmap(bm3, 
						out3.x - bm3.getWidth()/2, 	//shift the bitmap center
						out3.y - bm3.getHeight()/2, 	//shift the bitmap center
						null);
			}
			
		}
	 
	@Override
	public boolean onSingleTapUp(MotionEvent event, MapView mapView) {
		// TODO Auto-generated method stub
		//return super.onSingleTapUp(event, mapView);
		return true;
	}
}

private class uploadProcess extends AsyncTask<String, Void, Boolean> {
	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(LihatMap.this, "", "Uploading photos to server...", true);
	}
	
	protected void onPostExecute(final Boolean Suksess) {
		
		if (progressDialog.isShowing()) {
	 		progressDialog.dismiss();
	 	}
		
		if(db.getCountDataPhotoUloadedUpload(StatusAmbilSurvey.getNomid()) == 3){
			db.updateToDo(1, StatusAmbilSurvey.getNomid());
		}
		
		createButtonUpload();
		//doRefresh();
	}

	protected Boolean doInBackground(final String... args) {
		File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + ImageUtils.TAKE_AND_SAVE_PHOTO);
		Collections.addAll(files, root.listFiles());
		logMessage = "";
		logMessage += "upload without Thread \n";
		logMessage += "davin-test-jumlah-nom : "+String.valueOf(db.getListAllSurveyDataByNom(StatusAmbilSurvey.getNomid()).size())+"\n";
		
		for(DataPhoto x : db.getListAllSurveyDataByNomStatus(StatusAmbilSurvey.getNomid())){
			buttonName = x.getButtonnya();
			panoramic=""; param2=""; param3=""; param4="";
			listGetFiles = new ArrayList<File>();
		for (File file : files) {
			
			String[] parts = file.getName().split("_");
			if(
					parts[0].equals(StatusAmbilSurvey.getUserid()) && parts[1].equals("PANORAMIC") &&
					parts[2].equals(String.valueOf(x.getNomid())) && parts[3].equals(String.valueOf(x.getButtonnya()))
					){
				panoramic += file.getName()+";";
				listGetFiles.add(file);
				logMessage += "davin-test-get-file : "+x.getButtonnya()+" "+file.getName()+"\n";
			}else if( 
					parts[0].equals(StatusAmbilSurvey.getUserid()) && parts[1].equals("AKSESPLN") &&
					parts[2].equals(String.valueOf(x.getNomid())) && parts[3].equals(String.valueOf(x.getButtonnya()))
					){
				param2 += file.getName();
				listGetFiles.add(file);
				logMessage += "davin-test-get-file : "+x.getButtonnya()+" "+file.getName()+"\n";
			}else if( 
					parts[0].equals(StatusAmbilSurvey.getUserid()) && parts[1].equals("POLEPLN") &&
					parts[2].equals(String.valueOf(x.getNomid())) && parts[3].equals(String.valueOf(x.getButtonnya()))
					){
				param3 += file.getName();
				listGetFiles.add(file);
				logMessage += "davin-test-get-file : "+x.getButtonnya()+" "+file.getName()+"\n";
			}else if( 
					parts[0].equals(StatusAmbilSurvey.getUserid()) && parts[1].equals("KABELPLN") &&
					parts[2].equals(String.valueOf(x.getNomid())) && parts[3].equals(String.valueOf(x.getButtonnya()))
					){
				param4 += file.getName();
				listGetFiles.add(file);
				logMessage += "davin-test-get-file : "+x.getButtonnya()+" "+file.getName()+"\n";
			}
		}
		
		
		//new Thread(new Runnable() {
			//public void run() {
			int panjang = listGetFiles.size();
			for(int a=0; a<panjang; a++){
	  		fileDirName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + ImageUtils.TAKE_AND_SAVE_PHOTO).toString() +"/"+ listGetFiles.get(a).getName();
	  		fileName = listGetFiles.get(a).getName();
	  		//new Thread(new Runnable() {
	        //public void run() {
	        	 
	         		try {
	         			logMessage += "davin-proses-upload-file : "+buttonName+" "+fileName+"\n\n";
	         			if(db.getCountPhotoUploaded(fileName)==0){
	         				uploadFile(fileDirName, fileName);
	         			}
						//Thread.sleep(5000);
					} catch (ResponseCodeException e) {
						if (progressDialog.isShowing()) {
							progressDialog.dismiss();
		    			}
						checkHttpResponseServerError = 1;
						logMessage += "davin-test-exception : "+e.getMessage()+"\n";
			            Toast.makeText(getApplicationContext(), "Terjadi error pada upload survey. Hubungi administrator.", Toast.LENGTH_LONG).show();
			            break;
					}
	          	
	         //}
	  		//}).start();
	  		}
			logMessage += "davin-upload-parameter : "+x.getButtonnya()+" "+String.valueOf(StatusAmbilSurvey.getUserid())+" "+String.valueOf(StatusAmbilSurvey.getNomid())+" "+panoramic+" "+param2+" "+param3+" "+param4+" "+buttonName+"\n";
			//++countProcess;
			if(checkHttpResponseServerError == 0)
			{
				try {
					saveParameterPhotos(StatusAmbilSurvey.getUserid(), StatusAmbilSurvey.getNomid(), panoramic, param2, param3, param4, x.getParam_5(), buttonName, x.getLongt(), x.getLat());
					db.updateDataPhotoByButton("upload_status", buttonName, "UPLOADED", StatusAmbilSurvey.getNomid());
				} catch (ResponseCodeException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
	    			}
					Toast.makeText(getApplicationContext(), "Terjadi error pada upload survey. Hubungi administrator.", Toast.LENGTH_LONG).show();
					break;
				}             	
			}else{
				break;
			}
		  	logMessage += "davin-beres-upload : "+x.getButtonnya()+"\n \n";
		//}
	    //}).start();
	  	
		}
		
		
		logMessage += "davin-upload-end \n";
			
		createLog(logMessage);
		return true;
	}
}

private void createLog(String msg){
	FileHandler fh=null;
    String name;
    if ( 0 == Environment.getExternalStorageState().compareTo(Environment.MEDIA_MOUNTED))
        name = Environment.getExternalStorageDirectory().getAbsolutePath();
    else
        name = Environment.getDataDirectory().getAbsolutePath();

    name += "/LihatMap.log";

    try {
        fh = new FileHandler(name, 256*1024, 1, true);
        fh.setFormatter(new SimpleFormatter());
        fh.publish(new LogRecord(Level.ALL, " : "+msg));
    } catch (Exception e) 
    {
        Log.e("LihatMap", "FileHandler exception", e);
        return;
    }finally{
        if (fh != null)
            fh.close();
    }
}
@Override
public void onBackPressed()
{ 
     // code here to show dialog
     super.onBackPressed();  // optional depending on your needs 
     finish();
     
}

private void checkPinMark(){
	anotherOverlayItemArray1 = new ArrayList<OverlayItem>();
    if(db.getCountDataPhotoByNomButton(StatusAmbilSurvey.getNomid(),"SURVEY") > 0){
     	DataPhoto dp = db.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), "SURVEY");
     	if(CommonUtil.isNotNullOrEmpty(dp.getLat()) && CommonUtil.isNotNullOrEmpty(dp.getLongt()))
     	{
     		//logMessage += "hasil CANDIDATE1 latlong : "+dp.getLat()+"-"+dp.getLongt()+"\n";
	        	//anotherOverlayItemArray.add(new OverlayItem("Latitude", "Longitude", new GeoPoint(-6.217236, 106.810569)));
	        	anotherOverlayItemArray1.add(new OverlayItem("Latitude", "Longitude", new GeoPoint(Double.parseDouble(dp.getLat()), Double.parseDouble(dp.getLongt()))));
	        	
     	}
     }
    
    anotherOverlayItemArray2 = new ArrayList<OverlayItem>();
    if(db.getCountDataPhotoByNomButton(StatusAmbilSurvey.getNomid(),"CANDIDATE1") > 0){
     	DataPhoto dp = db.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), "CANDIDATE1");
     	if(CommonUtil.isNotNullOrEmpty(dp.getLat()) && CommonUtil.isNotNullOrEmpty(dp.getLongt()))
     	{
     		//logMessage += "hasil CANDIDATE1 latlong : "+dp.getLat()+"-"+dp.getLongt()+"\n";
	        	//anotherOverlayItemArray.add(new OverlayItem("Latitude", "Longitude", new GeoPoint(-6.217236, 106.810569)));
	        	anotherOverlayItemArray2.add(new OverlayItem("Latitude", "Longitude", new GeoPoint(Double.parseDouble(dp.getLat()), Double.parseDouble(dp.getLongt()))));
	        	
     	}
     }
     
    anotherOverlayItemArray3 = new ArrayList<OverlayItem>();
     if(db.getCountDataPhotoByNomButton(StatusAmbilSurvey.getNomid(),"CANDIDATE2") > 0){
     	DataPhoto dp = db.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), "CANDIDATE2");
     	if(CommonUtil.isNotNullOrEmpty(dp.getLat()) && CommonUtil.isNotNullOrEmpty(dp.getLongt()))
     	{
     		//logMessage += "hasil CANDIDATE2 latlong : "+dp.getLat()+"-"+dp.getLongt()+"\n";
	        	//anotherOverlayItemArray.add(new OverlayItem("Latitude", "Longitude", new GeoPoint(-6.218597, 106.801767)));
	        	anotherOverlayItemArray3.add(new OverlayItem("Latitude", "Longitude", new GeoPoint(Double.parseDouble(dp.getLat()), Double.parseDouble(dp.getLongt()))));
	        
     	}
     }
}

private class uploadProcessWithProgressBar extends AsyncTask<String, Void, Boolean> {

	protected void onPreExecute() {
		listDataNomToUpload.clear();
		listPhotosToUpload.clear();
		listDataNomToUpload = db.getListAllSurveyDataByNomStatus(StatusAmbilSurvey.getNomid());
		int countAll360 = 0; int batas = 0;
		
		for(DataPhoto dp : listDataNomToUpload){
			String split[] = dp.getPanoramic().split(";");
			int countSplit = split.length;
			countAll360 += countSplit;
			for(String s : split){
				batas += 1;
				listPhotosToUpload.add(s);
			}
			listPhotosToUpload.add(dp.getParam_2());
			listPhotosToUpload.add(dp.getParam_3());
			listPhotosToUpload.add(dp.getParam_4());
			batas += 3;
			if(dp.getButtonnya().equals("SURVEY")){
				DataPhotoCAN1 = dp;
				batasCAN1 = batas;
			}else if(dp.getButtonnya().equals("CANDIDATE1")){
				DataPhotoCAN2 = dp;
				batasCAN2 = batas;
			}else if(dp.getButtonnya().equals("CANDIDATE2")){
				DataPhotoCAN3 = dp;
				batasCAN3 = batas;
			}
		}
		
		countAllPhotos = countAll360 + 9;
		
		logMessage += "upload with progress bar \n";
		logMessage += "total upload data "+countAllPhotos+"\n";
		
		
		progressBar = new ProgressDialog(LihatMap.this);
		progressBar.setCancelable(false);
		progressBar.setMessage("Uploading ...");
		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBar.setProgress(0);
		progressBar.setMax(countAllPhotos);
		progressBar.show();
	}
	
	protected void onPostExecute(Boolean Suksess) {
		if(Suksess){
			progressBar.dismiss();
			
			logMessage += "davin-proses-upload-beres : \n\n";
		
		
			if(db.getCountDataPhotoUloadedUpload(StatusAmbilSurvey.getNomid()) == 3){
				db.updateToDo(1, StatusAmbilSurvey.getNomid());
			}
		
			createButtonUpload();
			//createLog(logMessage2);
			createLog(logMessage);
		}else{
			Toast.makeText(getApplicationContext(), "Terjadi error pada upload survey. Hubungi administrator.", Toast.LENGTH_LONG).show();
		}
	}

	protected Boolean doInBackground(final String... args) {
		//new Thread(new Runnable() {
			  //public void run() {
				while (progressBarStatus < countAllPhotos) {
				  // process some tasks
					fileDirName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + ImageUtils.TAKE_AND_SAVE_PHOTO).toString() +"/"+ listPhotosToUpload.get(progressBarStatus);
			  		fileName = listPhotosToUpload.get(progressBarStatus);
			  		logMessage += "davin-proses-upload-file : "+fileName+"\n\n";
			         	try {
			         		
			         		if(db.getCountPhotoUploaded(fileName)==0){
			         			uploadFile(fileDirName, fileName);
			         			
			         		}
						} catch (ResponseCodeException e) {
							if (progressBar.isShowing()) {
								progressBar.dismiss();
				    		}
							checkHttpResponseServerError = 1;
							logMessage += "davin-test-exception : "+e.getMessage()+"\n";
					        Toast.makeText(getApplicationContext(), "Terjadi error pada upload survey. Hubungi administrator.", Toast.LENGTH_LONG).show();
					        break;
						}
			         	
			         	if(progressBarStatus == (batasCAN1-1)){
			         		logMessage += "davin-proses-save-parameter : "+DataPhotoCAN1.getButtonnya()+" "+DataPhotoCAN1.getPanoramic()+" "+DataPhotoCAN1.getParam_2()+" "+DataPhotoCAN1.getParam_3()+" "+DataPhotoCAN1.getParam_4()+" "+"\n\n";
			         		try {
			         			if(checkHttpResponseServerError == 0){
			         				saveParameterPhotos2(DataPhotoCAN1);
			         				db.updateDataPhotoByButton("upload_status", DataPhotoCAN1.getButtonnya(), "UPLOADED", StatusAmbilSurvey.getNomid());
			         			}
							} catch (ResponseCodeException e) {
								// TODO Auto-generated catch block
								if (progressBar.isShowing()) {
									progressBar.dismiss();
					    		}
								checkHttpResponseServerError = 1;
								logMessage += "davin-test-exception : "+e.getMessage()+"\n";
						        Toast.makeText(getApplicationContext(), "Terjadi error pada upload survey. Hubungi administrator.", Toast.LENGTH_LONG).show();
						        break;
							}
			         	}else if(progressBarStatus == (batasCAN2-1)){
			         		logMessage += "davin-proses-save-parameter : "+DataPhotoCAN2.getButtonnya()+" "+DataPhotoCAN2.getPanoramic()+" "+DataPhotoCAN2.getParam_2()+" "+DataPhotoCAN2.getParam_3()+" "+DataPhotoCAN2.getParam_4()+" "+"\n\n";
			         		try {
			         			if(checkHttpResponseServerError == 0){
			         				saveParameterPhotos2(DataPhotoCAN2);
			         				db.updateDataPhotoByButton("upload_status", DataPhotoCAN2.getButtonnya(), "UPLOADED", StatusAmbilSurvey.getNomid());
			         			}
							} catch (ResponseCodeException e) {
								// TODO Auto-generated catch block
								if (progressBar.isShowing()) {
									progressBar.dismiss();
					    		}
								checkHttpResponseServerError = 1;
								logMessage += "davin-test-exception : "+e.getMessage()+"\n";
						        Toast.makeText(getApplicationContext(), "Terjadi error pada upload survey. Hubungi administrator.", Toast.LENGTH_LONG).show();
						        break;
							}
			         	}else if(progressBarStatus == (batasCAN3-1)){
			         		logMessage += "davin-proses-save-parameter : "+DataPhotoCAN3.getButtonnya()+" "+DataPhotoCAN3.getPanoramic()+" "+DataPhotoCAN3.getParam_2()+" "+DataPhotoCAN3.getParam_3()+" "+DataPhotoCAN3.getParam_4()+" "+"\n\n";
			         		try {
			         			if(checkHttpResponseServerError == 0){
			         				saveParameterPhotos2(DataPhotoCAN3);
			         				db.updateDataPhotoByButton("upload_status", DataPhotoCAN3.getButtonnya(), "UPLOADED", StatusAmbilSurvey.getNomid());
			         			}
							} catch (ResponseCodeException e) {
								// TODO Auto-generated catch block
								if (progressBar.isShowing()) {
									progressBar.dismiss();
					    		}
								checkHttpResponseServerError = 1;
								logMessage += "davin-test-exception : "+e.getMessage()+"\n";
						        Toast.makeText(getApplicationContext(), "Terjadi error pada upload survey. Hubungi administrator.", Toast.LENGTH_LONG).show();
						        break;
							}
			         	}
			         	
			         	progressBarStatus += 1;
			         	if( (progressBarStatus <= batasCAN1) && (batasCAN1 > 0) ){ 
			         		statusProgressBarNow = "Kandidat 1";
			         	}else if( (progressBarStatus <= batasCAN2) && (progressBarStatus > batasCAN1) && (batasCAN2 > 0) ){
			         		statusProgressBarNow = "Kandidat 2";
			         	}else if( (progressBarStatus <= batasCAN3) && (progressBarStatus > batasCAN2) && (batasCAN3 > 0) ){
			         		statusProgressBarNow = "Kandidat 3";
			         	}

				  // your computer is too fast, sleep 1 second
				  try {
					Thread.sleep(1000);
				  } catch (InterruptedException e) {
					e.printStackTrace();
				  }

				  // Update the progress bar
				  progressBarHandler.post(new Runnable() {
					public void run() {
						progressBar.setProgress(progressBarStatus);
						progressBar.setMessage("Uploading Data "+statusProgressBarNow+" ...");
					}
				  });
				}//end while

				// ok, file is downloaded,
				if (progressBarStatus >= countAllPhotos) {
					
					// sleep 2 seconds, so that you can see the 100%
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					// close the progress bar dialog
					
				}
			  //}
			  
		       //}).start();
				if(checkHttpResponseServerError == 0){
					return true;
				}else{
					return false;
				}
		
	}
}

}
		
	
		