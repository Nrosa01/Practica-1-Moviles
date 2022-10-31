package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.SurfaceView;

import com.example.gamelogic.states.LogicTest;
import com.example.androidengine.AEngine;
import com.example.gamelogic.states.StartMenuLogic;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private AEngine androidEngine;

    private SurfaceView renderView;

    AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assetManager = getAssets();
        this.renderView = new SurfaceView(this);
        setContentView(this.renderView);

        this.androidEngine = new AEngine(this.renderView, assetManager);
        StartMenuLogic logicTest = new StartMenuLogic(this.androidEngine);
        //LogicTest logicTest = new LogicTest(this.androidEngine);

        try {
            this.androidEngine.setState(logicTest);
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