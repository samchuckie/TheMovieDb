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
    @SerializedName("videos")
    private List<Videos> videos;
    public List<Videos> getVideos() {
        if (videos ==null)
        {
            videos = new ArrayList<>();
            Log.e("sam" , "videos is null");
        }
        return videos;
    }
}
