package com.example.asce.themoviedb.Clients;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class Discover implements Parcelable {
    private int page;
    private int total_results;
    private int total_pages;
    private List<Results> results ;

    public List<Results> getResults() {
        return results;
    }

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.total_results);
        dest.writeInt(this.total_pages);
        dest.writeList(this.results);
    }



    private Discover(Parcel in) {
        this.page = in.readInt();
        this.total_results = in.readInt();
        this.total_pages = in.readInt();
        this.results = new ArrayList<>();
        in.readList(this.results, Results.class.getClassLoader());
    }

    public static final Creator<Discover> CREATOR = new Creator<Discover>() {
        @Override
        public Discover createFromParcel(Parcel source) {
            return new Discover(source);
        }

        @Override
        public Discover[] newArray(int size) {
            return new Discover[size];
        }
    };
}
