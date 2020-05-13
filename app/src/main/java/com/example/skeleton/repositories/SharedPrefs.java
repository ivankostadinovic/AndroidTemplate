package com.example.skeleton.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

public class SharedPrefs {

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
}
