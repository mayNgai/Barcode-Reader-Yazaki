package com.dtc.service.yazaki.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 11/13/2017 AD.
 */

public class SharedPreference {
    public static final String PREFS_NAME = "PREFS";
    public static final String KEY_USERNAME = "user_name";
    public static final String KEY_REMEMBER = "remember_me";

    public SharedPreference() {
        super();
    }

    public static String getStringValue(Context context,String value) {
        SharedPreferences settings;
        String text = "";
        try {
            //settings = PreferenceManager.getDefaultSharedPreferences(context);
            settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            if(value.equalsIgnoreCase("username")){
                text = settings.getString(KEY_USERNAME, null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return text;
    }

    public static boolean getBooleanValue(Context context,String value){
        SharedPreferences settings;
        boolean res = false;
        try {
            //settings = PreferenceManager.getDefaultSharedPreferences(context);
            settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            if(value.equalsIgnoreCase("remember")){
                res = settings.getBoolean(KEY_REMEMBER, false);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return res;
    }

    public static void setStringValue(Context context,String name,String value){
        try {
            SharedPreferences settings = null;
            SharedPreferences.Editor editor = null;
            settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            editor = settings.edit();
            if(name.equalsIgnoreCase("username")){
                editor.putString(KEY_USERNAME,value);
            }

            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setBooleanValue(Context context,String name,boolean value){
        try {
            SharedPreferences settings = null;
            SharedPreferences.Editor editor = null;
            settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            editor = settings.edit();
            if(name.equalsIgnoreCase("remember")){
                editor.putBoolean(KEY_REMEMBER,value);
            }
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static SharedPreferences getPreferences(Context context){
        SharedPreferences settings = null;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings;
    }

    public static void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }
}
