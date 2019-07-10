package com.example.asce.themoviedb.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import com.example.asce.themoviedb.Clients.Reviews;

public class ReviewFrag extends DialogFragment {
    private Reviews reviews;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(reviews.getContent())
                .setNegativeButton("Ok", (dialog, which) -> onCancel(dialog))
                .setTitle(reviews.getAuthor());
        return builder.show();
    }

    public void setReview(Reviews reviews) {
        this.reviews = reviews;
    }
}
