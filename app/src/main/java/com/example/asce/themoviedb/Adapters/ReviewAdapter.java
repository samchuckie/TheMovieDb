package com.example.asce.themoviedb.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.asce.themoviedb.Clients.Reviews;
import com.example.asce.themoviedb.Fragments.ReviewFrag;
import com.example.asce.themoviedb.R;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    private List<Reviews> reviews = null;
    private ReviewItemClickListener reviewItemClickListener;
    public ReviewAdapter(ReviewItemClickListener reviewItemClickListener){
        this.reviewItemClickListener =reviewItemClickListener;

    }


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
    public interface ReviewItemClickListener {
        void onunStarredItemClickListener(Reviews reviews);
        // TODO CHANGE THE COLOR OF STAR AFTER IT HAS BEEN STARRED
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
        private final View.OnClickListener starred = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewItemClickListener.onunStarredItemClickListener(reviews.get(getAdapterPosition()));
            }
        };
        TextView author;
        ViewHolder(View itemView) {
            super(itemView);
            author= itemView.findViewById(R.id.author);
            itemView.setOnClickListener(starred);
        }
    }
}
