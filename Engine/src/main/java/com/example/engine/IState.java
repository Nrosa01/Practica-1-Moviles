package com.example.engine;


import java.util.List;

public interface IState {
    boolean init();
    void update(double deltaTime);
    void render();
    void handleInput(List<InputEvent> events);
    void SaveData();

    // EXAMEN EJER 2
    DataState getDataStateInstance();
    void setDataState(DataState d);

}
