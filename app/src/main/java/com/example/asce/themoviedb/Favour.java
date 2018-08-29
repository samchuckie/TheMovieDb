package com.example.asce.themoviedb;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity(tableName ="Favourites")
public class Favour {
    @SerializedName("poster_path")
    private String poster_path;
    public String getPoster_path() {
        return poster_path;
    }
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
    @PrimaryKey
    @SerializedName("id")
    private int id ;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @SerializedName("original_title")
    private String original_title;
    public String getOriginal_title() {
        return original_title;
    }
    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }
    @SerializedName("release_date")
    private String release_date;
    public String getRelease_date() {
        return release_date;
    }
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
    @SerializedName("overview")
    private String overview;
    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
    @SerializedName("vote_average")
    private double vote_average;
    public double getVote_average() {
        return vote_average;
    }
    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public Favour(String original_title, String poster_path, String release_date, String overview, int id, double vote_average){
        this.poster_path = poster_path;
        this.id = id;
        this.original_title=original_title;
        this.release_date=release_date;
        this.overview=overview;
        this.vote_average=vote_average;

    }
}
