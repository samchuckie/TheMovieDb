package com.example.asce.themoviedb;

import android.arch.lifecycle.ViewModel;


class MovieModel extends ViewModel{
    private String api_key = "ac620851205365b46eddb0c519ccae57";
    public static final String BASE_IMAGE_URL ="https://image.tmdb.org/t/p";


    public String getApi_key() {
        return api_key;
    }
}
