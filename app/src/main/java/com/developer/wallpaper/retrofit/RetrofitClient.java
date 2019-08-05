package com.developer.wallpaper.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static Retrofit retrofit=null;

    public static Retrofit getClient(String URL){

        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .writeTimeout(1000, TimeUnit.MILLISECONDS)
                .readTimeout(1000,TimeUnit.MILLISECONDS)
                .connectTimeout(5000,TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();

        Gson gson=new GsonBuilder().setLenient().create();

        if (retrofit==null){

             retrofit=new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();


        }

        return retrofit;

    }

}
