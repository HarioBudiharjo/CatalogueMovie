package com.hariobudiharjo.cataloguemovie.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hariobudiharjo.cataloguemovie.Adapter.RVMovieAdapter;
import com.hariobudiharjo.cataloguemovie.BuildConfig;
import com.hariobudiharjo.cataloguemovie.Helper.MovieHelper;
import com.hariobudiharjo.cataloguemovie.Model.MovieItems;
import com.hariobudiharjo.cataloguemovie.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    RVMovieAdapter rvMovieAdapter;
    RecyclerView rvCategory;
    ArrayList<MovieItems> movieItems = new ArrayList<>();
    String TAG = "DEBUG";

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MovieHelper movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        ArrayList<MovieItems> movieItemsArrayList = movieHelper.query();
        movieHelper.close();
        Log.d(TAG, "onCreateView: " + movieItemsArrayList.toString());

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        rvCategory = view.findViewById(R.id.rvFavorite);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovieAdapter = new RVMovieAdapter(getContext());
        rvCategory.setAdapter(rvMovieAdapter);
        getFavorite();
        return view;
    }

    private void getFavorite() {
        MovieHelper movieHelper = new MovieHelper(getContext());
        movieHelper.open();
//        movieItems.add(movieHelper.getAllData());
        Log.d(TAG, "onSuccess: " + movieItems);
        rvMovieAdapter.setListMovie(movieHelper.query());
        movieHelper.close();
        rvMovieAdapter.notifyDataSetChanged();
    }
}
