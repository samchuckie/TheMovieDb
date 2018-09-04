package com.example.asce.themoviedb.Clients;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReviewResult{
    @SerializedName("id")
    private
    int id;
    public int getId() {
        return id;
    }
    @SerializedName("results")
    private List<Reviews> reviews;
    public List<Reviews> getReviews() {
        return reviews;
    }
}
