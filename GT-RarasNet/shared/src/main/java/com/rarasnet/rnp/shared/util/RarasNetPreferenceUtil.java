package com.rarasnet.rnp.shared.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.rarasnet.rnp.shared.application.RarasNet;


/**
 * Created by Farina on 17/8/2015.
 *
 * Classe de acesso ao SharedPreferences da aplicação
 */
public class RarasNetPreferenceUtil {

    public static final String IS_LOGGED = "IS_LOGGED";
    public static final String LOGIN = "LOGIN";
    public static final String PASSW = "PASSW";

    private static Gson gson = new Gson();

    public static void savePreference(String key, String value) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(RarasNet.getAppContext());
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void savePreference(String key, boolean value) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(RarasNet.getAppContext());
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void savePreference(String key, Object value) {
        String json = gson.toJson(value);
        savePreference(key, json);
    }

    public static String getPreference(String key, String defaultValue) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(RarasNet.getAppContext());
        String preference = mPrefs.getString(key, defaultValue);
        return preference;
    }

    public static boolean getPreference(String key, boolean defaultValue) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(RarasNet.getAppContext());
        boolean preference = mPrefs.getBoolean(key, defaultValue);
        return preference;
    }

    public static <T> T getPreference(String key, Class<T> clazz) {
        if (!getPreference(key, "").isEmpty()) {
            return gson.fromJson(getPreference(key, ""), clazz);
        }
        return null;
    }
}
