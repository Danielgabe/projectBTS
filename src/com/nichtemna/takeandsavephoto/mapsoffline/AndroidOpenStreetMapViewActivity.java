package com.nichtemna.takeandsavephoto.mapsoffline;

import java.util.ArrayList;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.util.ArrayList;
import java.util.List; 

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;   

import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.fragment.CommonUtil;
import com.nichtemna.takeandsavephoto.fragment.StatusAmbilSurvey;
import com.nichtemna.takeandsavephoto.fungsi.DatabaseManager;
import com.nichtemna.takeandsavephoto.fungsi.nom_list;
import com.nichtemna.takeandsavephoto.fungsi.site_info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class AndroidOpenStreetMapViewActivity extends Activity {
	
	private MapView myOpenMapView;
	private MapController myMapController;
	private String pilihan;
	private List<Integer> listChecklist = new ArrayList<Integer>();
	private DatabaseManager dm;

	LocationManager locationManager; 
	ArrayList<OverlayItem> overlayItemArray;
	ArrayList<OverlayItem> anotherOverlayItemArray; 
	ArrayList<OverlayItem> anotherOverlayItemArray2; 
	MyLocationOverlay myLocationOverlay = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mains);
        
        Intent intent = getIntent();
        pilihan = intent.getStringExtra("pilihLocation");
        String split[] = pilihan.split(",");
        int countSplit = split.length;
        for(int a=0; a<countSplit; a++){
        	if((!split[a].equals("kosong")) && CommonUtil.isNotNullOrEmpty(split[a])){
        		listChecklist.add(Integer.valueOf(split[a]));
        	}
        }
        
        myOpenMapView = (MapView)findViewById(R.id.openmapview);
        myOpenMapView.setBuiltInZoomControls(true);
        myMapController = myOpenMapView.getController();
        myMapController.setZoom(18);
        overlayItemArray = new ArrayList<OverlayItem>();
        
        DefaultResourceProxyImpl defaultResourceProxyImpl 
        	= new DefaultResourceProxyImpl(this);
        MyItemizedIconOverlay myItemizedIconOverlay 
        	= new MyItemizedIconOverlay(overlayItemArray, null, defaultResourceProxyImpl);
        myOpenMapView.getOverlays().add(myItemizedIconOverlay);
 locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
        //for demo, getLastKnownLocation from GPS only, not from NETWORK
        Location lastLocation 
        	= locationManager.getLastKnownLocation(
        			LocationManager.GPS_PROVIDER);
        if(lastLocation != null){
        	updateLoc(lastLocation);
        }
        
        //Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
        myOpenMapView.getOverlays().add(myScaleBarOverlay);
      
		  
        //--- Create Another Overlay for multi marker
        anotherOverlayItemArray = new ArrayList<OverlayItem>();
        
        dm = new DatabaseManager(AndroidOpenStreetMapViewActivity.this);
        int countlistChecklist = listChecklist.size();
        for(int a=0; a<countlistChecklist; a++){
        	//Log.d("davin-check-before-looping",""+listChecklist.get(a));
        	nom_list x = dm.getSpesific_nom(listChecklist.get(a));
        	//Log.d("davin-check-looping", x.get_site_name()+" "+x.get_city()+" "+x.get_latitude()+" "+x.get_longitude());
        	if(CommonUtil.isNotNullOrEmpty(x.get_latitude()) && CommonUtil.isNotNullOrEmpty(x.get_longitude())){
        	
        	anotherOverlayItemArray.add(new OverlayItem(
        			x.get_site_name(), x.get_city(), new GeoPoint(Double.parseDouble(x.get_latitude()), Double.parseDouble(x.get_longitude()))));
        	}
        }
        
        /*anotherOverlayItemArray.add(new OverlayItem(
        		"0, 0", "0, 0", new GeoPoint(0, 0)));
        anotherOverlayItemArray.add(new OverlayItem(
        		"US", "US", new GeoPoint(38.883333, -77.016667)));
        anotherOverlayItemArray.add(new OverlayItem(
        		"China", "China", new GeoPoint(39.916667, 116.383333)));
        anotherOverlayItemArray.add(new OverlayItem(
        		"United Kingdom", "United Kingdom", new GeoPoint(51.5, -0.116667)));
        anotherOverlayItemArray.add(new OverlayItem(
        		"Germany", "Germany", new GeoPoint(52.516667, 13.383333)));
        anotherOverlayItemArray.add(new OverlayItem(
        		"Tanjung Priuk", "Tanjung Priuk", new GeoPoint(-6.132055, 106.871485)));
        anotherOverlayItemArray.add(new OverlayItem(
        		"India", "India", new GeoPoint(28.613333, 77.208333)));
        anotherOverlayItemArray.add(new OverlayItem(
        		"Russia", "Russia", new GeoPoint(55.75, 37.616667)));
        anotherOverlayItemArray.add(new OverlayItem(
        		"France", "France", new GeoPoint(48.856667, 2.350833)));
        anotherOverlayItemArray.add(new OverlayItem(
        		"Canada", "Canada", new GeoPoint(45.4, -75.666667)));*/
        
        ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay 
        	= new ItemizedIconOverlay<OverlayItem>(
        			this, anotherOverlayItemArray, myOnItemGestureListener);
        myOpenMapView.getOverlays().add(anotherItemizedIconOverlay);
        //---
        
        
        
        //Add MyLocationOverlay
        myLocationOverlay = new MyLocationOverlay(this, myOpenMapView);
        myOpenMapView.getOverlays().add(myLocationOverlay);
        myOpenMapView.postInvalidate();
    }
    
    OnItemGestureListener<OverlayItem> myOnItemGestureListener
    = new OnItemGestureListener<OverlayItem>(){

		 
		public boolean onItemLongPress(int arg0, OverlayItem arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		 
		public boolean onItemSingleTapUp(int index, OverlayItem item) {
			Toast.makeText(AndroidOpenStreetMapViewActivity.this, 
					item.mDescription + "\n"
					+ item.mTitle + "\n"
					+ item.mGeoPoint.getLatitudeE6() + " : " + item.mGeoPoint.getLongitudeE6(), 
					Toast.LENGTH_LONG).show();
			return true;
		}
    	
    }; 
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
	myLocationOverlay.disableMyLocation();
	myLocationOverlay.disableCompass();
}

@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	locationManager.removeUpdates(myLocationListener);
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
	}
 
@Override
public boolean onSingleTapUp(MotionEvent event, MapView mapView) {
	// TODO Auto-generated method stub
	//return super.onSingleTapUp(event, mapView);
	return true;
}
}
}