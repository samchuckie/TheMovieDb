package com.example.asce.themoviedb;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.asce.themoviedb.Clients.Results;
import com.example.asce.themoviedb.Clients.Reviews;
import com.example.asce.themoviedb.Clients.Videos;
import com.example.asce.themoviedb.ViewModel.MainViewModel;
import com.example.asce.themoviedb.Adapters.ReviewAdapter;
import com.example.asce.themoviedb.Adapters.TrailerAdapter;
import com.squareup.picasso.Picasso;
import java.util.List;
import static com.example.asce.themoviedb.Constant.BASE_IMAGE_URL;
import static com.example.asce.themoviedb.Constant.YOUTUBE_URI;
import static com.example.asce.themoviedb.Constant.size_small;

public class Movie extends AppCompatActivity implements TrailerAdapter.trailerInterface {
    public static final String MOVIE_ID="movie_id";
    TextView overview ,original_title,release_date,user_rating,review_tv,trailer_tv;
    ImageView imageView;
    String original_title_tx,overview_tx,release_date_tx,poster_path;
    Double user_rating_tx;
    RecyclerView recyclerView,trailer_rv;
    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager trailer_manger;
    ReviewAdapter reviewAdapter;
    TrailerAdapter trailerAdapter ;
    MainViewModel mainViewModel;
    // TODO REMOVE TRAILER TEXTVIEW IF EMPTY
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spec_movie);

        //  TODO CHANGE THE PICTURE TO OCCUPY STATUS BAR.Remove actionbar
        Intent intent =getIntent();
        review_tv = findViewById(R.id.review_tv);
        trailer_tv = findViewById(R.id.trailer_tv);
        imageView =findViewById(R.id.m_iv);
        overview =findViewById(R.id.ov_tv);
        original_title =findViewById(R.id.mn_tv);
        release_date =findViewById(R.id.rd_tv);
        user_rating =findViewById(R.id.ur_tv);
        recyclerView = findViewById(R.id.review_rv);
        trailer_rv =findViewById(R.id.youtube_rv);
        reviewAdapter = new ReviewAdapter();
        linearLayoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        trailer_manger= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(reviewAdapter);
        trailer_rv.setLayoutManager(trailer_manger);
        trailerAdapter = new TrailerAdapter(this);
        trailer_rv.setAdapter(trailerAdapter);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LiveData<List<Videos>> listLiveData = mainViewModel.getVideos();
        if(intent!=null && intent.hasExtra(MOVIE_ID)){
            Results results = intent.getParcelableExtra(MOVIE_ID);
            mainViewModel.getTrailers(results.getId());
            updateUi(results);
        }
        listLiveData.observe(this, videos -> {
            assert videos != null;
            if(!videos.isEmpty())
            trailer_tv.setVisibility(View.VISIBLE);
            trailerAdapter.setVideos(videos);
        });
    }
    private void updateUi(Results results) {
        original_title_tx =results.getOriginal_title();
        overview_tx=results.getOverview();
        user_rating_tx =results.getVote_average();
        release_date_tx=results.getRelease_date();
        poster_path=results.getPoster_path();
        Uri uri= Uri.parse(BASE_IMAGE_URL + size_small + poster_path);
        Picasso.get().load(uri).fit()
                .into(imageView);
        overview.setText(overview_tx);
        original_title.setText(original_title_tx);
        user_rating.setText(new StringBuilder().append(String.valueOf(user_rating_tx)).append("/10").toString());
        release_date.setText(release_date_tx);
        List<Reviews> reviewsmade =results.getReviews();
        if (!reviewsmade.isEmpty()) {
            Log.e("sam", "the reviews for non null " + reviewsmade.size());
            reviewAdapter.setreviews(reviewsmade);
        }
        else{review_tv.setVisibility(View.GONE);}
    }

    @Override
    public void ontrailerClickListener(String key) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(YOUTUBE_URI)
                .appendPath("watch")
                .appendQueryParameter("v" , key)
                .build();
        Uri uri = Uri.parse(builder.toString());
        Log.e("sam", "The uri is " + uri);
        Intent youtube_intent = new Intent(Intent.ACTION_VIEW,uri);
        Intent chooser = Intent.createChooser(youtube_intent , "Open using");
        if (youtube_intent .resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }
}
