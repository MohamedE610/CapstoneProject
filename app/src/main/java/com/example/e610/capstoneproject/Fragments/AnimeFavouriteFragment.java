package com.example.e610.capstoneproject.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e610.capstoneproject.Adapters.AnimeAdapter;
import com.example.e610.capstoneproject.Activitys.DetailedActivity;
import com.example.e610.capstoneproject.Data.AnimeContract;
import com.example.e610.capstoneproject.Models.Anime.Datum;
import com.example.e610.capstoneproject.Models.Anime.ExampleAnime;
import com.exampleAnime.e610.capstoneproject.R;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Berlin on 8/2/2017.
 */

public class AnimeFavouriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        AnimeAdapter.RecyclerViewClickListener {

    RecyclerView recyclerView;
    ExampleAnime exampleAnime;
    AnimeAdapter animeAdapter;
    View view;
    private String target;
    private boolean isTablet;

    public AnimeFavouriteFragment() {
        // Required empty public constructor
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ProgressBar progressBar;
    StaggeredGridLayoutManager sglm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.view_pager_fragment, container, false);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        return view;
    }


    @Override
    public void ItemClicked(View v, int position) {
        Bundle bundle = new Bundle();
        Toast.makeText(getContext(), "haha", Toast.LENGTH_SHORT).show();
        Datum anime = exampleAnime.data.get(position);
        bundle.putSerializable(getString(R.string.data), anime);
        bundle.putString(getString(R.string.type), getString(R.string.anime));
        if (!isTablet) {
            startActivity(new Intent(getActivity(), DetailedActivity.class).putExtra(getString(R.string.data), bundle));
        } else {
            AnimeDetailedFragment animeDetailedFragment = new AnimeDetailedFragment();
            its_Tablet(bundle, animeDetailedFragment, R.id.asd);
        }
    }

    public static final int LOADER_ID = 101;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER_ID, null, this);
        //flag = 0;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = null;
        if (target.equals(getString(R.string.favourite)))
            cursorLoader = new CursorLoader(getActivity(), AnimeContract.FavouriteAnimeEntry.CONTENT_URI, null,
                    null, null, null);
        else if (target.equals(getString(R.string.completed)))
            cursorLoader = new CursorLoader(getActivity(), AnimeContract.CompletedAnimeEntry.CONTENT_URI, null,
                    null, null, null);
        else if (target.equals(getString(R.string.startWatch)))
            cursorLoader = new CursorLoader(getActivity(), AnimeContract.StartWatchAnimeEntry.CONTENT_URI, null,
                    null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        exampleAnime = new ExampleAnime();
        exampleAnime.data = new ArrayList<>();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                while (data.moveToNext()) {
                    String animeJson = data.getString(AnimeContract.FavouriteAnimeEntry.COL_anime_content);
                    Gson gson = new Gson();
                    Datum anime = gson.fromJson(animeJson, Datum.class);
                    exampleAnime.data.add(anime);
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                animeAdapter = new AnimeAdapter(exampleAnime, getContext());
                animeAdapter.setClickListener((AnimeAdapter.RecyclerViewClickListener) f);
                recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
                sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                //recyclerView.setLayoutManager(new GridLayoutManager(this,2));
                recyclerView.setLayoutManager(sglm);
                recyclerView.setAdapter(animeAdapter);
                AnimeDetailedFragment detailedFragment = new AnimeDetailedFragment();
                if (exampleAnime.data.size() > 0)
                    CheckTabletOrNot(detailedFragment, exampleAnime.data.get(0), R.id.asd);

                super.onPostExecute(aVoid);
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    Fragment f = this;

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void CheckTabletOrNot(Fragment fragment, Datum datum, int id) {
        if (isTablet) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.data), datum);
            its_Tablet(bundle, fragment, id);
        }
    }

    private void its_Tablet(Bundle bundle, Fragment fragment, int fragmentIDContainer) {
        fragment.setArguments(bundle);
        if (getActivity() != null)
            getActivity().getSupportFragmentManager().beginTransaction().replace(fragmentIDContainer, fragment).commit();
    }


}

