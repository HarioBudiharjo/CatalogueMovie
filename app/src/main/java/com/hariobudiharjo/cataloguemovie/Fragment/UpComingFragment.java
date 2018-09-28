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
import com.hariobudiharjo.cataloguemovie.BuildConfig;
import com.hariobudiharjo.cataloguemovie.Model.MovieItems;
import com.hariobudiharjo.cataloguemovie.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class UpComingFragment extends Fragment {

    RVMovieAdapter rvMovieAdapter;
    RecyclerView rvCategory;
    ArrayList<MovieItems> movieItems = new ArrayList<>();
    String TAG = "DEBUG";

    public UpComingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("upcoming", movieItems);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);
        rvCategory = view.findViewById(R.id.rvUpComing);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovieAdapter = new RVMovieAdapter(getContext());
        rvCategory.setAdapter(rvMovieAdapter);
        if (savedInstanceState == null) {
            Log.d(TAG, "onCreateView: isi" + savedInstanceState);
            Log.d(TAG, "onCreateView: masuk kosong savedinstancestate");
            getUpComing();
        } else {
            Log.d(TAG, "onCreateView: masuk savedinstancestate");
            rvMovieAdapter.setListMovie(savedInstanceState.<MovieItems>getParcelableArrayList("upcoming"));
//            rvMovieAdapter.notifyDataSetChanged();
        }
        setRetainInstance(true);
        return view;
    }

    private void getUpComing() {
        Log.d(TAG, "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        String api_key = BuildConfig.API_KEY;
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + api_key + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItem = new MovieItems(movie);
                        Log.d(TAG, "onSuccess: " + movieItem);
                        movieItems.add(movieItem);
                    }
                    Log.d(TAG, "onSuccess: " + movieItems);
                    rvMovieAdapter.setListMovie(movieItems);
                    rvMovieAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

}
