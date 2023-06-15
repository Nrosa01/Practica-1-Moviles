package com.example.engine;

import java.util.Map;

public interface IEngine {
    IGraphics getGraphics();
    IAudio getAudio();
    IState getState();
    String getAssetsPath();
    boolean supportsTouch();

    void render();
    void update(double elapsedTime);
    void handleInput();

    // Esto podria estar definido en una clase abstracta, pero de esta forma
    // podemos hacer el efecto de fade, que en desktop será distinto que en android
    // Ademas, lo ideal es cambiar el estado al final del bucle de juego, cambiarlo de golpe puede dar problemas
    void setState(IState state) throws Exception;

    //EJER 2======================================

    //Map<String, Object> getData(); //No hace falta??????????????????'

    //void setNameState(int state);

    <T> void addSimpleData(String key, T variable);

    <T> void addArrayData(String key, T[] array);

    <T> void add2DArrayData(String key, T[][] array);
}
