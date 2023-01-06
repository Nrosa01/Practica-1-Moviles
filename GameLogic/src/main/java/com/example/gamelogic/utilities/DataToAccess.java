package com.example.gamelogic.utilities;

import java.util.HashMap;
import java.util.Map;

import com.example.engine.IEngine;
import com.example.gamelogic.levels.WorldLevelType;

public class DataToAccess {

    private static DataToAccess instance;

   /* String[] keysOfLevelData = {"Forest", "City", "Animals", "Sea"};
    String[] keysOfPaletteData = {"ForestPalette", "CityPalette", "AnimalsPalette", "SeaPalette"};*/

    String[] keysOfLevelData ;
    String[] keysOfPaletteData;
    Map<String, Integer> levelData;
    Map<String, Boolean> paletteData;

    IEngine engine;
    private DataToAccess(Map<String, Object> dat ) {

        int worlds = WorldLevelType.values().length;
        keysOfLevelData = new String[worlds];
        keysOfPaletteData = new String[worlds];
        for (int i = 0 ; i < worlds; i++){
            keysOfLevelData[i] = WorldLevelType.values()[i].toString();
            keysOfPaletteData[i] = WorldLevelType.values()[i].toString() + "Palette";
        }
        //keysOfPaletteData[0] = "ForestPalete";


        levelData = new HashMap<>();
        paletteData = new HashMap<>();

        //si esta guardada la key cojo la informacion y si no la inicializo a un valor predefinido
        for (String key: keysOfLevelData  ) {
            if(!dat.containsKey(key))
                levelData.put(key,1);
            else
                levelData.put(key, Integer.class.cast(dat.get(key)));

        }

        for (String key: keysOfPaletteData  ) {
            if(!dat.containsKey(key))
                paletteData.put(key,false);
            else
                paletteData.put(key, Boolean.class.cast(dat.get(key)));

        }

        // Cuando arranca:
        // ==> si no esta --> Default Value
        if(!dat.containsKey("CurrentColor")){
            levelData.put("CurrentColor", 0);
        }
        // si esta, guardamos los valores
        else{
            levelData.put("CurrentColor", Integer.class.cast(dat.get("CurrentColor")));
        }

        // ULTIMO TIPO JUGADO------------------------------------------
        if(!dat.containsKey("LastTypePlayed")){
            levelData.put("LastTypePlayed", -1); //default -1
        }
        // si esta, guardamos los valores
        else{
            Object l = dat.get("LastTypePlayed");
            levelData.put("LastTypePlayed", Integer.class.cast(l));//WorldLevelType.values()[i]);
        }
        //ULTIMO NIVEL--------------------------------------------------
        if(!dat.containsKey("LastLevelPlayed")){
            levelData.put("LastLevelPlayed", 0); //default -1
        }
        // si esta, guardamos los valores
        else{
            Object l = dat.get("LastLevelPlayed");
            levelData.put("LastLevelPlayed", Integer.class.cast(l));//WorldLevelType.values()[i]);
        }

    }

    public static DataToAccess getInstance() {

        return instance;
    }

    public static boolean Init(IEngine engine){
        if(instance == null) {
            Map<String, Object> data = engine.getData();
            instance = new DataToAccess(data);
            instance.engine = engine;

            return true;
        }
        return false;
    }

    public  void setInt(String key, int value){
        if(levelData.containsKey(key))
            levelData.put(key,Integer.class.cast(value));
    }

    public void setMaxLevel(String key, int value){

        if(levelData.containsKey(key))
            levelData.put(key, Math.max(value,levelData.get(key)));

    }

    public void setBool(String key, boolean value){
        if(paletteData.containsKey(key))
            paletteData.put(key,value); //Integer.class.cast(value)
    }

    public int getInt(String key){
        if(levelData.containsKey(key))
            return levelData.get(key);
        else return 0;
    }

    public boolean getBool(String key){
        if(paletteData.containsKey(key))
            return paletteData.get(key);
        else return false;
    }

    //metodos para guardar los datos
    public Map<String, Integer> getMapInt() {
        return levelData;
    }

    public Map<String, Boolean> getMapBool(){
        return paletteData;
    }



}
