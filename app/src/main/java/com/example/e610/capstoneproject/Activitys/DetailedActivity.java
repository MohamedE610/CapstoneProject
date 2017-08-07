package com.example.e610.capstoneproject.Activitys;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.e610.capstoneproject.Fragments.AnimeDetailedFragment;
import com.example.e610.capstoneproject.Fragments.MangaDetailedFragment;
import com.exampleAnime.e610.capstoneproject.R;


public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Bundle bundle = getIntent().getBundleExtra(getString(R.string.data));
        String type = bundle.getString(getString(R.string.type));
        if (type.equals(getString(R.string.anime))) {
            AnimeDetailedFragment animeDetailedFragment = new AnimeDetailedFragment();
            animeDetailedFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.asd, animeDetailedFragment).commit();
        } else {
            MangaDetailedFragment mangaDetailedFragment = new MangaDetailedFragment();
            mangaDetailedFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.asd, mangaDetailedFragment).commit();
        }
    }
}
