package com.example.asce.themoviedb.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.example.asce.themoviedb.Clients.Results;
import com.example.asce.themoviedb.Database.FavourDao;
import com.example.asce.themoviedb.Database.FavourDatabase;
import com.example.asce.themoviedb.R;

public class Delete_frag extends DialogFragment {
    Results results;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.Unstar_msg)
                .setNegativeButton("No",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onCancel(dialog);
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new deleteResult(getContext()).execute(results);
                    }
                })
                .setTitle(R.string.Unstar)
        ;
        return builder.show();
    }
    public void getResults(Results results) {
        this.results = results;
    }
    @SuppressLint("StaticFieldLeak")
    private static class deleteResult extends AsyncTask<Results,Void,Void> {
        FavourDao favouritesDao;
        deleteResult(Context context) {
            favouritesDao = FavourDatabase.getmFavourDatabase(context).favourDao();
        }
        @Override
        protected Void doInBackground(Results... results) {
            Log.e("sam" , "background process");
            favouritesDao.deletefavourite(results[0]);
            return null;
        }
    }
}