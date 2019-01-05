package com.example.asce.themoviedb;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.asce.themoviedb.Fragments.FavouriteFragment;
import com.example.asce.themoviedb.Fragments.MoviesFragment;

import static com.example.asce.themoviedb.Constant.Default;

public class Homepage extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    String defaultpreference;
    String top_rated,favourite_pref,popular;
    ConnectivityManager connectivityManager;
    Context context;
    FragmentManager fragmentManager;
    MainViewModel mainViewModel;
    MoviesFragment moviesFragment;
    FavouriteFragment favouriteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        sharedPreferences =getPreferences(MODE_PRIVATE);
        context= getApplicationContext();
        connectivityManager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        top_rated =getResources().getString(R.string.top_rated);
        popular =getResources().getString(R.string.popular);
        favourite_pref =getResources().getString(R.string.Favourites);

        defaultpreference = sharedPreferences.getString(Default, getResources().getString(R.string.defaulter));
        fragmentManager = getSupportFragmentManager();
        NavigationView navigationView =findViewById(R.id.nav_drawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(!item.isChecked())
                {

                }
                return true;
            }
        });
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        if(moviesFragment==null&&savedInstanceState==null) {
            moviesFragment = new MoviesFragment();
            fragmentManager.beginTransaction().add(R.id.movie_frame, moviesFragment).commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu ,menu);
        // To set the default preferences and check them
//        if (defaultpreference.equals(top_rated)){
//            menu.findItem(R.id.top_rated).setChecked(true);
//        }
//        else if (defaultpreference.equals(popular)){
//            menu.findItem(R.id.popular).setChecked(true);
//        }
//        if (defaultpreference.equals(favourite_pref)){
//            menu.findItem(R.id.favourite).setChecked(true);
//        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!item.isChecked()) {
            //TODO USE SHARED PREFERENCE MENU
            switch (id) {
                case (R.id.popular):
//                    editor.putString(Default, popular);
//                    editor.apply();
//                    item.setChecked(true);
                    mainViewModel.getPopular("change");
                    return true;
                case (R.id.top_rated):
//                    editor.putString(Default, top_rated);
//                    editor.apply();
//                    item.setChecked(true);
                    mainViewModel.getToprated("change");
                    return true;
                case(R.id.favourite):
//                    editor.putString(Default, favourite_pref);
//                    editor.apply();
//                    item.setChecked(true);
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
}
