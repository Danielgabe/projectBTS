package com.nichtemna.takeandsavephoto.fungsi;

 
import java.util.ArrayList;
import java.util.List;  

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;  

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;     

import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.activity.*;

public class LoginActivity extends Activity {
	/* deklarasi variabel */
	int length;
	Boolean Suksess = false;
	SessionManager session;
	EditText txtUsername;
	EditText txtPassword;
	TextView login_error;
	Button btnLogin;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_USERNAME = "username";
	private static final String TAG_PASSWORD = "password";
	String preferencename = "DataPreference";
	private static final String url_login = "http://bts.devtuwaga.com/api_services/login";	                
	JSONParser jsonParser = new JSONParser();
	DatabaseManager dm;
	int count = 0;
	public static String status_employee_id = "";
	public static String getStatus_employee_id() {
		return status_employee_id;
	}
	public static void setStatus_employee_id(String status_employee_id) {
		LoginActivity.status_employee_id = status_employee_id;
	}

	ArrayList<Object> baris;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		session = new SessionManager(getApplicationContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		// dm.resetTables();
		// Buttons
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		login_error = (TextView) findViewById(R.id.login_error);
	 
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new LoginProcess().execute();
				
			}
		});
	}

	private class LoginProcess extends AsyncTask<String, Void, Boolean> {
		private ProgressDialog pDialog;
		@Override
		protected void onPreExecute() {
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            
           
		}

		
		protected void onPostExecute(final Boolean Suksess) {
			Log.d("Masuk onPostExecute","Start");
			if (pDialog.isShowing()) {
				pDialog.dismiss();
			}			
			if (Suksess) {
				Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
			}
		}

		protected Boolean doInBackground(final String... args) {
            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();
            boolean kembalian;
            UserFunctions userFunction = new UserFunctions();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_USERNAME, username));
            params.add(new BasicNameValuePair(TAG_PASSWORD, password));
            Log.d("login","1");
            JSONObject json = jsonParser.makeHttpRequest(url_login,
                    "POST", params);
            Log.d("asdsdsds", json.toString());
            Log.d("login","2");
            try {				
				JSONObject parseJSONUser = json.getJSONObject("user_data");
				String dataUserName = parseJSONUser.getString("username");
				String dataIDuser = parseJSONUser.getString("id_user");
				String dataStatus = parseJSONUser.getString("status");
				String businessPartner_name = parseJSONUser.getString("busines_partner_name");
				Log.d("business name",businessPartner_name);
				String success = json.getString("message");				
				Log.d("message", success);
				Log.d("username", dataUserName);
				Log.d("id_user", dataIDuser);
				Log.d("status",dataStatus);
				Log.d("TAG_SUCCESS", TAG_SUCCESS);
                if (success.equals(TAG_SUCCESS)) {
                	Log.d("Masuk", "masuk success");
                	session.CreateSeassion(dataUserName, dataIDuser,dataStatus,businessPartner_name);
                	
                	//userFunction.logoutUser(getApplicationContext());                	
                	//DatabaseManager db = new DatabaseManager(getApplicationContext());
                	//setStatus_employee_id(dataStatus);
                	//db.addUser(json.getString("status_employee_id"), "1");
                    //Intent i = getIntent();
                    Log.d("Masuk", "intent Lewat");
                    //setResult(100, i);
                    Intent a = new Intent(getApplicationContext(), Splash.class);
                    a.putExtra("User_Id",dataIDuser);
                    a.putExtra("User_Name",dataUserName);
                    a.putExtra("User_Status", dataStatus);
                    finish();
                    startActivity(a);                                        
                    kembalian = true;
                    Log.d("Kembalian", "True");
                } else {
                	Log.d("Masuk", "masuk failed");
                	Intent i = getIntent();
                    setResult(100, i);
                    Intent a = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(a);
                    finish();
                    kembalian = false;
                }
				return kembalian;
			} catch (Exception e) {
				Log.e("tag", "error", e);
				return false;
			}
            
		}
		
	}
	@Override
	public void onBackPressed()
	{ 
	     // code here to show dialog
	     super.onBackPressed();  // optional depending on your needs 
	     finish();
	     
	}
}

