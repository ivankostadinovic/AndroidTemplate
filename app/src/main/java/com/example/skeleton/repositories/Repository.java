package com.example.skeleton.repositories;

import android.app.Application;

import com.example.skeleton.services.RemoteApiInterface;
import com.example.skeleton.services.RemoteApiService;

public class Repository {

    private static Repository sInstance;

    private RemoteApiInterface mRemoteApiInterface;
    private Application application;
    public SharedPrefs sharedPrefs;

    private Repository(Application application) {
        this.application = application;

        mRemoteApiInterface = RemoteApiService.getRemoteApiService(application);
        sharedPrefs = SharedPrefs.getInstance(application.getApplicationContext());

    }

    public static Repository getInstance(Application application) {
        if (sInstance == null) {
            synchronized (Repository.class) {
                if (sInstance == null) {
                    sInstance = new Repository(application);
                }
            }
        }
        return sInstance;
    }
}
