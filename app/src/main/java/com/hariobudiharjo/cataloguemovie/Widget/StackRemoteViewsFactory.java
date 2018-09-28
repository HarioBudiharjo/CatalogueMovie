package com.hariobudiharjo.cataloguemovie.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.hariobudiharjo.cataloguemovie.Helper.MovieHelper;
import com.hariobudiharjo.cataloguemovie.Model.MovieItems;
import com.hariobudiharjo.cataloguemovie.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Bitmap> mWidgetItems = new ArrayList<>();
    private List<MovieItems> imageUrlItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;
    private String TAG = "DEBUG";


    ArrayList<MovieItems> movieItems = new ArrayList<>();

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        getFavorite();
        MovieHelper movieHelper = new MovieHelper(mContext);
        movieHelper.open();
        imageUrlItems.addAll(movieHelper.getAllData());
//        movieItems.add(movieHelper.getAllData());
//        Log.d(TAG, "onSuccess: " + movieItems);
//        rvMovieAdapter.setListMovie(movieHelper.query());
//
//        rv.setImageViewBitmap(R.id.now_play_imageview,bmp);

        movieHelper.close();
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.darth_vader));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star_wars_logo));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.storm_trooper));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.starwars));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.falcon));
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();

//        getContentResolver()...
//        getFavoriteMovies(mContext);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.movie_item);
//        Log.d(TAG, "getViewAt ke " + position + ": " + imageUrlItems.get(position).getGambar());

        Bitmap bmp = null;
        try {

            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(imageUrlItems.get(position).getGambar())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "error");
        }

//        Glide.with(getActivity()).asBitmap().load(chalet.profilePhoto).into(mImageView);

//        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));
        rv.setImageViewBitmap(R.id.imageView, bmp);

        Bundle extras = new Bundle();
        extras.putInt(MovieBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void getFavorite() {
        MovieHelper movieHelper = new MovieHelper(mContext);
        movieHelper.open();
        movieItems.addAll(movieHelper.getAllData());
        Log.d(TAG, "getFavorite: " + movieItems.toString());
//        Log.d(TAG, "onSuccess STACK WIDGET: " + movieHelper.getAllData().toString());
//        rvMovieAdapter.setListMovie(movieHelper.query());
        movieHelper.close();
    }
}
