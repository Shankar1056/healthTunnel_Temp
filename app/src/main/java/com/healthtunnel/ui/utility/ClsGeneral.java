package com.healthtunnel.ui.utility;


import android.content.Context;
import android.content.SharedPreferences;

public class ClsGeneral {

    public static void setPreferences(String key, String value) {
        SharedPreferences.Editor editor = AppController.getInstance().getSharedPreferences(
                "WED_APP", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPreferences(String key) {
        SharedPreferences prefs = AppController.getInstance().getSharedPreferences("WED_APP",
                Context.MODE_PRIVATE);
        String text = prefs.getString(key, "");
        return text;
    }

    public static void setBooleanPreference(String key, Boolean value) {
        SharedPreferences.Editor editor = AppController.getInstance().getSharedPreferences(
                "WED_APP", Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static Boolean getBooleanPreferences(String key) {
        SharedPreferences prefs = AppController.getInstance().getSharedPreferences("WED_APP",
                Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static void setIntPreference(String key, Integer value) {
        SharedPreferences.Editor editor = AppController.getInstance().getSharedPreferences(
                "WED_APP", Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static Integer getIntPreferences(String key) {
        SharedPreferences prefs = AppController.getInstance().getSharedPreferences("WED_APP",
                Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }


}
