package com.example.practica1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.example.androidengine.AEngine;
import com.example.gamelogic.states.StartMenuLogic;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity  {

    private AEngine androidEngine;
    private AssetManager assetManager;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //----------------------------------------------------------------------
        createNotificationChannel();

        // CREAR INTENT: Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MainActivity.class); //triple
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        //CREAR NOTIFICACION:
        //var CHANNEL_ID = "My Notification";
        builder = new NotificationCompat.Builder(this,  "My Notification") //CHANNEL_ID
                .setSmallIcon(R.drawable.bell) //R.drawable.notification_icon
                .setContentTitle("Notificación del Nonograma")
                .setContentText("Eres un grande!")
                // Si quieres que ocupe mas de 1 linea la notificacion
                //.setStyle(NotificationCompat.BigTextStyle()
                  //      .bigText("Much longer text that cannot fit one line..."))
                //-----------------
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        SurfaceView view = (SurfaceView) findViewById(R.id.surfaceView);

        this.androidEngine = new AEngine(this,view, assetManager, mAdView,mInterstitialAd);


        //MOSTRAR NOTIFICACION
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build()); //notificationId

        //bloquea la orientacion del movil a vertical
        StartMenuLogic menuLogic = new StartMenuLogic(this.androidEngine);

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
    //CREAR CANAL ==> si quieres meter sonidos y mierdas:
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Notification";//getString(R.string.channel_name);
            String description = "My Notification Description";//getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("My Notification", name, importance); //CHANNEL_ID
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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

