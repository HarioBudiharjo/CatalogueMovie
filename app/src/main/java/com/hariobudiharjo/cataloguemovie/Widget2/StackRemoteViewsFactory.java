package com.hariobudiharjo.cataloguemovie.Widget2;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

class StackRemoteViewsFactory implements
        RemoteViewsService.RemoteViewsFactory {

    private List<Bitmap> mWidgetItems = new ArrayList<>();
    private List<MovieItems> imageUrlItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        MovieHelper movieHelper = new MovieHelper(mContext);
        movieHelper.open();
        imageUrlItems.addAll(movieHelper.getAllData());
        Log.d("DEBUGURL", "onCreate: "+imageUrlItems);
    }

    @Override
    public void onDataSetChanged() {
//        MovieHelper movieHelper = new MovieHelper(mContext);
//        movieHelper.open();
//        imageUrlItems.addAll(movieHelper.getAllData());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return imageUrlItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
//        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));

        Log.d("DEBUGWIDGET",position+"");
        Log.d("DEBUGWIDGET",imageUrlItems.size()+"");

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
        extras.putInt(ImagesBannerWidget.EXTRA_ITEM, position);
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
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}