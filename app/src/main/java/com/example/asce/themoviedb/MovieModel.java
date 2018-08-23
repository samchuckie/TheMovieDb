package com.example.asce.themoviedb;

import android.arch.core.BuildConfig;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.asce.themoviedb.Clients.MovieInt;
import com.example.asce.themoviedb.Clients.MovieResult;
import com.example.asce.themoviedb.Clients.Moviedbclient;

import io.reactivex.Observable;


class MovieModel extends ViewModel{
    private Observable <MovieResult> observe;
    MovieResult value;

    public MovieResult getValue() {
        return value;
    }

    public void setValue(MovieResult value) {
        this.value = value;
    }

    public @NonNull Observable<MovieResult> setObserve() {
        return observe;

    }

    public void getObserve(int anInt,String api_key) {
        MovieInt movieInt = Moviedbclient.getinstance().create(MovieInt.class);
        observe= movieInt.specific_movie(anInt,api_key);
    }
//    private Observer<? super MovieResult> movieobserver = new Observer<MovieResult>() {
//        @Override
//        public void onSubscribe(Disposable d) {
//
//        }
//
//        @Override
//        public void onNext(MovieResult value) {
//            setValue(value);
//
//        }
//
//        @Override
//        public void onError(Throwable e) {
//
//        }
//
//        @Override
//        public void onComplete() {
//
//        }
//    };
//
//    void l(){
//        observe.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribeWith(movieobserver);
//    }

}
