package com.example.asce.themoviedb.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.asce.themoviedb.Clients.SeriesResults;
import com.example.asce.themoviedb.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.asce.themoviedb.Constant.BASE_IMAGE_URL;
import static com.example.asce.themoviedb.Constant.size_small;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {
    Context context;
    private List<SeriesResults> seriesResults = null;

    public SeriesAdapter(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String poster_path= seriesResults.get(position).getPoster_path();
        Uri uri = Uri.parse( BASE_IMAGE_URL +size_small +  poster_path);
        Picasso.get().load(uri).into(holder.imageView);
        //TODO SET ERROR
    }

    @Override
    public int getItemCount() {
        if(seriesResults==null){
            Log.e("sam", "Series adapter is null");
            return 0;
        }
        return seriesResults.size();
    }

    public void setData(List<SeriesResults> seriesResults) {
        this.seriesResults = seriesResults;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView stars;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movieimage);
            stars = itemView.findViewById(R.id.starring);
        }
    }
}
