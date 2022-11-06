package com.example.androidengine;

import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.engine.ISound;

public class AMusic implements ISound {

   MediaPlayer mediaPlayer;

    float volume;


    public AMusic( MediaPlayer mediaPlayer){
        this.mediaPlayer = mediaPlayer;
        volume = 1;
    }

   /* public void loadSound(String pathToSound){
        int soundId = -1;
        try {
            AssetFileDescriptor assetDescriptor =
                    this.assetManager.openFd(pathToSound);
            //soundId = soundPool.load(assetDescriptor,1);
        } catch (RuntimeException | IOException e ) {
            throw new RuntimeException("Couldn't load sound.");
        }

    }*/

    @Override
    public void play(){

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
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        mediaPlayer.setVolume(volume,volume);
        this.volume = volume;
    }

    @Override
    public float getVolume() {
        return volume;
    }


  /*  public void play(int soundId) {
        //soundPool.play(soundId, 1, 1, 0, 0, 1);
    }


    public void stop(int soundId) {
        soundPool.stop(soundId);
    }*/
}

