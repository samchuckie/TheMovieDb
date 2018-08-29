package com.example.asce.themoviedb;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.asce.themoviedb.Clients.Discover;
import com.example.asce.themoviedb.Clients.MovieInt;
import com.example.asce.themoviedb.Clients.Moviedbclient;
import com.example.asce.themoviedb.Clients.Results;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModel {
    private MovieInt movieInt;
    private MutableLiveData<Response<Discover>> responses = new MutableLiveData<>();
    public MutableLiveData<Response<Discover>> getResponses() {
        return responses;
    }
    private Callback<Discover> callbacks=new Callback<Discover>() {
        @Override
        public void onResponse(@NonNull Call<Discover> call, @NonNull Response<Discover> response) {
            Log.e("sam" , "The raw url is " + response.raw());
            responses.postValue(response);
        }
        @Override
        public void onFailure(@NonNull Call<Discover> call, @NonNull Throwable t) {
        }
    };
    MainModel(){
        movieInt = Moviedbclient.getinstance().create(MovieInt.class);
    }
    public void getToprated(String api_key){
        Call<Discover> toprated= movieInt.toprated(api_key);
        toprated.enqueue(callbacks);
    }
    public void getPopular(String api_key){
        Call<Discover> popular= movieInt.popular(api_key);
        popular.enqueue(callbacks);
    }
    public LiveData<List<Results>> getFavourLiveData(Context application){

        return FavourDatabase.getmFavourDatabase(application).favourDao().getFavourites();

    }
}
