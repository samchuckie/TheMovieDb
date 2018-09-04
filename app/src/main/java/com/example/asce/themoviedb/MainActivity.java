package com.example.asce.themoviedb;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.asce.themoviedb.Clients.Discover;
import com.example.asce.themoviedb.Clients.MovieInt;
import com.example.asce.themoviedb.Clients.Moviedbclient;
import com.example.asce.themoviedb.Clients.Results;
import com.example.asce.themoviedb.Clients.ReviewResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.asce.themoviedb.Movie.MOVIE_ID;
public class MainActivity extends AppCompatActivity implements DiscoverAdapter.ItemClickListener, DiscoverAdapter.StarredItemClickListener {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    DiscoverAdapter discoverAdapter;
    FavouriteAdapter favouriteAdapter;
    Context context;
    SharedPreferences sharedPreferences;
    MovieInt movieInt;
    String defaultpreference;
    String api_key,toprated,popular,favourite_pref;
    ProgressBar progressBar;
    ConnectivityManager connectivityManager;
    MainViewModel mainViewModel;
    LiveData<Response<Discover>> response;
    LiveData<List<Results>> favourLiveData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView =  findViewById(R.id.discover_rv);
        context = getApplicationContext();
        connectivityManager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        api_key = BuildConfig.ApiKey;
        toprated =getResources().getString(R.string.top_rated);
        popular =getResources().getString(R.string.popular);
        favourite_pref =getResources().getString(R.string.Favourites);
        gridLayoutManager = new GridLayoutManager(context,2);
        sharedPreferences =getPreferences(MODE_PRIVATE);
        defaultpreference = sharedPreferences.getString("Default", getResources().getString(R.string.defaulter));
        movieInt = Moviedbclient.getinstance().create(MovieInt.class);
        discoverAdapter= new DiscoverAdapter(context,this,this );
        favouriteAdapter = new FavouriteAdapter(context,this,this);
        recyclerView.setLayoutManager(gridLayoutManager);
        response = mainViewModel.getResponseLiveData();
        response.observe(this, new Observer<Response<Discover>>() {
            @Override
            public void onChanged(@Nullable Response<Discover> discoverResponse) {
                assert discoverResponse != null;
                updates(discoverResponse);
            }
        });
        if(defaultpreference.equals(favourite_pref)){
            updateFavourite();
        }
        if (networkInfo != null&& networkInfo.isConnected()) {
            if(defaultpreference.equals(popular)){
                mainViewModel.getPopular();}
            else
                if(defaultpreference.equals(toprated)){
                mainViewModel.getToprated();
            }
        }
        else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this,"No internet connectivty" ,Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu ,menu);
        // To set the default preferences and check them
        if (defaultpreference.equals(toprated)){
            menu.findItem(R.id.top_rated).setChecked(true);
        }
        else if (defaultpreference.equals(popular)){
            menu.findItem(R.id.popular).setChecked(true);
        }
        if (defaultpreference.equals(favourite_pref)){
            menu.findItem(R.id.favourite).setChecked(true);
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
                    item.setChecked(true);
                    mainViewModel.getPopular();
                    return true;
                case (R.id.top_rated):
                    editor.putString("Default", toprated);
                    editor.apply();
                    item.setChecked(true);
                    mainViewModel.getToprated();
                    return true;
                case(R.id.favourite):
                    editor.putString("Default", favourite_pref);
                    editor.apply();
                    item.setChecked(true);
                    updateFavourite();

            }
        }
        return true;
    }
    @Override
    public void onItemClickListener(Results results) {
        Intent intent = new Intent(this,Movie.class);
        intent.putExtra(MOVIE_ID,results);
        startActivity(intent);
    }
    public void updates(@NonNull Response<Discover> response){
        Discover discover_response = response.body();
        recyclerView.setAdapter(discoverAdapter);
        assert discover_response != null;
        progressBar.setVisibility(View.GONE);
        List<Results> got = discover_response.getResults();
        int counter=0;
        for(final Results results:got){
            int id = results.getId();
            counter++;
            MovieInt movieInt= Moviedbclient.getinstance().create(MovieInt.class);
            Call<ReviewResult> rr = movieInt.getreview(id ,BuildConfig.ApiKey);

            rr.enqueue(new Callback<ReviewResult>() {
                @Override
                public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                    ReviewResult reviewResult = response.body();
                    assert reviewResult != null;
                    results.setReviews(reviewResult.getReviews());
                    Log.e("sam" , "Calling for reviews");
                }

                @Override
                public void onFailure(Call<ReviewResult> call, Throwable t) {

                }
            });
        }

        discoverAdapter.allitems(got);
    }
    public void updateFavourite()
    {
        recyclerView.setAdapter(favouriteAdapter);
        favourLiveData= mainViewModel.getFavourite(this);
        favourLiveData.observe(this, new Observer<List<Results>>() {
            @Override
            public void onChanged(@Nullable List<Results> favours) {
                assert favours != null;
                favouriteAdapter.allitems(favours);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    @Override
    public void onStarredItemClickListener(Results results) {
        new favouritesAsync(context).execute(results);
    }
    @SuppressLint("StaticFieldLeak")
    private static class favouritesAsync extends AsyncTask<Results,Void,Void>{
        FavourDao favouritesDao;
        favouritesAsync(Context context) {
             favouritesDao = FavourDatabase.getmFavourDatabase(context).favourDao();
        }
        @Override
        protected Void doInBackground(Results... results) {
            Log.e("sam" , "background process");
            favouritesDao.insertResult(results[0]);
            return null;
        }
    }
}
