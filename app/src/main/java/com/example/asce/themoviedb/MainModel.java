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
import com.example.asce.themoviedb.Clients.TrailerInt;
import com.example.asce.themoviedb.Clients.VideoResults;
import com.example.asce.themoviedb.Clients.Videos;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.asce.themoviedb.Constant.api_key;

class MainModel {
    private MovieInt movieInt;
    MainModel(){
        movieInt = Moviedbclient.getinstance().create(MovieInt.class);
    }
    private MutableLiveData<List<Videos>> video_responces = new MutableLiveData<>();
    MutableLiveData<List<Videos>> getVideo_responces() {
        return video_responces;
    }
    private MutableLiveData<List<Results>> responses = new MutableLiveData<>();
    MutableLiveData<List<Results>> getResponses() {
        return responses;
    }
    private void reviewcall(final Results results, int id){
        Call<ReviewResult> reviewResultCall = movieInt.getreview(id ,BuildConfig.ApiKey);
        reviewResultCall.enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResult> call, @NonNull Response<ReviewResult> response) {
                ReviewResult reviewResult = response.body();
                assert reviewResult != null;
                //TO SETREVIEWS
                results.setReviews(reviewResult.getReviews());

                Log.e("sam" , "Calling for reviews -" + results.getOriginal_title());
                //TODO DO FLATTENING TO SEE IF WE CAN DO BOTH CALLS IN ONE.AS VIC SUGGESTED
            }
            @Override
            public void onFailure(@NonNull Call<ReviewResult> call, @NonNull Throwable t) {
            }
        });
    }
    void videocall(int id){
        Call <VideoResults> videoResultsCall =movieInt.getvideos(id,BuildConfig.ApiKey);
        videoResultsCall.enqueue(new Callback<VideoResults>() {
            @Override
            public void onResponse(@NonNull Call<VideoResults> call, @NonNull Response<VideoResults> response) {
                VideoResults videoResults=response.body();
                assert videoResults!=null;
                Log.e("sam" , "raw url is " + response.raw());
                Log.e("sam" , "The id is " + videoResults.getId());
                List<Videos> videos = videoResults.getVideos();
                assert videos!=null;
                video_responces.postValue(videos);
            }
            @Override
            public void onFailure(@NonNull Call<VideoResults> call, @NonNull Throwable t) {
            }
        });
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
                reviewcall(gotten,id);
            }
            responses.postValue(got);
        }
        @Override
        public void onFailure(@NonNull Call<Discover> call, @NonNull Throwable t) {
        }
    };
    void getToprated(){
        Log.e("sam", "toprated should be called once");
        Call<Discover> toprated= movieInt.toprated(api_key);
        toprated.enqueue(callbacks);
    }
    void getPopular(){
        Log.e("sam", "popular should be called once");
        Call<Discover> popular= movieInt.popular(api_key);
        popular.enqueue(callbacks);
    }
    LiveData<List<Results>> getFavourLiveData(Context application){
        return FavourDatabase.getmFavourDatabase(application).favourDao().getFavourites();
    }
}
