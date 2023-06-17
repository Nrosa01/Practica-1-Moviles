package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.example.androidengine.AEngine;
import com.example.gamelogic.states.StartMenuLogic;

public class MainActivity extends AppCompatActivity {

    private AEngine androidEngine;
    private AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assetManager = getAssets();

        this.androidEngine = new AEngine(this, assetManager);
        //bloquea la orientacion del movil a vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(this.androidEngine);

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
    protected void onPause() {
        super.onPause();
        this.androidEngine.pause();
    }

}