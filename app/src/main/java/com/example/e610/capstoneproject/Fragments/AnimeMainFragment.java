package com.example.e610.capstoneproject.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e610.capstoneproject.Adapters.AnimeAdapter;
import com.example.e610.capstoneproject.Activitys.DetailedActivity;
import com.example.e610.capstoneproject.Models.Anime.Datum;
import com.example.e610.capstoneproject.Models.Anime.ExampleAnime;
import com.example.e610.capstoneproject.Utils.FetchData;
import com.example.e610.capstoneproject.Utils.NetworkResponse;
import com.example.e610.capstoneproject.Utils.NetworkState;
import com.example.e610.capstoneproject.R;
import com.google.gson.Gson;


public class AnimeMainFragment extends Fragment implements NetworkResponse, AnimeAdapter.RecyclerViewClickListener {

    RecyclerView recyclerView;
    ExampleAnime exampleAnime;
    String url;
    private boolean isTablet;
    String userInfo="";
    Bundle bundleUser=new Bundle();
    public AnimeMainFragment() {
        // Required empty public constructor
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_pager_fragment, container, false);

        bundleUser=getArguments();
        userInfo=bundleUser.getString(getString(R.string.user_info));

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
            Toast.makeText(getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        isTablet = getResources().getBoolean(R.bool.isTablet);


        TextView textView=(TextView)view.findViewById(R.id.main_title);
        if (url.equals(getString(R.string.top_airing_anime_link))) {
            String s=getString(R.string.top_airing) + getString(R.string.space) + getString(R.string.anime_);
            textView.setText(s);
        }
        else if (url.equals(getString(R.string.most_popular_anime_link))) {
            String s=getString(R.string.most_popular) + getString(R.string.space) + getString(R.string.anime_);
            textView.setText(s);
        }
        else if (url.equals(getString(R.string.top_upcoming_anime_link))) {
            String s=getString(R.string.top_upcoming) + getString(R.string.space) + getString(R.string.anime_);
            textView.setText(s);
        }
        else if (url.equals(getString(R.string.highest_rated_anime_link))) {
            String s=getString(R.string.highest_rated) + getString(R.string.space) + getString(R.string.anime_);
            textView.setText(s);
        }
        return view;
    }

    @Override
    public void OnSuccess(String JsonData) {

        Gson gson = new Gson();
        exampleAnime = gson.fromJson(JsonData, ExampleAnime.class);
        if(exampleAnime!=null) {
            progressBar.setVisibility(View.GONE);

            AnimeAdapter adapter = new AnimeAdapter(exampleAnime, getContext());
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
            AnimeDetailedFragment detailedFragment = new AnimeDetailedFragment();
            if (exampleAnime.data.size() > 0)
                CheckTabletOrNot(detailedFragment, exampleAnime.data.get(0), R.id.asd);
        }

    }

    public void CheckTabletOrNot(Fragment fragment, Datum datum, int id) {
        if (isTablet) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.data), datum);
            its_Tablet(bundle, fragment, id);
        }
    }

    @Override
    public void OnFailure(boolean Failure) {

    }

    @Override
    public void ItemClicked(View v, int position) {
        Bundle bundle = new Bundle();
        Datum anime = exampleAnime.data.get(position);
        bundle.putSerializable(getString(R.string.data), anime);
        bundle.putString(getString(R.string.type), getString(R.string.anime));
        bundle.putString(getString(R.string.user_info),userInfo);
        if (!isTablet) {
            startActivity(new Intent(getActivity(), DetailedActivity.class).putExtra(getString(R.string.data), bundle));
        } else {
            AnimeDetailedFragment animeDetailedFragment = new AnimeDetailedFragment();
            its_Tablet(bundle, animeDetailedFragment, R.id.asd);
        }
    }

    private void its_Tablet(Bundle bundle, Fragment fragment, int fragmentIDContainer) {
        fragment.setArguments(bundle);
        if (getActivity() != null)
            getActivity().getSupportFragmentManager().beginTransaction().replace(fragmentIDContainer, fragment).commit();
    }
}

