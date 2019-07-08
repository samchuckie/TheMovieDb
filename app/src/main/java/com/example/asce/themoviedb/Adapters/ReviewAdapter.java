package com.example.asce.themoviedb.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.asce.themoviedb.Clients.Reviews;
import com.example.asce.themoviedb.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    private List<Reviews> reviews = null;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewitems , parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.movie_reviews.setText(reviews.get(position).getContent());
        holder.author.setText(reviews.get(position).getAuthor());
    }
    @Override
    public int getItemCount() {
        return (reviews!=null)?reviews.size():0;
    }

    public void setreviews(List<Reviews> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView author,movie_reviews,hide_data;
        ViewHolder(View itemView) {
            super(itemView);
            author= itemView.findViewById(R.id.author);


            //TODO IMPLIMENT THE REVIEW DIALOG HERE


            itemView.setOnClickListener(v -> {


            });
        }
    }
}
