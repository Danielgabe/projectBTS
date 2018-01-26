package com.nichtemna.takeandsavephoto.activity;

import com.nichtemna.takeandsavephoto.R;
import com.nichtemna.takeandsavephoto.fragment.LeftFragment;
import com.nichtemna.takeandsavephoto.fragment.RightFragment;
import com.nichtemna.takeandsavephoto.fragment.TugasSaya;
import com.nichtemna.takeandsavephoto.fragment.TugasSaya.MyPageChangeListener;
import com.nichtemna.takeandsavephoto.fungsi.SessionManager;
import com.nichtemna.takeandsavephoto.view.SlidingMenu;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class jobList  extends FragmentActivity {

	SlidingMenu mSlidingMenu;
	LeftFragment leftFragment; 
	RightFragment rightFragment; 
	SessionManager session;
	TugasSaya tugasSaya;   
	FragmentManager fragmentManager=getSupportFragmentManager();
	
	@Override
	protected void onCreate(Bundle arg0) {
		session = new SessionManager(getApplicationContext());
		//session.CheckUser();
		Log.d("Masuk", "JobList");
		super.onCreate(arg0);
		setContentView(R.layout.main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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

		tugasSaya = new TugasSaya();
		t.replace(R.id.center_frame, tugasSaya);
		Log.d("Masuk", "init viewPageFragment");
		t.commit();
	}

	private void initListener() {
		Log.d("Masuk", "initListener");
		tugasSaya.setMyPageChangeListener(new MyPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				if(tugasSaya.isFirst()){
					mSlidingMenu.setCanSliding(true,false);
				}else if(tugasSaya.isEnd()){
					mSlidingMenu.setCanSliding(false,true);
				}else{
					mSlidingMenu.setCanSliding(false,false);
				}
			}
		});
	}

	public void showLeft() {
		Log.d("ShowLeft", "Click");
		mSlidingMenu.showLeftView();
	}  
	public void showSearch() {
		mSlidingMenu.showRightView();
	}
	public void lihatMap() {
		mSlidingMenu.lihatMap();
		
		
	}

}
