package com.example.asce.themoviedb;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.asce.themoviedb.Clients.MovieInt;
import com.example.asce.themoviedb.Clients.MovieResult;
import com.example.asce.themoviedb.Clients.Moviedbclient;
import com.squareup.picasso.Picasso;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.asce.themoviedb.MovieModel.BASE_IMAGE_URL;

public class Movie extends AppCompatActivity {
    public static final String MOVIE_ID="movie_id";
    int movie_id;
    MovieInt movieInt;
    TextView overview ,original_title,release_date,user_rating;
    ImageView imageView;
    MovieModel movieModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Intent intent =getIntent();
        imageView =findViewById(R.id.m_iv);
        overview =findViewById(R.id.ov_tv);
        original_title =findViewById(R.id.mn_tv);
        release_date =findViewById(R.id.rd_tv);
        user_rating =findViewById(R.id.ur_tv);
        movieModel = new MovieModel();
        if(intent!=null && intent.hasExtra(MOVIE_ID)){
            movie_id= intent.getIntExtra(MOVIE_ID, 0);
        }
//
//      movieInt = Moviedbclient.getinstance().create(MovieInt.class);
//      movieModel.getObserve(movie_id,movieInt);
//      movieModel.l();
      movieModel.setObserve()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<MovieResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MovieResult value) {
                updateui(value);
                Log.e("sam" ,"hey" );
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e("sam" ,"i said HEY" );
            }
        });
    }

    private void updateui(MovieResult movieResult) {
        String poster_path = movieResult.getPoster_path();
        Uri uri= Uri.parse(BASE_IMAGE_URL + "/w500/" + poster_path);
        Picasso.get().load(uri).fit()
                .into(imageView);
        overview.setText(movieResult.getOverview());
        original_title.setText(movieResult.getOriginal_title());
        user_rating.setText(new StringBuilder().append(String.valueOf(movieResult.getVote_average())).append("/10").toString());
        release_date.setText(movieResult.getRelease_date());


    }
}
