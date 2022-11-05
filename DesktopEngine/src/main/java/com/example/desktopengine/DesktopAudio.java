package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.ISound;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class DesktopAudio implements IAudio {

    Dictionary<String, ISound> sounds;
    Dictionary<String, ISound> music;

    public DesktopAudio() {
        sounds = new Hashtable<>();
        music = new Hashtable<>();
    }

    @Override
    public ISound newSound(String soundPath, String soundKey) {
        if(sounds.get(soundKey) != null)
            return (DesktopSound) sounds.get(soundKey);

        File audioFile = new File(soundPath);
        Clip clip = null;
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.setFramePosition(0);

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        DesktopSound desktopSound = new DesktopSound(clip);
        sounds.put(soundKey, desktopSound);
        return desktopSound;
    }

    @Override
    public ISound newMusic(String soundPath, String audioKey) {
        if(sounds.get(audioKey) != null)
            return (DesktopSound) sounds.get(audioKey);

        Clip clip = null;
        File audioFile = new File(soundPath);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        DesktopSound desktopSound = new DesktopSound(clip);
        sounds.put(audioKey, desktopSound);
        return desktopSound;
    }
}
