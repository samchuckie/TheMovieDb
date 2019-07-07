package com.example.asce.themoviedb;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.asce.themoviedb.Fragments.FavouriteFragment;
import com.example.asce.themoviedb.Fragments.MoviesFragment;
import com.example.asce.themoviedb.Fragments.SeriesFragment;
import com.example.asce.themoviedb.ViewModel.MainViewModel;

import static com.example.asce.themoviedb.Constant.MOVIE_KEY;
import static com.example.asce.themoviedb.Constant.SETTING_KEY;

public class Homepage extends AppCompatActivity {

    String defaultpreference ,top_rated,favourite_pref,popular;
    ConnectivityManager connectivityManager;
    Context context;
    FragmentManager fragmentManager;
    MainViewModel mainViewModel;
    MoviesFragment moviesFragment;
    SeriesFragment seriesFragment;
    FavouriteFragment favouriteFragment;
    Boolean movies = true;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        context= getApplicationContext();
        connectivityManager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        top_rated =getResources().getString(R.string.top_rated);
        popular =getResources().getString(R.string.popular);
        favourite_pref =getResources().getString(R.string.Favourites);

        drawerLayout =findViewById(R.id.drawer);
        if(savedInstanceState!=null ){
            movies=savedInstanceState.getBoolean(MOVIE_KEY);
            defaultpreference = savedInstanceState.getString(SETTING_KEY);
        }
        else {
            defaultpreference = popular;
        }
        Log.e("sam" , "preference is "+ defaultpreference);
        fragmentManager = getSupportFragmentManager();
        final NavigationView navigationView =findViewById(R.id.nav_drawer);
        navigationView.setNavigationItemSelectedListener(item -> {
                String item_name=item.getTitle().toString();
                // TODO change backround of of item clicked .Maybe on nav drawer menu method.Series the side drawer to be color green
                // TODO NODE SERVER IMPLIMENT SUGGESTED LIST USING THIER CLICKS AND FAVOURITES
                // TODO UNCHECK ALL SETTINGS
                // TODO FIX STACKED UP FRAGMENTS

                if(!item.isChecked()){
                    switch (item_name){
                        case "Series":
                            movies=false;
                            Log.e("sam","Series is selected in nav drawer" + movies);
                            seriesFragment = new SeriesFragment();
                            fragmentManager.beginTransaction().replace(R.id.movie_frame, seriesFragment , "series").commit();
                            return true;
                        case "Movies" :
                            movies=true;
                            Log.e("sam","movies is selected in nav drawer" + movies);
                            Fragment fragment =fragmentManager.findFragmentByTag("series");
                            if(fragment!=null){
                                fragmentManager.beginTransaction().remove(fragment).commit();
                                Log.e("sam" , "series is found");
                                fragmentManager.popBackStack();
                            }
                            return  true;
                    }
                    drawerLayout.closeDrawers();
                }
                return true;
        });

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        if(movies){
            if(moviesFragment==null&&savedInstanceState==null) {
                moviesFragment = new MoviesFragment();
                fragmentManager.beginTransaction().add(R.id.movie_frame, moviesFragment, "movies").commit();
            }
        }
        else {
            if(seriesFragment==null){
                Log.e("sam" ,"seriesfragment created");
                seriesFragment = new SeriesFragment();
                fragmentManager.beginTransaction().add(R.id.movie_frame,seriesFragment,"series").commit();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu ,menu);
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
        if(!item.isChecked()) {
            Fragment fragment =fragmentManager.findFragmentByTag("movies");
            switch (id) {
                case (R.id.popular):
                    defaultpreference = popular;
                    item.setChecked(true);
                    mainViewModel.getPopular("change");
                    if(fragment!=null){
                        Log.e("sam" , "movies is not null for popular");
                    }
                    return true;
                case (R.id.top_rated):
                    item.setChecked(true);
                    defaultpreference =top_rated;
                    mainViewModel.getToprated("change");
                    if(fragment!=null){
                        Log.e("sam" , "movies is not null for top rated");
                    }
                    return true;
                case(R.id.favourite):
                    item.setChecked(true);
                    defaultpreference =favourite_pref;
                    if(favouriteFragment==null) {
                    Log.e("sam" , "favouritefragment is null");
                    favouriteFragment = new FavouriteFragment();
                    fragmentManager.beginTransaction().replace(R.id.movie_frame, favouriteFragment).commit();
                }
                    return true;
            }
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(MOVIE_KEY ,movies);
        outState.putString(SETTING_KEY , defaultpreference);
        Log.e("sam" , ""+ defaultpreference);
    }
}
