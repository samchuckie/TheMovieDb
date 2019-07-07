package com.example.asce.themoviedb.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.asce.themoviedb.Adapters.SeriesAdapter;
import com.example.asce.themoviedb.Clients.SeriesResults;
import com.example.asce.themoviedb.R;
import com.example.asce.themoviedb.ViewModel.SeriesViewModel;

import java.util.List;

public class SeriesFragment extends Fragment {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    SeriesAdapter seriesAdapter;
    private SeriesViewModel seriesViewModel;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seriesViewModel= ViewModelProviders.of(getActivity()).get(SeriesViewModel.class);
        seriesViewModel.getToprated("change");
        seriesViewModel.getResponseLiveData().observe(this, new Observer<List<SeriesResults>>() {
            @Override
            public void onChanged(@Nullable List<SeriesResults> seriesResults) {
                assert  seriesResults!=null;
                progressBar.setVisibility(View.GONE);
                Log.e("sam", "Observing for seriesresults");
                seriesAdapter.setData(seriesResults);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_main, container, false);
        recyclerView =  view.findViewById(R.id.discover_rv);
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        seriesAdapter= new SeriesAdapter(getContext() );
        recyclerView.setAdapter(seriesAdapter);
        progressBar= view.findViewById(R.id.progress_bar);
        return view;
    }

}
