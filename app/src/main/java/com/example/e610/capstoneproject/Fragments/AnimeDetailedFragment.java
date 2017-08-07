package com.example.e610.capstoneproject.Fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e610.capstoneproject.Data.AnimeContract;
import com.example.e610.capstoneproject.Models.Anime.Datum;
import com.exampleAnime.e610.capstoneproject.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


/**
 * Created by E610 on 8/3/2017.
 */
public class AnimeDetailedFragment extends Fragment {

    Datum anime;
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
    ImageView trailer;
    FloatingActionButton fabShare;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_anime_detail, container, false);

        title = (TextView) view.findViewById(R.id.title_txt);
        startDate = (TextView) view.findViewById(R.id.start_date_txt);
        rating = (TextView) view.findViewById(R.id.rating_txt);
        overview = (TextView) view.findViewById(R.id.overview);
        ageGuide = (TextView) view.findViewById(R.id.age_guide);
        status = (TextView) view.findViewById(R.id.status);
        epNum = (TextView) view.findViewById(R.id.ep_num);
        epDuration = (TextView) view.findViewById(R.id.ep_duration);

        ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);

        cover = (ImageView) view.findViewById(R.id.cover);
        favourite = (ImageView) view.findViewById(R.id.favourite_img);
        completed = (ImageView) view.findViewById(R.id.completed_img);
        startWatch = (ImageView) view.findViewById(R.id.start_watch_img);
        trailer = (ImageView) view.findViewById(R.id.trailer);

        fabShare = (FloatingActionButton) view.findViewById(R.id.share_fab);

        Bundle bundle = getArguments();
        anime = (Datum) bundle.getSerializable(getString(R.string.data));

        title.setText(anime.attributes.canonicalTitle);
        startDate.setText(anime.attributes.startDate);
        overview.setText(anime.attributes.synopsis);
        ageGuide.setText(anime.attributes.ageRatingGuide);
        status.setText(anime.attributes.status);
        epNum.append(String.valueOf(anime.attributes.episodeCount));
        epDuration.append(String.valueOf(anime.attributes.episodeLength));

        float f = anime.attributes.averageRating == null ? 1 : Float.valueOf(anime.attributes.averageRating);
        ratingBar.setRating(f / 20);

        String s = anime.attributes.coverImage == null ? getString(R.string.error) : anime.attributes.coverImage.original;
        Picasso.with(getActivity()).load(s).placeholder(R.drawable.asd)
                .error(R.drawable.asd).into(cover);

        try {
            Picasso.with(getActivity()).load(getString(R.string.img_youtube_com) + anime.attributes.youtubeVideoId + getString(R.string.hqdefault)).placeholder(R.drawable.asd)
                    .error(R.drawable.asd).into(trailer);
        } catch (Exception e) {
        }

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

        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.www_youtube_com_watch) + anime.attributes.youtubeVideoId)));
            }
        });


        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "";
                if (anime != null) {
                    shareBody = anime.attributes.canonicalTitle + "\n\n" + anime.attributes.synopsis;
                }

                if (shareBody.equals(""))
                    shareBody = getString(R.string.share_content_body);

                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.subject_here));
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
                movieValues.put(AnimeContract.FavouriteAnimeEntry.COLUMN_anime_ID,
                        anime.id);
                Gson gson = new Gson();
                String jsonAnime = gson.toJson(anime);
                movieValues.put(AnimeContract.FavouriteAnimeEntry.COLUMN_anime_content,
                        jsonAnime);

                getActivity().getContentResolver().insert(
                        AnimeContract.FavouriteAnimeEntry.CONTENT_URI,
                        movieValues
                );

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(getContext(), R.string.anime_saved, Toast.LENGTH_SHORT).show();
                favourite.setImageResource(R.drawable.unfavorite);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void removeFromFavorites() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (isFavorite()) {
                    getActivity().getContentResolver().delete(AnimeContract.FavouriteAnimeEntry.CONTENT_URI,
                            AnimeContract.FavouriteAnimeEntry.COLUMN_anime_ID + " = " + anime.id, null);
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
                AnimeContract.FavouriteAnimeEntry.CONTENT_URI,
                new String[]{AnimeContract.FavouriteAnimeEntry.COLUMN_anime_ID},
                AnimeContract.FavouriteAnimeEntry.COLUMN_anime_ID + " = " + anime.id,
                null,
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
                movieValues.put(AnimeContract.CompletedAnimeEntry.COLUMN_anime_ID,
                        anime.id);
                Gson gson = new Gson();
                String jsonAnime = gson.toJson(anime);
                movieValues.put(AnimeContract.CompletedAnimeEntry.COLUMN_anime_content,
                        jsonAnime);

                getActivity().getContentResolver().insert(
                        AnimeContract.CompletedAnimeEntry.CONTENT_URI,
                        movieValues
                );

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(getContext(), getString(R.string.anime_saved), Toast.LENGTH_SHORT).show();
                completed.setImageResource(R.drawable.checked);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void removeFromCompleted() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (isCompleted()) {
                    getActivity().getContentResolver().delete(AnimeContract.CompletedAnimeEntry.CONTENT_URI,
                            AnimeContract.CompletedAnimeEntry.COLUMN_anime_ID + " = " + anime.id, null);
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
                AnimeContract.CompletedAnimeEntry.CONTENT_URI,
                new String[]{AnimeContract.CompletedAnimeEntry.COLUMN_anime_ID},
                AnimeContract.CompletedAnimeEntry.COLUMN_anime_ID + " = " + anime.id,
                null,
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
                movieValues.put(AnimeContract.StartWatchAnimeEntry.COLUMN_anime_ID,
                        anime.id);
                Gson gson = new Gson();
                String jsonAnime = gson.toJson(anime);
                movieValues.put(AnimeContract.StartWatchAnimeEntry.COLUMN_anime_content,
                        jsonAnime);

                getActivity().getContentResolver().insert(
                        AnimeContract.StartWatchAnimeEntry.CONTENT_URI,
                        movieValues
                );

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(getContext(), getString(R.string.anime_saved), Toast.LENGTH_SHORT).show();
                startWatch.setImageResource(R.drawable.checked);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void removeFromStartWatch() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (isStartWatch()) {
                    getActivity().getContentResolver().delete(AnimeContract.StartWatchAnimeEntry.CONTENT_URI,
                            AnimeContract.StartWatchAnimeEntry.COLUMN_anime_ID + " = " + anime.id, null);
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
                AnimeContract.StartWatchAnimeEntry.CONTENT_URI,
                new String[]{AnimeContract.StartWatchAnimeEntry.COLUMN_anime_ID},
                AnimeContract.StartWatchAnimeEntry.COLUMN_anime_ID + " = " + anime.id,
                null,
                null);

        if (movieCursor != null && movieCursor.moveToFirst()) {
            movieCursor.close();
            return true;
        } else {
            return false;
        }
    }

}

