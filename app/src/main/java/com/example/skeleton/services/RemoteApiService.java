package com.example.skeleton.services;


import android.app.Application;

import androidx.annotation.NonNull;

import com.example.skeleton.BuildConfig;
import com.example.skeleton.utils.Tools;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
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
                .addInterceptor(new StatusCodeInterceptor())
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

    }

    public static class StatusCodeInterceptor implements okhttp3.Interceptor {
        @NonNull
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            //Tools.processing.postValue(true);

            Request request = chain.request();
            try {
                request = request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            okhttp3.Response response = chain.proceed(request);

            if (!response.isSuccessful())
                Tools.log("error123 " + request);
//
            //Tools.processing.postValue(false);


            ResponseBody body = response.body();
            String bodyString = body.string();
            Tools.log("Response from API" + bodyString);

            MediaType contentType = body.contentType();

            try {
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonData = jsonParser.parse(bodyString);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return response.newBuilder().body(ResponseBody.create(contentType, bodyString)).build();
        }
    }

}

