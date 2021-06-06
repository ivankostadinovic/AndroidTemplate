package com.ivankostadinovic.template.services;


import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.ivankostadinovic.template.BuildConfig;
import com.ivankostadinovic.template.repositories.SharedPrefs;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteApiService {

    private static RemoteApiInterface sInstance;
    private static Application sApplication;

    public static RemoteApiInterface getRemoteApiService(Application application) {
        sApplication = application;

        if (sInstance == null) {
            sInstance = getRemoteHttpApiClient().create(RemoteApiInterface.class);
        }

        return sInstance;
    }

    private static Retrofit getRemoteHttpApiClient() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        BooleanTypeAdapter booleanTypeAdapter = new BooleanTypeAdapter();
        try {
            gsonBuilder.registerTypeAdapter(Boolean.class, booleanTypeAdapter);
            gsonBuilder.registerTypeAdapter(boolean.class, booleanTypeAdapter);
            gsonBuilder.addSerializationExclusionStrategy(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    if (f.getAnnotation(Expose.class) == null) {
                        return false;
                    }

                    return !f.getAnnotation(Expose.class).serialize();
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(getOKHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();
    }


    private static OkHttpClient getOKHttpClient() {
        return
            new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .addInterceptor(new StatusCodeInterceptor())
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

    }

    public static class StatusCodeInterceptor implements okhttp3.Interceptor {


        @NonNull
        @Override
        public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {

            Request request = chain.request();
            Request.Builder requestBuilder = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("app-os", "android");

            String apiToken = SharedPrefs.getInstance(sApplication).getApiToken();
            if (!TextUtils.isEmpty(apiToken)) {
                requestBuilder.addHeader("Authorization", apiToken);
            }

            return chain.proceed(request);
        }
    }
}