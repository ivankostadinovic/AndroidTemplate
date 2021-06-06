package com.ivankostadinovic.template.repositories;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPrefs {

    public static final String API_TOKEN = "api_token";

    private static SharedPrefs sInstance;
    private SharedPreferences mSharedPrefs;


    private Gson gson;

    private SharedPrefs(Context context) {
        mSharedPrefs = context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        mSharedPrefs.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
        });
        gson = new Gson();
    }

    public static SharedPrefs getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPrefs(context);
        }
        return sInstance;
    }

    public void deleteTokens() {
        mSharedPrefs.edit().clear().apply();
    }

    public String getApiToken() {
        return mSharedPrefs.getString(API_TOKEN, null);
    }

    public void saveApiToken(String apiToken) {
        mSharedPrefs.edit().putString(API_TOKEN, apiToken).apply();
    }
}
