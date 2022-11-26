package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.SurfaceView;

import com.example.androidengine.AEngine;
import com.example.gamelogic.states.StartMenuLogic;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    private AEngine androidEngine;
    private AssetManager assetManager;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        assetManager = getAssets();

        SurfaceView view = (SurfaceView) findViewById(R.id.surfaceView);
        this.androidEngine = new AEngine(view, assetManager);

        //anuncios--------------
        /*MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });*/

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //----------------------
        //bloquea la orientacion del movil a vertical
        StartMenuLogic menuLogic = new StartMenuLogic(this.androidEngine);

        try {
            this.androidEngine.setState(menuLogic);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.androidEngine.resume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.androidEngine.resume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.androidEngine.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.androidEngine.pause();
    }

}