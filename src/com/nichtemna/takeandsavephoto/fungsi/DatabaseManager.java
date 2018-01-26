package com.nichtemna.takeandsavephoto.fungsi;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;

import com.nichtemna.takeandsavephoto.fragment.CommonUtil;
import com.nichtemna.takeandsavephoto.fragment.DataPhoto;
 
import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DatabaseManager extends SQLiteOpenHelper {
 
	SessionManager session;
	CommonUtil util;
	
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "bts_db";
 
    // Login table name
    private static final String TABLE_LOGIN = "login";
    private static final String Table_Nom = "nom_list";
    
    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_STATUS_EMPLOYEE_ID = "status_employee_id";
    private static final String KEY_ISLOGIN = "islogin";
    SQLiteDatabase db;
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override   
    
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_STATUS_EMPLOYEE_ID + " TEXT,"
                + KEY_ISLOGIN + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }
    
    public List<site_info> getSiteInfo(int nom_id)
    {
    	 String selectQuery = "select DISTINCT(a.nom_id),a.site_id,b.site_name,b.province,b.city,b.latitude,b.longitude,c.status from master_nom a"
         		+" INNER JOIN to_do c on a.nom_id = c.nom_id"
         		+" INNER JOIN datatower b on a.site_id = b.site_id";        		                	
      
    	 List<site_info> site_infoList = new ArrayList<site_info>();
         SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursor = db.rawQuery(selectQuery, null);
         Boolean statuSurvey;
         // looping through all rows and adding to list
         if (cursor.moveToFirst()) {
             do {
             	site_info _to_do_list = new site_info();
             	_to_do_list.set_nom_id(Integer.parseInt(cursor.getString(0)));
             	//Log.d("Site ID ", cursor.getString(0));
             	_to_do_list.set_site_id(cursor.getString(0));
             	//Log.d("Site Name ", cursor.getString(1));
             	_to_do_list.set_site_name(cursor.getString(2));    
             	//Log.d("Province", cursor.getString(0));
             	_to_do_list.set_site_province(cursor.getString(3));
             	_to_do_list.set_city(cursor.getString(4));
             	_to_do_list.set_latitude(cursor.getString(5));
             	_to_do_list.set_longitude(cursor.getString(6));
             	if(cursor.getInt(7) == 0)
             	{
             		statuSurvey = false;
             	}
             	else{ statuSurvey = true;}   
             	_to_do_list.set_surveyStat(statuSurvey);
                 // Adding nom_list to list
             	site_infoList.add(_to_do_list);
             } while (cursor.moveToNext());
         }
      
         // return contact list
         return site_infoList;
    }
    
    public void dropCreateDataTower()
    {   
    	//Log.d("Drop Data Tower","Masuk");
    	db = this.getWritableDatabase();
    	db.execSQL("DROP TABLE IF EXISTS datatower");
    	db.execSQL("CREATE TABLE datatower ("
    			  +"site_id TEXT,"
    			  +"site_name TEXT,"
    			  +"towertype TEXT,"
    			  +"site_address TEXT,"
    			  +"province TEXT,"
    			  +"city TEXT,"
    			  +"sub_district TEXT,"
    			  +"longitude TEXT,"
    			  +"latitude TEXT," +
    			  "update_time DATE)");
    	db.close();
    }
    
    
    
    public List<site_info> get_siteInfo(int nom_id){		
		List<site_info> to_doList = new ArrayList<site_info>();
        // Select All Query
		//Log.d("Where Nom Id", Integer.toString(nom_id));
        String selectQuery = "select DISTINCT(a.nom_id),a.site_id,b.site_name,b.province,b.city,b.latitude,b.longitude,c.status from master_nom a"
        		+" INNER JOIN to_do c on a.nom_id = c.nom_id"
        		+" INNER JOIN datatower b on a.site_id = b.site_id"
        		+" WHERE a.nom_id = "+nom_id;        		                	
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Boolean statuSurvey;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	site_info _to_do_list = new site_info();
            	_to_do_list.set_nom_id(Integer.parseInt(cursor.getString(0)));
            	_to_do_list.set_site_id(cursor.getString(1));
            	//Log.d("Cursor Site Id", cursor.getString(1));
            	_to_do_list.set_site_name(cursor.getString(2));                
            	_to_do_list.set_site_province(cursor.getString(3));
            	_to_do_list.set_city(cursor.getString(4));
            	_to_do_list.set_latitude(cursor.getString(5));
            	_to_do_list.set_longitude(cursor.getString(6));
            	if(cursor.getInt(7) == 0)
            	{
            		statuSurvey = false;
            	}
            	else{ statuSurvey = true;}   
            	_to_do_list.set_surveyStat(statuSurvey);
                // Adding nom_list to list
            	to_doList.add(_to_do_list);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return to_doList;
    	
    }
    
    public List<to_do> getAll_todo_list() {
    	//Log.d("Masuk get All Todo","Masuk");
        List<to_do> to_doList = new ArrayList<to_do>();
        // Select All Query
        String selectQuery = "select DISTINCT(a.nom_id),a.site_id,b.site_name,b.province,b.city,b.latitude,b.longitude,c.status from master_nom a"
        		+" INNER JOIN to_do c on a.nom_id = c.nom_id"
        		+" INNER JOIN datatower b on a.site_id = b.site_id";        		                	
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Boolean statuSurvey;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	to_do _to_do_list = new to_do();
            	_to_do_list.set_nom_id(Integer.parseInt(cursor.getString(0)));
            	_to_do_list.set_site_id(cursor.getString(1));
            	_to_do_list.set_site_name(cursor.getString(2));                
            	_to_do_list.set_site_province(cursor.getString(3));
            	_to_do_list.set_city(cursor.getString(4));
            	_to_do_list.set_latitude(cursor.getString(5));
            	_to_do_list.set_longitude(cursor.getString(6));
            	if(cursor.getInt(7) == 0)
            	{
            		statuSurvey = false;
            	}
            	else{ statuSurvey = true;}   
            	_to_do_list.set_surveyStat(statuSurvey);
                // Adding nom_list to list
            	to_doList.add(_to_do_list);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return to_doList;
    }
    
    public List<nom_list> getAll_nom_list() {
    	Log.d("getAll_nom_list","MASUK");
        List<nom_list> nomList = new ArrayList<nom_list>();
        // Select All Query
        String selectQuery = "select DISTINCT(a.nom_id),a.site_id,b.site_name,b.province,b.city,b.latitude,b.longitude from master_nom a"
        		+" inner join datatower b on a.site_id = b.site_id"
        		+" where a.nom_id not in ( select to_do.nom_id from to_do)";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Log.d("getAll_nom_list","inserting List " + cursor.getString(0));
                nom_list _nom_list = new nom_list();
                _nom_list.set_nom_id(Integer.parseInt(cursor.getString(0)));
                _nom_list.set_site_id(cursor.getString(1));
                _nom_list.set_site_name(cursor.getString(2));                
                _nom_list.set_site_province(cursor.getString(3));
                _nom_list.set_city(cursor.getString(4));
                _nom_list.set_latitude(cursor.getString(5));
                _nom_list.set_longitude(cursor.getString(6));
                // Adding nom_list to list
                nomList.add(_nom_list);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return nomList;
    }
    
    public void addDataTower(String site_id, String site_name, String towertype, 
    		String site_address,String province, String city, String sub_district,String longitude, String latitude,String update_time)
    {   
    	//Log.d("Add Data Tower","Masuk");
    	db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
        values.put("site_id", site_id); 
        values.put("site_name", site_name);
        values.put("towertype",towertype);
        values.put("site_address", site_address);
        values.put("province", province);
        values.put("city", province);
        values.put("sub_district", sub_district);
        values.put("longitude", longitude);
        values.put("latitude", latitude);        
        values.put("update_time", update_time);
        // Inserting Row
        db.insert("datatower", null, values);
        
        db.close(); // Closing database conneection"    			  	
    }
    
    public Boolean checkSurvey(int nom_id, String user_id)
    {
    	//Log.d("Masuk Check Survey", "Nom = "+nom_id);
    	db = this.getWritableDatabase();
    	String selectQuery = "select panorama, param_2,param_3,param_4 from data_survey"
    			+" where nom_id = "+nom_id;
    	Cursor cursor = db.rawQuery(selectQuery, null);
        Boolean statuSurvey = null;
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
        	if(CommonUtil.isNotNullOrEmpty(cursor.getString(0)) && CommonUtil.isNotNullOrEmpty(cursor.getString(1)) &&  CommonUtil.isNotNullOrEmpty(cursor.getString(2)) &&  CommonUtil.isNotNullOrEmpty(cursor.getString(3)))
        	{
        		statuSurvey = true;
        	}
        	else
        	{
        		statuSurvey = false;        
        	}
        }
        else{
        	statuSurvey = false;
        	ContentValues values = new ContentValues();        	
    		//Log.d("Masuk  Nom",Integer.toString(nom_id));
    		//Log.d("Masuk  user Id",user_id);
    		values.put("nom_id", nom_id);
            values.put("user_id", user_id); 
            // Inserting Row
            db.insert("data_survey", null, values);
            db.close(); // Closing database connection
        }
        //Log.d("Masuk Hasil Survey", Boolean.toString(statuSurvey));
		return statuSurvey;		
    }
    
    public void createTableSurvey()
    {
    	//Log.d("Masuk Table Survey","Masuk");
    	db = this.getWritableDatabase();
    	db.execSQL("create table if not exists data_survey" +
    			"(id INTEGER PRIMARY KEY AUTOINCREMENT,"
    			+"nom_id INTEGER ,"
    			+"user_id TEXT,"
    			+"upload_date TEXT,"
    			+"panorama TEXT,"
    			+"param_2 text,"
    			+"param_3 text,"
    			+"param_4 text,"
    			+"checklis text"
    			+")");
    	db.close();
    }
    
    public void dropCreateto_do()
    {
    	//Log.d("Drop Create To Do", "Masuk");
    	db = this.getWritableDatabase();
    	db.execSQL("DROP TABLE IF EXISTS to_do");
    	db.execSQL("CREATE TABLE to_do ("
        		+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom_id INTEGER,"
        		+ "status INTEGER DEFAULT 0,"+
                "update_time DATE)");
    	db.close();    	
    }
    
    public void addto_do(int nom_id, String update_time)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put("nom_id", nom_id); 
        values.put("update_time", update_time);
        // Inserting Row
        db.insert("to_do", null, values);
        db.close(); // Closing database connection
    }
 
    
    public void dropCreateMasterNom()
    {   
    	db = this.getWritableDatabase();
    	db.execSQL("DROP TABLE IF EXISTS master_nom");
    	db.execSQL("CREATE TABLE master_nom ("
        		+ "nom_id INTEGER PRIMARY KEY,"
                + "site_id TEXT,"
                + "bussines_id  INTEGER,"
                + "survey_status  INTEGER," +
                "update_time DATE )");
    	db.close();
    }
    
    public Boolean checkedTable(String TableName)
    {
    	Boolean existing = false;
    	db = this.getWritableDatabase();
    	String selectQuery = "SELECT COUNT() FROM sqlite_master WHERE name ='"+TableName+"'";
    	Cursor cursor = db.rawQuery(selectQuery, null);        
        cursor.moveToFirst();
        if(cursor.getInt(0) > 0){
           existing = true;
        }
        return existing;
    }
    
    public void Todo_update(int nom_id, String update_time){
    	db = this.getWritableDatabase();
    	
    	db.execSQL("DELETE FROM to_do WHERE nom_id = "+nom_id);
    	    	
    	ContentValues values = new ContentValues();
        values.put("nom_id", nom_id); 
        values.put("update_time", update_time);
        // Inserting Row
        db.insert("to_do", null, values);        
        db.close(); // 
    }
    
    public void update_DataTower(String site_id, String site_name, String towertype, 
    		String site_address,String province, String city, String sub_district,String longitude, String latitude,String update_time)
    {    	
    	db = this.getWritableDatabase();
    	db.execSQL("DELETE FROM datatower WHERE site_id = '"+site_id+"'");

    	ContentValues values = new ContentValues();
        values.put("site_id", site_id); 
        values.put("site_name", site_name);
        values.put("towertype",towertype);
        values.put("site_address", site_address);
        values.put("province", province);
        values.put("city", province);
        values.put("sub_district", sub_district);
        values.put("longitude", longitude);
        values.put("latitude", latitude);        
        values.put("update_time", update_time);
        // Inserting Row
        db.insert("datatower", null, values);
        
        db.close(); // Closing database conneection"
    }
    
    public void updateNom(int nom_id, String site_id, int bussines_id, int survey_status, String Date ){
    	db = this.getWritableDatabase();
    	
    	db.execSQL("DELETE FROM master_nom WHERE nom_id = "+nom_id);
    	    	
        ContentValues values = new ContentValues();
        values.put("nom_id", nom_id); 
        values.put("site_id", site_id);
        values.put("bussines_id", bussines_id); 
        values.put("survey_status", site_id);
        values.put("update_time", Date);
        // Inserting Row
        db.insert("master_nom", null, values);
        db.close(); // 
    }
    
    public void addMaster_Nom(int nom_id, String site_id, int bussines_id, int survey_status, String Date )
    {
    	Log.d("addMaster_Nom","Masuk");
    	db = this.getWritableDatabase();    	 
        ContentValues values = new ContentValues();
        values.put("nom_id", nom_id); 
        values.put("site_id", site_id);
        values.put("bussines_id", bussines_id); 
        values.put("survey_status", site_id);
        values.put("update_time", Date);
        Log.d("addMaster_Nom","Inserting Row");
        // Inserting Row
        db.insert("master_nom", null, values);
        db.close(); // Closing database connection
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * Storing user details in database
     * */
    public void addUser(String status_employee_id, String islogin) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        
        ContentValues values = new ContentValues();
        values.put(KEY_STATUS_EMPLOYEE_ID, status_employee_id); 
        values.put(KEY_ISLOGIN, islogin);
 
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }
     
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
          
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("status_employee_id", cursor.getString(1));
            user.put("islogin", cursor.getString(2));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }
 
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
         
        // return row count
        return rowCount;
    }
     
    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();
    }
    
    public site_info getDataSiteByNom(int nom_id) {
    	site_info _to_do_list = new site_info();
        // Select All Query
    	String selectQuery = "select DISTINCT(a.nom_id),a.site_id,b.site_name,b.province,b.city,b.latitude,b.longitude,c.status from master_nom a"
         		+" INNER JOIN to_do c on a.nom_id = c.nom_id"
         		+" INNER JOIN datatower b on a.site_id = b.site_id"
         		+" WHERE a.nom_id = "+nom_id;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
        	boolean statuSurvey=false;
            do {
            	_to_do_list.set_nom_id(Integer.parseInt(cursor.getString(0)));
            	_to_do_list.set_site_id(cursor.getString(1));
            	//Log.d("Cursor Site Id", cursor.getString(1));
            	_to_do_list.set_site_name(cursor.getString(2));                
            	_to_do_list.set_site_province(cursor.getString(3));
            	_to_do_list.set_city(cursor.getString(4));
            	_to_do_list.set_latitude(cursor.getString(5));
            	_to_do_list.set_longitude(cursor.getString(6));
            	if(cursor.getInt(7) == 0)
            	{
            		statuSurvey = false;
            	}
            	else{ statuSurvey = true;}   
            	_to_do_list.set_surveyStat(statuSurvey);
                // Adding nom_list to list
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return _to_do_list;
    }
    
  //for info site & ambil survey
    public void createDataPhoto()
    {   
    	//Log.d("davin","create table ");
    	db = this.getWritableDatabase();
    	//db.execSQL("DROP TABLE  EXISTS dataphoto");
    	db.execSQL("CREATE TABLE IF NOT EXISTS dataphoto ("
    			  +"user_id TEXT,"
    			  +"nom_id INTEGER,"
    			  +"panoramic TEXT,"
    			  +"param_2 TEXT,"
    			  +"param_3 TEXT,"
    			  +"param_4 TEXT,"
    			  +"checklis TEXT,"
    			  +"upload_status TEXT,"
    			  +"seq_no INTEGER,"
    			  +"buttonnya TEXT,"
    			  +"lat TEXT,"
    			  +"longt TEXT,"
    			  +"param_5 TEXT)");
    	db.close();
    }
    
    public void addDataPhoto(String user_id, int nom_id, String panoramic, String param_2, String param_3, String param_4, String checklis, int seqNo, String buttonnya, String lat, String longt, String param_5)
    {
    	//Log.d("davin","Masuk add");
    	db = this.getWritableDatabase();    	 
        ContentValues values = new ContentValues();
        values.put("user_id", user_id); 
        values.put("nom_id", nom_id);
        values.put("panoramic", panoramic);
        values.put("param_2", param_2); 
        values.put("param_3", param_3); 
        values.put("param_4", param_4);
        values.put("checklis", checklis); 
        values.put("upload_status", ""); 
        values.put("seq_no", seqNo);
        values.put("buttonnya", buttonnya);
        values.put("lat", lat);
        values.put("longt", longt);
        values.put("param_5", param_5);
 
        // Inserting Row
        db.insert("dataphoto", null, values);
        db.close(); // Closing database connection
    }
    
    public int getCountDataPhotoByNom(int nom_id) {
        String countQuery = "SELECT * FROM dataphoto WHERE nom_id = "+nom_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
         
        // return row count
        return rowCount;
    }
    
    public int getCountDataPhotoByNomButton(int nom_id, String button) {
        String countQuery = "SELECT * FROM dataphoto WHERE nom_id = "+nom_id+" AND buttonnya = '"+button+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
         
        // return row count
        return rowCount;
    }
    
    public void updateDataPhoto(String column, String value, int nom_id)
    {
    	//Log.d("davin", "update");
    	db = this.getWritableDatabase();
    	db.execSQL("UPDATE dataphoto SET "
        		+ column +" = '"+value+"' WHERE nom_id = "+nom_id);
    	db.close();    	
    }
    
    public void updateDataPhotoByButton(String column, String button, String value, int nom_id)
    {
    	//Log.d("davin", "update");
    	db = this.getWritableDatabase();
    	db.execSQL("UPDATE dataphoto SET "
        		+ column +" = '"+value+"' WHERE nom_id = "+nom_id+" AND buttonnya = '"+button+"'");
    	db.close();    	
    }
    
    public void updateDataPhotoSeqNo(int value, int nom_id)
    {
    	//Log.d("davin", "update");
    	db = this.getWritableDatabase();
    	db.execSQL("UPDATE dataphoto SET "
        		+"seq_no = "+value+" WHERE nom_id = "+nom_id);
    	db.close();    	
    }
    
    public void updateDataPhotoSeqNoButton(int value, int nom_id, String button)
    {
    	//Log.d("davin", "update");
    	db = this.getWritableDatabase();
    	db.execSQL("UPDATE dataphoto SET "
        		+"seq_no = "+value+" WHERE nom_id = "+nom_id+" AND buttonnya = '"+button+"'");
    	db.close();    	
    }
    
    public void updateLatLongNomidButton(String lat, String longt, int nom_id, String button)
    {
    	//Log.d("davin", "update");
    	db = this.getWritableDatabase();
    	db.execSQL("UPDATE dataphoto SET "
        		+"lat = '"+lat+"' , longt = '"+longt+"' WHERE nom_id = "+nom_id+" AND buttonnya = '"+button+"'");
    	db.close();    	
    }
    
    public DataPhoto getDataPhotoByNom(int nom_id) {
    	DataPhoto dp = new DataPhoto();
        // Select All Query
        String selectQuery = "select * from dataphoto "
        		+" where nom_id = "+nom_id;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                dp.setUserid(cursor.getString(0));
                dp.setNomid(Integer.parseInt(cursor.getString(1)));
                dp.setPanoramic(cursor.getString(2));                
                dp.setParam_2(cursor.getString(3));
                dp.setParam_3(cursor.getString(4));
                dp.setParam_4(cursor.getString(5));
                dp.setChecklis(cursor.getString(6));
                dp.setUploadStatus(cursor.getString(7));
                dp.setSeqNo(cursor.getInt(8));
                dp.setButtonnya(cursor.getString(9));
                dp.setLat(cursor.getString(10));
                dp.setLongt(cursor.getString(11));
                dp.setParam_5(cursor.getString(12));
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return dp;
    }
    
    public DataPhoto getDataPhotoByNomButton(int nom_id, String button) {
    	DataPhoto dp = new DataPhoto();
        // Select All Query
        String selectQuery = "select * from dataphoto "
        		+" where nom_id = "+nom_id+" AND buttonnya = '"+button+"'";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                dp.setUserid(cursor.getString(0));
                dp.setNomid(Integer.parseInt(cursor.getString(1)));
                dp.setPanoramic(cursor.getString(2));                
                dp.setParam_2(cursor.getString(3));
                dp.setParam_3(cursor.getString(4));
                dp.setParam_4(cursor.getString(5));
                dp.setChecklis(cursor.getString(6));
                dp.setUploadStatus(cursor.getString(7));
                dp.setSeqNo(cursor.getInt(8));
                dp.setButtonnya(cursor.getString(9));
                dp.setLat(cursor.getString(10));
                dp.setLongt(cursor.getString(11));
                dp.setParam_5(cursor.getString(12));
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return dp;
    }
    
    public List<DataPhoto> getListAllSurveyByUser(String userid) {
    	List<DataPhoto> dpList = new ArrayList<DataPhoto>();
        // Select All Query
        String selectQuery = "select a.user_id, a.nom_id, a.panoramic, a.param_2, a.param_3, a.param_4, a.checklis, a.upload_status, a.seq_no, a.buttonnya from dataphoto a "
        		+"INNER JOIN to_do c on a.nom_id = c.nom_id "
        		+"where a.user_id = '"+userid+"' "
        		+"AND c.status = 1";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	DataPhoto dp = new DataPhoto();
                dp.setUserid(cursor.getString(0));
                dp.setNomid(Integer.parseInt(cursor.getString(1)));
                dp.setPanoramic(cursor.getString(2));                
                dp.setParam_2(cursor.getString(3));
                dp.setParam_3(cursor.getString(4));
                dp.setParam_4(cursor.getString(5));
                dp.setChecklis(cursor.getString(6));
                dp.setUploadStatus(cursor.getString(7));
                dp.setSeqNo(cursor.getInt(8));
                dp.setButtonnya(cursor.getString(9));
                dp.setLat(cursor.getString(10));
                dp.setLongt(cursor.getString(11));
                dp.setParam_5(cursor.getString(12));
                dpList.add(dp);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return dpList;
    }
    
    public List<DataPhoto> getListAllSurveyDataByUser(String userid) {
    	List<DataPhoto> dpList = new ArrayList<DataPhoto>();
        // Select All Query
        String selectQuery = "select * from dataphoto "
        		+"where user_id = '"+userid;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	DataPhoto dp = new DataPhoto();
                dp.setUserid(cursor.getString(0));
                dp.setNomid(Integer.parseInt(cursor.getString(1)));
                dp.setPanoramic(cursor.getString(2));                
                dp.setParam_2(cursor.getString(3));
                dp.setParam_3(cursor.getString(4));
                dp.setParam_4(cursor.getString(5));
                dp.setChecklis(cursor.getString(6));
                dp.setUploadStatus(cursor.getString(7));
                dp.setSeqNo(cursor.getInt(8));
                dp.setButtonnya(cursor.getString(9));
                dp.setLat(cursor.getString(10));
                dp.setLongt(cursor.getString(11));
                dp.setParam_5(cursor.getString(12));
                dpList.add(dp);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return dpList;
    }
    
    public List<DataPhoto> getListAllSurveyDataByNom(int nomid) {
    	List<DataPhoto> dpList = new ArrayList<DataPhoto>();
        // Select All Query
        String selectQuery = "select * from dataphoto "
        		+"where nom_id = "+nomid;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	DataPhoto dp = new DataPhoto();
                dp.setUserid(cursor.getString(0));
                dp.setNomid(Integer.parseInt(cursor.getString(1)));
                dp.setPanoramic(cursor.getString(2));                
                dp.setParam_2(cursor.getString(3));
                dp.setParam_3(cursor.getString(4));
                dp.setParam_4(cursor.getString(5));
                dp.setChecklis(cursor.getString(6));
                dp.setUploadStatus(cursor.getString(7));
                dp.setSeqNo(cursor.getInt(8));
                dp.setButtonnya(cursor.getString(9));
                dp.setLat(cursor.getString(10));
                dp.setLongt(cursor.getString(11));
                dp.setParam_5(cursor.getString(12));
                dpList.add(dp);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return dpList;
    }
    
    public List<DataPhoto> getListAllSurveyDataByNomStatus(int nomid) {
    	List<DataPhoto> dpList = new ArrayList<DataPhoto>();
        // Select All Query
        String selectQuery = "select * from dataphoto "
        		+"where nom_id = "+nomid+" AND upload_status = 'READY'";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	DataPhoto dp = new DataPhoto();
                dp.setUserid(cursor.getString(0));
                dp.setNomid(Integer.parseInt(cursor.getString(1)));
                dp.setPanoramic(cursor.getString(2));                
                dp.setParam_2(cursor.getString(3));
                dp.setParam_3(cursor.getString(4));
                dp.setParam_4(cursor.getString(5));
                dp.setChecklis(cursor.getString(6));
                dp.setUploadStatus(cursor.getString(7));
                dp.setSeqNo(cursor.getInt(8));
                dp.setButtonnya(cursor.getString(9));
                dp.setLat(cursor.getString(10));
                dp.setLongt(cursor.getString(11));
                dp.setParam_5(cursor.getString(12));
                dpList.add(dp);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return dpList;
    }
    
    public void updateToDo(int value, int nom_id)
    {
    	db = this.getWritableDatabase();
    	db.execSQL("UPDATE to_do SET status"
    			+" = "+value+" WHERE nom_id = "+nom_id);
    	db.close();    	
    }
    
    
    public int getSurveyByUserNom(String userid, int nomid) {
    	int result = 0;
        // Select All Query
        String selectQuery = "select c.status from dataphoto a "
        		+"INNER JOIN to_do c on a.nom_id = c.nom_id "
        		+"where a.user_id = '"+userid+"' "
        		+"AND a.nom_id = "+nomid;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	result = Integer.parseInt(cursor.getString(0));
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return result;
    }
    
    public int getCountNomByUser(String userid) {
        String countQuery = "SELECT nom_id FROM dataphoto WHERE user_id = '"+userid+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
         
        // return row count
        return rowCount;
    }
    
    public int getCountDataPhotoReadyUpload(int nomid) {
        String countQuery = "SELECT * FROM dataphoto WHERE nom_id = "+nomid+" AND upload_status = 'READY'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
         
        // return row count
        return rowCount;
    }
    
    public int getCountDataPhotoUloadedUpload(int nomid) {
        String countQuery = "SELECT * FROM dataphoto WHERE nom_id = "+nomid+" AND upload_status = 'UPLOADED'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
         
        // return row count
        return rowCount;
    }
    
    public nom_list getSpesific_nom(int nomid) {
    	Log.d("getAll_nom_list","MASUK");
        nom_list nomList = new nom_list();
        // Select All Query
        String selectQuery = "select a.nom_id,a.site_id,b.site_name,b.province,b.city,b.latitude,b.longitude from master_nom a"
        		+" inner join datatower b on a.site_id = b.site_id"
        		+" where a.nom_id = "+nomid;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	/*Log.d("getAll_nom_list","inserting List " + cursor.getString(0));
            	Log.d("getAll_nom_list","inserting List " + cursor.getString(1));
            	Log.d("getAll_nom_list","inserting List " + cursor.getString(2));
            	Log.d("getAll_nom_list","inserting List " + cursor.getString(3));
            	Log.d("getAll_nom_list","inserting List " + cursor.getString(4));
            	Log.d("getAll_nom_list","inserting List " + cursor.getString(5));
            	Log.d("getAll_nom_list","inserting List " + cursor.getString(6));*/
                nomList.set_nom_id(Integer.parseInt(cursor.getString(0)));
                nomList.set_site_id(cursor.getString(1));
                nomList.set_site_name(cursor.getString(2));                
                nomList.set_site_province(cursor.getString(3));
                nomList.set_city(cursor.getString(4));
                nomList.set_latitude(cursor.getString(5));
                nomList.set_longitude(cursor.getString(6));
                // Adding nom_list to list
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return nomList;
    }
    
    public int getToDoStatus(int nomid){
    	int result = 0;
    	
    	String selectQuery = "select status from to_do "
        		+"where nom_id = "+nomid;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	result = Integer.parseInt(cursor.getString(0));
            } while (cursor.moveToNext());
        }
    	
    	return result;
    }
    
    public void createPhotoUploaded()
    {   
    	//Log.d("davin","create table ");
    	db = this.getWritableDatabase();
    	db.execSQL("CREATE TABLE IF NOT EXISTS photo_uploaded ("
    			  +"user_id TEXT,"
    			  +"nom_id INTEGER,"
    			  +"filename TEXT)");
    	db.close();
    }
    
    public void insertPhotoUploaded(String user_id, int nom_id, String filename)
    {
    	//Log.d("davin","Masuk add");
    	db = this.getWritableDatabase();    	 
        ContentValues values = new ContentValues();
        values.put("user_id", user_id); 
        values.put("nom_id", nom_id);
        values.put("filename", filename);
 
        // Inserting Row
        db.insert("photo_uploaded", null, values);
        db.close(); // Closing database connection
    }
    
    public int getCountPhotoUploaded(String filename) {
        String countQuery = "SELECT * FROM photo_uploaded WHERE filename = '"+filename+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
         
        // return row count
        return rowCount;
    }
    
    public List<to_do> getAll_todo_list_1() {
    	//Log.d("Masuk get All Todo","Masuk");
        List<to_do> to_doList = new ArrayList<to_do>();
        // Select All Query
        String selectQuery = "select DISTINCT(a.nom_id),a.site_id,b.site_name,b.province,b.city,b.latitude,b.longitude,c.status from master_nom a"
        		+" INNER JOIN to_do c on a.nom_id = c.nom_id"
        		+" INNER JOIN datatower b on a.site_id = b.site_id"
        		+" WHERE c.status = 1";        		                	
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Boolean statuSurvey;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	to_do _to_do_list = new to_do();
            	_to_do_list.set_nom_id(Integer.parseInt(cursor.getString(0)));
            	_to_do_list.set_site_id(cursor.getString(1));
            	_to_do_list.set_site_name(cursor.getString(2));                
            	_to_do_list.set_site_province(cursor.getString(3));
            	_to_do_list.set_city(cursor.getString(4));
            	_to_do_list.set_latitude(cursor.getString(5));
            	_to_do_list.set_longitude(cursor.getString(6));
            	if(cursor.getInt(7) == 0)
            	{
            		statuSurvey = false;
            	}
            	else{ statuSurvey = true;}   
            	_to_do_list.set_surveyStat(statuSurvey);
                // Adding nom_list to list
            	to_doList.add(_to_do_list);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return to_doList;
    }
    
    public List<to_do> getAll_todo_list_0() {
    	//Log.d("Masuk get All Todo","Masuk");
        List<to_do> to_doList = new ArrayList<to_do>();
        // Select All Query
        String selectQuery = "select DISTINCT(a.nom_id),a.site_id,b.site_name,b.province,b.city,b.latitude,b.longitude,c.status from master_nom a"
        		+" INNER JOIN to_do c on a.nom_id = c.nom_id"
        		+" INNER JOIN datatower b on a.site_id = b.site_id"
        		+" WHERE c.status = 0";        		                	
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Boolean statuSurvey;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	to_do _to_do_list = new to_do();
            	_to_do_list.set_nom_id(Integer.parseInt(cursor.getString(0)));
            	_to_do_list.set_site_id(cursor.getString(1));
            	_to_do_list.set_site_name(cursor.getString(2));                
            	_to_do_list.set_site_province(cursor.getString(3));
            	_to_do_list.set_city(cursor.getString(4));
            	_to_do_list.set_latitude(cursor.getString(5));
            	_to_do_list.set_longitude(cursor.getString(6));
            	if(cursor.getInt(7) == 0)
            	{
            		statuSurvey = false;
            	}
            	else{ statuSurvey = true;}   
            	_to_do_list.set_surveyStat(statuSurvey);
                // Adding nom_list to list
            	to_doList.add(_to_do_list);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return to_doList;
    }
    
}
