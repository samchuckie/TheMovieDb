package com.example.asce.themoviedb.Clients;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "Results")
public class Results implements Parcelable {
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

    public Results(String original_title, String poster_path, String release_date, String overview, int id, double vote_average){
        this.poster_path = poster_path;
        this.id = id;
        this.original_title=original_title;
        this.release_date=release_date;
        this.overview=overview;
        this.vote_average=vote_average;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.original_title);
        dest.writeString(this.poster_path);
        dest.writeString(this.release_date);
        dest.writeString(this.overview);
        dest.writeInt(this.id);
        dest.writeDouble(this.vote_average);
    }

    protected Results(Parcel in) {
        this.original_title = in.readString();
        this.poster_path = in.readString();
        this.release_date = in.readString();
        this.overview = in.readString();
        this.id = in.readInt();
        this.vote_average=in.readDouble();

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

