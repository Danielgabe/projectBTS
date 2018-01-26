
package com.nichtemna.takeandsavephoto.fragment;
 
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;   
import android.widget.TextView;    

import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.fungsi.LogOutActivity;
import com.nichtemna.takeandsavephoto.fungsi.SessionManager;
import com.nichtemna.takeandsavephoto.activity.*; 

public class LeftFragment extends Fragment {   
	Context context;
	String lastMenu = "kosong";
	Resources resource;
	String pfUserName;
	String userId;
	String partnerName;
	SessionManager session;
	TextView txtuserName;TextView grade_textview;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState	) {
		context = this.getActivity();
		resource = context.getResources(); 
		Log.d("leftFragment", "masuk");
		session = new SessionManager(getActivity());
		HashMap<String, String> user = session.getUserDetails();		
		pfUserName = user.get(SessionManager.User_Name);
		userId = user.get(SessionManager.User_Id);
		partnerName = user.get(SessionManager.businessPartner_name);
		//Log.d("partnername", partnerName);
		View mView = inflater.inflate(R.layout.left, null);
		txtuserName = (TextView) mView.findViewById(R.id.name_textview);
		txtuserName.setText(pfUserName);
		grade_textview = (TextView)mView.findViewById(R.id.grade_textview);
		grade_textview.setText(partnerName);		
		//HashMap<String, String> menu = session.getLastMenuPosition();		
		//if(lastMenu != "kosong")
			//{lastMenu = lastMenu;}		
		final TextView daftarNom = (TextView) mView.findViewById(R.id.daftarNom);
		if(lastMenu == "daftarNom"){daftarNom.setBackgroundColor(resource.getColor(R.color.orange));}
		daftarNom.setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View v){            	     
            	daftarNom.setBackgroundColor(resource.getColor(R.color.orange));
            	//session.setMenuListener("daftarNom");
            	lastMenu = "daftarNom";
            	Log.d("lastmenu", lastMenu);
            	Intent myIntent= new            	
            	Intent(v.getContext(), SlidingActivity.class);
            	startActivityForResult(myIntent, 0);
            }
        });		   
		final TextView tugasS = (TextView) mView.findViewById(R.id.tugasS);
		if(lastMenu == "tugasS"){tugasS.setBackgroundColor(resource.getColor(R.color.orange));}
		tugasS.setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View v){
            	tugasS.setBackgroundColor(resource.getColor(R.color.orange));
            	//session.setMenuListener("tugasS");
            	lastMenu = "tugasS";
            	Intent myIntent= new	
            	Intent(v.getContext(), jobList.class);
            	startActivityForResult(myIntent, 0);
            }
        });
		
		final TextView folderDone = (TextView) mView.findViewById(R.id.folderDone);
		if(lastMenu == "folderDone"){folderDone.setBackgroundColor(resource.getColor(R.color.orange));}
		folderDone.setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View v){
            	folderDone.setBackgroundColor(resource.getColor(R.color.orange));
            	//session.setMenuListener("folderDone");
            	lastMenu = "folderDone";
            	Intent myIntent= new	
            	Intent(v.getContext(), TugasSelesai.class);
            	startActivityForResult(myIntent, 0);
            }
        });
 
		
		TextView showLogout = (TextView) mView.findViewById(R.id.showLogout);
		showLogout.setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View v){
            	Intent myIntent= new
                    	Intent(v.getContext(), LogOutActivity.class);  
                    	startActivityForResult(myIntent, 0);                
            } 
			 
        });
		
		return mView;    
}

}