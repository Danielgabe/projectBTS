
package com.nichtemna.takeandsavephoto.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;    
import android.util.Log;    

import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.fragment.LeftFragment;
import com.nichtemna.takeandsavephoto.fragment.LihatMap;
import com.nichtemna.takeandsavephoto.fragment.RightFragment;
import com.nichtemna.takeandsavephoto.fragment.ViewPageFragment;
import com.nichtemna.takeandsavephoto.fragment.ViewPageFragment.MyPageChangeListener;
import com.nichtemna.takeandsavephoto.fungsi.SessionManager;
import com.nichtemna.takeandsavephoto.view.SlidingMenu;  

public class SlidingActivity extends FragmentActivity {
	 
	SlidingMenu mSlidingMenu;
	LeftFragment leftFragment; 
	RightFragment rightFragment; 
	SessionManager session;
	ViewPageFragment viewPageFragment;   
	FragmentManager fragmentManager=getSupportFragmentManager();

	
 
	@Override
	protected void onCreate(Bundle arg0) {
		session = new SessionManager(getApplicationContext());
		session.CheckUser();
		Log.d("Masuk", "Sliding Activity");
		super.onCreate(arg0);
		setContentView(R.layout.main);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		init();
		initListener();  
		
	} 
	private void init() {
		Log.d("Masuk", "init");
		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
		mSlidingMenu.setLeftView(getLayoutInflater().inflate(
				R.layout.left_frame, null));  
		mSlidingMenu.setRightView(getLayoutInflater().inflate(
				R.layout.right_frame, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(
				R.layout.center_frame, null)); 
		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();
		leftFragment = new LeftFragment();
		t.replace(R.id.left_frame, leftFragment);
	 
		
		rightFragment = new RightFragment();
		t.replace(R.id.right_frame, rightFragment);
		Log.d("Masuk", "init rightFragment");

		viewPageFragment = new ViewPageFragment();
		t.replace(R.id.center_frame, viewPageFragment);
		Log.d("Masuk", "init viewPageFragment");
		t.commit();
	}

	private void initListener() {
		Log.d("Masuk", "initListener");
		viewPageFragment.setMyPageChangeListener(new MyPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				if(viewPageFragment.isFirst()){
					mSlidingMenu.setCanSliding(true,false);
				}else if(viewPageFragment.isEnd()){
					mSlidingMenu.setCanSliding(false,true);
				}else{
					mSlidingMenu.setCanSliding(false,false);
				}
			}
		});
	}

	public void showLeft() {
		mSlidingMenu.showLeftView();
	}  
	public void showSearch() {
		mSlidingMenu.showRightView();
	}
	public void lihatMap() {
		mSlidingMenu.lihatMap();
		
		
	}

}
