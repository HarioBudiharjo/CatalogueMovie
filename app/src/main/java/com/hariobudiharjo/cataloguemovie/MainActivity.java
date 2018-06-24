package com.hariobudiharjo.cataloguemovie;

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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    ListView listView;
    MovieAdapter movieAdapter;
    EditText etJudul;
    Button btnCari;
    static String TAG = "DEBUG status :";

    static final String JUDUL = "JUDUL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieAdapter = new MovieAdapter(this);
        movieAdapter.notifyDataSetChanged();
        listView = findViewById(R.id.list_item);
        listView.setAdapter(movieAdapter);

        etJudul = findViewById(R.id.edtFilm);
        btnCari = findViewById(R.id.btnCari);

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