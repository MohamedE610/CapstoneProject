package com.example.e610.capstoneproject.Fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e610.capstoneproject.Data.AnimeContract;
import com.example.e610.capstoneproject.Models.Manga.Datum;
import com.example.e610.capstoneproject.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


/**
 * Created by E610 on 8/3/2017.
 */
public class MangaDetailedFragment extends Fragment {

    Datum manga;
    ImageView cover;
    TextView title;
    TextView startDate;
    TextView rating;
    RatingBar ratingBar;
    ImageView favourite;
    ImageView startWatch;
    ImageView completed;
    TextView overview;
    TextView ageGuide;
    TextView status;
    TextView epNum;//append
    TextView epDuration;//append
    TextView serialization;
    TextView mangaType;
    private FloatingActionButton fabShare;

    String userInfo="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manga_detail, container, false);

            title = (TextView) view.findViewById(R.id.title_txt);
            startDate = (TextView) view.findViewById(R.id.start_date_txt);
            rating = (TextView) view.findViewById(R.id.rating_txt);
            overview = (TextView) view.findViewById(R.id.overview);
            ageGuide = (TextView) view.findViewById(R.id.age_guide);
            status = (TextView) view.findViewById(R.id.status);
            epNum = (TextView) view.findViewById(R.id.ch_num);
            epDuration = (TextView) view.findViewById(R.id.ch_volume);
            serialization = (TextView) view.findViewById(R.id.serialization);
            mangaType = (TextView) view.findViewById(R.id.mangaType);


            ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);

            cover = (ImageView) view.findViewById(R.id.cover);
            favourite = (ImageView) view.findViewById(R.id.favourite_img);
            completed = (ImageView) view.findViewById(R.id.completed_img);
            startWatch = (ImageView) view.findViewById(R.id.start_read_img);

            fabShare = (FloatingActionButton) view.findViewById(R.id.share_fab);

            Bundle bundle = getArguments();
            userInfo=bundle.getString(getString(R.string.user_info));
            manga = (Datum) bundle.getSerializable(getString(R.string.data));

            title.setText(manga.attributes.canonicalTitle);
            startDate.setText(manga.attributes.startDate);
            overview.setText(manga.attributes.synopsis);
            ageGuide.setText(manga.attributes.ageRatingGuide == null ? getString(R.string.all_ages) : manga.attributes.ageRatingGuide.toString());
            status.setText(manga.attributes.status);
            epNum.append(String.valueOf(manga.attributes.chapterCount));
            epDuration.append(String.valueOf(manga.attributes.volumeCount));
            serialization.setText(manga.attributes.serialization);
            mangaType.setText(manga.attributes.mangaType);

            float f = manga.attributes.averageRating == null ? 1 : Float.valueOf(manga.attributes.averageRating);
            ratingBar.setRating(f / 20);

            String s = manga.attributes.coverImage == null ? getString(R.string.error) : manga.attributes.coverImage.large;
            Picasso.with(getActivity()).load(s).placeholder(R.drawable.asd)
                    .error(R.drawable.asd).into(cover);


            if (isFavorite())
                favourite.setImageResource(R.drawable.unfavorite);
            else
                favourite.setImageResource(R.drawable.favorite);

            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isFavorite())
                        addToFavorite();
                    else
                        removeFromFavorites();
                }
            });

            if (isCompleted())
                completed.setImageResource(R.drawable.checked);
            else
                completed.setImageResource(R.drawable.unchecked);

            completed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isCompleted())
                        addToCompleted();
                    else
                        removeFromCompleted();
                }
            });

            if (isStartWatch())
                startWatch.setImageResource(R.drawable.checked);
            else
                startWatch.setImageResource(R.drawable.unchecked);

            startWatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isStartWatch())
                        addToStartWatch();
                    else
                        removeFromStartWatch();
                }
            });


        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType(getActivity().getString(R.string.text_plain));
                String shareBody = "";
                if (manga != null) {
                    shareBody = manga.attributes.canonicalTitle + "\n\n" + manga.attributes.synopsis;
                }

                if (shareBody.equals(""))
                    shareBody = getString(R.string.share_content_body);

                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,getString(R.string.subject_here ));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
            }
        });


        return view;
    }

    public void addToFavorite() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                ContentValues movieValues = new ContentValues();
                movieValues.put(AnimeContract.FavouriteMangaEntry.COLUMN_manga_ID,
                        manga.id);
                Gson gson = new Gson();
                String jsonAnime = gson.toJson(manga);
                movieValues.put(AnimeContract.FavouriteMangaEntry.COLUMN_manga_content,
                        jsonAnime);

                movieValues.put(AnimeContract.FavouriteMangaEntry.COLUMN_USER,
                        userInfo);

                getActivity().getContentResolver().insert(
                        AnimeContract.FavouriteMangaEntry.CONTENT_URI,
                        movieValues
                );

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(getContext(), R.string.manga_saved, Toast.LENGTH_SHORT).show();
                favourite.setImageResource(R.drawable.unfavorite);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void removeFromFavorites() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (isFavorite()) {
                    getActivity().getContentResolver().delete(AnimeContract.FavouriteMangaEntry.CONTENT_URI,
                            AnimeContract.FavouriteMangaEntry.COLUMN_manga_ID + " = " + manga.id
                            +" and "+AnimeContract.FavouriteMangaEntry.COLUMN_USER+" = ?", new String[]{userInfo});

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                favourite.setImageResource(R.drawable.favorite);

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    private boolean isFavorite() {
        Cursor movieCursor = getActivity().getContentResolver().query(
                AnimeContract.FavouriteMangaEntry.CONTENT_URI,
                new String[]{"*"},
                AnimeContract.FavouriteMangaEntry.COLUMN_manga_ID + " = " + manga.id +" and "
                        +AnimeContract.FavouriteMangaEntry.COLUMN_USER+" = ?",
                new String[]{userInfo},
                null);

        if (movieCursor != null && movieCursor.moveToFirst()) {
            movieCursor.close();
            return true;
        } else {
            return false;
        }
    }

    public void addToCompleted() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                ContentValues movieValues = new ContentValues();
                movieValues.put(AnimeContract.CompletedMangaEntry.COLUMN_manga_ID,
                        manga.id);
                Gson gson = new Gson();
                String jsonAnime = gson.toJson(manga);
                movieValues.put(AnimeContract.CompletedMangaEntry.COLUMN_manga_content,
                        jsonAnime);

                movieValues.put(AnimeContract.CompletedMangaEntry.COLUMN_USER,
                        userInfo);

                getActivity().getContentResolver().insert(
                        AnimeContract.CompletedMangaEntry.CONTENT_URI,
                        movieValues
                );

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(getContext(), R.string.manga_saved, Toast.LENGTH_SHORT).show();
                completed.setImageResource(R.drawable.checked);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void removeFromCompleted() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (isCompleted()) {
                    getActivity().getContentResolver().delete(AnimeContract.CompletedMangaEntry.CONTENT_URI,
                            AnimeContract.CompletedMangaEntry.COLUMN_manga_ID + " = " + manga.id
                                    +" and "+AnimeContract.CompletedMangaEntry.COLUMN_USER+" = ?", new String[]{userInfo});
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                completed.setImageResource(R.drawable.unchecked);

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    private boolean isCompleted() {
        Cursor movieCursor = getActivity().getContentResolver().query(
                AnimeContract.CompletedMangaEntry.CONTENT_URI,
                new String[]{"*"},
                AnimeContract.CompletedMangaEntry.COLUMN_manga_ID + " = " + manga.id +" and "
                        +AnimeContract.CompletedMangaEntry.COLUMN_USER+" = ?",
                new String[]{userInfo},
                null);
        if (movieCursor != null && movieCursor.moveToFirst()) {
            movieCursor.close();
            return true;
        } else {
            return false;
        }
    }

    public void addToStartWatch() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                ContentValues movieValues = new ContentValues();
                movieValues.put(AnimeContract.StartWatchMangaEntry.COLUMN_manga_ID,
                        manga.id);
                Gson gson = new Gson();
                String jsonAnime = gson.toJson(manga);
                movieValues.put(AnimeContract.StartWatchMangaEntry.COLUMN_manga_content,
                        jsonAnime);

                movieValues.put(AnimeContract.StartWatchMangaEntry.COLUMN_USER,
                        userInfo);

                getActivity().getContentResolver().insert(
                        AnimeContract.StartWatchMangaEntry.CONTENT_URI,
                        movieValues
                );

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(getContext(), R.string.manga_saved, Toast.LENGTH_SHORT).show();
                startWatch.setImageResource(R.drawable.checked);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void removeFromStartWatch() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (isStartWatch()) {
                    getActivity().getContentResolver().delete(AnimeContract.StartWatchMangaEntry.CONTENT_URI,
                            AnimeContract.StartWatchMangaEntry.COLUMN_manga_ID + " = " + manga.id
                                    +" and "+AnimeContract.StartWatchMangaEntry.COLUMN_USER+" = ?", new String[]{userInfo});
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                startWatch.setImageResource(R.drawable.unchecked);

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    private boolean isStartWatch() {
        Cursor movieCursor = getActivity().getContentResolver().query(
                AnimeContract.StartWatchMangaEntry.CONTENT_URI,
                new String[]{"*"},
                AnimeContract.StartWatchMangaEntry.COLUMN_manga_ID + " = " + manga.id +" and "
                        +AnimeContract.StartWatchMangaEntry.COLUMN_USER+" = ?",
                new String[]{userInfo},
                null);

        if (movieCursor != null && movieCursor.moveToFirst()) {
            movieCursor.close();
            return true;
        } else {
            return false;
        }
    }


}
