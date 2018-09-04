package com.example.asce.themoviedb.Clients;

import android.os.Parcel;
import android.os.Parcelable;

 public class Reviews implements Parcelable {
    private String author;
    public String getAuthor() {
        return author;
    }
    private String content;
    public String getContent() {
        return content;
    }
    private String id;
    public String getId() {
        return id;
    }
    private String url;
    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.id);
        dest.writeString(this.url);
    }

    public Reviews() {
    }

    protected Reviews(Parcel in) {
        this.author = in.readString();
        this.content = in.readString();
        this.id = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Reviews> CREATOR = new Parcelable.Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel source) {
            return new Reviews(source);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };
}
