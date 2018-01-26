package com.nichtemna.takeandsavephoto.fungsi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.activity.SlidingActivity; 

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import android.widget.TextView;
 
public class Splash extends Activity {
    protected boolean _active = true;
    SessionManager session;
    ConnectionDetector cd;
    Handler handler;
    private String currentDateandTime;
    protected int _splashTime = 5000;
    private static int waited;
    private static int time;
    private static final String url_to_do = "http://bts.devtuwaga.com/api_services/get_data_to_do";
	private static final String url_data_tower = "http://bts.devtuwaga.com/api_services/get_data_tower";
	private static final String url_master_nom = "http://bts.devtuwaga.com/api_services/get_master_nom";
	private static final String url_checked_master_nom = "http://bts.devtuwaga.com/api_services/checked_master_nom";
	private static final String url_checked_to_do = "http://bts.devtuwaga.com/api_services/checked_todo_update";
	private static final String url_checked_datatower = "http://bts.devtuwaga.com/api_services/checked_datatower_update";
	private TextView loadingText;
	private HashMap<String, String> user;
	private String User_Id;
	private String User_Status;
	private String User_Name;
	private DatabaseManager db;
	ProgressBar progressBar2;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);    	
        setContentView(R.layout.splash);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDateandTime = sdf.format(new Date());
        db = new DatabaseManager(getApplicationContext());
        final Boolean existing_master_nom = db.checkedTable("master_nom");
        final Boolean existing_to_do = db.checkedTable("to_do");
        final Boolean existing_datatower = db.checkedTable("datatower");
        File database=getApplicationContext().getDatabasePath("bts_db.db");
        final boolean db_exists; 
        if (!database.exists()) {
            // Database does not exist so copy it from assets here
            Log.i("Database", "Not Found");
            db_exists = false;
        } else {
            Log.i("Database", "Found");
            db_exists = true;
        }
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        loadingText = (TextView)findViewById(R.id.loadingText);
        session = new SessionManager(getApplicationContext());
        session.CheckUser();
        Boolean _isLogin = session.isLoggedIn();
        if(_isLogin)
        {	        	
	        user = session.getUserDetails();
	    	User_Name = user.get(SessionManager.User_Name);
	    	User_Id = user.get(SessionManager.User_Id);
	    	User_Status = user.get(SessionManager.User_Status);
	        cd = new ConnectionDetector(getApplicationContext());	        
	        // thread for displaying the SplashScreen
	        Thread splashTread = new Thread() {
	            @Override
	            public void run() {
	                try {	                	
	                	JSONParser jsonParser = new JSONParser();                	                		
	            		//String userName = user.get(SessionManager.User_Name);            		
	            		 List<NameValuePair> params = new ArrayList<NameValuePair>();
	            		 
	            		 Boolean isOnline = cd.isConnectingToInternet();
	            		 if(isOnline)
                     		{
	            			 	setTextLoad("Synchronize data...");
	            			 	waited =0;time = 0;
	            			 	while(_active && (waited < _splashTime)) {
	            			 		sleep(50);	                        	            			 			                        	                      
	            			 		if(_active) {	                        	
	                        	   
	                        		Log.d("Splash", "Masuk Online");	                        		
	                        		if (android.os.Build.VERSION.SDK_INT > 9) {
		                     			    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		                     			    StrictMode.setThreadPolicy(policy);
		                     			    
		                     			    
		                     			    
		                     			    
		                     			    //Log.d("Masuk Get Data Nom"e,Boolean.toString(isOnline()));	                     		 	                        			                        		
			                        		params.add(new BasicNameValuePair("id", User_Id));
			                        		params.add(new BasicNameValuePair("date", currentDateandTime));
			                        		Log.d("Splash", "Curent Date = " +currentDateandTime);
			                        		JSONObject json_to_do = jsonParser.makeHttpRequest(url_to_do, "POST", params);
			                        		JSONObject json_master_nom = jsonParser.makeHttpRequest(url_master_nom, "POST", params);
			                        		JSONObject json_data_tower = jsonParser.makeHttpRequest(url_data_tower, "POST", params);
			                        		
			                        		JSONObject json_checked_master_nom = jsonParser.makeHttpRequest(url_checked_master_nom, "POST", params);
			                        		JSONObject json_checked_to_do = jsonParser.makeHttpRequest(url_checked_to_do, "POST", params);
			                        		JSONObject json_checked_datatower = jsonParser.makeHttpRequest(url_checked_datatower, "POST", params);
			                        		
			                        		//JSONObject json_nom_list = jsonParser.makeHttpRequest(url_nom_list,
			                   	              //   "POST", params);
			                        		
			                        		
			                        		JSONArray jsonArray = new JSONArray();
			                        		if(existing_master_nom)
			                        		{
			                        			Log.d("SPLASH", "MasterNom EXISTING");
												try {
													String message = json_checked_master_nom.getString("message");
													Log.d("SPLASH", "MasterNom EXISTING message = " +message );
													if(message.equals("NoData")){ Log.d("SPLASH", "JSON MASTER NOM NODATA"); }
				                        			else
				                        			{
				                        				jsonArray = json_checked_master_nom.getJSONArray("user_data");
				                        				Log.d("SPLASH", "MasterNom EXISTING message = " +jsonArray.toString() );
				                        				for (int i=0;i<jsonArray.length();i++){
				                        					Log.d("SPLASH-SYNCRONIZE", "JSON MASTER NOM SYNCRONIZE");
				                        					db.updateNom(jsonArray.getJSONObject(i).getInt("nom_id"), jsonArray.getJSONObject(i).getString("site_id"), jsonArray.getJSONObject(i).getInt("bussines_id"), jsonArray.getJSONObject(i).getInt("survey_status"),jsonArray.getJSONObject(i).getString("update_time"));
				                   						}
				                        			}
												} catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
			                        			//String TAG_Message = "success";
			                        		}
			                        		else
			                        		{
			                        			Log.d("SPLASH", "Master Nom Dont Existing");
			                        			Log.d("SPLASH", "Master Nom Start");
			                   					try {
													jsonArray = json_master_nom.getJSONArray("user_data");
													Log.d("SPLASH", "GET JSON ARRAY = "+ jsonArray.toString());
				                   					
				                   					if (jsonArray != null) {
				                   						//setTextLoad("Syncronize Data NOM...");
				                   						Log.d("SPLASH", "Drop Create Master nom");
				                   						db.dropCreateMasterNom();
				                   						Log.d("SPLASH", "Drop Create Master nom Finish");
				                   						for (int i=0;i<jsonArray.length();i++){
				                   							Log.d("SPLASH", "GET JSON DATA WITH NOM = "+Integer.toString(jsonArray.getJSONObject(i).getInt("nom_id")));
				                   								db.addMaster_Nom(jsonArray.getJSONObject(i).getInt("nom_id"), jsonArray.getJSONObject(i).getString("site_id"), jsonArray.getJSONObject(i).getInt("bussines_id"), jsonArray.getJSONObject(i).getInt("survey_status"),jsonArray.getJSONObject(i).getString("update_time"));			                   							
				                   							}
				                   						Log.d("SPLASH", "Master Nom finish");
				                   					}
												} catch (JSONException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
			                   						                        			
			                        		}
			                        		
			                        		
			                        		if(existing_to_do)
			                        		{
			                        			Log.d("SPLASH", "MasterNom EXISTING");
												try {
													String message = json_checked_to_do.getString("message");
													Log.d("SPLASH", "MasterNom EXISTING message = " +message );
													if(message.equals("NoData")){ Log.d("SPLASH", "JSON MASTER NOM NODATA"); }
				                        			else
				                        			{
				                        				jsonArray = json_checked_to_do.getJSONArray("user_data");
				                        				Log.d("SPLASH", "MasterNom EXISTING message = " +jsonArray.toString() );
				                        				for (int i=0;i<jsonArray.length();i++){
				                        					Log.d("SPLASH-SYNCRONIZE", "JSON MASTER NOM SYNCRONIZE");
				                        					db.Todo_update(jsonArray.getJSONObject(i).getInt("nom_id"), jsonArray.getJSONObject(i).getString("update_time"));
				                   						}
				                        			}
												} catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
			                        			//String TAG_Message = "success";			                        			
			                        		}
			                        		else
			                        		{
			                        			Log.d("SPLASH", "to do Start");
			                   					try {
													jsonArray = json_to_do.getJSONArray("user_data");
													if (jsonArray != null) {
				                   						//setTextLoad("Syncronize Data JOB...");	                   					 	                   						                   					
				                   						db.dropCreateto_do();
				                   						for (int i=0;i<jsonArray.length();i++){						 
				                   							db.addto_do(jsonArray.getJSONObject(i).getInt("nom_id"), jsonArray.getJSONObject(i).getString("update_time"));
				                   						}
				                   					}
				                   					Log.d("SPLASH", "to do finish");
												} catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}                   								                   					
			                        		}
			                        		
			                        		if(existing_datatower)
			                        		{
			                        			Log.d("SPLASH-Tower", "Data Tower EXISTING");
												try {
													String message = json_checked_datatower.getString("message");
													Log.d("SPLASH-Tower", "Data Tower EXISTING message = " +message );
													if(message.equals("NoData")){ Log.d("SPLASH", "JSON Data Tower NODATA"); }
				                        			else
				                        			{
				                        				jsonArray = json_checked_datatower.getJSONArray("user_data");
				                        				Log.d("SPLASH-Tower", "Data Tower EXISTING message = " +jsonArray.toString() );
				                        				for (int i=0;i<jsonArray.length();i++){
				                        					Log.d("SPLASH-Tower-SYNCRONIZE", "JSON  Data Tower SYNCRONIZE");
				                        					db.update_DataTower(jsonArray.getJSONObject(i).getString("site_id"), jsonArray.getJSONObject(i).getString("site_name"), jsonArray.getJSONObject(i).getString("towertype"),  jsonArray.getJSONObject(i).getString("site_address"),  jsonArray.getJSONObject(i).getString("province"),  jsonArray.getJSONObject(i).getString("city"),  jsonArray.getJSONObject(i).getString("sub_district"),  jsonArray.getJSONObject(i).getString("longitude"),  jsonArray.getJSONObject(i).getString("latitude"),jsonArray.getJSONObject(i).getString("update_time"));
				                   						}
				                        			}
												} catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
			                        			//String TAG_Message = "success";
			                        		}
			                        		else
			                        		{
			                        			Log.d("SPLASH-Tower", "Data Tower NOT EXISTING");			                        			
				                        		try
				                        		{
				                        			Log.d("SPLASH", "Data Tower Start");			
				                   				 	jsonArray = json_data_tower.getJSONArray("user_data");
				                   				 	if (jsonArray != null) {
				                   				 	//setTextLoad("Syncronize Data Tower...");
				                   				 		db.dropCreateDataTower();
				                   				 		for (int i=0;i<jsonArray.length();i++){          			                   				 				                   			    		
				                   						db.addDataTower(jsonArray.getJSONObject(i).getString("site_id"), jsonArray.getJSONObject(i).getString("site_name"), jsonArray.getJSONObject(i).getString("towertype"),  jsonArray.getJSONObject(i).getString("site_address"),  jsonArray.getJSONObject(i).getString("province"),  jsonArray.getJSONObject(i).getString("city"),  jsonArray.getJSONObject(i).getString("sub_district"),  jsonArray.getJSONObject(i).getString("longitude"),  jsonArray.getJSONObject(i).getString("latitude"),jsonArray.getJSONObject(i).getString("update_time"));						 
				                   					 }
				                   				 	Log.d("SPLASH", "Data Tower Finish");
				                   					}
				                   				 }                   				 		                        		
				                        		catch(Exception e){
			                        			Log.e("Splas", e.getMessage());
			                        			}	
			                        		}
	                        		time += 5000;
		                        	Log.d("Splash", "Online Time = "+Integer.toString(time));
	                        	}
	            			 		waited += time;
	            			 		Log.d("Splash", "Online Time = "+Integer.toString(time));
	                        }	            			 	
	                    }
                     		}
	            		 else
	            		 {
                    		 Log.d("Splash", "Masuk Offline");	                        		                     		 
                    		 waited = 0; time = 0;	
                    		 while(_active && (waited < _splashTime)) {
                                 sleep(200);
                                 if(_active) {
                                     time += 100;
                                     Log.d("Splash", "Time 2 = "+Integer.toString(time));
                                 }
                                 waited = time;
                                 Log.d("Splash", "waited 2 = "+Integer.toString(waited));
                             }	                        		 
                    		 Log.d("Splash", "Time = "+Integer.toString(time));
                		 }            	                		                		 
	                } catch(InterruptedException e) {
	                    // do nothing
	                	Log.e("Splash", e.getMessage());
	                } finally {                                        
	                    //session.CheckUser();
	                    //Boolean _isLogin = session.isLoggedIn();                    
	                    //Log.d("Session Login", _isLogin.toString() );                    
	                    //if(_isLogin)
	                    //{                    	
	                    /*
	                    Intent newIntent=new Intent(Splash.this, LoginActivity.class);
	            		startActivityForResult(newIntent,0);
	            		*/            		
	                    	Intent a = new Intent(getApplicationContext(), SlidingActivity.class);
	                    	a.putExtra("User_Id",User_Id);
	                    	a.putExtra("User_Name",User_Name);
	                    	a.putExtra("User_Status", User_Status);
	                    	finish();       	
	                    	startActivity(a);                    	                    
	                    //}
	                }
	            }
	        };
	        splashTread.start();
        }
    }
    
    
    public void setTextLoad(final String Text)
    {    	
    	loadingText.setText(Text);	
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }
}
