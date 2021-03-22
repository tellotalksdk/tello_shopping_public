package com.tilismtech.tellotalk_shopping_sdk.api;

import com.google.gson.GsonBuilder;

import java.util.logging.Level;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;


public class RetrofitClient {

    public static final String BASE_URL = "";
    public static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .build();
        }
        return retrofit;
    }

    public static Iapi getRetrofitClient() {
        return retrofit.create(Iapi.class);
    }

}
