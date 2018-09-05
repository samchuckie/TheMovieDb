package com.example.asce.themoviedb;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.asce.themoviedb.Clients.Discover;
import com.example.asce.themoviedb.Clients.MovieInt;
import com.example.asce.themoviedb.Clients.Moviedbclient;
import com.example.asce.themoviedb.Clients.Results;
import com.example.asce.themoviedb.Clients.ReviewResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModel {
    private MovieInt movieInt;
    private MutableLiveData<List<Results>> responses = new MutableLiveData<>();
    public MutableLiveData<List<Results>> getResponses() {
        return responses;
    }
    private Callback<Discover> callbacks=new Callback<Discover>() {
        @Override
        public void onResponse(@NonNull Call<Discover> call, @NonNull Response<Discover> response) {
            Log.e("sam" , "The raw url is " + response.raw());
            Discover discover_response = response.body();
            assert discover_response != null;
            List<Results> got = discover_response.getResults();
            for (final Results gotten:got)
            {
                int id = gotten.getId();
                Call<ReviewResult> rr = movieInt.getreview(id ,BuildConfig.ApiKey);
                rr.enqueue(new Callback<ReviewResult>() {
                @Override
                public void onResponse(@NonNull Call<ReviewResult> call, @NonNull Response<ReviewResult> response) {
                    ReviewResult reviewResult = response.body();
                    assert reviewResult != null;
                    gotten.setReviews(reviewResult.getReviews());
                    Log.e("sam" , "Calling for reviews" + gotten.getOriginal_title());
                }

                @Override
                public void onFailure(@NonNull Call<ReviewResult> call, @NonNull Throwable t) {

                }
            });
            }
            responses.postValue(got);
        }
        @Override
        public void onFailure(@NonNull Call<Discover> call, @NonNull Throwable t) {
        }
    };
    MainModel(){
        movieInt = Moviedbclient.getinstance().create(MovieInt.class);
    }
    public void getToprated(String api_key){
        Log.e("sam", "toprated should be called once");
        Call<Discover> toprated= movieInt.toprated(api_key);
        toprated.enqueue(callbacks);
    }
    public void getPopular(String api_key){
        Log.e("sam", "popular should be called once");
        Call<Discover> popular= movieInt.popular(api_key);
        popular.enqueue(callbacks);
    }
    public LiveData<List<Results>> getFavourLiveData(Context application){
        return FavourDatabase.getmFavourDatabase(application).favourDao().getFavourites();
    }
}
