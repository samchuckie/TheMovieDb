package com.example.asce.themoviedb.Clients;

import com.google.gson.annotations.SerializedName;

public class Results {
    @SerializedName("poster_path")
    private String poster_path;
    public String getPoster_path() {
        return poster_path;
    }

    @SerializedName("id")
    private int id ;
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }
    public Results(String original_title,String poster_path,String release_date, String overview,int id,double vote_average){
        this.poster_path = poster_path;
        this.id = id;

    }
}

