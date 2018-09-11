package com.example.asce.themoviedb;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.asce.themoviedb.Clients.Reviews;
import java.util.List;

class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    private List<Reviews> reviews = null;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewitems , parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.movie_reviews.setText(reviews.get(position).getContent());
        holder.author.setText(reviews.get(position).getAuthor());

    }

    @Override
    public int getItemCount() {
        if (reviews!=null)
        {
            return reviews.size();
        }
        return 0;
    }

    public void setreviews(List<Reviews> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView author,movie_reviews;
        ViewHolder(View itemView) {
            super(itemView);
            author= itemView.findViewById(R.id.author);
            movie_reviews = itemView.findViewById(R.id.movie_content);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            movie_reviews.setVisibility(View.VISIBLE);
            author.setVisibility(View.VISIBLE);
        }
    }
}
