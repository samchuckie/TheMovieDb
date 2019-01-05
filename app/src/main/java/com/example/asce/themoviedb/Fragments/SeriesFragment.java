package com.example.asce.themoviedb.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.asce.themoviedb.Adapters.SeriesAdapter;
import com.example.asce.themoviedb.Clients.Results;
import com.example.asce.themoviedb.DiscoverAdapter;
import com.example.asce.themoviedb.MainViewModel;
import com.example.asce.themoviedb.Movie;
import com.example.asce.themoviedb.R;

import java.util.List;

import static com.example.asce.themoviedb.Movie.MOVIE_ID;

public class SeriesFragment extends Fragment {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    SeriesAdapter seriesAdapter;
    private MainViewModel mainViewModel;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel= ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_main, container, false);
        recyclerView =  view.findViewById(R.id.discover_rv);
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        discoverAdapter= new DiscoverAdapter(getContext(),this,this );
        recyclerView.setAdapter(discoverAdapter);
        progressBar= view.findViewById(R.id.progress_bar);
        return view;
    }
    @Override
    public void onItemClickListener(Results results) {
        Intent intent = new Intent(getContext(),Movie.class);
        intent.putExtra(MOVIE_ID,results);
        startActivity(intent);

    }

}
