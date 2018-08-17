package com.example.asce.themoviedb.Clients;

import com.google.gson.annotations.SerializedName;

public class MovieResult {
    @SerializedName("original_title")
    private String original_title;
    public String getOriginal_title() {
        return original_title;
    }
    @SerializedName("overview")
    private String overview;
    public String getOverview() {
        return overview;
    }
    @SerializedName("poster_path")
    private String poster_path;
    public String getPoster_path() {
        return poster_path;
    }

    @SerializedName("release_date")
    private String release_date;
    public String getRelease_date() {
        return release_date;
    }

    @SerializedName("vote_average")
    private double vote_average;
    public double getVote_average() {
        return vote_average;
    }

    public MovieResult   (String original_title,String poster_path,String release_date, String overview,double vote_average){
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.overview = overview;
        this.vote_average = vote_average;

    }
}
