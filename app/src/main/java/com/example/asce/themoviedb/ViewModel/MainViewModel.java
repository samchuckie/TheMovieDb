package com.example.asce.themoviedb.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.asce.themoviedb.Clients.Results;
import com.example.asce.themoviedb.Clients.Videos;
import com.example.asce.themoviedb.Model.MainModel;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private MainModel mainModel;
    public MainViewModel(@NonNull Application application) {
        super(application);
        mainModel= new MainModel();
    }
    private MutableLiveData<List<Results>> responses = new MutableLiveData<>();
    public MutableLiveData<List<Results>> getResponseLiveData() {
        Log.e("sam","getting responses ");
        responses=mainModel.getResponses();
        return responses;
    }

    public MutableLiveData<List<Videos>> getVideos(){
        return mainModel.getVideo_responces();
    }
    public void getTrailers(int id){
        mainModel.videocall(id);
    }
    private LiveData<List<Results>> favourite = new LiveData<List<Results>>() {
    };

    public void getPopular(){
        //checks to see if a responce was already gotten. if there was a response then the adapter will use the same data
        //from the viewmodel and no network call made
        if(getResponseLiveData().getValue()==null)
        {
            mainModel.getPopular();
        }
    }
    public void getToprated(){
        if(getResponseLiveData().getValue() == null){
            mainModel.getToprated();
        }
    }
    public LiveData<List<Results>> getFavourite(Context application){
            favourite =mainModel.getFavourLiveData(application);
            Log.e("sam", "making a database call everytime");

        return favourite;
    }
    public void getToprated(String change) {
        mainModel.getToprated();
    }
    public void getPopular(String change) {
        mainModel.getPopular();
    }
}
