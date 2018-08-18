package com.example.asce.themoviedb;

import android.arch.lifecycle.ViewModel;

import com.example.asce.themoviedb.Clients.MovieInt;
import com.example.asce.themoviedb.Clients.MovieResult;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


class MovieModel extends ViewModel{
    static final String BASE_IMAGE_URL ="https://image.tmdb.org/t/p";
    private Observable <MovieResult> observe;
    MovieResult value;
    int anInt;
    MovieInt movieInt;

    public MovieResult getValue() {
        return value;
    }

    public void setValue(MovieResult value) {
        this.value = value;
    }

    private Observer<? super MovieResult> movieobserver = new Observer<MovieResult>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(MovieResult value) {
            setValue(value);

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    void l(){
        observe.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(movieobserver);
    }


    public Observable<MovieResult> setObserve() {
        return observe;

    }

    public String getApi_key() {
        return "ac620851205365b46eddb0c519ccae57";
    }

    public void getObserve(int anInt, MovieInt movieInt) {
        this.anInt = anInt;
        this.movieInt=movieInt;
        observe= movieInt.specific_movie(anInt,getApi_key());


    }
}
