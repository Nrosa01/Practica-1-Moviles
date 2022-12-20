package com.example.androidengine;

import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.engine.ISound;

public class AMusic implements ISound {

    MediaPlayer mediaPlayer;
    //guardo el volumen para poder saberlo mas tarde
    float volume;


    public AMusic( MediaPlayer mediaPlayer){
        this.mediaPlayer = mediaPlayer;
        volume = 1;
    }

    @Override
    public void play(){
        //si esta sonando se reinicia
        
        if (!mediaPlayer.isPlaying())
            mediaPlayer.seekTo(0);

        mediaPlayer.start();}
    @Override
    public void stop(){mediaPlayer.stop();}

    @Override
    public boolean isPlaying() {
       return  mediaPlayer.isPlaying();
    }

    @Override
    public void setVolume(float volume) {
        //control de que el valor del volumen entra dentro del rango válido
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        mediaPlayer.setVolume(volume,volume);
        this.volume = volume;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public int getPos() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void moveMusic(int pos) {
        mediaPlayer.seekTo(pos);
    }

}

