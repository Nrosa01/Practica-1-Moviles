package com.example.androidengine;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ADataState implements Serializable {

    float musicDuration;

    Integer nameOfState;

    Map<String, Object> simpleData;
    Map<String, Object[]> arrayData;
    Map<String, Object[]> arrayData2D;
    public ADataState(){
        simpleData = new HashMap<>();
        arrayData = new HashMap<>();
        arrayData2D = new HashMap<>();
    }
    public void addStateName(int numState){
        nameOfState = numState;
    }

    public <T> void addSimpleData(String key, T variable){
        simpleData.put(key, variable);
    }

   /* public <T> void addArrayData(String key, T[] array, int dim, T[] dimSize){
        Object[] data = array;
        arrayData.put(key, data);
        arrayData.put(key+"sizeOfDims", dimSize);
        simpleData.put(key+"dimensiones", dim);
    }*/
    public <T> void addArrayData(String key, T[] array){

        arrayData.put(key, array);

    }
    public <T> T getSimpleData(String key){
        return (T) simpleData.get(key);
    }
    public void flushStateData() {
        nameOfState = null;
        simpleData.clear();
        arrayData.clear();
    }

    public int getNameState() {
        return nameOfState;
    }

    public <T> T[] getArrayData(String key) {
        return (T[]) arrayData.get(key);
    }

    public <T> T[][] get2DArrayData(String key) {
        return (T[][]) arrayData2D.get(key);
    }

    public <T> void add2DArrayData(String key, T[][] array) {
        arrayData2D.put(key,array);
    }
}
