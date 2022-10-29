package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IInput;
import com.example.engine.IState;
import com.example.engine.InputEvent;

import java.util.List;

public class UITest implements IState {
    static final int LOGIC_WIDTH = 400;
    static final int LOGIC_HEIGHT = 600;

    IFont testFont;
    Button button;

    IEngine engine = null;
    IGraphics graphics = null;
    float circlePosX = 120;
    float circlePosY = 120;
    float radius = 12;

    public UITest(IEngine engine) {
        this.engine = engine;
        graphics = engine.getGraphics();
    }

    @Override
    public boolean init() {
        try {
            graphics.setLogicSize(LOGIC_WIDTH, LOGIC_HEIGHT);

            testFont = engine.getGraphics().newFont(engine.getAssetsPath() + "fonts/Antihero.ttf", 24, false);
            button = new Button(LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2, 100, 35, "Jugar", testFont,  engine);
            button.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    System.out.println("Callback");
                    try {
                        engine.setState(new LogicTest(engine));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(double deltaTime) {
        button.update((float)deltaTime);
    }

    @Override
    public void render() {
        graphics.setColor(255,255,255);
        graphics.drawTextCentered("Nonogramas", LOGIC_WIDTH / 2, 90, testFont);

        button.render();
        graphics.setColor(0,0,0, 120);
        graphics.drawCircle((int)circlePosX, (int)circlePosY, (int)radius);
    }

    @Override
    public void handleInput(List<InputEvent> events) {
        for (InputEvent inputEvent: events)
        {
            int proccesedX =  graphics.windowsXPositionToLogicXPosition(inputEvent.x);
            int proccesedY =  graphics.windowsYPositionToLogicYPosition(inputEvent.y);

            button.HandleInput(proccesedX, proccesedY, inputEvent.type);
            if(inputEvent.type == IInput.InputTouchType.TOUCH_MOVE)
            {
                circlePosX = proccesedX;
                circlePosY = proccesedY;
            }
        }
    }
}
