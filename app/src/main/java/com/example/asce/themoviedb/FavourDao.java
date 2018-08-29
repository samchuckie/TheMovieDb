package com.example.asce.themoviedb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.example.asce.themoviedb.Clients.Results;

import java.util.List;

@Dao
public interface FavourDao {
    @Query("SELECT * from results")
    LiveData<List<Results>> getFavourites();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertResult(@NonNull Results favourites);
}
