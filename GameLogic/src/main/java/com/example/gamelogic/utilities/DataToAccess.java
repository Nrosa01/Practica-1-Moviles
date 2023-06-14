package com.example.gamelogic.utilities;

import java.util.HashMap;
import java.util.Map;

import com.example.engine.IEngine;

public class DataToAccess {

    private static DataToAccess instance;

    IEngine engine;

    //
    Map<String, int[][]> matrices;
    Map<String, Integer> levelData;

    private DataToAccess(Map<String, Object> dat ) {
        //================================================================
        if(!dat.containsKey("SolvedPuzzle")){
            matrices.put("SolvedPuzzle", new int[1][1]); //default -1
        }
        // si esta, guardamos los valores
        else{
            Object l = dat.get("SolvedPuzzle");
            matrices.put("SolvedPuzzle", (int[][]) l);//WorldLevelType.values()[i]);
        }

        //====================================================================
        if(!dat.containsKey("NonogramCellStates")){
            matrices.put("NonogramCellStates", new int[1][1]); //default -1
        }
        // si esta, guardamos los valores
        else{
            Object l = dat.get("NonogramCellStates");
            matrices.put("NonogramCellStates", (int[][]) l);//WorldLevelType.values()[i]);
        }

        //====================================================================
        if(!dat.containsKey("BestContrarreloj")){
            levelData.put("BestContrarreloj", 15 * 60);
        }
        else{
            levelData.put("BestContrarreloj", Integer.class.cast(dat.get("BestContrarreloj")));
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

    public  void setInt(String key, int value) {
        if (levelData.containsKey(key))
            levelData.put(key, Integer.class.cast(value));
    }

    public int getInt(String key){
        if(levelData.containsKey(key))
            return levelData.get(key);
        else return 0;
    }

    //metodos para guardar los datos
    public Map<String, Integer> getMapInt() {
        return levelData;
    }

    // EXAMEN======================================================================
    public Map<String, int[][]> getMapMatrix(){return matrices; }

    public int[][] getMatrix(String key){
        if(matrices.containsKey(key))
            return matrices.get(key);
        return new int[1][1];
    }

    public void setMatrix(String key, int[][] value){
        if(matrices.containsKey(key))
            matrices.put(key,value); //Integer.class.cast(value)
    }

}
