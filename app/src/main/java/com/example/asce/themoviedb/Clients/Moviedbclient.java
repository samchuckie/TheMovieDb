package com.example.asce.themoviedb.Clients;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Moviedbclient {
     static final String BASE_URL ="https://api.themoviedb.org/3/";
    static Retrofit retrofit;
    public static Retrofit getinstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().
                    baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;

    }
}
