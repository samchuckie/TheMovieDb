package com.example.asce.themoviedb.Clients;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieInt {
// using path to make the calls
//    @GET("movie/{path}")
//    Call<Discover> getmovies( @Path("path") String path ,@Query("api_key") String apikey);
    @GET("movie/top_rated")
    Call<Discover> toprated( @Query("api_key") String apikey);
    @GET("movie/popular")
    Call<Discover> popular( @Query("api_key") String apikey);
    @GET("movie/{movie_id}/reviews")
    Call<ReviewResult> getreview(@Path("movie_id") int movie_id,@Query("api_key") String apikey);
    @GET("movie/{movie_id}/videos")
    Call<VideoResults> getvideos(@Path("movie_id") int movie_id, @Query("api_key") String apikey);
}
