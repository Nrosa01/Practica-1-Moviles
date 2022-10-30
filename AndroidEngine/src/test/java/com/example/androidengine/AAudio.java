package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import com.example.engine.IAudio;
import com.example.engine.ISound;

public class AAudio implements IAudio {

    MediaPlayer mplayer ;
    AssetManager assetManager;
    public AAudio(AssetManager assetManager){
        this.assetManager = assetManager;
        this.mplayer = new MediaPlayer();
        this.mplayer.reset();
    }

    public ISound newSound(){
        return null;
    }
    public ISound newAudio(String pathToAudio) {
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
    }
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

}
