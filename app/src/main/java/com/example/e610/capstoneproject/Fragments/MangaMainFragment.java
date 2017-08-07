package com.example.e610.capstoneproject.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e610.capstoneproject.Activitys.DetailedActivity;
import com.example.e610.capstoneproject.Adapters.MangaAdapter;
import com.example.e610.capstoneproject.Models.Manga.Datum;
import com.example.e610.capstoneproject.Models.Manga.ExampleManga;
import com.example.e610.capstoneproject.Utils.FetchData;
import com.example.e610.capstoneproject.Utils.NetworkResponse;
import com.example.e610.capstoneproject.Utils.NetworkState;
import com.exampleAnime.e610.capstoneproject.R;
import com.google.gson.Gson;

/**
 * Created by Berlin on 8/2/2017.
 */

public class MangaMainFragment extends Fragment implements NetworkResponse, MangaAdapter.RecyclerViewClickListener {

    RecyclerView recyclerView;
    ExampleManga exampleManga;
    private String url;
    private boolean isTablet;
    ProgressBar progressBar;

    public MangaMainFragment() {
        // Required empty public constructor
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_pager_fragment, container, false);


        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setLayoutManager(sglm);

        if (NetworkState.ConnectionAvailable(getContext())) {
            FetchData fetchData = new FetchData(url);
            fetchData.setNetworkResponse(this);
            fetchData.execute();
        } else
            Toast.makeText(getContext(), "No internet Connection", Toast.LENGTH_SHORT).show();

        isTablet = getResources().getBoolean(R.bool.isTablet);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void OnSuccess(String JsonData) {


        Gson gson = new Gson();
        exampleManga = gson.fromJson(JsonData, ExampleManga.class);
        progressBar.setVisibility(View.GONE);

        MangaAdapter adapter = new MangaAdapter(exampleManga, getContext());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        MangaDetailedFragment detailedFragment = new MangaDetailedFragment();
        if (exampleManga.data.size() > 0)
            CheckTabletOrNot(detailedFragment, exampleManga.data.get(0), R.id.asd);
    }

    @Override
    public void OnFailure(boolean Failure) {

    }

    @Override
    public void ItemClicked(View v, int position) {
        Bundle bundle = new Bundle();
        Toast.makeText(getContext(), "haha", Toast.LENGTH_SHORT).show();
        Datum manga = exampleManga.data.get(position);
        bundle.putSerializable(getString(R.string.data), manga);
        bundle.putString(getString(R.string.type), getString(R.string.manga));
        if (!isTablet)
            startActivity(new Intent(getActivity(), DetailedActivity.class).putExtra(getString(R.string.data), bundle));
        else {
            MangaDetailedFragment detailedFragment = new MangaDetailedFragment();
            its_Tablet(bundle, detailedFragment, R.id.asd);
        }
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
