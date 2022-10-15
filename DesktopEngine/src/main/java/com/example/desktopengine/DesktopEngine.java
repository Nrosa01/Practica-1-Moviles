package com.example.desktopengine;

import com.example.engine.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class DesktopEngine implements IEngine {
    DesktopGraphics graphics;
    DesktopAudio audio;
    // DesktopState state;
    JFrame mView;
    BufferStrategy bufferStrategy;
    Thread renderThread;
    IState currentState;
    boolean isRunning = true;

    public DesktopEngine(int wWidth, int wHeight, String wTittle) {
        mView = new JFrame(wTittle);

        mView.setSize(wWidth, wHeight);
        mView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mView.setIgnoreRepaint(false);

        mView.setVisible(true);

        int tries = 100;
        while (tries-- > 0) {
            try {
                this.mView.createBufferStrategy(2);
                break;
            } catch (Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (tries == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }

        this.bufferStrategy = this.mView.getBufferStrategy();
        graphics = new DesktopGraphics((Graphics2D) bufferStrategy.getDrawGraphics());
    }

    public void run() {
        isRunning = true;

        if (renderThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }
        // Si el Thread se pone en marcha
        // muy rápido, la vista podría todavía no estar inicializada.
        while (this.isRunning && this.mView.getWidth() == 0) ;
        // Espera activa. Sería más elegante al menos dormir un poco.
        long lastFrameTime = System.nanoTime();
        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;

        // Bucle de juego principal.
        while (isRunning) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            // Actualizamos
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            this.update(elapsedTime);

            // Pintamos el frame
            do {
                do {
                    Graphics graphics = this.bufferStrategy.getDrawGraphics();
                    try {
                        this.render();
                    } finally {
                        graphics.dispose(); //Elimina el contexto gráfico y libera recursos del sistema realacionado
                    }
                } while (this.bufferStrategy.contentsRestored());
                this.bufferStrategy.show();
            } while (this.bufferStrategy.contentsLost());
        }
    }

    @Override
    public IGraphics getGraphics() {
        return graphics;
    }

    @Override
    public IAudio getAudio() {
        return audio;
    }

    @Override
    public IState getState() {
        return currentState;
    }

    @Override
    public void render() {
        currentState.render();
    }

    @Override
    public void update(double deltaTime) {
        currentState.update(deltaTime);
    }

    @Override
    public void setState(IState state) {
        // Deberiamos esperar al final del bucle lógico antes de cambiar de estado
        // para evitar problemas

        this.currentState = state;
    }
}