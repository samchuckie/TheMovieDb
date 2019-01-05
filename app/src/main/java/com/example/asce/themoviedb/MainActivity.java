package com.example.asce.themoviedb;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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
import com.example.asce.themoviedb.Clients.MovieInt;
import com.example.asce.themoviedb.Clients.Moviedbclient;
import com.example.asce.themoviedb.Clients.Results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.asce.themoviedb.Constant.Default;
import static com.example.asce.themoviedb.Movie.MOVIE_ID;

public class MainActivity extends AppCompatActivity implements DiscoverAdapter.ItemClickListener, DiscoverAdapter.StarredItemClickListener, FavouriteAdapter.unStarredItemClickListener {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    DiscoverAdapter discoverAdapter;
    FavouriteAdapter favouriteAdapter;
    Context context;
    SharedPreferences sharedPreferences;
    String defaultpreference;
    String top_rated,favourite_pref,popular;
    ProgressBar progressBar;
    ConnectivityManager connectivityManager;
    MainViewModel mainViewModel;
    MutableLiveData<List<Results>> response;
    LiveData<List<Results>> favourLiveData;
    private Observer<List<Results>> networkObserver= new Observer<List<Results>>() {
        @Override
        public void onChanged(@Nullable List<Results> results) {
            assert results != null;
            updates(results);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView =  findViewById(R.id.discover_rv);
        context = getApplicationContext();
        connectivityManager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        top_rated =getResources().getString(R.string.top_rated);
        popular =getResources().getString(R.string.popular);
        favourite_pref =getResources().getString(R.string.Favourites);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        gridLayoutManager = new GridLayoutManager(context,2);
        sharedPreferences =getPreferences(MODE_PRIVATE);
        defaultpreference = sharedPreferences.getString(Default, getResources().getString(R.string.defaulter));
        discoverAdapter= new DiscoverAdapter(context,this,this );
        favouriteAdapter = new FavouriteAdapter(context,this,this);
        recyclerView.setLayoutManager(gridLayoutManager);
        response = mainViewModel.getResponseLiveData();
        if(defaultpreference.equals(favourite_pref)){
            updateFavourite();
        }
        Log.e("sam" , "has observers is " + response.hasActiveObservers());
        if (networkInfo != null&& networkInfo.isConnected()) {
            if(defaultpreference.equals(popular)){
                // TODO Use a broadcast receiver to check network status after some time
                mainViewModel.getPopular();
                Log.e("sam","popular");
            }
            else
                if(defaultpreference.equals(top_rated)) {
                    mainViewModel.getToprated();
                    Log.e("sam", "toprated");
                }
            response.observe(this,networkObserver);
        }
        else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this,getResources().getString(R.string.connecton) ,Toast.LENGTH_LONG).show();
        }
        // TODO SET REMEMBER SCROLL POSITION FOR RECYCLERVIEW
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu ,menu);
        // To set the default preferences and check them
        if (defaultpreference.equals(top_rated)){
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
        int id =item.getItemId();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!item.isChecked()) {
            progressBar.setVisibility(View.VISIBLE);
            switch (id) {
                case (R.id.popular):
                    editor.putString(Default, popular);
                    editor.apply();
                    item.setChecked(true);
                    mainViewModel.getPopular("change");

                    return true;
                case (R.id.top_rated):
                    editor.putString(Default, top_rated);
                    editor.apply();
                    item.setChecked(true);
                    mainViewModel.getToprated("change");
                    return true;
                case(R.id.favourite):
                    editor.putString(Default, favourite_pref);
                    editor.apply();
                    item.setChecked(true);
                    updateFavourite();
                    return true;
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
    public void updates(@NonNull List<Results> response){
        defaultpreference = sharedPreferences.getString(Default, getResources().getString(R.string.defaulter));
        Log.e("sam" , "update called");
        Log.e("sam" , "" + defaultpreference);
        if(!defaultpreference.equals(favourite_pref)){
            Log.e("sam" , "update called and defauletr");
            recyclerView.setAdapter(discoverAdapter);
            progressBar.setVisibility(View.GONE);
            discoverAdapter.allitems(response);
            // TODO FIX THE PICASSO LOADING ISSUE
        }
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
    @Override
    public void onunStarredItemClickListener(final Results results) {
            Delete_frag delete_frag= new Delete_frag();
            Log.e("sam", "the adapter is " + recyclerView.getAdapter().toString());
            delete_frag.getResults(results);
            delete_frag.show(getSupportFragmentManager(),"Delete");
            favouriteAdapter.notifyDataSetChanged();

    }
    @SuppressLint("StaticFieldLeak")
    public static class favouritesAsync extends AsyncTask<Results,Void,Void>{
        FavourDao favouritesDao;
        public favouritesAsync(Context context) {
             favouritesDao = FavourDatabase.getmFavourDatabase(context).favourDao();
        }
        @Override
        protected Void doInBackground(Results... results) {
            favouritesDao.insertResult(results[0]);
            return null;
        }
    }
}
