package com.hariobudiharjo.favoritmovie.Activity;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hariobudiharjo.favoritmovie.Adapter.RVMovieAdapter;
import com.hariobudiharjo.favoritmovie.Model.MovieItems;
import com.hariobudiharjo.favoritmovie.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.hariobudiharjo.favoritmovie.Provider.DatabaseContract.TABLE_NOTE;

public class MainActivity extends AppCompatActivity {
    public static final String AUTHORITY = "com.hariobudiharjo.cataloguemovie";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NOTE)
            .build();

    private String TAG = "DEBUG";

    private static String ID = "id";
    private static String JUDUL = "judul";
    private static String DESKRIPSI = "deskripsi";
    private static String GAMBAR = "gambar";
    private static String RELEASE = "rilis";

    RVMovieAdapter rvMovieAdapter;
    RecyclerView rvCategory;
    ArrayList<MovieItems> movieItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
//        while (cursor.moveToNext()) {
//            Log.d(TAG+"CURSOR", "onCreate: " + cursor.getString(cursor.getColumnIndex(JUDUL))+" : "+cursor.getString(cursor.getColumnIndex(ID)));
//        }
        rvCategory = findViewById(R.id.rvNowPlaying);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        rvMovieAdapter = new RVMovieAdapter(this);
        rvCategory.setAdapter(rvMovieAdapter);
        getFavorite();
    }

    private void getFavorite() {
//        for (int i = 0; i < list.length(); i++) {
//            MovieItems movieItem = new MovieItems(movie);
//            Log.d(TAG, "onSuccess: " + movieItem);
//            movieItems.add(movieItem);
//        }
        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
//            Log.d(TAG+"CURSOR", "onCreate: " + cursor.getString(cursor.getColumnIndex(JUDUL))+" : "+cursor.getString(cursor.getColumnIndex(ID)));
            MovieItems movieItem = new MovieItems();
            movieItem.setDeskripsi(cursor.getString(cursor.getColumnIndex(DESKRIPSI)));
            movieItem.setGambar(cursor.getString(cursor.getColumnIndex(GAMBAR)));
            movieItem.setJudul(cursor.getString(cursor.getColumnIndex(JUDUL)));
            movieItem.setRelease(cursor.getString(cursor.getColumnIndex(RELEASE)));
            Log.d(TAG, movieItem.toString());
            movieItems.add(movieItem);
        }
        Log.d(TAG, "onSuccess: " + movieItems);
        rvMovieAdapter.setListMovie(movieItems);
        rvMovieAdapter.notifyDataSetChanged();
    }
}
