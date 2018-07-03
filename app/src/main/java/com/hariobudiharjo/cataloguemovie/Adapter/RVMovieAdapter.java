package com.hariobudiharjo.cataloguemovie.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hariobudiharjo.cataloguemovie.Model.MovieItems;
import com.hariobudiharjo.cataloguemovie.R;

import java.util.ArrayList;

public class RVMovieAdapter extends RecyclerView.Adapter<RVMovieAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MovieItems> listMovie= new ArrayList<>();

    public RVMovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<MovieItems> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<MovieItems> listMovie) {
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public RVMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_movie_items, parent, false);
        return new RVMovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVMovieAdapter.ViewHolder holder, int position) {
        MovieItems movieItems = getListMovie().get(position);
        holder.tvJudul.setText(movieItems.getJudul());
        holder.tvDeskripsi.setText(movieItems.getDeskripsi());
        holder.tvRelease.setText(movieItems.getRelease());
        Glide.with(context)
                .load(movieItems.getGambar())
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvJudul;
        TextView tvDeskripsi;
        TextView tvRelease;

        ViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imageMovieRV);
            tvJudul = itemView.findViewById(R.id.textJudulRV);
            tvDeskripsi = itemView.findViewById(R.id.textDeskripsiRV);
            tvRelease = itemView.findViewById(R.id.textReleaseRV);
        }
    }
}
