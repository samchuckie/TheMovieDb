package com.example.asce.themoviedb.Clients;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Results implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster_path);
        dest.writeInt(this.id);
    }

    protected Results(Parcel in) {
        this.poster_path = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<Results> CREATOR = new Parcelable.Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel source) {
            return new Results(source);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };
}

