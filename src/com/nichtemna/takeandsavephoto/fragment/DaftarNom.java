package com.nichtemna.takeandsavephoto.fragment;

import com.nichtemna.takeandsavephoto.*;

import android.app.Activity; 
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;   
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DaftarNom extends Activity {
 
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        setContentView(R.layout.daftarnom);
	        
	        Button lihatMap = (Button) findViewById(R.id.lihatMap);
	        lihatMap.setOnClickListener(new TextView.OnClickListener(){
	            public void onClick(View v){
	            	Intent myIntent= new
	            	Intent(v.getContext(), PhotoActivity.class);
	            	startActivityForResult(myIntent, 0);
	            }
	        });

	 }
}