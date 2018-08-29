package com.example.asce.themoviedb;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.asce.themoviedb.Clients.Discover;
import com.example.asce.themoviedb.Clients.Results;
import java.util.List;

import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    private String apikey;
    private MainModel mainModel;
    public MutableLiveData<Response<Discover>> getResponseLiveData() {
        Log.e("sam","getting responces ");
        return mainModel.getResponses();
    }
    LiveData<List<Results>> favourite = null;
    public MainViewModel(@NonNull Application application) {
        super(application);
        apikey = BuildConfig.ApiKey;
        mainModel= new MainModel();
    }
    public void getPopular(){
        mainModel.getPopular(apikey);
    }
    public void getToprated(){
        mainModel.getToprated(apikey);
    }
    public LiveData<List<Results>> getFavourite(Context application){
        if(favourite==null){
            favourite =mainModel.getFavourLiveData(application);
            Log.e("sam", "making a call everytime");
        }
        return favourite;    }
}
