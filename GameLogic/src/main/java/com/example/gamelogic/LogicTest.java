package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IState;

public class LogicTest implements IState {

    static final int LOGIC_WIDTH = 640;
    static final int LOGIC_HEIGHT = 480;

    IImage testImage;
    IFont testFont;
    // Los estados necesitan acceder al engine
    IEngine engine = null;

    public LogicTest(IEngine engine) {
        this.engine = engine;
    }

    @Override
    public boolean init() {
        try {
            testImage = engine.getGraphics().newImage(engine.getAssetsPath() + "images/eevee.png");
            //testFont = engine.getGraphics().newFont(engine.getAssetsPath() + "fonts/Antihero.ttf");

            engine.getGraphics().setLogicSize(LOGIC_WIDTH, LOGIC_HEIGHT);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        IGraphics graphics = engine.getGraphics();
        graphics.drawImage(testImage, 0,0);
        graphics.drawImage(testImage, 1400 - testImage.getWidth(),0);
        // graphics.drawImage(testImage, LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2);0
        //graphics.drawText("Eeevee", LOGIC_WIDTH / 2, 100, testFont);
    }
}
