package com.example.asce.themoviedb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.example.asce.themoviedb.Clients.Discover;
import com.example.asce.themoviedb.Clients.MovieInt;
import com.example.asce.themoviedb.Clients.Moviedbclient;
import com.example.asce.themoviedb.Clients.Results;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.asce.themoviedb.Movie.MOVIE_ID;

public class MainActivity extends AppCompatActivity implements DiscoverAdapter.ItemClickListener {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    DiscoverAdapter discoverAdapter;
    Context context;
    MovieModel movieModel;
    SharedPreferences sharedPreferences;
    MovieInt movieInt;
    String defaultpreference;
    String api_key;
    String vote ;
    String popular;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView =  findViewById(R.id.discover_rv);
        context = getApplicationContext();
        movieModel = new MovieModel();
        api_key = movieModel.getApi_key();
        vote =getResources().getString(R.string.vote);
        popular =getResources().getString(R.string.popular);
        gridLayoutManager = new GridLayoutManager(context,2);
        sharedPreferences =getPreferences(MODE_PRIVATE);
        defaultpreference = sharedPreferences.getString("Default", getResources().getString(R.string.defaulter));
        movieInt = Moviedbclient.getinstance().create(MovieInt.class);
        discoverAdapter= new DiscoverAdapter(context,this );
        recyclerView.setAdapter(discoverAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        Call<Discover> discoverCall = movieInt.getmovies( defaultpreference,api_key);
        discoverCall.enqueue(new Callback<Discover>() {
            @Override
            public void onResponse(@NonNull Call<Discover> call, @NonNull Response<Discover> response) {
                Discover discover = response.body();
                assert discover != null;
                progressBar.setVisibility(View.GONE);
                List<Results> results = discover.getResults();
                discoverAdapter.allitems(results);

            }

            @Override
            public void onFailure(@NonNull Call<Discover> call, @NonNull Throwable t) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu ,menu);
        // To set the default preferences and check them
        if (defaultpreference.equals(vote)){
            menu.findItem(R.id.vote).setChecked(true);
        }
        else if (defaultpreference.equals(popular)){
            menu.findItem(R.id.popular).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        progressBar.setVisibility(View.VISIBLE);
        int id =item.getItemId();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!item.isChecked()) {
            switch (id) {
                case (R.id.popular):
                    editor.putString("Default", popular);
                    editor.apply();
                    String pop = sharedPreferences.getString("Default", getResources().getString(R.string.defaulter));
                    item.setChecked(true);
                    Call<Discover> popular = movieInt.getmovies(pop, api_key);
                    popular.enqueue(new Callback<Discover>() {
                        @Override
                        public void onResponse(@NonNull Call<Discover> call, @NonNull Response<Discover> response) {
                            Discover discover_response = response.body();
                            assert discover_response != null;
                            progressBar.setVisibility(View.GONE);
                            List<Results> got = discover_response.getResults();
                            discoverAdapter.allitems(got);
                        }

                        @Override
                        public void onFailure(@NonNull Call<Discover> call, @NonNull Throwable t) {

                        }
                    });
                    return true;
                case (R.id.vote):
                    editor.putString("Default", vote);
                    editor.apply();
                    String voter = sharedPreferences.getString("Default", getResources().getString(R.string.defaulter));
                    Log.e("sam", "NEW default string is " + voter);
                    item.setChecked(true);
                    Call<Discover> vote = movieInt.getmovies(voter, api_key);
                    vote.enqueue(new Callback<Discover>() {
                        @Override
                        public void onResponse(@NonNull Call<Discover> call, @NonNull Response<Discover> response) {
                            Discover discover_response = response.body();
                            assert discover_response != null;
                            progressBar.setVisibility(View.GONE);
                            List<Results> got = discover_response.getResults();
                            discoverAdapter.allitems(got);
                        }

                        @Override
                        public void onFailure(@NonNull Call<Discover> call, @NonNull Throwable t) {

                        }
                    });


                    return true;
            }
        }
        return true;
    }

    @Override
    public void onItemClickListener(int itemId) {
        Intent intent = new Intent(this,Movie.class);
        intent.putExtra(MOVIE_ID,itemId);
        startActivity(intent);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionreturned = event.getAction();
        switch (actionreturned){
            case (MotionEvent.ACTION_DOWN):
                Log.e("sam","Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.e("sam","Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.e("sam","Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.e("sam","Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.e("sam","Movement occurred outside bounds " +
                        "of current screen element");
                return true;

            default:
                    return super.onTouchEvent(event);
        }

    }

}
