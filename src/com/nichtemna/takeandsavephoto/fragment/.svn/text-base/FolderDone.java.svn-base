package com.nichtemna.takeandsavephoto.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.nichtemna.takeandsavephoto.activity.TugasSelesai;
import com.nichtemna.takeandsavephoto.fragment.DecimalUtil;
import com.nichtemna.takeandsavephoto.fragment.LihatMap;
import com.nichtemna.takeandsavephoto.fragment.PageFragment1;
import com.nichtemna.takeandsavephoto.fragment.PageFragment2;
import com.nichtemna.takeandsavephoto.fragment.ViewPageFragment.MyAdapter;
import com.nichtemna.takeandsavephoto.fragment.ViewPageFragment.MyPageChangeListener;
import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.fungsi.DatabaseManager;
import com.nichtemna.takeandsavephoto.fungsi.JSONParser;
import com.nichtemna.takeandsavephoto.fungsi.SessionManager;
import com.nichtemna.takeandsavephoto.fungsi.nom_list;
import com.nichtemna.takeandsavephoto.fungsi.to_do; 

import android.app.Activity; 
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Paint;
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

 
 
public class FolderDone extends Fragment {
	Context context;
	Resources resource;
	DatabaseManager db = new DatabaseManager(this.getActivity());
	private TableLayout tableLayout;
	private ImageView imageview;
	private ArrayList<Fragment> pagerItemList = new ArrayList<Fragment>();
	private MyAdapter mAdapter;
	private ViewPager mPager;
	private View mView;	
	Button showLeft;
//	Button showSearch;
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {		 
		//super.onCreate(savedInstanceState);
	        //setRequestedOrientation(tthis..SCREEN_ORIENTATION_LANDSCAPE);
	        //setContentView(R.layout.tugassaya); 	        
		 context = this.getActivity();
			resource = context.getResources(); 
	        mView = inflater.inflate(R.layout.folderdone, null);			
			showLeft = (Button) mView.findViewById(R.id.showLeft);			
//			showSearch = (Button) mView.findViewById(R.id.showSearch);
			
	        mPager = (ViewPager) mView.findViewById(R.id.pager);
			PageFragment1 page1 = new PageFragment1();
			PageFragment2 page2 = new PageFragment2();
			pagerItemList.add(page1);
			pagerItemList.add(page2);
			mAdapter = new MyAdapter(getFragmentManager());
			mPager.setAdapter(mAdapter);
			getDataNom();
			mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {						
				@Override
				public void onPageSelected(int position) {

					if (myPageChangeListener != null)
						myPageChangeListener.onPageSelected(position);

				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					

				}

				@Override
				public void onPageScrollStateChanged(int position) {

					

				}
			});

			return mView;
		}

	private void getDataNom() {
		Log.d("Masuk GetDataNom", "Masuk");
		db = new DatabaseManager(this.getActivity());
		List<to_do> to_do_List = db.getAll_todo_list_1();
		Log.d("Masuk View Data Nom","Masuk Query");
		tableLayout = (TableLayout)mView.findViewById(R.id.simple_table);
		int i = 0;
		for (to_do dataNom : to_do_List) {
			i = i + 1;
			TableRow tableRow = new TableRow(this.getActivity());
			tableRow.setPadding(20, 20, 20, 20);
	        if(i%2==0){
	        	tableRow.setBackgroundColor(resource .getColor(R.color.row_ganjil));
	        }
	        else{
	        	tableRow.setBackgroundColor(resource .getColor(R.color.row_genap));
	        }	        
	        
	        TextView siteId  =  new TextView(this.getActivity());
	        siteId.setId(dataNom.get_nom_id()); 
	        siteId.setTextColor(resource.getColor(R.color.biru_tua));
            siteId.setText(dataNom.get_site_id());
            siteId.setGravity(Gravity.CENTER | Gravity.CENTER);
            siteId.setOnClickListener(TextView(siteId));	
            siteId.setPaintFlags(siteId.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        	tableRow.addView(siteId);
	        
			TextView siteName  = new TextView(this.getActivity());
			siteName.setTextColor(resource.getColor(R.color.biru_tua));
        	siteName.setText( dataNom.get_site_name());
        	//teName.setId(dataNom.get_nom_id());
        	siteName.setGravity(Gravity.CENTER | Gravity.CENTER);
        	//siteName.setOnClickListener(TextView(siteName));				        	
        	tableRow.addView(siteName);
         
            TextView province  = new TextView(this.getActivity());
            province.setText(dataNom.get_site_province());
            province.setGravity(Gravity.CENTER | Gravity.RIGHT);
        	tableRow.addView(province);
            
        	
            TextView city  = new TextView(this.getActivity());
            city.setText(dataNom.get_city());
            city.setGravity(Gravity.CENTER | Gravity.CENTER);
        	tableRow.addView(city);
        	
        	TextView latitude =  new TextView(this.getActivity());
            latitude.setText(DecimalUtil.toString(Double.parseDouble(dataNom.get_latitude())));
            latitude.setGravity(Gravity.CENTER | Gravity.CENTER);
        	tableRow.addView(latitude);
        	
            TextView longTitude = new TextView(this.getActivity());
            longTitude.setText(DecimalUtil.toString(Double.parseDouble(dataNom.get_longitude())));
            longTitude.setGravity(Gravity.CENTER | Gravity.CENTER);
        	tableRow.addView(longTitude);
        	
        	Boolean surveyStatus  = dataNom.get_surveyStat();
        	Log.d("Hasil Survey Status", Boolean.toString(dataNom.get_surveyStat()));
        	String uri = "checklist";
        	
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
						   Intent(v.getContext(), LihatMap.class);
				   			
				   //Bundle dataBundle = new Bundle();
				   int nomId = siteName.getId();
				   myIntent.putExtra("nom_id", nomId);
				   startActivityForResult(myIntent, 0);				   							 
			   }
		};		
	}


	public void onActivityCreated(Bundle savedInstanceState) { 
		Log.d("Masuk Fragment","Masuk");
		super.onActivityCreated(savedInstanceState); 
		showLeft.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View v) {
				((TugasSelesai) getActivity()).showLeft();
			}
		});

//		showSearch.setOnClickListener(new OnClickListener() { 
//			@Override
//			public void onClick(View v) {
//				((jobList) getActivity()).showSearch();
//			}
//		});
	 		
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

	private MyPageChangeListener myPageChangeListener;

	public void setMyPageChangeListener(MyPageChangeListener l) {

		myPageChangeListener = l;

	}

	public interface MyPageChangeListener {
		public void onPageSelected(int position);
	}

}
 