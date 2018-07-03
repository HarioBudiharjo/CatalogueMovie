package com.hariobudiharjo.cataloguemovie.Network;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hariobudiharjo.cataloguemovie.BuildConfig;
import com.hariobudiharjo.cataloguemovie.Model.MovieItems;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieItems>> {
    private String judul;
    private Boolean hasResult = false;
    private ArrayList<MovieItems> data;
    String TAG = "DEBUG loade";

    public MovieAsyncTaskLoader(final Context context, String judul) {
        super(context);

        onContentChanged();
        this.judul = judul;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(data);
    }

    @Override
    public void deliverResult(@Nullable ArrayList<MovieItems> data) {
        super.deliverResult(data);
        this.data = data;
        this.hasResult = true;
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStartLoading();
        if (hasResult) {
            onReleaseResources(data);
            this.data = null;
            this.hasResult = false;
        }
    }


    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<MovieItems> movieItems = new ArrayList<>();
//        String api_key = "f6d4b87f37b107f90af522238d98e349";
        String api_key = BuildConfig.API_KEY;
        String url = "https://api.themoviedb.org/3/search/movie?query=" + judul + "&language=en-US&api_key=" + api_key;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItem = new MovieItems(movie);
                        Log.d(TAG, "onSuccess: " + movieItem);
                        movieItems.add(movieItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieItems;
    }

    protected void onReleaseResources(ArrayList<MovieItems> data) {

    }
}
