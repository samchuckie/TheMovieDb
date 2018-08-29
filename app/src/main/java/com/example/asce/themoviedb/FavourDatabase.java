package com.example.asce.themoviedb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.example.asce.themoviedb.Clients.Results;

@Database(entities = {Results.class},version = 2,exportSchema = false)
public abstract class FavourDatabase extends RoomDatabase{
    private static FavourDatabase mFavourDatabase;

    public static FavourDatabase getmFavourDatabase(Context context) {
        if(mFavourDatabase==null){
            synchronized (FavourDatabase.class){
                String DATABASE_NAME = "FAVOURITE_DATABASE";
                mFavourDatabase = Room.databaseBuilder(context ,FavourDatabase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return mFavourDatabase;
    }

    public abstract FavourDao favourDao();
}
