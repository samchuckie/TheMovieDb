package com.example.asce.themoviedb.Fragments;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.asce.themoviedb.BuildConfig;
import com.example.asce.themoviedb.Clients.Discover;
import com.example.asce.themoviedb.Clients.MovieInt;
import com.example.asce.themoviedb.Clients.Moviedbclient;
import com.example.asce.themoviedb.Clients.Results;
import com.example.asce.themoviedb.Clients.ReviewResult;
import com.example.asce.themoviedb.Clients.SeriesDbclient;
import com.example.asce.themoviedb.Clients.SeriesDiscover;
import com.example.asce.themoviedb.Clients.SeriesInt;
import com.example.asce.themoviedb.Clients.SeriesResults;
import com.example.asce.themoviedb.Clients.VideoResults;
import com.example.asce.themoviedb.Clients.Videos;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.asce.themoviedb.Constant.api_key;

public class SeriesModel {
    SeriesInt seriesInt;
    private Callback<SeriesDiscover> callbacks = new Callback<SeriesDiscover>() {
        @Override
        public void onResponse(Call<SeriesDiscover> call, Response<SeriesDiscover> response) {
            Log.e("sam" , "The raw url is " + response.raw());
            SeriesDiscover seriesDiscover = response.body();
            assert seriesDiscover != null;
            List<SeriesResults> got = seriesDiscover.getResults();
//            for (final Results gotten:got)
//            {
//                int id = gotten.getId();
//                reviewcall(gotten,id);
//            }
            responses.postValue(got);
        }

        @Override
        public void onFailure(Call<SeriesDiscover> call, Throwable t) {

        }
    };
    private MutableLiveData<List<SeriesResults>> responses = new MutableLiveData<>();

    SeriesModel(){
        seriesInt = SeriesDbclient.getinstance().create(SeriesInt.class);
    }
    void getToprated(){
        Log.e("sam", "toprated should be called once");
        Call<SeriesDiscover> toprated= seriesInt.toprated(api_key);
        toprated.enqueue(callbacks);
    }
    void getPopular(){
        Log.e("sam", "popular should be called once");
        Call<SeriesDiscover> popular= seriesInt.popular(api_key);
        popular.enqueue(callbacks);
    }

}