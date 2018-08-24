package com.hariobudiharjo.favoritmovie.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hariobudiharjo.favoritmovie.Model.MovieItems;
import com.hariobudiharjo.favoritmovie.R;


import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {
    @BindView(R.id.imageMovieDetail)
    ImageView ivPoster;
    @BindView(R.id.textJudulDetail)
    TextView tvJudul;
    @BindView(R.id.textDeskripsiDetail)
    TextView tvDeskripsi;
    @BindView(R.id.textReleaseDetail)
    TextView tvrelease;

    int total;
    String TAG = "DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        final MovieItems movieItems = getIntent().getParcelableExtra("MOVIE");

        String image = movieItems.getGambar();
        String judul = movieItems.getJudul();
        String deskripsi = movieItems.getDeskripsi();
        String release = movieItems.getRelease();

        tvJudul.setText(judul);
        tvDeskripsi.setText(deskripsi + "Lorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blablaLorem ipsum blabla" +
                "");
        tvrelease.setText(" ( " + release + " )");
        Glide.with(this)
                .load(image)
                .into(ivPoster);
    }
}
