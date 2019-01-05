package com.example.asce.themoviedb.Clients;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SeriesInt {
    @GET("tv/top_rated")
    Call<SeriesDiscover> toprated(@Query("api_key") String apikey);
    @GET("tv/popular")
    Call<SeriesDiscover> popular( @Query("api_key") String apikey);
}
