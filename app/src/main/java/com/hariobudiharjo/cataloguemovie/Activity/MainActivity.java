package com.hariobudiharjo.cataloguemovie.Activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.hariobudiharjo.cataloguemovie.Adapter.MovieAdapter;
import com.hariobudiharjo.cataloguemovie.Network.MovieAsyncTaskLoader;
import com.hariobudiharjo.cataloguemovie.Model.MovieItems;
import com.hariobudiharjo.cataloguemovie.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    @BindView(R.id.list_item) ListView listView;
    MovieAdapter movieAdapter;
    @BindView(R.id.edtFilm) EditText etJudul;
    @BindView(R.id.btnCari) Button btnCari;
    static String TAG = "DEBUG status :";

    static final String JUDUL = "JUDUL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        movieAdapter = new MovieAdapter(this);
        movieAdapter.notifyDataSetChanged();
        listView.setAdapter(movieAdapter);

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul = etJudul.getText().toString();

                Log.d(TAG, "onClick: " + TextUtils.isEmpty(judul));
//                if (TextUtils.isEmpty(judul)) return;

                Bundle bundle = new Bundle();
                bundle.putString(JUDUL, judul);
                getLoaderManager().restartLoader(0, bundle, MainActivity.this);
            }
        });

    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        String judul = "";
        if (args != null) {
            judul = args.getString(JUDUL);
        }
        Log.d(TAG, "onCreateLoader: " + judul);
        return new MovieAsyncTaskLoader(this, judul);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        movieAdapter.setDataMovie(data);
        Log.d(TAG, "onLoadFinished: " + data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
        movieAdapter.setDataMovie(null);
    }
}
