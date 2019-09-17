package com.example.pubgtournament.Api;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Darshan Soni on 13-Jun-19.
 */
public class RetrofitClient {
    private static final String BASE_URL="https://nacxo.com/PubgTournament/";

    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    public RetrofitClient() {
        retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("retrofit", String.valueOf(retrofit));
    }
    public static synchronized RetrofitClient getInstance(){
        if (mInstance==null){
            mInstance=new RetrofitClient();
        }
        return mInstance;
    }
    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
