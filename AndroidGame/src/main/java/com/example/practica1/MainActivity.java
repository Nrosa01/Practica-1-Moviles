package com.example.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.example.androidengine.AEngine;
import com.example.engine.DataState;
import com.example.gamelogic.states.MainGameLogic;
import com.example.gamelogic.states.StartMenuLogic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    private AEngine androidEngine;
    private AssetManager assetManager;

    //EJER 2
    String filename = "file.ser";

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

        //EXAMEN EJER 2:
        // DESERIALIZACION =======================================================

        DataState object1 = null;
        // Deserialization
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            // Method for deserialization of object
            object1 = (DataState)in.readObject();
            in.close();
            file.close();
            System.out.println("Object has been deserialized ");
        } catch(Exception ex) {
            System.out.println("Exception is caught");
        }

        androidEngine.getState().setDataState(object1);
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

    //EXAMEN EJER 2=============================================================0
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        androidEngine.getState().SaveData();
        DataState object = androidEngine.getState().getDataStateInstance();

        // Serialization
        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename) ;
            ObjectOutputStream out = new ObjectOutputStream(file) ;
            // Method for serialization of object
            out.writeObject(object) ;
            out.close() ;
            file.close() ;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}