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

class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder>{

    private List<Results> favourite_list =null;
    private Context context;
    private DiscoverAdapter.ItemClickListener itemClickListener;
    private unStarredItemClickListener unstarredItemClickListener;

    FavouriteAdapter(Context context, DiscoverAdapter.ItemClickListener itemClickListener, unStarredItemClickListener unstarredItemClickListener)  {
        this.itemClickListener=itemClickListener;
        this.unstarredItemClickListener =unstarredItemClickListener;
        this.context = context;
    }
    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items, parent,false);
        return new FavouriteAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
        String poster_path= favourite_list.get(position).getPoster_path();
        Uri uri = Uri.parse( BASE_IMAGE_URL +"/w500/" +  poster_path);
        Picasso.get().load(uri).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(favourite_list==null){
            Log.e("sam", "null");
            return 0;
        }
        return favourite_list.size();
    }

    public void allitems(List<Results> results) {
        this.favourite_list = results;
        Log.e("sam" , "the size is " + results.size());
        notifyDataSetChanged();
    }

    public interface unStarredItemClickListener {
        void onunStarredItemClickListener(Results results);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final View.OnClickListener starred = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("sam", "deleted an item");
                // TODO USE DIALOG
                unstarredItemClickListener.onunStarredItemClickListener(favourite_list.get(getAdapterPosition()));
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
            itemClickListener.onItemClickListener(favourite_list.get(getAdapterPosition()));

        }
    }
}
