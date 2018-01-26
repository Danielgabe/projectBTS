package com.nichtemna.takeandsavephoto.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;   

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;   

import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.activity.SlidingActivity;
import com.nichtemna.takeandsavephoto.fungsi.JSONParser;
import com.nichtemna.takeandsavephoto.fungsi.LoginActivity; 

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle; 
import android.util.Log; 
import android.view.View; 
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class FragmentUnattendanceRequest extends Activity {
	 
	TextView txtUnattendance;
	EditText txtStartdate;
	EditText txtEnddate;
	Button btnSubmit;
	Button setStartDate;
	Button setEndDate;
	Spinner spinner;
	ProgressDialog pDialog;
	static final int DATE_DIALOG_STARTDATE = 0;
	static final int DATE_DIALOG_ENDDATE = 1;
	private int fromYear;
	private int fromMonth;
	private int fromDay;
	private int toYear;
	private int toMonth;
	private int toDay;
	String short_desc;
	String start_date;
	String last_date;
	JSONParser jsonParser = new JSONParser();
	String status_employee_id;
	String unattendance_id = "";
	private static String url_all_unattendance = "http://10.0.2.2/hris_mobile/mobile_apis/get_unattendance_list";
	private static String url_submit_unattendance_request = "http://10.0.2.2/hris_mobile/mobile_apis/submit_unattendance_request";
	private static final String TAG_SUCCESS = "success"; 
	
		  @Override
		    public void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.unattendance_request); 
		        status_employee_id = LoginActivity.status_employee_id;
		        btnSubmit = (Button) findViewById(R.id.btnSubmit);
				txtStartdate = (EditText) findViewById(R.id.txtStartdate);
				txtEnddate = (EditText) findViewById(R.id.txtEnddate);
				setStartDate = (Button) findViewById(R.id.setStartDate);
				setEndDate = (Button)  findViewById(R.id.setEndDate);
		  
 
		setStartDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_STARTDATE);
				// TODO Auto-generated method stub
					
			}
		});
		
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			 
            @Override
            public void onClick(View arg0) {
            	start_date = txtStartdate.getText().toString();
            	last_date = txtEnddate.getText().toString();
            	Log.d("parameter",status_employee_id + " " + short_desc + " " + start_date + " " + last_date);
                new SaveUnattendanceRequest().execute();
            }
        });
		
		final Calendar calendar = Calendar.getInstance();
		fromYear = calendar.get(Calendar.YEAR);
		fromMonth = calendar.get(Calendar.MONTH);
		fromDay = calendar.get(Calendar.DAY_OF_MONTH);
		updateDisplay(); 
		  

		//fill spinner unattendance
	        	List<NameValuePair> params = new ArrayList<NameValuePair>();
				JSONArray roles = null;
				final ArrayList<String> results = new ArrayList<String>();
				JSONObject json = jsonParser.makeHttpRequest(url_all_unattendance, "POST",
						params);
				try {
					roles = json.getJSONArray("unattendances");
					for (int i = 0; i < roles.length(); i++) {
						JSONObject c = roles.getJSONObject(i);
						String short_desc = c.getString("short_desc");
						results.add(short_desc);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				spinner = (Spinner) findViewById(R.id.spinner);
				ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,results);
				spinner.setAdapter(arrayadapter);
				spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView, View view,
							int i, long l) {
						short_desc = results.get(i).toString();			
					}			
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});	 
		
		setEndDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ENDDATE);
			}
		});
		final Calendar calendar1 = Calendar.getInstance();
		toYear = calendar1.get(Calendar.YEAR);
		toMonth = calendar1.get(Calendar.MONTH);
		toDay = calendar1.get(Calendar.DAY_OF_MONTH);
		updateDisplay1();
		};
		  	
		@Override
	    protected Dialog onCreateDialog(int id)
	    {
	        switch (id) {
	            case DATE_DIALOG_STARTDATE: 
				return new DatePickerDialog(
	                    this, mDateSetListener, fromYear, fromMonth, fromDay);
				
	            case DATE_DIALOG_ENDDATE: 
				return new DatePickerDialog(
	                    this, mDateSetListener1, toYear, toMonth, toDay);
	        }
	        return null;	 
	   
	    } 
 
	OnDateSetListener startdate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			fromYear = year;
			fromMonth = monthOfYear;
			fromDay = dayOfMonth;
			updateDisplay();
		}
	};
	     
	private void updateDisplay() {
		txtStartdate.setText(new StringBuilder()
		.append(fromYear).append("-").append(fromMonth+1).append("-").append(fromDay).append(""));
	}
	
	  private DatePickerDialog.OnDateSetListener mDateSetListener =
		        new DatePickerDialog.OnDateSetListener() {
		  		@Override
		            public void onDateSet(DatePicker view, int year, 
		                                  int monthOfYear, int dayOfMonth) {
		                fromYear = year;
		                fromMonth = monthOfYear;
		                fromDay = dayOfMonth;
		                updateDisplay();
		            }
		        };
	
	
	
	OnDateSetListener enddate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			toYear = year;
			toMonth = monthOfYear;
			toDay = dayOfMonth;
			updateDisplay1();
		}
	};
	private void updateDisplay1() {
		txtEnddate.setText(new StringBuilder()
		.append(toYear).append("-").append(toMonth+1).append("-").append(toDay).append(""));
	}
	  private DatePickerDialog.OnDateSetListener mDateSetListener1 =
		        new DatePickerDialog.OnDateSetListener() {

		            public void onDateSet(DatePicker view, int year, 
		                                  int monthOfYear, int dayOfMonth) {
		                toYear = year;
		                toMonth = monthOfYear;
		                toDay = dayOfMonth;
		                updateDisplay1();
		            }
		        };
	

		        class SaveUnattendanceRequest extends AsyncTask<String, String, String> {
		            @Override
		            protected void onPreExecute() {
		                super.onPreExecute();
		                pDialog = new ProgressDialog(FragmentUnattendanceRequest.this);
		                pDialog.setMessage("Saving unattendancerequest ...");
		                pDialog.setIndeterminate(false);
		                pDialog.setCancelable(true);
		                pDialog.show();
		            }
		            protected String doInBackground(String... args) {
		                // Building Parameters
		                List<NameValuePair> params = new ArrayList<NameValuePair>();
		                params.add(new BasicNameValuePair("status_employee_id", status_employee_id));
		                params.add(new BasicNameValuePair("short_desc", short_desc));
		                params.add(new BasicNameValuePair("start_date", start_date));
		                params.add(new BasicNameValuePair("last_date", last_date));
		                Log.d("allparameter ", status_employee_id+ " "+short_desc+" "+last_date+" "+last_date);
		                // sending modified data through http request
		                // Notice that update unattendancerequest url accepts POST method
		                JSONObject json = jsonParser.makeHttpRequest(url_submit_unattendance_request,
		                        "POST", params);
		                Log.d("json submit ", json.toString());
		                // check json success tag
		                try {
		                    int success = json.getInt(TAG_SUCCESS);
		     
		                    if (success == 1) {
		                    	Intent a = new Intent(getApplicationContext(), SlidingActivity.class);
		                        startActivity(a);
		                    } else {
		                        // failed to update unattendancerequest
		                    	Intent a = new Intent(getApplicationContext(), FragmentUnattendanceRequest.class);
		                        startActivity(a);
		                    }
		                } catch (JSONException e) {
		                    e.printStackTrace();
		                }
		     
		                return null;
		            }
		     
		            /**
		             * After completing background task Dismiss the progress dialog
		             * **/
		            protected void onPostExecute(String file_url) {
		                // dismiss the dialog once unattendancerequest uupdated
		                pDialog.dismiss();
		            }
		        }
		    }
		    	