package com.hariobudiharjo.cataloguemovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        MovieItems movieItems = getIntent().getParcelableExtra("MOVIE");

        String image = movieItems.getGambar();
        String judul = movieItems.getJudul();
        String deskripsi = movieItems.getDeskripsi();
        String release = movieItems.getRelease();


        tvJudul.setText(judul);
        tvDeskripsi.setText(deskripsi);
        tvrelease.setText(" ( " + release + " )");
        Glide.with(this)
                .load(image)
                .into(ivPoster);

    }
}
