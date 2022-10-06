package com.example.engine;

public interface IEngine {
    IGraphics getGraphics();
    IAudio getAudio();
    IState getState();
}
