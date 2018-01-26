package com.nichtemna.takeandsavephoto.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.nichtemna.takeandsavephoto.activity.jobList;
import com.nichtemna.takeandsavephoto.fragment.LihatMap;
import com.nichtemna.takeandsavephoto.fragment.ViewPageFragment.MyAdapter;
import com.nichtemna.takeandsavephoto.fragment.ViewPageFragment.MyPageChangeListener;
import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.fungsi.DatabaseManager;
import com.nichtemna.takeandsavephoto.fungsi.JSONParser;
import com.nichtemna.takeandsavephoto.fungsi.SessionManager;
import com.nichtemna.takeandsavephoto.fungsi.nom_list;
import com.nichtemna.takeandsavephoto.fungsi.to_do; 
import com.nichtemna.takeandsavephoto.mapsoffline.AndroidOpenStreetMapViewActivity;

import android.app.Activity; 
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;   
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class LihatPeta extends Fragment {

	DatabaseManager db = new DatabaseManager(this.getActivity());
	private TableLayout tableLayout;
	 
	private View mView;	
	 
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	        //super.onCreate(savedInstanceState);
	        //setRequestedOrientation(tthis..SCREEN_ORIENTATION_LANDSCAPE);
	        //setContentView(R.layout.tugassaya); 	        
	       
			getDataNom();
		  

			return mView;
		}

	private void getDataNom() {
		Log.d("Masuk GetDataNom", "Masuk");
		db = new DatabaseManager(this.getActivity());
		List<to_do> to_do_List = db.getAll_todo_list();
		Log.d("Masuk View Data Nom","Masuk Query");
		tableLayout = (TableLayout)mView.findViewById(R.id.simple_table);
		for (to_do dataNom : to_do_List) {
			TableRow tableRow = new TableRow(this.getActivity());
			
			TextView siteName  = new TextView(this.getActivity());
        	siteName.setText( dataNom.get_site_name());
        	siteName.setId(dataNom.get_nom_id());
        	siteName.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        	siteName.setOnClickListener(TextView(siteName));				        	
        	tableRow.addView(siteName);
        
        	
            TextView siteId  =  new TextView(this.getActivity());
            siteId.setText(dataNom.get_site_id());
            siteId.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        	tableRow.addView(siteId);
        	
            TextView province  = new TextView(this.getActivity());
            province.setText(dataNom.get_site_province());
            province.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        	tableRow.addView(province);
            
        	
            TextView city  = new TextView(this.getActivity());
            city.setText(dataNom.get_city());
            city.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        	tableRow.addView(city);
        	
            TextView latitude =  new TextView(this.getActivity());
            latitude.setText(dataNom.get_latitude());
            latitude.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        	tableRow.addView(latitude);
        	
            TextView longTitude = new TextView(this.getActivity());
            longTitude.setText(dataNom.get_longitude());
            longTitude.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        	tableRow.addView(longTitude);
        	
        	Boolean surveyStatus  = dataNom.get_surveyStat();
        	Log.d("Hasil Survey Status", Boolean.toString(dataNom.get_surveyStat()));
        	String uri;
        	if(surveyStatus == true){  Log.d("Survey Status", "TRUE"); uri = "checklist"; }
        	else{  Log.d("Survey Status", "FALSE"); uri = "cross"; }
        	
        	int imageResource = getResources().getIdentifier(uri, "drawable", getActivity().getPackageName());
        	ImageView imageStatus = new ImageView(this.getActivity());
        	Drawable res = getResources().getDrawable(imageResource);
        	imageStatus.setImageDrawable(res);
        	tableRow.addView(imageStatus);
        	
            Log.d("Masuk Get Data", "Masuk Looping Set Text");            
                        
            tableLayout.addView(tableRow);
		}
	}

	private OnClickListener TextView(final TextView siteName) {
		// TODO Auto-generated method stub
		Log.d("OnClick SiteName", "Masuk");
		return new View.OnClickListener() {
			
			   public void onClick(View v) {
				   Intent myIntent= new
						   Intent(v.getContext(), AndroidOpenStreetMapViewActivity.class);
				   			
				   //Bundle dataBundle = new Bundle();
				   int nomId = siteName.getId();
				   StatusAmbilSurvey.setNomid(nomId);
				   myIntent.putExtra("nom_id", nomId);
				   startActivityForResult(myIntent, 0);				   							 
			   }
		};		
	}

}
	 