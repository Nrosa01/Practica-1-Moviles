package com.example.engine;

public interface IAudio {
    ISound newSound(String soundPath, String soundKey);
    ISound newMusic(String soundPath, String audioKey);
    void pause();
    void resume();
    boolean freeResources();
}
