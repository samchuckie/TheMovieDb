package com.example.asce.themoviedb.Clients;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrailerInt {
    @GET("movie/{movie_id}/videos")
    Call<VideoResults> getvideos(@Path("movie_id") int movie_id, @Query("api_key") String apikey);
}
