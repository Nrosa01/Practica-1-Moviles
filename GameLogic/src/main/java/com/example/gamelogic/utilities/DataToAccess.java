package com.example.gamelogic.utilities;

import java.util.HashMap;
import java.util.Map;

import com.example.engine.IEngine;
import com.example.gamelogic.levels.WorldLevelType;

public class DataToAccess {

    private static DataToAccess instance;

    String[] keysOfLevelData = {"Forest", "City", "Animals", "Sea"};
    String[] keysOfPaletteData = {"ForestPalette", "CityPalette", "AnimalsPalette", "SeaPalette"};

    Map<String, Integer> levelData;
    Map<String, Boolean> paletteData;

    IEngine engine;
    private DataToAccess(Map<String, Object> dat ) {
        // The following code emulates slow initialization.
        levelData = new HashMap<>();
        paletteData = new HashMap<>();
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

    public  void changeInt(String key, int value){

        if(levelData.containsKey(key))
            levelData.put(key,Integer.class.cast(value));

    }

    public void changeBool(String key, boolean value){
        if(levelData.containsKey(key))
            levelData.put(key,Integer.class.cast(value));
    }

    public int getInt(String key){
        if(levelData.containsKey(key)){
            return levelData.get(key);
        }
        else return 0;
    }

    public boolean getBool(String key){
        if(paletteData.containsKey(key)){
            return paletteData.get(key);
        }
        else return false;
    }

    public Map<String, Integer> getMapInt() {
        return levelData;
    }

    public Map<String, Boolean> getMapBool(){
        return paletteData;
    }


   /* dogMap.put("Fido", new Dog("Fido"));

    Dog myPet = dogMap.get("Fido");*/
}
