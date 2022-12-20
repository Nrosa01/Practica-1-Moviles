package com.example.engine;

public interface ISound {
    void play();
    void stop();
    boolean isPlaying();
    void setVolume(float volume);
    float getVolume();

    int getPos();

    void moveMusic(int pos);
}
