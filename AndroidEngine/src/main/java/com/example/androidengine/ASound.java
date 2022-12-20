package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;

import com.example.engine.ISound;

import java.io.IOException;

public class ASound implements ISound {

    SoundPool soundPool;
    //mi id dentro de soundPool
    int id;
    //guardo el volumen para poder saberlo mas tarde
    float volume;


    public ASound(int id,SoundPool soundPool){
        this.soundPool = soundPool;
        this.id = id;
        volume = 1;
    }

    @Override
    public void play(){ soundPool.play(id, volume, volume, 0, 0, 1);}
    @Override
    public void stop(){soundPool.stop(id);}

    //no he encontrado forma de saber si un sonido esta sonando con soundPool
    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void setVolume(float volume) {
        //control de que el valor del volumen entra dentro del rango válido
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        soundPool.setVolume(id,volume,volume);
        this.volume = volume;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public int getPos() {
        return 0;
    }

    @Override
    public void moveMusic(int pos) {

    }

}
