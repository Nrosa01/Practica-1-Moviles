package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IInput;
import com.example.engine.IState;
import com.example.engine.InputEvent;

import java.util.List;

public class LogicTest implements IState {

    static final int LOGIC_WIDTH = 400;
    static final int LOGIC_HEIGHT = 600;

    IImage testWidth;
    IImage testHeight;
    IImage circle;
    IFont testFont;
    // Los estados necesitan acceder al engine
    IEngine engine = null;
    IGraphics graphics = null;
    float t = 0;
    float circlePosX = 120;
    float circlePosY = 120;

    public LogicTest(IEngine engine) {
        this.engine = engine;
        graphics = engine.getGraphics();
    }

    @Override
    public boolean init() {
        try {
            graphics.setLogicSize(LOGIC_WIDTH, LOGIC_HEIGHT);

            testWidth = graphics.newImage(engine.getAssetsPath() + "images/fWidth.png");
            testHeight = graphics.newImage(engine.getAssetsPath() + "images/fHeight.png");
            circle = graphics.newImage(engine.getAssetsPath() + "images/circle.png");
            testFont = engine.getGraphics().newFont(engine.getAssetsPath() + "fonts/Antihero.ttf", 12, false);
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
        //graphics.drawImage(testWidth, 0, 0);
        graphics.drawImage(testWidth, LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2);
        graphics.drawImage(testHeight, LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2);

        graphics.drawImage(testHeight, testHeight.getWidth() / 2,               LOGIC_HEIGHT / 2);
        graphics.drawImage(testHeight, LOGIC_WIDTH - (testHeight.getWidth() / 2), LOGIC_HEIGHT / 2);

        graphics.drawImage(testWidth, LOGIC_WIDTH / 2, testWidth.getHeight() / 2);
        graphics.drawImage(testWidth, LOGIC_WIDTH / 2, LOGIC_HEIGHT - (testWidth.getHeight() / 2));

        graphics.drawImage(circle, (int)circlePosX, (int)circlePosY);
        graphics.setColor(255,255,255);
        graphics.drawTextCentered("Eevee is a great pokemon", LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2, testFont);
        graphics.drawText("Eevee", 0,15, testFont);
    }

    @Override
    public void handleInput(List<InputEvent> events) {
        for (InputEvent inputEvent: events)
        {
            if(inputEvent.type == IInput.InputTouchType.TOUCH_MOVE)
            {
                circlePosX = graphics.windowsXPositionToLogicXPosition(inputEvent.x);
                circlePosY = graphics.windowsYPositionToLogicYPosition(inputEvent.y);
            }
        }
    }
}
