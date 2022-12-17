package com.example.practica1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.example.androidengine.AEngine;
import com.example.engine.IState;
import com.example.gamelogic.states.GetDataState;
import com.example.gamelogic.states.StartMenuLogic;

import com.example.gamelogic.utilities.DataToAccess;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity  {

    private AEngine androidEngine;
    private AssetManager assetManager;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile,
                MODE_PRIVATE);
        Map<String, Object> savedValuesMap  = (Map<String, Object>) mPreferences.getAll();



        Intent intentParam = getIntent();
        if (intentParam != null){
            int a = intentParam.getIntExtra("someKey", 0);
            Log.i("Cosa ","Intent cargado onCreate: "+a);
        }

        cargarBanner();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        //cargarVideoAnuncio();

        assetManager = getAssets();


        //anuncios--------------
        /*MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });*/
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        //Log.i("Cosa ","BBBBBBBBBB");
        Constraints constraints = new Constraints.Builder().build();
        PeriodicWorkRequest build = new PeriodicWorkRequest.Builder(NotificationWork.class, 15, TimeUnit.MINUTES)
                .addTag("TAG")
                .setConstraints(constraints)
                .build();

        WorkManager instance = WorkManager.getInstance();
        if (instance != null) {
            instance.enqueueUniquePeriodicWork("TAG", ExistingPeriodicWorkPolicy.REPLACE, build);
        }


        //----------------------------------------------------------------------------------
        SurfaceView view = (SurfaceView) findViewById(R.id.surfaceView);

        this.androidEngine = new AEngine(this,view, assetManager, mAdView,mInterstitialAd,savedValuesMap);




        //bloquea la orientacion del movil a vertical
        StartMenuLogic menuLogic = new StartMenuLogic(this.androidEngine);
        //GetDataState dataState = new GetDataState(this.androidEngine);
        try {
            this.androidEngine.setState(menuLogic);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.androidEngine.resume();
    }

    private void cargarBanner(){
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
        Log.i("Cosa ","DESTROY ");
    }

    private String sharedPrefFile = "com.example.android.hellosharedprefs";

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences mPreferences;


        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
// MODE_WORLD_WRITEABLE and MODE_WORLD_READABLE están deprecados desde API 17


        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        DataToAccess data = DataToAccess.getInstance();
        Map<String, Integer> levels = data.getMapInt();
        Map<String, Boolean> palettes = data.getMapBool();
        for (Map.Entry<String, Integer> entry: levels.entrySet()             ) {
            preferencesEditor.putInt(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Boolean> entry: palettes.entrySet()             ) {
            preferencesEditor.putBoolean(entry.getKey(), entry.getValue());
        }
        preferencesEditor.commit();


        this.androidEngine.pause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putSerializable("escena", this.androidEngine.getCurrentSceneState());

        //FileOutputStream file = new FileOutputStream("completedLevels");

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
      /*  Serializable state = savedInstanceState.getSerializable("escena");
        try {
            this.androidEngine.setState((IState) state);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }





}




