package com.example.asce.themoviedb.Clients;

import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeriesResults implements Parcelable {
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

    @SerializedName("original_name")
    private String original_name;
    public String getOriginal_name() {
        return original_name;
    }
    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    @SerializedName("first_air_date")
    private String first_air_date;
    public String getFirst_air_date() {
        return first_air_date;
    }
    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
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

    @TypeConverters(ReviewsConverter.class)
    private List<Reviews> reviews;
    public List<Reviews> getReviews() {
        return reviews;
    }
    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    @TypeConverters(TrailersConvertors.class)
    private List<Videos> videos;
    public List<Videos> getVideos() {
        return videos;
    }
    public void setVideos(List<Videos> videos) {
        this.videos = videos;
    }

    public SeriesResults(String original_name, String poster_path, String first_air_date, String overview, int id, double vote_average){
        this.poster_path = poster_path;
        this.id = id;
        this.original_name=original_name;
        this.first_air_date=first_air_date;
        this.overview=overview;
        this.vote_average=vote_average;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster_path);
        dest.writeInt(this.id);
        dest.writeString(this.original_name);
        dest.writeString(this.first_air_date);
        dest.writeString(this.overview);
        dest.writeDouble(this.vote_average);
        dest.writeTypedList(this.reviews);
        dest.writeTypedList(this.videos);
    }

    protected SeriesResults(Parcel in) {
        this.poster_path = in.readString();
        this.id = in.readInt();
        this.original_name = in.readString();
        this.first_air_date = in.readString();
        this.overview = in.readString();
        this.vote_average = in.readDouble();
        this.reviews = in.createTypedArrayList(Reviews.CREATOR);
        this.videos = in.createTypedArrayList(Videos.CREATOR);
    }
    public static final Parcelable.Creator<SeriesResults> CREATOR = new Parcelable.Creator<SeriesResults>() {
        @Override
        public SeriesResults createFromParcel(Parcel source) {
            return new SeriesResults(source);
        }

        @Override
        public SeriesResults[] newArray(int size) {
            return new SeriesResults[size];
        }
    };
}
