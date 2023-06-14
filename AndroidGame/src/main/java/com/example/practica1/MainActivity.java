package com.example.practica1;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.example.androidengine.AEngine;
import com.example.gamelogic.states.StartMenuLogic;
import com.example.gamelogic.utilities.DataToAccess;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AEngine androidEngine;
    private AssetManager assetManager;

    //EJER 2=====
    private String sharedPrefFile = "com.example.android.sharedPrefs";
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assetManager = getAssets();

        //EXAMEN EJER 2=======================================================
        SharedPreferences mPreferences = getSharedPreferences();
        Map<String, Object> savedValuesMap = new HashMap<>();
        //
        this.androidEngine = new AEngine(this, assetManager, savedValuesMap);
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

    ///EJER 2 =========================================================================================
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
        //DataState?????????????????????????????????????????????????????????????????
        outState.putSerializable("escena", this.androidEngine.getDataState());

        SharedPreferences mPreferences = getSharedPreferences();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        DataToAccess data = DataToAccess.getInstance();
        Map<String, Integer> levels = data.getMapInt();
        Map<String, int[][]> matrices = data.getMapMatrix();
        //Map<String, Boolean> palettes = data.getMapBool();

        for (Map.Entry<String, Integer> entry: levels.entrySet())
            preferencesEditor.putInt(entry.getKey(), entry.getValue());

        for (Map.Entry<String, int[][]> entry: matrices.entrySet())
            //for (int i = 0; i < entry.getValue().length; i++)
            //  for (int j = 0; j < entry.getValue()[0].length; j++)
            preferencesEditor.putInt(entry.getKey(), entry.getValue());
        //COMO GUARDO UN int[][]????????????????????????????????????????????????????????''

        //preferencesEditor.putBoolean(entry.getKey(), entry.getValue());

        //preferencesEditor.clear();

        if(!preferencesEditor.commit())
            Log.i(TAG, "fallo al guardar datos");

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //NECESITO DataState??????????????????????????????????????????????????

        //ADataState state = (ADataState) savedInstanceState.getSerializable("escena");
        //IState estado = null;

        // NECESARIO ????????????????????????????????????????????????????????????????????????'
        //switch (StatesNames.values()[state.getNameState()]) {
//     case StartMenuLogic:
//         estado = new StartMenuLogic(androidEngine);
//         break;
//     case SelectThemeState:
//         estado = new SelectThemeState(androidEngine);
//         break;
//     case SelectLevelLogic:
//         estado = new SelectLevelLogic(androidEngine);
//         break;
//     case WorldLevelSelectionPageLogic:
//         int type = state.getSimpleData("type");
//         estado = new WorldLevelSelectionPageLogic(androidEngine, WorldLevelType.values()[type]);
//         break;
//     case WorldSelectionPageLogic:
//         estado = new WorldSelectionPageLogic(androidEngine);
//         break;
//     case MainGameLogic:
//         Integer[][]boardAux = state.get2DArrayData("board");

//         int row = boardAux.length;
//         int col = boardAux[0].length;

//         int[][] board = new int[row][col];

//         int lives = state.getSimpleData("lives");

//         for(int x = 0; x <row ; x++)
//             for (int y = 0; y < col; y++)
//                 board[x][y] = boardAux[x][y];

//         if(state.getSimpleData("random")){
//             String level = state.getSimpleData("level");
//             Integer[][]boardSolAux = state.get2DArrayData("boardSolution");
//             int[][] boardSol = new int[row][col];
//             for(int x = 0; x <row ; x++)
//                 for (int y = 0; y < col; y++)
//                     boardSol[x][y] = boardSolAux[x][y];
//             estado = new MainGameLogic(androidEngine,level,board , boardSol,lives);

//         }
//         else {
//             WorldLevelType worldType = WorldLevelType.values()[(int)state.getSimpleData("type")];
//             int numLevel = (int) state.getSimpleData("numLevel");
//             estado = new MainGameLogic(androidEngine,numLevel,worldType , board,lives);
//         }
        // default:

        //   break;

        //}
        try {
            //androidEngine.setState(estado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}