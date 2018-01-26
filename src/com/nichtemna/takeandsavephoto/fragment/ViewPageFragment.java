
package com.nichtemna.takeandsavephoto.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject; 
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;  
import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.activity.SlidingActivity;
import com.nichtemna.takeandsavephoto.fungsi.ConnectionDetector;
import com.nichtemna.takeandsavephoto.fungsi.DatabaseManager;
import com.nichtemna.takeandsavephoto.fungsi.JSONParser;
import com.nichtemna.takeandsavephoto.fungsi.SessionManager;
import com.nichtemna.takeandsavephoto.fungsi.nom_list;
import com.nichtemna.takeandsavephoto.mapsoffline.AndroidOpenStreetMapViewActivity;

public class ViewPageFragment extends Fragment {
	Context context;
	Resources resource;
	SessionManager session;
	DatabaseManager db;
	Boolean isOnline;
	ConnectionDetector cd;
	SharedPreferences pref;
	String checked ="";
	private Button showLeft;
//	private Button showSearch;
	private Button lihatMap;
	private Button tambahTugas;
	private ViewPager mPager;
	private ArrayList<Fragment> pagerItemList = new ArrayList<Fragment>();
	private HashMap<String, String> user;
	private View mView;
	private TableLayout tableLayout;
	private static final String url_to_do = "http://bts.devtuwaga.com/api_services/get_data_to_do";
	private static final String url_insert_into_todoList = "http://bts.devtuwaga.com/api_services/insert_todo";
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {				
		context = this.getActivity();	
		cd = new ConnectionDetector(this.getActivity());
		isOnline = cd.isConnectingToInternet();
		resource = context.getResources();		
		mView = inflater.inflate(R.layout.view_pager2, null);		
		showLeft = (Button) mView.findViewById(R.id.showLeft);		
//		showSearch = (Button) mView.findViewById(R.id.showSearch);
		//user = new HashMap<String, String>();
		session = new SessionManager(this.getActivity());
		user = session.getUserDetails();
		//Log.d("Masuk Fragmet", "Masuk 4");
	 
		lihatMap =(Button) mView.findViewById(R.id.lihatMap);
		tambahTugas =(Button)mView.findViewById(R.id.tambahTugas);		
		mPager = (ViewPager) mView.findViewById(R.id.pager);
		new PageFragment1();
		new PageFragment2();		
		viewDataNom();
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			    StrictMode.setThreadPolicy(policy);	
		}
		return mView;
		
	}
	
	
		
	public void viewDataNom()
	{		
		Log.d("Masuk View Data Nom","Masuk");
		db = new DatabaseManager(this.getActivity());
		List<nom_list> nomList = db.getAll_nom_list();
		Log.d("Masuk View Data Nom","Masuk Query");
		int i = 0;
		tableLayout=(TableLayout)mView.findViewById(R.id.simple_table);
		for (nom_list dataNom : nomList) {
			i = i + 1;
			Log.d("Masuk View Data Nom","Masuk loop " + Integer.toString(i));
			TableRow tableRow = new TableRow(this.getActivity());			
			        tableRow.setPadding(20, 20, 20, 20);
			        if(i%2==0){
			        	tableRow.setBackgroundColor(resource .getColor(R.color.row_ganjil));
			        }
			        else{
			        	tableRow.setBackgroundColor(resource .getColor(R.color.row_genap));
			        }
			CheckBox checkBox = new CheckBox(this.getActivity());			
			  checkBox.setId(dataNom.get_nom_id());
			  checkBox.setGravity(Gravity.CENTER | Gravity.CENTER);
			  checkBox.setWidth(5);
			  checkBox.setTextSize(5);			  
			  checkBox.setOnClickListener(checkBoxClick(checkBox));
	            tableRow.addView(checkBox);            
	        /*  
			i = i + 1;

        	TextView id  = new TextView(this.getActivity());
        	id.setText(Integer.toString(i));
        	tableRow.addView(id);
        	*/
	            TextView siteId  =  new TextView(this.getActivity()); 
	            siteId.setTextColor(resource.getColor(R.color.biru_tua));
	            siteId.setText(dataNom.get_site_id());
	            siteId.setGravity(Gravity.CENTER | Gravity.CENTER);
	        	tableRow.addView(siteId);
	        	
	        	TextView siteName  = new TextView(this.getActivity());
	        	siteName.setText( dataNom.get_site_name());
	        	siteName.setGravity(Gravity.CENTER | Gravity.CENTER); 
	        	siteName.setTextColor(resource.getColor(R.color.biru_tua));
	        	tableRow.addView(siteName); 
	        	
	        	   TextView province  = new TextView(this.getActivity());
	               province.setText(dataNom.get_site_province());
	               province.setGravity(Gravity.LEFT | Gravity.CENTER);
	               tableRow.addView(province);
	               
	           	
	               TextView city  = new TextView(this.getActivity());
	               city.setText(dataNom.get_city());
	               city.setGravity(Gravity.LEFT | Gravity.CENTER);
	               tableRow.addView(city);
	           	
	            TextView latitude =  new TextView(this.getActivity());
	            double dLatitude = Double.parseDouble(dataNom.get_latitude());
	            latitude.setText(DecimalUtil.toString(dLatitude));
	            siteName.setGravity(Gravity.CENTER | Gravity.CENTER);
	        	tableRow.addView(latitude);
	        	
	            TextView longTitude = new TextView(this.getActivity());
	            double dLongiTitude = Double.parseDouble(dataNom.get_longitude());
	            longTitude.setText(DecimalUtil.toString(dLongiTitude));
	            siteName.setGravity(Gravity.CENTER | Gravity.CENTER);
	        	tableRow.addView(longTitude);
	        	
	            Log.d("Masuk Get Data", "Masuk Looping Set Text");            
	                        
	            tableLayout.addView(tableRow);			
		}			
	}
	
	private OnClickListener checkBoxClick(final CheckBox CheckBox) {
		return new View.OnClickListener() {
			
			   public void onClick(View v) {
				   if (CheckBox.isChecked()) {
					checked += Integer.toString(CheckBox.getId()) + ",";
				}
				   else
				   {
					   Boolean finValue = checked.contains(Integer.toString(CheckBox.getId()));
					   if(finValue)
					   {
						   checked = checked.replace(Integer.toString(CheckBox.getId()), "kosong");
					   }
				   }				   
			   }
		};
	}

	public void onActivityCreated(Bundle savedInstanceState) { 
		Log.d("Masuk Fragment","Masuk");
		super.onActivityCreated(savedInstanceState); 
		showLeft.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View v) {
				((SlidingActivity) getActivity()).showLeft();
			}
		});
		
		mView.setFocusableInTouchMode(true);
		mView.requestFocus();
		mView.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    //Log.d("davin-test-fragment", "onKey Back listener is working!!!");
					getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return true;
				} else {
					return false;
				}
			}
	    });

		/*showSearch.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View v) {
				((SlidingActivity) getActivity()).showSearch();
			}
		});*/
	 		
		lihatMap.setOnClickListener(new OnClickListener() { 
		
		@Override
		public void onClick(View v) {
			Intent myIntent= new
	            	Intent(v.getContext(), AndroidOpenStreetMapViewActivity.class);
					myIntent.putExtra("pilihLocation", checked);
	            	startActivityForResult(myIntent, 0);
	            }
	        });
		
		tambahTugas.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JSONArray jsonArray = new JSONArray();
				Log.d("Connection", isOnline.toString());
				isOnline = cd.isConnectingToInternet();
				if(isOnline)
				{
					try
					{
						//String UserName = user.get(SessionManager.User_Name);
						String userId = user.get(SessionManager.User_Id);
						Log.d("ViewPage UserId", userId);
						Log.d("ViewPage list_nom", checked);			
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("id", userId));
						params.add(new BasicNameValuePair("list_nom", checked));					 
						JSONParser jsonParser = new JSONParser();
						JSONObject json_insert_todo = jsonParser.makeHttpRequest(url_insert_into_todoList, "POST", params);
						Log.d("ViewPage Json", json_insert_todo.toString());
						JSONObject json_to_do = jsonParser.makeHttpRequest(url_to_do, "POST", params);
						jsonArray = json_to_do.getJSONArray("user_data");                   					
       					if (jsonArray != null) {
       						//setTextLoad("Syncronize Data JOB...");	                   					 	                   						                   					
       						db.dropCreateto_do();
       						for (int i=0;i<jsonArray.length();i++){						 
       							db.addto_do(jsonArray.getJSONObject(i).getInt("nom_id"), jsonArray.getJSONObject(i).getString("update_time"));
       						}
						
						Toast.makeText(v.getContext(), json_insert_todo.getString("user_data"), Toast.LENGTH_LONG).show(); 
					 }
					}
					 catch (Exception e) {
							Log.e("tag", "error", e);				
							Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_LONG).show();
						}
				}
				else
				{
					Toast.makeText(v.getContext(), "Internet connection is Unavailable Please Check Your Network Connection", Toast.LENGTH_LONG).show();
				}
			}
		});
}
	public boolean isFirst() {
		if (mPager.getCurrentItem() == 0)
			return true;
		else
			return false;
	}

	public boolean isEnd() {
		if (mPager.getCurrentItem() == pagerItemList.size() - 1)
			return true;
		else
			return false;
	}

	public class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return pagerItemList.size();
		}

		@Override
		public Fragment getItem(int position) {

			Fragment fragment = null;
			if (position < pagerItemList.size())
				fragment = pagerItemList.get(position);
			else
				fragment = pagerItemList.get(0);

			return fragment;

		}
	}

	public void setMyPageChangeListener(MyPageChangeListener l) {

	}

	public interface MyPageChangeListener {
		public void onPageSelected(int position);
	}

}
