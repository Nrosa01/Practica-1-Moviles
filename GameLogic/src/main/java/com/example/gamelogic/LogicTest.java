package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IState;

public class LogicTest implements IState {

    static final int LOGIC_WIDTH = 640;
    static final int LOGIC_HEIGHT = 480;

    IImage testWidth;
    IImage testHeight;
    IFont testFont;
    // Los estados necesitan acceder al engine
    IEngine engine = null;
    float t = 0;

    public LogicTest(IEngine engine) {
        this.engine = engine;
    }

    @Override
    public boolean init() {
        try {
            IGraphics graphics = engine.getGraphics();
            graphics.setLogicSize(LOGIC_WIDTH, LOGIC_HEIGHT);

            testWidth = graphics.newImage(engine.getAssetsPath() + "images/fWidth.png");
            testHeight = graphics.newImage(engine.getAssetsPath() + "images/fHeight.png");
            //testFont = engine.getGraphics().newFont(engine.getAssetsPath() + "fonts/Antihero.ttf");


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
        //graphics.drawImage(testWidth, 0, 0);
        graphics.drawImage(testWidth, LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2);
        graphics.drawImage(testHeight, LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2);

        graphics.drawImage(testHeight, 10,               LOGIC_HEIGHT / 2);
        graphics.drawImage(testHeight, LOGIC_WIDTH - 10, LOGIC_HEIGHT / 2);

        graphics.drawImage(testWidth, LOGIC_WIDTH / 2, 10);
        graphics.drawImage(testWidth, LOGIC_WIDTH / 2, LOGIC_HEIGHT - 10);
    }

    @Override
    public void handleInput() {

    }
}
