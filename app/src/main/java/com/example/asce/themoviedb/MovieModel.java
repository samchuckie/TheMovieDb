package com.example.asce.themoviedb;

import android.arch.lifecycle.ViewModel;

import com.example.asce.themoviedb.Clients.MovieInt;
import com.example.asce.themoviedb.Clients.MovieResult;
import com.example.asce.themoviedb.Clients.Moviedbclient;

import io.reactivex.Observable;



class MovieModel extends ViewModel{
    static final String BASE_IMAGE_URL ="https://image.tmdb.org/t/p";
    private Observable <MovieResult> observe;
    MovieResult value;

    public MovieResult getValue() {
        return value;
    }

    public void setValue(MovieResult value) {
        this.value = value;
    }



    public Observable<MovieResult> setObserve() {
        return observe;

    }

    public String getApi_key() {
        return "ac620851205365b46eddb0c519ccae57";
    }

    public void getObserve(int anInt) {
        MovieInt movieInt = Moviedbclient.getinstance().create(MovieInt.class);
        observe= movieInt.specific_movie(anInt,getApi_key());


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
