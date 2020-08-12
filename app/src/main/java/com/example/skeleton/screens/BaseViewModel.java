package com.example.skeleton.screens;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.AndroidViewModel;

import com.example.skeleton.repositories.Repository;
import com.example.skeleton.utils.SingleLiveEvent;
import com.example.skeleton.utils.Tools;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class BaseViewModel extends AndroidViewModel {
    public CompositeDisposable disposable = new CompositeDisposable();
    public SingleLiveEvent<String> messageEvent = new SingleLiveEvent<>();
    public Repository repository;
    public ObservableBoolean processing = new ObservableBoolean();


    public BaseViewModel(@NonNull Application application) {
        super(application);

        repository = Repository.getInstance(application);

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Network nw = connectivityManager.getActiveNetwork();
                NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
                if (actNw != null && (
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                                || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))) {
                    return true;
                } else {
                    messageEvent.setValue("No internet");
                    return false;
                }
            } else {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    return true;
                } else {
                    messageEvent.setValue("No internet");
                    return false;
                }
            }
        } else return false;
    }

    protected void handleError(Throwable err) {
        messageEvent.setValue(err.getMessage());
        if (isNetworkAvailable()) {
        }
        err.printStackTrace();
        Tools.log(err.getMessage());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
