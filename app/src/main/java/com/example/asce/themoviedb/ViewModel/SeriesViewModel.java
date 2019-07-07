package com.example.asce.themoviedb.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.asce.themoviedb.Clients.Results;
import com.example.asce.themoviedb.Clients.SeriesResults;
import com.example.asce.themoviedb.Fragments.SeriesModel;

import java.util.List;

public class SeriesViewModel extends AndroidViewModel {
    SeriesModel seriesModel;
    public SeriesViewModel(@NonNull Application application) {
        super(application);
        seriesModel= new SeriesModel();
    }
    private MutableLiveData<List<Results>> responses = new MutableLiveData<>();
    public MutableLiveData<List<SeriesResults>> getResponseLiveData() {
    return  seriesModel.getResponses();
    }


    public void getToprated(String change) {
        seriesModel.getToprated();
    }
    public void getPopular(String change) {
        seriesModel.getPopular();
    }
}
