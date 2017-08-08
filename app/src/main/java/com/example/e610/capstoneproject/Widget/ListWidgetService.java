package com.example.e610.capstoneproject.Widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.example.e610.capstoneproject.Data.AnimeContract;
import com.example.e610.capstoneproject.Models.Anime.Datum;
import com.example.e610.capstoneproject.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;


public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    ArrayList<Datum> list = new ArrayList<>();

    Context context;

    public ListRemoteViewsFactory(Context ctx) {

        context = ctx;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        list = getFavouriteMovies();

    }

    public ArrayList<Datum> getFavouriteMovies() {
        ArrayList<Datum> StartWatchList = new ArrayList<>();
        Datum anime;
        Cursor cursor;
        cursor = context.getContentResolver().query(AnimeContract.StartWatchAnimeEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {

            while (cursor.moveToNext()) {
                String animeJson = cursor.getString(AnimeContract.FavouriteAnimeEntry.COL_anime_content);
                Gson gson = new Gson();
                anime = new Datum();
                anime = gson.fromJson(animeJson, Datum.class);
                StartWatchList.add(anime);

            }
        }
        return StartWatchList;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.row_widget_item);

        if (list == null)
            return views;

        views.setTextViewText(R.id.anime_widget_title, list.get(position).attributes.canonicalTitle);

       /* AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        ComponentName widgetComponent = new ComponentName(context,AnimeSanWidget.class);
        int[] widgetIds = widgetManager.getAppWidgetIds(widgetComponent);*/
      /*  Picasso.with(context).load(list.get(position).attributes.posterImage.original)
                .into(views,R.id.anime_widget_img,widgetIds);*/

        try {
            Bitmap bitmap = Picasso.with(context).load(list.get(position).attributes.posterImage.original).get();
            views.setImageViewBitmap(R.id.anime_widget_img, bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(context.getString(R.string.data), list.get(position));
        bundle.putString(context.getString(R.string.type), context.getString(R.string.anime));
        Intent intent = new Intent();
        intent.putExtra(context.getString(R.string.data), bundle);
        views.setOnClickFillInIntent(R.id.row_item_container_widget, intent);
        return views;
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
        return true;
    }

}
