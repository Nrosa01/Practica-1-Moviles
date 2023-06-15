package com.example.practica1;


import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.example.androidengine.AEngine;
import com.example.engine.DataState;
import com.example.engine.IState;
import com.example.gamelogic.states.AbstractState;
import com.example.gamelogic.states.MainGameLogic;
import com.example.gamelogic.states.StartMenuLogic;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {

    private AEngine androidEngine;
    private AssetManager assetManager;

    //EXAMEN EJER 2
    private String sharedPrefFile = "com.example.android.sharedPrefs";
    private String key;

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

    // EJER 2 ===========================================================
    //devuelve datos encriptados
    protected SharedPreferences getSharedPreferences (){
        try {
            key = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        };
        SharedPreferences sharedPreferences = null;
        try {
            sharedPreferences = EncryptedSharedPreferences.create(
                    sharedPrefFile,
                    key,
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sharedPreferences;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("escena", this.androidEngine.getDataState());

        SharedPreferences mPreferences;

        mPreferences = getSharedPreferences();

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        if(!preferencesEditor.commit())
            Log.i(TAG, "fallo al guardar datos");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        DataState state = (DataState) savedInstanceState.getSerializable("escena");

        if(state != null){
            IState estado = null;

            Integer[][]boardAux = state.get2DArrayData("board");

            int row = boardAux.length;
            int col = boardAux[0].length;

            int[][] board = new int[row][col];

            for(int x = 0; x <row ; x++)
                for (int y = 0; y < col; y++)
                    board[x][y] = boardAux[x][y];

            String level = state.getSimpleData("level");
            Integer[][]boardSolAux = state.get2DArrayData("boardSolution");
            int[][] boardSol = new int[row][col];
            for(int x = 0; x <row ; x++)
                for (int y = 0; y < col; y++)
                    boardSol[x][y] = boardSolAux[x][y];
            estado = new MainGameLogic(androidEngine,level,board, boardSol);

            AbstractState.lastLevel = estado;
        }


        //androidEngine.setState(estado);
    }

}