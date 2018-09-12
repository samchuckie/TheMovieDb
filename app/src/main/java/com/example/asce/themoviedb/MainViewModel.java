package com.example.asce.themoviedb;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.asce.themoviedb.Clients.Results;
import com.example.asce.themoviedb.Clients.Videos;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private String apikey;
    private MainModel mainModel;
    public MutableLiveData<List<Results>> getResponseLiveData() {
        Log.e("sam","getting responces ");
        return mainModel.getResponses();
    }
    public MutableLiveData<List<Videos>> getVideos(){
        return mainModel.getVideo_responces();
    }
    public void getTrailers(int id){
        mainModel.videocall(id);
    }
    LiveData<List<Results>> favourite = null;
    public MainViewModel(@NonNull Application application) {
        super(application);
        apikey = BuildConfig.ApiKey;
        mainModel= new MainModel();
    }
    public void getPopular(){
        //checks to see if a responce was already gotten. if there was a responce then the adapter will use the same data
        //from the viewmodel and no networl call made
        if(getResponseLiveData().getValue()==null)
        {
            mainModel.getPopular(apikey);
        }
    }
    public void getToprated(){
        if(getResponseLiveData().getValue() == null){
            mainModel.getToprated(apikey);
        }
    }
    public LiveData<List<Results>> getFavourite(Context application){
        if(favourite==null){
            favourite =mainModel.getFavourLiveData(application);
            Log.e("sam", "making a database call everytime");
        }
        return favourite;    }
    public void getToprated(String change) {
        mainModel.getToprated(apikey);
    }
    public void getPopular(String change) {
        mainModel.getPopular(apikey);
    }
}
