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
import android.widget.TextView;
import android.widget.Toast;

import com.example.e610.capstoneproject.Activitys.DetailedActivity;
import com.example.e610.capstoneproject.Adapters.MangaAdapter;
import com.example.e610.capstoneproject.Data.AnimeContract;
import com.example.e610.capstoneproject.Models.Manga.Datum;
import com.example.e610.capstoneproject.Models.Manga.ExampleManga;
import com.example.e610.capstoneproject.R;
import com.google.gson.Gson;

import java.util.ArrayList;


public class MangaFavouriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        MangaAdapter.RecyclerViewClickListener {

    RecyclerView recyclerView;
    ExampleManga exampleManga;
    MangaAdapter mangaAdapter;
    View view;
    private String target;
    private boolean isTablet;
    String userInfo="";
    Bundle bundleUser=new Bundle();
    public MangaFavouriteFragment() {
        // Required empty public constructor
    }

    public void setTarget(String target) {
        this.target = target;
    }

    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    StaggeredGridLayoutManager sglm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.view_pager_fragment, container, false);
        isTablet = getResources().getBoolean(R.bool.isTablet);

        bundleUser=getArguments();
        userInfo=bundleUser.getString(getString(R.string.user_info));

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        TextView textView=(TextView)view.findViewById(R.id.main_title);
        if (target.equals(getString(R.string.favourite))) {
            String s=getString(R.string.favourite) + getString(R.string.space) + getString(R.string.manga_);
            textView.setText(s);
        }
        else if (target.equals(getString(R.string.completed))) {
            String s=getString(R.string.completed) + getString(R.string.space) + getString(R.string.manga_);
            textView.setText(s);
        }
        else if (target.equals(getString(R.string.startWatch))) {
            String s=getString(R.string.start_reading) + getString(R.string.space) + getString(R.string.manga_);
            textView.setText(s);
        }

        return view;
    }


    @Override
    public void ItemClicked(View v, int position) {
        Bundle bundle = new Bundle();
        Datum manga = exampleManga.data.get(position);
        bundle.putSerializable(getString(R.string.data), manga);
        bundle.putString(getString(R.string.type), getString(R.string.manga));
        bundle.putString(getString(R.string.user_info),userInfo);
        if (!isTablet)
            startActivity(new Intent(getActivity(), DetailedActivity.class).putExtra(getString(R.string.data), bundle));
        else {
            MangaDetailedFragment detailedFragment = new MangaDetailedFragment();
            its_Tablet(bundle, detailedFragment, R.id.asd);
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
            cursorLoader = new CursorLoader(getActivity(), AnimeContract.FavouriteMangaEntry.CONTENT_URI, new String[]{"*"},
                    AnimeContract.FavouriteMangaEntry.COLUMN_USER+" = ?", new String[]{userInfo}, null);
        else if (target.equals(getString(R.string.completed)))
            cursorLoader = new CursorLoader(getActivity(), AnimeContract.CompletedMangaEntry.CONTENT_URI, new String[]{"*"},
                    AnimeContract.CompletedMangaEntry.COLUMN_USER+" = ?", new String[]{userInfo}, null);
        else if (target.equals(getString(R.string.startWatch)))
            cursorLoader = new CursorLoader(getActivity(), AnimeContract.StartWatchMangaEntry.CONTENT_URI, new String[]{"*"},
                    AnimeContract.StartWatchMangaEntry.COLUMN_USER+" = ?", new String[]{userInfo}, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        exampleManga = new ExampleManga();
        exampleManga.data = new ArrayList<>();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                while (data.moveToNext()) {
                    String animeJson = data.getString(AnimeContract.FavouriteMangaEntry.COL_manga_content);
                    Gson gson = new Gson();
                    Datum manga = gson.fromJson(animeJson, Datum.class);
                    exampleManga.data.add(manga);
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                mangaAdapter = new MangaAdapter(exampleManga, getContext());
                mangaAdapter.setClickListener((MangaAdapter.RecyclerViewClickListener) f);
                recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
                sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                //recyclerView.setLayoutManager(new GridLayoutManager(this,2));
                recyclerView.setLayoutManager(sglm);
                recyclerView.setAdapter(mangaAdapter);
                MangaDetailedFragment detailedFragment = new MangaDetailedFragment();
                if (exampleManga.data.size() > 0)
                    CheckTabletOrNot(detailedFragment, exampleManga.data.get(0), R.id.asd);
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

