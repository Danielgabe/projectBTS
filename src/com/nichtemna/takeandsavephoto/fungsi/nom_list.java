package com.nichtemna.takeandsavephoto.fungsi;

public class nom_list {
	
	//private variables
	int _nom_id;
	String _site_id;
	String _site_name;
	String _province;
	String _city;
	String _latitude;
	String _longitude;
	
	 public nom_list(){
         
	    }
	 public nom_list(int nom_id,String site_id,String site_name,String province,
			 String city,String latitude,String longitude){
		 this._nom_id = nom_id;
		 this._site_id = site_id;
		 this._site_name = site_name;
		 this._province = province;
		 this._city = city;
		 this._latitude = latitude;
		 this._longitude = longitude;			
	 }
	 
	 // set value
	 public void set_nom_id(int nom_id)
	 {
		 this._nom_id = nom_id;
	 }
	 public void set_site_id(String site_id)
	 {
		 this._site_id = site_id;
	 }
	 public void set_site_name(String site_name)
	 {
		 this._site_name = site_name;
	 }
	 public void set_site_province(String province)
	 {
		 this._province = province;		
	 }
	 public void set_city(String city)
	 {
		 this._city = city;
	 }
	 public void set_latitude(String latitude)
	 {
		 this._latitude = latitude;
	 }
	 public void set_longitude(String logitude)
	 {
		 this._longitude = logitude;
	 }
	 
	 //get value
	 public int get_nom_id()
	 {
		 return this._nom_id;
	 }
	 public String get_site_id()
	 {
		 return this._site_id;
	 }
	 public String get_site_name()
	 {
		 return this._site_name;
	 }
	 public String get_site_province()
	 {
		 return this._province;		
	 }
	 public String get_city()
	 {
		 return this._city;
	 }
	 public String get_latitude()
	 {
		 return this._latitude;
	 }
	 public String get_longitude()
	 {
		 return this._longitude;
	 }	 
}
