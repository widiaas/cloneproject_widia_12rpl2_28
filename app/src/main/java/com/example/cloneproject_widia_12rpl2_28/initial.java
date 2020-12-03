package com.example.cloneproject_widia_12rpl2_28;
import  android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.BuildConfig;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class initial extends Application {
    private OkHttpClient okHttpClient = null;
    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(240, TimeUnit.SECONDS)
                .readTimeout(240, TimeUnit.SECONDS)
                .writeTimeout(240, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
                .build();
        AndroidNetworking.initialize(this,okHttpClient);

    }

    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }
}

