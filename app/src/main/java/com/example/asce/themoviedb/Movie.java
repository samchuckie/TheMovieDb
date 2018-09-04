package com.example.asce.themoviedb;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.asce.themoviedb.Clients.MovieResult;
import com.example.asce.themoviedb.Clients.Results;
import com.example.asce.themoviedb.Clients.Reviews;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.asce.themoviedb.Constant.BASE_IMAGE_URL;

public class Movie extends AppCompatActivity {
    public static final String MOVIE_ID="movie_id";
    TextView overview ,original_title,release_date,user_rating;
    ImageView imageView;
    ProgressBar progressBar;
    String api_key,original_title_tx,overview_tx,release_date_tx,poster_path;
    Double user_rating_tx;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ReviewAdapter reviewAdapter;

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
        recyclerView = findViewById(R.id.review_rv);
        reviewAdapter = new ReviewAdapter();
        linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(reviewAdapter);
        if(intent!=null && intent.hasExtra(MOVIE_ID)){
            Results results = intent.getParcelableExtra(MOVIE_ID);
            Log.e("sam" , "name is" + results.getOriginal_title());
            updateUi(results);
        }
//        api_key = BuildConfig.ApiKey;
    }
    private void updateUi(Results results) {
        original_title_tx =results.getOriginal_title();
        overview_tx=results.getOverview();
        user_rating_tx =results.getVote_average();
        release_date_tx=results.getRelease_date();
        poster_path=results.getPoster_path();
        // TODO MOVE PROGRESS TO THE END
        progressBar.setVisibility(View.GONE);
        Uri uri= Uri.parse(BASE_IMAGE_URL + "/w500/" + poster_path);
        Picasso.get().load(uri).fit()
                .into(imageView);
        overview.setText(overview_tx);
        original_title.setText(original_title_tx);
        user_rating.setText(new StringBuilder().append(String.valueOf(user_rating_tx)).append("/10").toString());
        release_date.setText(release_date_tx);
        List<Reviews> reviewsmade =results.getReviews();
        if (reviewsmade!=null) {
            Log.e("sam", "the reviews for non null " + reviewsmade.size());
            reviewAdapter.setreviews(reviewsmade);
        }
        else
        {
            Log.e("sam", "the reviews for  null " + reviewsmade.size());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}