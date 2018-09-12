package com.example.asce.themoviedb;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asce.themoviedb.Clients.Videos;

import java.util.List;

class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private List<Videos> videos = null;
    private trailerInterface tinterface;
    TrailerAdapter(trailerInterface tinterface){
    this.tinterface =tinterface;
    }

    @NonNull
    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_items , parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.ViewHolder holder, int position) {
    holder.trailers.setText(videos.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (videos!=null)
        {
            return  videos.size();
        }
        return 0;
    }

    public void setVideos(List<Videos> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }
    public interface trailerInterface{
        void ontrailerClickListener(String key);
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView trailers;
        ViewHolder(View itemView) {
            super(itemView);
            trailers =itemView.findViewById(R.id.Trailer_name);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            tinterface.ontrailerClickListener(videos.get(getAdapterPosition()).getKey());
        }
    }
}
