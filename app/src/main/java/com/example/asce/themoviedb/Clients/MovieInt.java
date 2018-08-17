package com.example.asce.themoviedb.Clients;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieInt {
    @GET("discover/movie")
    Call<Discover> discoverpage( @Query("sort_by") String sort_by,@Query("api_key") String apikey);
    @GET("movie/{movie_id}")
    Call<MovieResult> specific_movie(@Path("movie_id" )int movie_id,@Query("api_key") String apikey);

}
