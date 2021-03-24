package com.twocoms.rojgarkendra.global.model;

import android.content.Context;
import android.content.SharedPreferences;

/**/

public class GlobalPreferenceManager {

    private static final String APP_SHARED_PREFERENCES_FILE = "RojGarKendraPref";

    public static void saveIntForKey(Context c, String keyMain, int dataMain) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(APP_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(keyMain, dataMain);
        editor.commit();
    }

    public static int getIntForKey(Context c, String keyMain, int defaultDataMain) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(APP_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(keyMain, defaultDataMain);
    }

    public static void saveBooleanForKey(Context c, String keyMain, boolean dataMain) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(APP_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(keyMain, dataMain);
        editor.commit();
    }

    public static void saveStringForKey(Context c, String keyMain, String dataMain) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(APP_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(keyMain, dataMain);
        editor.commit();
    }

    public static String getStringForKey(Context c, String keyMain, String defaultDataMain) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(APP_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyMain, defaultDataMain);
    }


    public static boolean getBooleanForKey(Context c, String keyMain, boolean defaultDataMain) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(APP_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(keyMain, defaultDataMain);
    }

    public static void removeDataforKey(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }


    public static void clearSharedPref(Context context){
        SharedPreferences preferences =context.getSharedPreferences(APP_SHARED_PREFERENCES_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }


}
