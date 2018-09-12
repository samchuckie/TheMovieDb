package com.example.asce.themoviedb.Clients;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VideoResults {
    @SerializedName("id")
    private
    int id;
    public int getId() {
        return id;
    }
    @SerializedName("results")
    private List<Videos> results;
    public List<Videos> getVideos() {
        return results;
    }
}
