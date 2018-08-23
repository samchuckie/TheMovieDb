package com.example.asce.themoviedb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
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
    String api_key,toprated,popular;
    ProgressBar progressBar;
    ConnectivityManager connectivityManager;
    Call<Discover> discoverCall;
    Callback<Discover> callback=new Callback<Discover>() {
        @Override
        public void onResponse(@NonNull Call<Discover> call, @NonNull Response<Discover> response) {
            //When i log the raw response for both menu items it returns the correct url. i do not have vote_average in my code.
            //My endpoints work
            Log.e("sam" , "The raw url is " + response.raw());
            updates(response);
        }
        @Override
        public void onFailure(@NonNull Call<Discover> call, @NonNull Throwable t) {
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
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        movieModel = new MovieModel();
        api_key = BuildConfig.ApiKey;
        toprated =getResources().getString(R.string.vote);
        popular =getResources().getString(R.string.popular);
        gridLayoutManager = new GridLayoutManager(context,2);
        sharedPreferences =getPreferences(MODE_PRIVATE);
        defaultpreference = sharedPreferences.getString("Default", getResources().getString(R.string.defaulter));
        movieInt = Moviedbclient.getinstance().create(MovieInt.class);
        discoverAdapter= new DiscoverAdapter(context,this );
        recyclerView.setAdapter(discoverAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        if (networkInfo != null&& networkInfo.isConnected()) {
            if(defaultpreference.equals(popular)){
            discoverCall = movieInt.popular( api_key);}
            else if(defaultpreference.equals(toprated)){
                discoverCall = movieInt.toprated( api_key);
            }
            discoverCall.enqueue(callback);

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
                    //String pop = sharedPreferences.getString("Default", getResources().getString(R.string.defaulter));
                    item.setChecked(true);
                    Call<Discover> popular = movieInt.popular(api_key);
                    popular.enqueue(callback);
                    return true;
                case (R.id.top_rated):
                    editor.putString("Default", toprated);
                    editor.apply();
//                    String voter = sharedPreferences.getString("Default", getResources().getString(R.string.defaulter));
                    item.setChecked(true);
                    Call<Discover> vote = movieInt.toprated(api_key);
                    vote.enqueue(callback);
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
    public void updates(@NonNull Response<Discover> response){
        Discover discover_response = response.body();
        assert discover_response != null;
        progressBar.setVisibility(View.GONE);
        List<Results> got = discover_response.getResults();
        discoverAdapter.allitems(got);
    }
}
