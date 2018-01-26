package com.nichtemna.takeandsavephoto.fragment;
 
 
import java.util.ArrayList;
import java.util.List; 

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;     

import android.os.AsyncTask; 
import android.os.Bundle;    
import android.widget.EditText;  

import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.fungsi.JSONParser; 
import com.nichtemna.takeandsavephoto.fungsi.LoginActivity; 

import android.app.Activity; 
import android.app.ProgressDialog;


public class FragmentProfil extends Activity {
  
	EditText txtCode;
	EditText txtPersonCode;
	EditText txtFullname;
	String status_employee_id = "";
	JSONParser jsonParser = new JSONParser();
	private ProgressDialog pDialog;
	private static final String url_employee_details = "http://10.0.2.2/hris_mobile/mobile_apis/get_employee_detail";
	private static final String TAG_STATUS_EMPLOYEE = "StatusEmployee";
	private static final String TAG_PERSON = "Person";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil); 
        new GetProfilStatusEmployeeId().execute();
    }

    class GetProfilStatusEmployeeId extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(FragmentProfil.this);
			pDialog.setMessage("Loading status employee details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {

			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					// Check for success tag
					try {
						// Building Parameters
						status_employee_id = LoginActivity.status_employee_id;
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("status_employee_id",status_employee_id));
						JSONObject json = jsonParser.makeHttpRequest(
								url_employee_details, "POST", params);
						//Log.d("json", json.toString());
						JSONObject statusEmployee = json.getJSONObject(TAG_STATUS_EMPLOYEE);
						txtCode = (EditText) findViewById(R.id.txtCode);
						txtCode.setText(statusEmployee.getString("code"));
						JSONObject person = json.getJSONObject(TAG_PERSON);
						txtPersonCode = (EditText) findViewById(R.id.txtPersonCode);
						txtPersonCode.setText(person.getString("person_code"));
						txtFullname = (EditText) findViewById(R.id.txtFullname);
						txtFullname.setText(person.getString("fullname"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}
		protected void onPostExecute(String file_url) {
			 
			// dismiss the dialog once got details employee
			pDialog.dismiss();
		}
	}
}