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
import static com.example.asce.themoviedb.Constant.BASE_IMAGE_URL;

public class Movie extends AppCompatActivity {
    public static final String MOVIE_ID="movie_id";
    int movie_id;
    TextView overview ,original_title,release_date,user_rating;
    ImageView imageView;
    MovieModel movieModel;
    ProgressBar progressBar;
    String api_key,original_title_tx,overview_tx,release_date_tx,poster_path;
    Double user_rating_tx;


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
            Results results = intent.getParcelableExtra(MOVIE_ID);
            Log.e("sam" , "name is" + results.getOriginal_title());
            updateUi(results);
        }
        api_key = BuildConfig.ApiKey;;
        //movieModel.getObserve(movie_id,api_key);
//        movieModel.setObserve()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribeWith(new Observer<MovieResult>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                disposable = d;
//            }
//
//            @Override
//            public void onNext(MovieResult value) {
//                updateui(value);
//                Log.e("sam" ,"UI updated" );
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e("sam" ,"Completed" );
//            }
//        });
    }

    private void updateUi(Results results) {
        original_title_tx =results.getOriginal_title();
        overview_tx=results.getOverview();
        user_rating_tx =results.getVote_average();
        release_date_tx=results.getRelease_date();
        poster_path=results.getPoster_path();
        progressBar.setVisibility(View.GONE);
        Uri uri= Uri.parse(BASE_IMAGE_URL + "/w500/" + poster_path);
        Picasso.get().load(uri).fit()
                .into(imageView);
        overview.setText(overview_tx);
        original_title.setText(original_title_tx);
        user_rating.setText(new StringBuilder().append(String.valueOf(user_rating_tx)).append("/10").toString());
        release_date.setText(release_date_tx);
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
    }
}