package com.example.asce.themoviedb;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.asce.themoviedb.Clients.MovieResult;
import com.example.asce.themoviedb.Clients.Results;
import com.squareup.picasso.Picasso;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.asce.themoviedb.Constant.BASE_IMAGE_URL;

public class Movie extends AppCompatActivity {
    public static final String MOVIE_ID="movie_id";
    int movie_id;
    TextView overview ,original_title,release_date,user_rating;
    ImageView imageView;
    MovieModel movieModel;
    Disposable disposable;
    ProgressBar progressBar;
    String api_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        progressBar =findViewById(R.id.progress_barm);
        Intent intent =getIntent();
        imageView =findViewById(R.id.m_iv);
        overview =findViewById(R.id.ov_tv);
        original_title =findViewById(R.id.mn_tv);
        release_date =findViewById(R.id.rd_tv);
        user_rating =findViewById(R.id.ur_tv);
        movieModel = new MovieModel();
        if(intent!=null && intent.hasExtra(MOVIE_ID)){
            Results f = intent.getParcelableExtra(MOVIE_ID);
            Log.e("sam" , "name is" + f.getPoster_path());
            movie_id=f.getId();
        }
        api_key = BuildConfig.ApiKey;;
        movieModel.getObserve(movie_id,api_key);
        movieModel.setObserve()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<MovieResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(MovieResult value) {
                updateui(value);
                Log.e("sam" ,"UI updated" );
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                Log.e("sam" ,"Completed" );
            }
        });
    }

    private void updateui(MovieResult movieResult) {
        progressBar.setVisibility(View.GONE);
        String poster_path = movieResult.getPoster_path();
        Uri uri= Uri.parse(BASE_IMAGE_URL + "/w500/" + poster_path);
        Picasso.get().load(uri).fit()
                .into(imageView);
        overview.setText(movieResult.getOverview());
        original_title.setText(movieResult.getOriginal_title());
        user_rating.setText(new StringBuilder().append(String.valueOf(movieResult.getVote_average())).append("/10").toString());
        release_date.setText(movieResult.getRelease_date());

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}