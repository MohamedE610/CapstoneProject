package com.example.e610.capstoneproject.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.e610.capstoneproject.Fragments.MainFragment;
import com.example.e610.capstoneproject.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    MainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);


        fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.m_frag, fragment).commit();

    }


}
