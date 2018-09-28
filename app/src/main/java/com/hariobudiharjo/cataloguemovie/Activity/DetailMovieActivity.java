package com.hariobudiharjo.cataloguemovie.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hariobudiharjo.cataloguemovie.Helper.MovieHelper;
import com.hariobudiharjo.cataloguemovie.Model.MovieItems;
import com.hariobudiharjo.cataloguemovie.R;

import java.util.ArrayList;

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
    @BindView(R.id.ibFavorite)
    ImageButton ibFavorite;

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

        final MovieHelper movieHelper = new MovieHelper(getApplicationContext());


        tvJudul.setText(judul);
        tvDeskripsi.setText(deskripsi);
        tvrelease.setText(" ( " + release + " )");
        Glide.with(this)
                .load(image)
                .into(ivPoster);

        movieHelper.open();
        total = movieHelper.getDataById(Integer.toString(movieItems.getId())).size();
        movieHelper.close();

        if (total > 0)
            ibFavorite.setImageResource(R.drawable.ic_star_black_24dp);

        ibFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieHelper.open();
                if (total > 0) {
                    total = 0;
                    movieHelper.open();
                    movieHelper.delete(movieItems.getId());
                    ArrayList<MovieItems> movieItemsArrayList = movieHelper.query();
                    movieHelper.close();
                    ibFavorite.setImageResource(R.drawable.ic_star_border_black_24dp);
                    Log.d(TAG, "onCreateView: " + movieItemsArrayList.toString());
                } else {
                    total = 1;
                    movieHelper.open();
                    Log.d(TAG + "movieitem", movieItems.toString());
                    movieHelper.insert(movieItems);
                    ArrayList<MovieItems> movieItemsArrayList = movieHelper.query();
                    movieHelper.close();
                    ibFavorite.setImageResource(R.drawable.ic_star_black_24dp);
                    Log.d(TAG, "onCreateView: " + movieItemsArrayList.toString());
                }
                movieHelper.close();
            }
        });
    }
}
