package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;

import com.example.engine.ISound;

import java.io.IOException;

public class ASound implements ISound {
    SoundPool soundPool;
    AssetManager assetManager;
    public ASound(AssetManager assetManager){
        soundPool = new SoundPool.Builder().setMaxStreams(10).build();
        this.assetManager = assetManager;
    }

    public void loadSound(String pathToSound){
        int soundId = -1;
        try {
            AssetManager assetManager;
            AssetFileDescriptor assetDescriptor =
                    this.assetManager.openFd(pathToSound);
            soundId = soundPool.load(assetDescriptor,1);
        } catch (RuntimeException | IOException e ) {
            throw new RuntimeException("Couldn't load sound.");
        }

    }

    @Override
    public void play(){}
    @Override
    public void stop(){}


    public void play(int soundId) {
        soundPool.play(soundId, 1, 1, 0, 0, 1);
    }


    public void stop(int soundId) {
        soundPool.stop(soundId);
    }
}
