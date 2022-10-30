package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IInput;
import com.example.engine.IState;
import com.example.engine.InputEvent;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.IInteractableCallback;
import com.example.gamelogic.entities.Pointer;

import java.util.List;

public class StartMenuLogic implements IState {
    static final int LOGIC_WIDTH = 400;
    static final int LOGIC_HEIGHT = 600;

    IFont testFont;
    Button button;

    IEngine engine = null;
    IGraphics graphics = null;
    Pointer pointer;

    public StartMenuLogic(IEngine engine) {
        this.engine = engine;
        graphics = engine.getGraphics();
    }

    @Override
    public boolean init() {
        try {
            graphics.setLogicSize(LOGIC_WIDTH, LOGIC_HEIGHT);

            pointer = new Pointer(engine);
            testFont = engine.getGraphics().newFont(engine.getAssetsPath() + "fonts/Antihero.ttf", 24, false);
            button = new Button(LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2, 100, 35, engine);
            button.setText("Jugar", testFont);
            button.setBackgroundColor(0,0,0,0);
            button.setBorderSize(0);
            button.setHoverColor(200,200,200);
            button.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    try {
                        engine.setState(new SelectLevelLogic(engine));
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
        button.update((float) deltaTime);
        pointer.update((float) deltaTime);
    }

    @Override
    public void render() {
        graphics.drawTextCentered("Nonogramas", LOGIC_WIDTH / 2, 90, testFont);

        button.render();
        pointer.render();
    }

    @Override
    public void handleInput(List<InputEvent> events) {
        for (InputEvent inputEvent : events) {
            int proccesedX = graphics.windowsXPositionToLogicXPosition(inputEvent.x);
            int proccesedY = graphics.windowsYPositionToLogicYPosition(inputEvent.y);

            button.handleInput(proccesedX, proccesedY, inputEvent.type);
            pointer.handleInput(proccesedX, proccesedY, inputEvent.type);
        }
    }
}
