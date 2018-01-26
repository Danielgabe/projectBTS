package com.nichtemna.takeandsavephoto.fungsi;
import android.content.Context;
 
public class UserFunctions {
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseManager db = new DatabaseManager(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
     
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
    	DatabaseManager db = new DatabaseManager(context);
        db.resetTables();
        return true;
    }
     
}