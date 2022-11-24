package com.example.androidengine;

import com.example.engine.*;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

public class AEngine extends SurfaceView implements IEngine, Runnable {

    private Canvas canvas;
    private Paint paint;

    private Thread renderThread;

    private boolean running;

    private StateManager stateManager;

    private AssetManager assetManager;
    private AGraphics graphics;
    private AInput inputManager;
    AAudio audio;

    public AEngine(Context context, AssetManager assetManager) {
        super(context); //constructora de SurfaceView

        this.paint = new Paint();
        this.paint.setColor(0xFF000000);

        //obtengo el alto y el alto de la zona usable de pantalla
        int sWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int sHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        this.graphics = new AGraphics(getHolder(),paint,assetManager,this,sWidth,sHeight);

        stateManager = new StateManager(this, 0.5f);
        inputManager = new AInput();
        assetManager = assetManager;

        audio = new AAudio(assetManager);
    }

    public Canvas getCurrentCanvas(){
        return canvas;
    }

    //cambio de estado de logica
    @Override
    public void setState(IState state) throws Exception {

        this.stateManager.setState(state);
        inputManager.clear();
    }

    public void resume() {
        if (!this.running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva)
            this.running = true;
            this.audio.resume();
            // Lanzamos la ejecución de nuestro método run() en un nuevo Thread.
            this.renderThread = new Thread(this);
            this.renderThread.start();
        }
    }

    public void pause() {
        if (this.running) {
            this.running = false;
            this.audio.pause();
            while (true) {
                try {
                    this.renderThread.join();
                    this.renderThread = null;
                    break;
                } catch (InterruptedException ie) {
                    ie.printStackTrace(); // Esto no debería ocurrir nunca...
                }
            }
        }
    }


    @Override
    public void run() {
        if (renderThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }

        // Si el Thread se pone en marcha
        // muy rápido, la vista podría todavía no estar inicializada.
        while (this.running && getWidth() == 0) ; //this.myView.getWidth()
        // Espera activa. Sería más elegante al menos dormir un poco.

        long lastFrameTime = System.nanoTime();

        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;

        // Bucle de juego principal.
        while (running) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            // Informe de FPS
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;

            this.handleInput();
            this.update(elapsedTime);

            //cada segundo se muestran los fps
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;

            //espera activa a que el surface este libre
            while (!getHolder().getSurface().isValid()) ;
            // Pintamos el frame
            canvas = getHolder().lockCanvas();
            this.render();
            getHolder().unlockCanvasAndPost(canvas);
        }
    }


    @Override
    public boolean supportsTouch() {
        return true;
    }

    public void render() {
        // "Borramos" el fondo.

        graphics.clear(255,255,255);
        this.stateManager.render();
        graphics.renderBordersAndroid();
    }

    @Override
    public void update(double deltaTime) {
        this.stateManager.update(deltaTime);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        inputManager.addEvent(event); //actualizamos nuestro registro de eventos
        return true; //se ha manejado el evento

    }

    @Override
    public void handleInput() {
        //comprobamos nuestro registro de eventos y actualizamos el juego
        List<InputEvent> events = inputManager.getEventList();
        inputManager.swapListBuffer();
        inputManager.clear();

        this.stateManager.handleInput(events);
    }

    public void stop()
    {
        audio.freeResources();
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
        return this.stateManager;
    }

    @Override
    public String getAssetsPath() {
        return "";
    }
}
