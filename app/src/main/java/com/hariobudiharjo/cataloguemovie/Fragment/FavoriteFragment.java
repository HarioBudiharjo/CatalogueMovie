package com.hariobudiharjo.cataloguemovie.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hariobudiharjo.cataloguemovie.Adapter.RVMovieAdapter;
import com.hariobudiharjo.cataloguemovie.Helper.MovieHelper;
import com.hariobudiharjo.cataloguemovie.Model.MovieItems;
import com.hariobudiharjo.cataloguemovie.R;

import java.util.ArrayList;

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("favorite", movieItems);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
//            someStateValue = savedInstanceState.getInt(SOME_VALUE_KEY);
            // Lakukan ssesuatu dengan someStateValue jika diperlukan
        }
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
        if (savedInstanceState == null) {
            Log.d(TAG, "onCreateView: isi" + savedInstanceState);
            Log.d(TAG, "onCreateView: masuk kosong savedinstancestate");
            getFavorite();
        } else {
            Log.d(TAG, "onCreateView: masuk savedinstancestate");
            rvMovieAdapter.setListMovie(savedInstanceState.<MovieItems>getParcelableArrayList("favorite"));
//            rvMovieAdapter.notifyDataSetChanged();
        }
        setRetainInstance(true);
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
