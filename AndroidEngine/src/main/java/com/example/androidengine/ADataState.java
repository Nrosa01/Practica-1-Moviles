package com.example.androidengine;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ADataState implements Serializable {

    float musicDuration;

    Integer nameOfState;

    Map<String, Object> simpleData;
    Map<String, Object[]> arrayData;
    public ADataState(){
        simpleData = new HashMap<>();
        arrayData = new HashMap<>();
    }
    public void addStateName(int numState){
        nameOfState = numState;
    }

    public <T> void addSimpleData(String key, T variable){
        simpleData.put(key, variable);
    }

    public <T> void addArrayData(String key, T[] array, int dim, T[] dimSize){
        Object[] data = array;
        arrayData.put(key, data);
        arrayData.put(key+"sizeOfDims", dimSize);
        simpleData.put(key+"dimensiones", dim);
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
}
