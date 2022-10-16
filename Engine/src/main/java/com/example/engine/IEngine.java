package com.example.engine;

public interface IEngine {
    IGraphics getGraphics();
    IAudio getAudio();
    IState getState();
    String getAssetsPath();

    void render();
    void update(double elapsedTime);

    // Esto podria estar definido en una clase abstracta, pero de esta forma
    // podemos hacer el efecto de fade, que en desktop será distinto que en android
    // Ademas, lo ideal es cambiar el estado al final del bucle de juego, cambiarlo de golpe puede dar problemas
    void setState(IState state) throws Exception;
}
