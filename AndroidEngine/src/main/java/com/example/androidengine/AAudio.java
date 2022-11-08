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

    //guardamos los nombres asociados a cada sonido/musica
    Dictionary<String, ISound> sounds;

    SoundPool soundPool;
    AssetManager assetManager;

    public AAudio(AssetManager assetManager){
        this.assetManager = assetManager;
        sounds = new Hashtable<>();
        soundPool = new SoundPool.Builder().setMaxStreams(10).build();
    }

    //creacion de sonidos cortos mediante soundPool
    @Override
    public ISound newSound(String soundPath, String soundKey) {
        //control de que el sonido no ha sido creado
        if(sounds.get(soundKey) != null)
            return (ASound) sounds.get(soundKey);

        int soundId = -1;
        try {
            AssetFileDescriptor assetDescriptor = this.assetManager.openFd(soundPath);
            soundId = soundPool.load(assetDescriptor,1);
        } catch (RuntimeException | IOException e ) {
            throw new RuntimeException("Couldn't load sound.");
        }
        //ASound recibe 'soundId' y soundPool con el cual se manejara el sonido desde fuera
        ISound sound = new ASound(soundId,soundPool);
        sounds.put(soundKey, sound);
        return sound;
    }

    //creacion de musica mediante mediaplayers
    @Override
    public ISound newMusic(String soundPath, String audioKey) {

        //control de que la musica no ha sido creado
        if(sounds.get(audioKey) != null)
            return (AMusic) sounds.get(audioKey);
        MediaPlayer media = new MediaPlayer();
        try {
            AssetFileDescriptor afd = assetManager.openFd(soundPath);
            media.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            media.prepare();

        } catch (Exception e) {
            System.err.println("Couldn't load audio file");
            e.printStackTrace();
        }
        //Amusic recibe su mediaPlayer con el cual se manejara la musica desde fuera
        ISound music = new AMusic(media);
        sounds.put(audioKey, music);
        return music;
    }
}
