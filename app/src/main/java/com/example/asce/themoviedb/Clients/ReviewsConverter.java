package com.example.asce.themoviedb.Clients;


import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ReviewsConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<Reviews> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Reviews>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Reviews> someObjects) {
        return gson.toJson(someObjects);
    }
}
