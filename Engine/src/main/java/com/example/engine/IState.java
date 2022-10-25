package com.example.engine;

public interface IState {
    boolean init();
    void update(double deltaTime);
    void render();
    void handleInput();
}
