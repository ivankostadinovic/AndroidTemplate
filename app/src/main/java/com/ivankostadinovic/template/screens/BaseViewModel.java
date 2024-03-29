package com.ivankostadinovic.template.screens;

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

import com.ivankostadinovic.template.R;
import com.ivankostadinovic.template.repositories.Repository;
import com.ivankostadinovic.template.utils.SingleLiveEvent;
import com.ivankostadinovic.template.utils.Tools;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class BaseViewModel extends AndroidViewModel {
    public CompositeDisposable disposable = new CompositeDisposable();
    public SingleLiveEvent<String> messageEvent = new SingleLiveEvent<>();
    public ObservableBoolean processing = new ObservableBoolean();
    public SingleLiveEvent<String> infoDialogEvent = new SingleLiveEvent<>();
    private Repository repository;


    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    protected Repository getRepository() {
        if (repository == null) {
            repository = Repository.getInstance(getApplication());
        }
        return repository;
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
                    infoDialogEvent.setValue(getString(R.string.no_internet));
                    return false;
                }
            } else {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    return true;
                } else {
                    infoDialogEvent.setValue(getString(R.string.no_internet));
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

    public String getString(int stringId) {
        return getApplication().getString(stringId);
    }
}
