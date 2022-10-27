package com.example.desktopengine;

import com.example.engine.*;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

public class DesktopEngine implements IEngine, Runnable, MouseInputListener {
    DesktopGraphics graphics;
    DesktopAudio audio;
    DesktopInput inputManager;
    // DesktopState state;
    JFrame mView;
    Thread renderThread;
    IState currentState;

    boolean isRunning = false;

    public DesktopEngine(int wWidth, int wHeight, String wTittle) {
        mView = new JFrame(wTittle);
        graphics = new DesktopGraphics(mView, wWidth, wHeight);
        inputManager = new DesktopInput();
        mView.addMouseListener(this);
        mView.addMouseMotionListener(this);
    }

    //Métodos sincronización (parar y reiniciar aplicación)
    public void resume() {
        if (!this.isRunning) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva)
            this.isRunning = true;
            // Lanzamos la ejecución de nuestro método run() en un nuevo Thread.
            this.renderThread = new Thread(this);
            this.renderThread.start();
        }
    }

    public void pause() {
        if (this.isRunning) {
            this.isRunning = false;
            while (true) {
                try {
                    this.renderThread.join();
                    this.renderThread = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            }
        }
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
        while (this.isRunning && this.graphics.getWidth() == 0) ;
        // Espera activa. Sería más elegante al menos dormir un poco.
        long lastFrameTime = System.nanoTime();

        // Bucle de juego principal.
        while (isRunning) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            // Actualizamos
            double deltaTime = (double) nanoElapsedTime / 1.0E9;
            this.handleInput();
            this.update(deltaTime);

            do {
                this.graphics.prepareFrame();
                this.render();
                this.graphics.finishFrame();
            } while (!graphics.swapBuffer());
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
    public String getAssetsPath() {
        return "GameLogic/assets/";
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
    public void handleInput() {
        this.currentState.handleInput(inputManager.getEventList());
        inputManager.clear();
    }

    @Override
    public void setState(IState state) throws Exception {
        // Deberiamos esperar al final del bucle lógico antes de cambiar de estado
        // para evitar problemas
        if (state.init())
            this.currentState = state;
        else
            throw new Exception("State didn't init correctly");
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (graphics.isInsideLogicCanvas(mouseEvent.getX(), mouseEvent.getY()))
            inputManager.addEvent(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (graphics.isInsideLogicCanvas(mouseEvent.getX(), mouseEvent.getY()))
            inputManager.addEvent(mouseEvent);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if (graphics.isInsideLogicCanvas(mouseEvent.getX(), mouseEvent.getY()))
            inputManager.addEvent(mouseEvent);
    }
}