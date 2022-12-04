package com.example.androidengine;

import static android.content.ContentValues.TAG;

import com.example.engine.*;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AEngine implements IEngine, Runnable {

    private Canvas canvas;
    private Paint paint;
    private SurfaceView view;
    private AssetManager assetManager;

    private Thread renderThread;

    private boolean running;

    private StateManager stateManager;

    private AGraphics graphics;
    private AInput inputManager;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    private Activity activity;

    AAudio audio;

    public AEngine( Activity act, SurfaceView context, AssetManager assetManager, AdView adView, InterstitialAd mInterstitialAd) {

        //this.mInterstitialAd = mInterstitialAd;

        this.mAdView = adView;
        this.activity = act;

        this.cargarVideoAnuncio();

        enableBanner(true);
        this.paint = new Paint();
        this.paint.setColor(0xFF000000);
        this.view = context;

        this.assetManager = assetManager;

        //obtengo el alto y el alto de la zona usable de pantalla
        int sWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int sHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        this.graphics = new AGraphics(context.getHolder(),paint,assetManager,this,sWidth,sHeight);

        stateManager = new StateManager(this, 0.5f);
        inputManager = new AInput();

        audio = new AAudio(assetManager);


        final AGraphics graph = this.graphics;
        this.view.addOnLayoutChangeListener((view, i, i1, i2, i3, i4, i5, i6, i7) -> graph.setScreenSize(view.getWidth(),view.getHeight()));

        view.setOnTouchListener((view1, motionEvent) ->
                onTouchEvent(motionEvent));

       /* view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });*/
    }

    private void cargarVideoAnuncio(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this.activity,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        if(!videoAd()) setAd(mInterstitialAd);
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });
    }

    @Override
    public void enableBanner(boolean enable){
        this.activity.runOnUiThread(() -> {
            if (enable)
                mAdView.setVisibility(View.VISIBLE);
            else {
                mAdView.setVisibility(View.GONE);
                mAdView.clearAnimation();
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {

        inputManager.addEvent(event); //actualizamos nuestro registro de eventos
        return true; //se ha manejado el evento
    }

    public Canvas getCurrentCanvas(){
        return canvas;
    }

    //cambio de estado de logica
    @Override
    public void setState(IState state) throws Exception {
        //si esta en un hilo que no es el principal manda la accion a que la realice el principal

        stateManager.setState(state);

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
        while (this.running && view.getWidth() == 0) ; //this.myView.getWidth()
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
            while (!view.getHolder().getSurface().isValid()) ;
            // Pintamos el frame
            canvas = view.getHolder().lockCanvas();
            this.render();
            view.getHolder().unlockCanvasAndPost(canvas);
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

    public boolean videoAd() {
        return mInterstitialAd != null;
    }

    public void setAd(InterstitialAd mInterstitialAd) {
        this.mInterstitialAd = mInterstitialAd;
    }
    @Override
    public void showVid(){
        this.activity.runOnUiThread(() -> {
            try {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(this.activity);
                    cargarVideoAnuncio();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    @Override
    public InputStream openFile(String filename) {
        try {
            return assetManager.open(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
