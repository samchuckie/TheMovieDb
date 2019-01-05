package com.example.asce.themoviedb;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.asce.themoviedb.Clients.Results;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.asce.themoviedb.Constant.BASE_IMAGE_URL;
import static com.example.asce.themoviedb.Constant.size_small;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder>{

    private List<Results> results =null;
    private Context context;
    private ItemClickListener itemClickListener;
    private StarredItemClickListener starredItemClickListener;

    public DiscoverAdapter(Context context, ItemClickListener itemClickListener, StarredItemClickListener starredItemClickListener)  {
        this.itemClickListener=itemClickListener;
        this.starredItemClickListener =starredItemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items, parent,false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String poster_path= results.get(position).getPoster_path();
        Uri uri = Uri.parse( BASE_IMAGE_URL +size_small +  poster_path);
        Picasso.get().load(uri).into(holder.imageView);
        //TODO SET ERROR
    }
    @Override
    public int getItemCount() {
        if(results==null){
            Log.e("sam", "null");
            return 0;
        }
        return results.size();
    }
    public void allitems(List<Results> results) {
        this.results = results;
        Log.e("sam" , "the size is " + results.size());
        notifyDataSetChanged();
    }
    public interface ItemClickListener {
        void onItemClickListener(Results results);
    }
    public interface StarredItemClickListener {
        void onStarredItemClickListener(Results results);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final View.OnClickListener starred = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("sam","clicked on starred");
                starredItemClickListener.onStarredItemClickListener(results.get(getAdapterPosition()));
            }
        };
        ImageView imageView;
        ImageView stars;
        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movieimage);
            stars = itemView.findViewById(R.id.starring);
            imageView.setOnClickListener(this);
            stars.setOnClickListener(starred);
        }


        @Override
        public void onClick(View view) {
            itemClickListener.onItemClickListener(results.get(getAdapterPosition()));
            // TODO MAKE ONCLICK SYNCHRONIZED
            // TODO CREATE A PUBLIC CLASS VIEWHOLDER

        }
    }
}
