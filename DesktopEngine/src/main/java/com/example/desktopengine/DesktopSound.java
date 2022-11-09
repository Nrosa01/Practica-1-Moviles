package com.example.desktopengine;

import com.example.engine.ISound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;

public class DesktopSound implements ISound {
    Clip clip;

    public DesktopSound(Clip clip) {
        this.clip = clip;
    }

    @Override
    public void play() {
        if (!clip.isRunning())
            clip.setFramePosition(0);

        clip.start();
    }

    @Override
    public void stop() {
        clip.stop();
    }

    @Override
    public boolean isPlaying() {
        return clip.isRunning();
    }

    // Obtiene volumen en escala lineal entre 0 y 1
    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    // Setea el volumen en escala logaritmica entre 0 y 1
    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}
