package com.nichtemna.takeandsavephoto.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nichtemna.takeandsavephoto.*;
import com.nichtemna.takeandsavephoto.fungsi.DatabaseManager;

import android.app.Activity; 
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;   
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Obstacle extends Activity {
	
	private RadioGroup radioChoose;
	private RadioButton radioChooseButton1;
	private RadioButton radioChooseButton2;
	private RadioButton radioChooseButton;
	private Button btnSimpan;
	private Button btnNone;
	private String valBtnSimpan="";
	private DatabaseManager db;
	private String dataSebelumnya;
 
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        setContentView(R.layout.obstacle);
	        
	        db = new DatabaseManager(Obstacle.this);
	        radioChoose = (RadioGroup) findViewById(R.id.radioChoose);
	        btnSimpan = (Button) findViewById(R.id.button1);
	        btnNone = (Button) findViewById(R.id.button2);
	        radioChooseButton1 = (RadioButton) findViewById(R.id.radioButton1);
	        radioChooseButton2 = (RadioButton) findViewById(R.id.radioButton2);
	        
	        dataSebelumnya = db.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya()).getParam_5();
	        if(dataSebelumnya.contains(StatusAmbilSurvey.getButtonChecklist()+"1"))
	        {
	        	radioChooseButton2.setChecked(true);
	        	radioChooseButton1.setChecked(false);
	        }else if(dataSebelumnya.contains(StatusAmbilSurvey.getButtonChecklist()+"2"))
	        {
	        	radioChooseButton2.setChecked(false);
	        	radioChooseButton1.setChecked(true);
	        }else{
	        	radioChooseButton2.setChecked(false);
	        	radioChooseButton1.setChecked(true);
	        }
	        
	        btnSimpan.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int selectedId = radioChoose.getCheckedRadioButtonId();
					 
					// find the radiobutton by returned id
					radioChooseButton = (RadioButton) findViewById(selectedId);
		 
					//Toast.makeText(Obstacle.this, radioChooseButton.getText(), Toast.LENGTH_SHORT).show();
					if(radioChooseButton.getText().equals("Existing ?")){
						valBtnSimpan = "1";
					}else{
						valBtnSimpan = "2";
					}
					
					String valAllChecklist = "";
					//String oldData = db.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya()).getParam_5();
					if(CommonUtil.isNotNullOrEmpty(dataSebelumnya)){
						if(dataSebelumnya.contains(StatusAmbilSurvey.getButtonChecklist())){
							String result1 = dataSebelumnya.replaceAll((StatusAmbilSurvey.getButtonChecklist()+"1;"), "");
							String result2 = result1.replaceAll((StatusAmbilSurvey.getButtonChecklist()+"2;"), "");
							valAllChecklist += result2;
							valAllChecklist += StatusAmbilSurvey.getButtonChecklist()+valBtnSimpan+";";
						}else{
							valAllChecklist += dataSebelumnya;
							valAllChecklist += StatusAmbilSurvey.getButtonChecklist()+valBtnSimpan+";";
						}
						
					}else{
						valAllChecklist += StatusAmbilSurvey.getButtonChecklist()+valBtnSimpan+";";
					}
					
					db.updateDataPhotoByButton("param_5", StatusAmbilSurvey.getButtonnya(), valAllChecklist, StatusAmbilSurvey.getNomid());
					
					Intent myIntent= new Intent(v.getContext(), CheckList.class);
					myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(myIntent); 
					finish();
				}
	     
	    	});
	        
	        btnNone.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					String valAllChecklist = "";
					//String oldData = db.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya()).getParam_5();
					if(CommonUtil.isNotNullOrEmpty(dataSebelumnya)){
						if(dataSebelumnya.contains(StatusAmbilSurvey.getButtonChecklist())){
							String result1 = dataSebelumnya.replaceAll((StatusAmbilSurvey.getButtonChecklist()+"1;"), "");
							String result2 = result1.replaceAll((StatusAmbilSurvey.getButtonChecklist()+"2;"), "");
							valAllChecklist += result2;
							db.updateDataPhotoByButton("param_5", StatusAmbilSurvey.getButtonnya(), valAllChecklist, StatusAmbilSurvey.getNomid());
						}
						
					}
					
					Intent myIntent= new Intent(v.getContext(), CheckList.class);
					myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(myIntent); 
					finish();
				}
	     
	    	});
	 }
	 
	 @Override
	 public void onBackPressed() {
		 //super.onBackPressed();
	 }
	 
}