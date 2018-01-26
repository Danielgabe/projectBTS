package com.nichtemna.takeandsavephoto.fungsi;

import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.activity.SlidingActivity; 

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LogOutActivity extends Activity {
	SessionManager session;	       
	Activity _context;
	protected void onCreate(Bundle savedInstanceState) {				
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		//SharedPreferences settings =  getSharedPreferences("pref_", 0);		 
		session = new SessionManager(LogOutActivity.this);
		session.clearSession();
        Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_LONG).show();
        Intent a = new Intent(getApplicationContext(), Splash.class);                          
        a.putExtra("finish", true); // if you are checking for this in your other Activities
        a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        finish();    
	}	
}
