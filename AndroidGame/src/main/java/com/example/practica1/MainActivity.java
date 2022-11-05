package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.SurfaceView;

import com.example.gamelogic.states.LogicTest;
import com.example.androidengine.AEngine;
import com.example.gamelogic.states.StartMenuLogic;
import com.example.gamelogic.states.*;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private AEngine androidEngine;
    //private SurfaceView renderView;
    private AssetManager assetManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assetManager = getAssets();

        //this.androidEngine = new AEngine(this.renderView, assetManager);
        this.androidEngine = new AEngine(this, assetManager);

        //this.renderView = new SurfaceView(this);
        //setContentView(this.renderView);
        setContentView(this.androidEngine);
        //clasederelleno clase = new clasederelleno(this.androidEngine);
        StartMenuLogic menuLogic = new StartMenuLogic(this.androidEngine);
        //LogicTest logicTest = new LogicTest(this.androidEngine);

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