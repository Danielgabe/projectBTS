package com.nichtemna.takeandsavephoto.fungsi;

import java.util.HashMap;

import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.nichtemna.takeandsavephoto.fungsi.LoginActivity;

public class SessionManager {
	SharedPreferences pref;
	Editor editor;
	Context _context;
	int Private_Mode = 0;
	
	private static final String Pref_Name = "pref_";
	private static final String Is_login = "is_Login_";
	public static final String User_Name = "username";
	public static final String User_Id = "id_user";
	public static final String User_Status = "user_status";	
	public static final String businessPartner_name = "businessPartner_name";
	public static final String menu_click = "kosong";
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(Pref_Name, Private_Mode);
		editor = pref.edit();
	}
	
	public void setMenuListener(String MenuClick)
	{
		Log.d("setMenuListener", MenuClick);
		editor.putString(menu_click,MenuClick);
		editor.commit();
	}
	public void CreateSeassion (String username, String id_user,String status,String businessPartner_name){
		//Log.d("Session", businessPartner_name);
		editor.putBoolean(Is_login, true);
		editor.putString(User_Name, username);
		editor.putString(User_Id, id_user);
		editor.putString(User_Status, status);
		editor.putString("businessPartner_name", businessPartner_name);
		editor.commit();
		//Log.d("Masuk Create Session", "Finish");
	}
	
	public void CheckUser (){
		if(!this.isLoggedIn()){
			Intent i = new Intent(_context, LoginActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			_context.startActivity(i);			
		}
	}
	public HashMap<String, String> getLastMenuPosition(){
		HashMap<String, String> menu = new HashMap<String, String>();
		menu.put(menu_click, pref.getString(menu_click, null));
		return menu;
	}
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		user.put(User_Name, pref.getString(User_Name, null));
		user.put(User_Id, pref.getString(User_Id, null));
		user.put("businessPartner_name",pref.getString(businessPartner_name,null));
		Log.d("pref =", pref.getString(User_Id, null));
		user.put(User_Status, pref.getString(User_Status, null));
		return user;
	}
	
	public void clearSession()
	{
		editor.clear();
		editor.putBoolean(Is_login, false);
		editor.commit();
		
	}
	
	public boolean isLoggedIn(){
		return pref.getBoolean(Is_login, false);
	}
}
