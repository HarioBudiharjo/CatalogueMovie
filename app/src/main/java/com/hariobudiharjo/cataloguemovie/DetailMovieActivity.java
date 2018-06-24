package com.hariobudiharjo.cataloguemovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        String image = getIntent().getStringExtra("IMAGE");
        String judul = getIntent().getStringExtra("JUDUL");
        String deskripsi = getIntent().getStringExtra("DESKRIPSI");
        String release = getIntent().getStringExtra("RELEASE");

        ImageView ivPoster = findViewById(R.id.imageMovieDetail);
        TextView tvJudul = findViewById(R.id.textJudulDetail);
        TextView tvDeskripsi = findViewById(R.id.textDeskripsiDetail);
        TextView tvrelease = findViewById(R.id.textReleaseDetail);

        tvJudul.setText(judul);
        tvDeskripsi.setText(deskripsi);
        tvrelease.setText(" ( " + release + " )");
        Glide.with(this)
                .load(image)
                .into(ivPoster);

    }
}
