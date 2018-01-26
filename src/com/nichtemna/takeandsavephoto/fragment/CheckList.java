package com.nichtemna.takeandsavephoto.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nichtemna.takeandsavephoto.*;
import com.nichtemna.takeandsavephoto.fungsi.ConnectionDetector;
import com.nichtemna.takeandsavephoto.fungsi.DatabaseManager;
import com.nichtemna.takeandsavephoto.fungsi.SessionManager;
import com.nichtemna.takeandsavephoto.fungsi.nom_list;
import com.nichtemna.takeandsavephoto.fungsi.site_info;

import android.app.Activity; 
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;   
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CheckList extends Activity {
	private TextView showSiteId,showSiteName,showLatitude,showLogitude,showProvince,showCity, showLegendYellow, showLegendGreen;
	SessionManager session;
	DatabaseManager db;
	Boolean isOnline = false;
	ConnectionDetector cd;
	SharedPreferences pref;
	private Button button111, button112, button113, button121, button122, button123, button131, button132, button141;
	private Button button211, button212, button213, button221, button222, button223, button231, button232, button241;
	private Button button311, button312, button313, button321, button322, button323, button331, button332, button341;
	private Button button411, button412, button413, button421, button422, button423, button431, button432, button441;
	private Button buttonYellow, buttonGreen, buttonSimpan, buttonClear;
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        setContentView(R.layout.checklist);
	        
	        db = new DatabaseManager(CheckList.this);
	        
	        buttonYellow = (Button) findViewById(R.id.buttonYellow);
	        buttonYellow.setBackgroundColor(Color.YELLOW);
	        showLegendYellow = (TextView)findViewById(R.id.showLegendYellow);
	        showLegendYellow.setText(" Bangunan Lebih dari 2 lantai");
	        
	        buttonGreen = (Button) findViewById(R.id.buttonGreen);
	        buttonGreen.setBackgroundColor(Color.GREEN);
	        showLegendGreen = (TextView)findViewById(R.id.showLegendGreen);
	        showLegendGreen.setText(" Existing");
	        
	        button111 = (Button) findViewById(R.id.button111);
	        button111.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("1-1-");
		      		}
		      });
	        
	        
	        button112 = (Button) findViewById(R.id.button112);
	        button112.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("1-2-");
		      		}
		      });
	        
	        button113 = (Button) findViewById(R.id.button113);
	        button113.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("1-3-");
		      		}
		      });
	        
	        button121 = (Button) findViewById(R.id.button121);
	        button121.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("1-4-");
		      		}
		      });
	        
	        button122 = (Button) findViewById(R.id.button122);
	        button122.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0);
		      			StatusAmbilSurvey.setButtonChecklist("1-5-");
		      		}
		      });
	        
	        button123 = (Button) findViewById(R.id.button123);
	        button123.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("1-6-");
		      		}
		      });
	        
	        button131 = (Button) findViewById(R.id.button131);
	        button131.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("1-7-");
		      		}
		      });
	        
	        button132 = (Button) findViewById(R.id.button132);
	        button132.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("1-8-");
		      		}
		      });
	        
	        button141 = (Button) findViewById(R.id.button141);
	        button141.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("1-9-");
		      		}
		      });
	        
	        button211 = (Button) findViewById(R.id.button211);
	        button211.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0);
		      			StatusAmbilSurvey.setButtonChecklist("2-1-");
		      		}
		      });
	        
	        button212 = (Button) findViewById(R.id.button212);
	        button212.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("2-2-");
		      		}
		      });
	        
	        button213 = (Button) findViewById(R.id.button213);
	        button213.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("2-3-");
		      		}
		      });
	        
	        button221 = (Button) findViewById(R.id.button221);
	        button221.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0);
		      			StatusAmbilSurvey.setButtonChecklist("2-4-");
		      		}
		      });
	        
	        button222 = (Button) findViewById(R.id.button222);
	        button222.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("2-5-");
		      		}
		      });
	        
	        button223 = (Button) findViewById(R.id.button223);
	        button223.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("2-6-");
		      		}
		      });
	        
	        button231 = (Button) findViewById(R.id.button231);
	        button231.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("2-7-");
		      		}
		      });
	        
	        button232 = (Button) findViewById(R.id.button232);
	        button232.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0);
		      			StatusAmbilSurvey.setButtonChecklist("2-8-");
		      		}
		      });
	        
	        button241 = (Button) findViewById(R.id.button241);
	        button241.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("2-9-");
		      		}
		      });
	        
	        button311 = (Button) findViewById(R.id.button311);
	        button311.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("3-1-");
		      		}
		      });
	        
	        button312 = (Button) findViewById(R.id.button312);
	        button312.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("3-2-");
		      		}
		      });
	        
	        button313 = (Button) findViewById(R.id.button313);
	        button313.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("3-3-");
		      		}
		      });
	        
	        button321 = (Button) findViewById(R.id.button321);
	        button321.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("3-4-");
		      		}
		      });
	        
	        button322 = (Button) findViewById(R.id.button322);
	        button322.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("3-5-");
		      		}
		      });
	        
	        button323 = (Button) findViewById(R.id.button323);
	        button323.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("3-6-");
		      		}
		      });
	        
	        button331 = (Button) findViewById(R.id.button331);
	        button331.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("3-7-");
		      		}
		      });
	        
	        button332 = (Button) findViewById(R.id.button332);
	        button332.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0);
		      			StatusAmbilSurvey.setButtonChecklist("3-8-");
		      		}
		      });
	        
	        button341 = (Button) findViewById(R.id.button341);
	        button341.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("3-9-");
		      		}
		      });  
	        
	        button411 = (Button) findViewById(R.id.button411);
	        button411.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("4-1-");
		      		}
		      }); 
	        
	        button412 = (Button) findViewById(R.id.button412);
	        button412.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("4-2-");
		      		}
		      }); 
	        button413 = (Button) findViewById(R.id.button413);
	        button413.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("4-3-");
		      		}
		      }); 
	        
	        button421 = (Button) findViewById(R.id.button421);
	        button421.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("4-4-");
		      		}
		      }); 
	        
	        button422 = (Button) findViewById(R.id.button422);
	        button422.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("4-5-");
		      		}
		      }); 
	        
	        button423 = (Button) findViewById(R.id.button423);
	        button423.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("4-6-");
		      		}
		      }); 
	        
	        button431 = (Button) findViewById(R.id.button431);
	        button431.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("4-7-");
		      		}
		      }); 
	        
	        button432 = (Button) findViewById(R.id.button432);
	        button432.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0); 
		      			StatusAmbilSurvey.setButtonChecklist("4-8-");
		      		}
		      }); 
	        
	        button441 = (Button) findViewById(R.id.button441);
	        button441.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new
		      			Intent(v.getContext(), Obstacle.class);
		      			startActivityForResult(myIntent, 0);
		      			StatusAmbilSurvey.setButtonChecklist("4-9-");
		      		}
		      }); 
	        
	        buttonSimpan = (Button) findViewById(R.id.buttonSimpan);
	        buttonSimpan.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			Intent myIntent= new Intent(v.getContext(), AmbilSurvey.class);
		      			myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		      			startActivity(myIntent);
		      			finish();
		      		}
		      });
	        
	        buttonClear = (Button) findViewById(R.id.buttonClear);
	        buttonClear.setOnClickListener(new Button.OnClickListener(){
		      		public void onClick(View v){
		      			db.updateDataPhotoByButton("param_5", StatusAmbilSurvey.getButtonnya(), "", StatusAmbilSurvey.getNomid());
		      			checkButtonBackground();
		      		}
		      });
	        
	        setText();
	        checkButtonBackground();
	        
	 }
	 
	 protected void onResume(){
		 super.onResume();
		 setText();
		 checkButtonBackground();
	 }
	 
	 private void setText(){
		 nom_list siteData = db.getSpesific_nom(StatusAmbilSurvey.getNomid());
		 	showSiteId = (TextView)findViewById(R.id.showSiteID);
			showSiteId.setText(siteData.get_site_id());
				
			showSiteName = (TextView)findViewById(R.id.showSiteName);
			showSiteName.setText(siteData.get_site_name());
				
			showLatitude = (TextView)findViewById(R.id.showLatitude);
			showLatitude.setText(DecimalUtil.toString(Double.parseDouble(siteData.get_latitude())));
				
			showLogitude = (TextView)findViewById(R.id.showLongitude);
			showLogitude.setText(DecimalUtil.toString(Double.parseDouble(siteData.get_longitude())));
				
			showProvince = (TextView)findViewById(R.id.showProvince);
			showProvince.setText(siteData.get_site_province());
				
			showCity = (TextView)findViewById(R.id.showCity);
			showCity.setText(siteData.get_city());		
	 }
	 
	 private void checkButtonBackground(){
		 String dataCheck = db.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya()).getParam_5();
		 Log.d("davin-check-aja", dataCheck);
		 if(dataCheck.contains("1-1-1")){
			 button111.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("1-1-2")){
			 button111.setBackgroundColor(Color.GREEN);
		 }else{
			 button111.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("1-2-1")){
			 button112.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("1-2-2")){
			 button112.setBackgroundColor(Color.GREEN);
		 }else{
			 button112.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("1-3-1")){
			 button113.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("1-3-2")){
			 button113.setBackgroundColor(Color.GREEN);
		 }else{
			 button113.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("1-4-1")){
			 button121.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("1-4-2")){
			 button121.setBackgroundColor(Color.GREEN);
		 }else{
			 button121.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("1-5-1")){
			 button122.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("1-5-2")){
			 button122.setBackgroundColor(Color.GREEN);
		 }else{
			 button122.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("1-6-1")){
			 button123.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("1-6-2")){
			 button123.setBackgroundColor(Color.GREEN);
		 }else{
			 button123.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("1-7-1")){
			 button131.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("1-7-2")){
			 button131.setBackgroundColor(Color.GREEN);
		 }else{
			 button131.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("1-8-1")){
			 button132.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("1-8-2")){
			 button132.setBackgroundColor(Color.GREEN);
		 }else{
			 button132.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("1-9-1")){
			 button141.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("1-9-2")){
			 button141.setBackgroundColor(Color.GREEN);
		 }else{
			 button141.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 
		 if(dataCheck.contains("2-1-1")){
			 button211.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("2-1-2")){
			 button211.setBackgroundColor(Color.GREEN);
		 }else{
			 button211.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("2-2-1")){
			 button212.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("2-2-2")){
			 button212.setBackgroundColor(Color.GREEN);
		 }else{
			 button212.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("2-3-1")){
			 button213.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("2-3-2")){
			 button213.setBackgroundColor(Color.GREEN);
		 }else{
			 button213.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("2-4-1")){
			 button221.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("2-4-2")){
			 button221.setBackgroundColor(Color.GREEN);
		 }else{
			 button221.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("2-5-1")){
			 button222.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("2-5-2")){
			 button222.setBackgroundColor(Color.GREEN);
		 }else{
			 button222.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("2-6-1")){
			 button223.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("2-6-2")){
			 button223.setBackgroundColor(Color.GREEN);
		 }else{
			 button223.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("2-7-1")){
			 button231.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("2-7-2")){
			 button231.setBackgroundColor(Color.GREEN);
		 }else{
			 button231.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("2-8-1")){
			 button232.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("2-8-2")){
			 button232.setBackgroundColor(Color.GREEN);
		 }else{
			 button232.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("2-9-1")){
			 button241.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("2-9-2")){
			 button241.setBackgroundColor(Color.GREEN);
		 }else{
			 button241.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 
		 if(dataCheck.contains("3-1-1")){
			 button311.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("3-1-2")){
			 button311.setBackgroundColor(Color.GREEN);
		 }else{
			 button311.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("3-2-1")){
			 button312.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("3-2-2")){
			 button312.setBackgroundColor(Color.GREEN);
		 }else{
			 button312.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("3-3-1")){
			 button313.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("3-3-2")){
			 button313.setBackgroundColor(Color.GREEN);
		 }else{
			 button313.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("3-4-1")){
			 button321.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("3-4-2")){
			 button321.setBackgroundColor(Color.GREEN);
		 }else{
			 button321.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("3-5-1")){
			 button322.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("3-5-2")){
			 button322.setBackgroundColor(Color.GREEN);
		 }else{
			 button322.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("3-6-1")){
			 button323.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("3-6-2")){
			 button323.setBackgroundColor(Color.GREEN);
		 }else{
			 button323.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("3-7-1")){
			 button331.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("3-7-2")){
			 button331.setBackgroundColor(Color.GREEN);
		 }else{
			 button331.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("3-8-1")){
			 button332.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("3-8-2")){
			 button332.setBackgroundColor(Color.GREEN);
		 }else{
			 button332.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("3-9-1")){
			 button341.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("3-9-2")){
			 button341.setBackgroundColor(Color.GREEN);
		 }else{
			 button341.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 
		 if(dataCheck.contains("4-1-1")){
			 button411.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("4-1-2")){
			 button411.setBackgroundColor(Color.GREEN);
		 }else{
			 button411.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("4-2-1")){
			 button412.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("4-2-2")){
			 button412.setBackgroundColor(Color.GREEN);
		 }else{
			 button412.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("4-3-1")){
			 button413.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("4-3-2")){
			 button413.setBackgroundColor(Color.GREEN);
		 }else{
			 button413.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("4-4-1")){
			 button421.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("4-4-2")){
			 button421.setBackgroundColor(Color.GREEN);
		 }else{
			 button421.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("4-5-1")){
			 button422.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("4-5-2")){
			 button422.setBackgroundColor(Color.GREEN);
		 }else{
			 button422.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("4-6-1")){
			 button423.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("4-6-2")){
			 button423.setBackgroundColor(Color.GREEN);
		 }else{
			 button423.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("4-7-1")){
			 button431.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("4-7-2")){
			 button431.setBackgroundColor(Color.GREEN);
		 }else{
			 button431.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("4-8-1")){
			 button432.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("4-8-2")){
			 button432.setBackgroundColor(Color.GREEN);
		 }else{
			 button432.setBackgroundResource(android.R.drawable.btn_default);
		 }
		 if(dataCheck.contains("4-9-1")){
			 button441.setBackgroundColor(Color.YELLOW);
		 }else if(dataCheck.contains("4-9-2")){
			 button441.setBackgroundColor(Color.GREEN);
		 }else{
			 button441.setBackgroundResource(android.R.drawable.btn_default);
		 }
	 }
	 
/*	 @Override
	 public void onBackPressed() {
		 //super.onBackPressed();
	 }*/
	
}