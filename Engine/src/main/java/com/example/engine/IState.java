package com.example.engine;


import java.io.Serializable;
import java.util.List;



public interface IState {
    boolean init();
    void update(double deltaTime);
    void render();
    void handleInput(List<InputEvent> events);
    void saveState();
}
