package com.hariobudiharjo.cataloguemovie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {
    private ArrayList<MovieItems> dataMovie = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDataMovie(ArrayList<MovieItems> items) {
        dataMovie = items;
        notifyDataSetChanged();
    }

    public void addDataMovie(final MovieItems item) {
        dataMovie.add(item);
        notifyDataSetChanged();
    }

    public void clearDataMovie() {
        dataMovie.clear();
    }

    @Override
    public int getCount() {
        if (dataMovie == null) return 0;
        return dataMovie.size();
    }

    @Override
    public MovieItems getItem(int position) {
        return dataMovie.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.movie_items, null);
            holder.tvJudul = (TextView) convertView.findViewById(R.id.textJudul);
            holder.tvDeskripsi = (TextView) convertView.findViewById(R.id.textDeskripsi);
            holder.tvRelease = (TextView) convertView.findViewById(R.id.textRelease);
            holder.ivGambar = (ImageView) convertView.findViewById(R.id.imageMovie);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvJudul.setText(dataMovie.get(position).getJudul());
        holder.tvDeskripsi.setText(dataMovie.get(position).getDeskripsi());
        holder.tvRelease.setText(dataMovie.get(position).getRelease());
        Glide.with(context)
                .load(dataMovie.get(position).getGambar())
                .into(holder.ivGambar);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra("IMAGE", dataMovie.get(position).getGambar());
                intent.putExtra("JUDUL", dataMovie.get(position).getJudul());
                intent.putExtra("DESKRIPSI", dataMovie.get(position).getDeskripsi());
                intent.putExtra("RELEASE", dataMovie.get(position).getRelease());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView tvJudul;
        TextView tvDeskripsi;
        TextView tvRelease;
        ImageView ivGambar;
    }
}
