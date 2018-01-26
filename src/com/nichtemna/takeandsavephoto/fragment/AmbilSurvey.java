package com.nichtemna.takeandsavephoto.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

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

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView; 

import com.nichtemna.takeandsavephoto.PhotoActivity;
import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.fungsi.ConnectionDetector;
import com.nichtemna.takeandsavephoto.fungsi.CountDistance;
import com.nichtemna.takeandsavephoto.fungsi.DatabaseManager;
import com.nichtemna.takeandsavephoto.fungsi.nom_list;
import com.nichtemna.takeandsavephoto.fungsi.site_info;


public class AmbilSurvey extends Activity {
	private int nom_id; private String typenya;
	private MapView myOpenMapView;
	private MapController myMapController;
	ArrayList<OverlayItem> anotherOverlayItemArray; 
	ArrayList<OverlayItem> anotherOverlayItemArray1; 
	LocationManager locationManager;
	MyLocationOverlay myLocationOverlay = null;
	ArrayList<OverlayItem> overlayItemArray;
	private ImageView imgViewChecklist1, imgViewChecklist2, imgViewChecklist3, imgViewChecklist4;
	private ImageView imgViewCross1, imgViewCross2, imgViewCross3, imgViewCross4;
	private DatabaseManager dm;
	private TextView showSiteId;
	private TextView showSiteName;
	private TextView showLatitude;
	private TextView showLogitude;
	private TextView showProvince;
	private TextView showCity;
	private TextView showCurrentLong;
	private TextView showCurrentLat;
	private TextView showDistanceFromNom;
	private Button ambilPanoramic, aksesPln, polePln, jalurKabelPln, simpanLanjutkan, marker , checkList;
	private Location lastLoc2;
	private double myLat, myLong, nomLat, nomLong;
	 
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        ConnectionDetector cd;
	    	Boolean isOnline = false; 
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        setContentView(R.layout.daftarnom2);
	        Log.d("AmbilSurvey", "Masuk");
	        Intent intent = getIntent();
	        nom_id = intent.getIntExtra("nom_id", 0);
	        StatusAmbilSurvey.setNomid(nom_id);
	        typenya = intent.getStringExtra("typenya");
	        StatusAmbilSurvey.setButtonnya(typenya);
	        dm = new DatabaseManager(AmbilSurvey.this);
	        /*if(StatusAmbilSurvey.getStatusDB().isEmpty()){
	        	dm.createDataPhoto();
	        	StatusAmbilSurvey.setStatusDB("CREATE");
	        }*/
	         
	         Log.d("AmbilSurvey", "Desc Constructor");
	         myOpenMapView = (MapView)findViewById(R.id.openmapview);
	         myOpenMapView.setTileSource(TileSourceFactory.MAPNIK);
	         myOpenMapView.setBuiltInZoomControls(true);
	         myOpenMapView.setClickable(true);
	         myMapController = myOpenMapView.getController();
	         myMapController.setZoom(18);
	         DefaultResourceProxyImpl defaultResourceProxyImpl = new DefaultResourceProxyImpl(this);
	         Log.d("AmbilSurvey", "Desc Map");
	         overlayItemArray = new ArrayList<OverlayItem>(); 
	         MyItemizedIconOverlay myItemizedIconOverlay = new MyItemizedIconOverlay(overlayItemArray, null, defaultResourceProxyImpl);
	         Log.d("AmbilSurvey", "Item Overlay Map");
	        	myOpenMapView.getOverlays().add(myItemizedIconOverlay);
	        	locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	        	Location lastLocation 
		        	= locationManager.getLastKnownLocation(
		        			LocationManager.GPS_PROVIDER);
	        	Log.d("AmbilSurvey", "Desc Location");
		        if(lastLocation != null){
		        	Log.d("AmbilSurvey-davin-cek", "updateLoc");
		        	updateLoc(lastLocation);
		        }
		        
		        Log.d("AmbilSurvey", "masuk 1");
		        
		        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
		        myOpenMapView.getOverlays().add(myScaleBarOverlay);
		      
		        nom_list si = dm.getSpesific_nom(StatusAmbilSurvey.getNomid());
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
		        
		        
		        
		        //Add MyLocationOverlay
		        myLocationOverlay = new MyLocationOverlay(this, myOpenMapView);
		        myOpenMapView.getOverlays().add(myLocationOverlay);
		        myOpenMapView.postInvalidate();
		       
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
	  
		        
		        
	      ambilPanoramic = (Button) findViewById(R.id.ambilPanoramic);
	      ambilPanoramic.setOnClickListener(new Button.OnClickListener(){
	      		public void onClick(View v){
	      			Intent myIntent= new
	      			Intent(v.getContext(), PhotoActivity.class);
	      			startActivityForResult(myIntent, 0);
	      			StatusAmbilSurvey.setActiveCamera("PANORAMIC");
	      		}
	      });
	      
	      aksesPln = (Button) findViewById(R.id.aksesPln);
	      aksesPln.setOnClickListener(new Button.OnClickListener(){
	      		public void onClick(View v){
	      			Intent myIntent= new
	      			Intent(v.getContext(), PhotoActivity.class);
	      			startActivityForResult(myIntent, 0);
	      			StatusAmbilSurvey.setActiveCamera("AKSESPLN");
	      		}
	      });
	      
	      polePln = (Button) findViewById(R.id.polePln);
	      polePln.setOnClickListener(new Button.OnClickListener(){
	      		public void onClick(View v){
	      			Intent myIntent= new
	      			Intent(v.getContext(), PhotoActivity.class);
	      			startActivityForResult(myIntent, 0);
	      			StatusAmbilSurvey.setActiveCamera("POLEPLN");
	      		}
	      });
	      
	      jalurKabelPln = (Button) findViewById(R.id.jalurKabelPln);
	      jalurKabelPln.setOnClickListener(new Button.OnClickListener(){
	      		public void onClick(View v){
	      			Intent myIntent= new
	      			Intent(v.getContext(), PhotoActivity.class);
	      			startActivityForResult(myIntent, 0);
	      			StatusAmbilSurvey.setActiveCamera("KABELPLN");
	      		}
	      });
	      
	      checkList = (Button) findViewById(R.id.checkList);
	      checkList.setOnClickListener(new Button.OnClickListener(){
	      		public void onClick(View v){
	      			Intent myIntent= new
	      			Intent(v.getContext(), CheckList.class);
	      			startActivityForResult(myIntent, 0); 
	      		}
	      });

	      
	      simpanLanjutkan = (Button) findViewById(R.id.simpanLanjutkan);
	      simpanLanjutkan.setOnClickListener(new Button.OnClickListener(){
	      		public void onClick(View v){
	      			
	      			DataPhoto dp = dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya());
	      			if(CommonUtil.isNotNullOrEmpty(dp.getPanoramic()) && CommonUtil.isNotNullOrEmpty(dp.getParam_2()) &&
	      					CommonUtil.isNotNullOrEmpty(dp.getParam_3()) && CommonUtil.isNotNullOrEmpty(dp.getParam_4()) && CommonUtil.isNotNullOrEmpty(dp.getParam_5())){
	      				String[] parts = dp.getPanoramic().split(";");
	      				if(parts.length >= 8){
	      					dm.updateDataPhotoByButton("upload_status", StatusAmbilSurvey.getButtonnya(), "READY", StatusAmbilSurvey.getNomid());
	      				}else{
	      					dm.updateDataPhotoByButton("upload_status", StatusAmbilSurvey.getButtonnya(), "NOT_READY", StatusAmbilSurvey.getNomid());
	      				}
	      				
	      			}else{
	      				dm.updateDataPhotoByButton("upload_status", StatusAmbilSurvey.getButtonnya(), "NOT_READY", StatusAmbilSurvey.getNomid());
	      			}
	      			
	      			Intent myIntent= new
	      			Intent(v.getContext(), LihatMap.class);
	      			myIntent.putExtra("nom_id", nom_id);
	      			myIntent.putExtra("status_AmbilSurvey", 1);
	      			myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	      			startActivity(myIntent);
	      			finish();
	      		}
	      });
	      
	      marker = (Button) findViewById(R.id.marker);
	      marker.setOnClickListener(new Button.OnClickListener(){
	      		public void onClick(View v){
	            	if(!overlayItemArray.isEmpty() && (CommonUtil.isNotNullOrEmpty(myLong)) && (CommonUtil.isNotNullOrEmpty(myLat))){
	    				//overlayItemArray have only ONE element only, so I hard code to get(0)
	    				
	            		showCurrentLong = (TextView)findViewById(R.id.showPosisiLongitude);
		    			showCurrentLong.setText(String.valueOf(myLong));
		    			
		    			showCurrentLat = (TextView)findViewById(R.id.showPosisiLatitude);
		    			showCurrentLat.setText(String.valueOf(myLat));
		    			
		    			ambilPanoramic.setEnabled(true);
		    			marker.setEnabled(false);
		    			anotherOverlayItemArray1 = new ArrayList<OverlayItem>();
		    			anotherOverlayItemArray1.add(new OverlayItem("Latitude", "Longitude", new GeoPoint(myLat, myLong)));
		    			dm.updateLatLongNomidButton(String.valueOf(myLat), String.valueOf(myLong), StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya());
	            	}else{
	            		ambilPanoramic.setEnabled(false);
	            	}
	      		}
	      });
	       
	      if(dm.getCountDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya()) > 0){
	    	  DataPhoto dp = dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya());
	    	  if(CommonUtil.isNotNullOrEmpty(dp.getLat()) && CommonUtil.isNotNullOrEmpty(dp.getLongt())){
	    		  ambilPanoramic.setEnabled(true);
	    		  marker.setEnabled(false);
	    	  }else{
	    		  ambilPanoramic.setEnabled(false);
	    	  }
	      }
	      
	      setText();
	      
	      DataPhoto dp = dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya());
	      checkPinMark(dp);
	      checkMarker(dp);
	      checkPanoramic(dp);
	      checkAksesPln(dp);
	      checkPolePln(dp);
	      checkJalurPln(dp); 
	      checkDataList(dp);
	 }
	 
	 protected void onResume(){
		 super.onResume();
		 setText();
		 DataPhoto dp = dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya());
		 checkPinMark(dp);
		 checkMarker(dp);
		 checkPanoramic(dp);
	     checkAksesPln(dp);
	     checkPolePln(dp);
	     checkJalurPln(dp);
	     checkDataList(dp);
	      
	      /*dm = new DatabaseManager(this);
			if(CommonUtil.isNotNullOrEmpty(dp.getPanoramic()) && CommonUtil.isNotNullOrEmpty(dp.getParam_2()) &&
					CommonUtil.isNotNullOrEmpty(dp.getParam_3()) && CommonUtil.isNotNullOrEmpty(dp.getParam_4())){
				String[] parts = dp.getPanoramic().split(";");
				if(parts.length >= 8){
					dm.updateDataPhotoByButton("upload_status", StatusAmbilSurvey.getButtonnya(), "READY", StatusAmbilSurvey.getNomid());
				}else{
					dm.updateDataPhotoByButton("upload_status", StatusAmbilSurvey.getButtonnya(), "NOT_READY", StatusAmbilSurvey.getNomid());
				}
				
			}else{
				dm.updateDataPhotoByButton("upload_status", StatusAmbilSurvey.getButtonnya(), "NOT_READY", StatusAmbilSurvey.getNomid());
			}*/
	      
	      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
	   	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
	   	myLocationOverlay.disableMyLocation();
	   	myLocationOverlay.disableCompass();
	      
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
	 
	 private void checkPanoramic(DataPhoto dp){
	      String x = "cross";
	      if( CommonUtil.isNotNullOrEmpty(dp.getPanoramic()) ){
	    	  String[] parts = dp.getPanoramic().split(";");
	    	  if(parts.length >= 8){
	    		  x = "checklist";
					aksesPln.setEnabled(true);
	    	  }else{
					aksesPln.setEnabled(false);
	    	  }
	      }else{
	    	  	aksesPln.setEnabled(false);
	      }
	      
	      int gambar = getResources().getIdentifier(x, "drawable", getPackageName());
	         ImageView gambarXml = (ImageView)findViewById(R.id.imageChecklist1);
	         Drawable res = getResources().getDrawable(gambar);
	         gambarXml.setImageDrawable(res);
	 }
	 
	 private void checkAksesPln(DataPhoto dp){
	      
		 String x = "cross";
	      if( CommonUtil.isNotNullOrEmpty(dp.getParam_2()) ){
	    	  x="checklist";
	    	  polePln.setEnabled(true);
	      }else{
	    	  polePln.setEnabled(false);
	      }
	      
	      int gambar = getResources().getIdentifier(x, "drawable", getPackageName());
	         ImageView gambarXml = (ImageView)findViewById(R.id.imageChecklist2);
	         Drawable res = getResources().getDrawable(gambar);
	         gambarXml.setImageDrawable(res);
	 }
	 
	 private void checkPolePln(DataPhoto dp){
	      String x = "cross";
	      if( CommonUtil.isNotNullOrEmpty(dp.getParam_3()) ){
	    	  x = "checklist";
	    	  jalurKabelPln.setEnabled(true);
	      }else{
	    	  jalurKabelPln.setEnabled(false);
	      }
	      
	      int gambar = getResources().getIdentifier(x, "drawable", getPackageName());
	         ImageView gambarXml = (ImageView)findViewById(R.id.imageChecklist3);
	         Drawable res = getResources().getDrawable(gambar);
	         gambarXml.setImageDrawable(res);
	 }
	 
	 private void checkJalurPln(DataPhoto dp){
	      String x = "cross";
	      if( CommonUtil.isNotNullOrEmpty(dp.getParam_4()) ){
	    	  x = "checklist";
	    	  checkList.setEnabled(true);
	      }else{
	    	  checkList.setEnabled(false);
	      }
	      
	      int gambar = getResources().getIdentifier(x, "drawable", getPackageName());
	         ImageView gambarXml = (ImageView)findViewById(R.id.imageChecklist4);
	         Drawable res = getResources().getDrawable(gambar);
	         gambarXml.setImageDrawable(res);
	 }
	 
	 private void checkDataList(DataPhoto dp){
	      String x = "cross";
	      if( CommonUtil.isNotNullOrEmpty(dp.getParam_5()) ){
	    	  x = "checklist";
	    	  simpanLanjutkan.setEnabled(true);
	      }else{
	    	  simpanLanjutkan.setEnabled(false);
	      }
	      
	      int gambar = getResources().getIdentifier(x, "drawable", getPackageName());
	      ImageView gambarXml = (ImageView)findViewById(R.id.imageChecklist5);
	         Drawable res = getResources().getDrawable(gambar);
	         gambarXml.setImageDrawable(res);
	 }
	 
	 
	 private void setText(){
		 /*String uriY ="";
		 LocationManager manager = (LocationManager)getSystemService(Activity.LOCATION_SERVICE);
	        if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	        	uriY = "cross";
	        }
	        else{uriY = "checklist";} 
	        int imageResourceGps = getResources().getIdentifier(uriY, "drawable", getPackageName());
	        ImageView checkGps = (ImageView)findViewById(R.id.gpsLock);
	        Drawable resGps = getResources().getDrawable(imageResourceGps);
	        checkGps.setImageDrawable(resGps);*/
	        
	        dm = new DatabaseManager(this);
		 List<site_info>site_info = dm.get_siteInfo(StatusAmbilSurvey.getNomid());
			for (site_info dataNom : site_info) {
				showSiteId = (TextView)findViewById(R.id.showSiteID);
				showSiteId.setText(dataNom.get_site_id());
				
				showSiteName = (TextView)findViewById(R.id.showSiteName);
				showSiteName.setText(dataNom.get_site_name());
				
				showLatitude = (TextView)findViewById(R.id.showLatitude);
				showLatitude.setText(DecimalUtil.toString(Double.parseDouble(dataNom.get_latitude())));
				nomLat = Double.parseDouble(dataNom.get_latitude());
				
				showLogitude = (TextView)findViewById(R.id.showLongitude);
				showLogitude.setText(DecimalUtil.toString(Double.parseDouble(dataNom.get_longitude())));
				nomLong = Double.parseDouble(dataNom.get_longitude());
				
				showProvince = (TextView)findViewById(R.id.showProvince);
				showProvince.setText(dataNom.get_site_province());
				
				showCity = (TextView)findViewById(R.id.showCity);
				showCity.setText(dataNom.get_city());		
				}
				
				DataPhoto dp = dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya());
				showCurrentLong = (TextView)findViewById(R.id.showPosisiLongitude);
    			showCurrentLong.setText(dp.getLongt());
    			
    			showCurrentLat = (TextView)findViewById(R.id.showPosisiLatitude);
    			showCurrentLat.setText(dp.getLat());
	 }
	 
	 private class MyItemizedIconOverlay extends ItemizedIconOverlay<OverlayItem>{		 
			public MyItemizedIconOverlay(
					List<OverlayItem> pList,
					org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<OverlayItem> pOnItemGestureListener,
					ResourceProxy pResourceProxy) {
				super(pList, pOnItemGestureListener, pResourceProxy);
				Log.d("myItemized Icon Overlay", "masuk");
				// TODO Auto-generated constructor stub
			}
		 
		 
		 
			@Override
			public void draw(Canvas canvas, MapView mapview, boolean arg2) {
				// TODO Auto-generated method stub
				super.draw(canvas, mapview, arg2);
				
				if(!overlayItemArray.isEmpty()){
					
					//overlayItemArray have only ONE element only, so I hard code to get(0)
					GeoPoint in = overlayItemArray.get(0).getPoint();
					
					myLat = in.getLatitudeE6() / 1E6;
					myLong = in.getLongitudeE6() / 1E6;
					
					Log.d("davin-test-log", myLat+" "+myLong);
					Log.d("davin-test-log", in.getLatitudeE6()+" "+in.getLongitudeE6());
					
					double result = CountDistance.distance(myLat, myLong, nomLat, nomLong, 'K');
            		Log.d("davin-count-meters", " : "+(result*1000));
            		//createLog("davin-count-meters : "+(result*1000));
            		
            		showDistanceFromNom = (TextView)findViewById(R.id.showDistanceFromNom);
            		showDistanceFromNom.setText(DecimalUtil.toString1Digit((result*1000))+" m");
            		int retval = Double.compare((result*1000), 50d);
            		String check = "";
            		if(retval > 0){
            			showDistanceFromNom.setTextColor(Color.parseColor("#FF0000"));
            			check = "cross";
            			marker.setEnabled(false);
            		}else{
            			showDistanceFromNom.setTextColor(Color.parseColor("#0000FF"));
            			check = "checklist";
            			marker.setEnabled(true);
            		}
            		
            		int imageResourceGps = getResources().getIdentifier(check, "drawable", getPackageName());
        	        ImageView checkGps = (ImageView)findViewById(R.id.gpsLock);
        	        Drawable resGps = getResources().getDrawable(imageResourceGps);
        	        checkGps.setImageDrawable(resGps);
					
					/*locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		        	lastLoc2 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);*/
					
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
					GeoPoint in = anotherOverlayItemArray1.get(0).getPoint();
					
					Point out = new Point();
					mapview.getProjection().toPixels(in, out);
					
					Bitmap bm = BitmapFactory.decodeResource(getResources(), 
							R.drawable.candidate_location);
					canvas.drawBitmap(bm, 
							out.x - bm.getWidth()/2, 	//shift the bitmap center
							out.y - bm.getHeight()/2, 	//shift the bitmap center
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
	 
	 private void createLog(String msg){
			FileHandler fh=null;
		    String name;
		    if ( 0 == Environment.getExternalStorageState().compareTo(Environment.MEDIA_MOUNTED))
		        name = Environment.getExternalStorageDirectory().getAbsolutePath();
		    else
		        name = Environment.getDataDirectory().getAbsolutePath();

		    name += "/AmbilSurvey.log";

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
	 public void onBackPressed()
	 { 
	      // code here to show dialog
	      super.onBackPressed();  // optional depending on your needs 
	      finish();
	      
	 }
	 
	 public void checkMarker(DataPhoto dp){
		 
		 if((CommonUtil.isNotNullOrEmpty(dp.getLongt())) && (CommonUtil.isNotNullOrEmpty(dp.getLat()))){
				//overlayItemArray have only ONE element only, so I hard code to get(0)
				
     		showCurrentLong = (TextView)findViewById(R.id.showPosisiLongitude);
 			showCurrentLong.setText(dp.getLongt());
 			
 			showCurrentLat = (TextView)findViewById(R.id.showPosisiLatitude);
 			showCurrentLat.setText(dp.getLat());
 			
 			ambilPanoramic.setEnabled(true);
 			marker.setEnabled(false);
     	}else{
     		ambilPanoramic.setEnabled(false);
     		marker.setEnabled(true);
     	}
	 }
	 
	 private void checkPinMark(DataPhoto dp){
			anotherOverlayItemArray1 = new ArrayList<OverlayItem>();
		     if(CommonUtil.isNotNullOrEmpty(dp.getLat()) && CommonUtil.isNotNullOrEmpty(dp.getLongt()))
		     {
		     	anotherOverlayItemArray1.add(new OverlayItem("Latitude", "Longitude", new GeoPoint(Double.parseDouble(dp.getLat()), Double.parseDouble(dp.getLongt()))));   	
		     }
		}
} 
