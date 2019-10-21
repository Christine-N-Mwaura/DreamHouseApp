package com.christine.dreamhouseapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.christine.dreamhouseapp.Constants.MOVIEDB_BASE_URL;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = MOVIEDB_BASE_URL;

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
