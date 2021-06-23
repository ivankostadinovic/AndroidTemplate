package com.ivankostadinovic.template;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.ivankostadinovic.template.utils.Tools;

public class MyApplication extends Application implements LifecycleObserver {

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    //this is called when app comes to foreground
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        Tools.log("App onStart");
    }

    //this is called when app goes to background
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Tools.log("App onStop");
    }
}
