package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.engine.IAudio;
import com.example.engine.ISound;

import org.hamcrest.core.Is;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

public class AAudio implements IAudio {

    Dictionary<String, ISound> sounds;
    Dictionary<String, MediaPlayer> music;

    MediaPlayer mplayer ;
    SoundPool soundPool;
    AssetManager assetManager;
    public AAudio(AssetManager assetManager){
        this.assetManager = assetManager;

        sounds = new Hashtable<>();
        soundPool = new SoundPool.Builder().setMaxStreams(10).build();

        this.mplayer = new MediaPlayer();
        this.mplayer.reset();
    }

  /*  public ISound newSound(){
        int soundId = -1;
        try {
            AssetFileDescriptor assetDescriptor =
                    this.assetManager.openFd(pathToSound);
            //soundId = soundPool.load(assetDescriptor,1);
        } catch (RuntimeException | IOException e ) {
            throw new RuntimeException("Couldn't load sound.");
        }
        return null;
    }
    public ISound newAudio(String pathToAudio) {
        MediaPlayer media = new MediaPlayer();
        try {
            AssetFileDescriptor afd = assetManager.openFd(pathToAudio);
            this.mplayer.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            this.mplayer.prepare();

        } catch (Exception e) {
            System.err.println("Couldn't load audio file");
            e.printStackTrace();
        }
        return null;
    }*/
    public void setLoop(boolean loop){
        mplayer.setLooping(loop);
    }

    public void play() {
        this.mplayer.start();
    }


    public void stop() {
        this.mplayer.stop();
    }

    public void release(){
        this.mplayer.release();
    }

    @Override
    public ISound newSound(String soundPath, String soundKey) {

        if(sounds.get(soundKey) != null)
            return (ASound) sounds.get(soundKey);

        int soundId = -1;
        try {
            AssetFileDescriptor assetDescriptor = this.assetManager.openFd(soundPath);
            soundId = soundPool.load(assetDescriptor,1);
        } catch (RuntimeException | IOException e ) {
            throw new RuntimeException("Couldn't load sound.");
        }

        ISound sound = new ASound(soundId,soundPool);
        sounds.put(soundKey, sound);
        return sound;
    }

    @Override
    public ISound newMusic(String soundPath, String audioKey) {
        MediaPlayer media = new MediaPlayer();
        try {
            AssetFileDescriptor afd = assetManager.openFd(soundPath);
            this.mplayer.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            this.mplayer.prepare();

        } catch (Exception e) {
            System.err.println("Couldn't load audio file");
            e.printStackTrace();
        }
        return null;
    }
}
