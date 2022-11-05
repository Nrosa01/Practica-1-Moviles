package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.InputEvent;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.IInteractableCallback;

import java.util.List;

public class clasederelleno extends AbstractState {
    IFont testFont;
    Button button;
    public clasederelleno(IEngine engine) {
        super(engine);
    }

    @Override
    public boolean init() {
        testFont = graphics.newFont(engine.getAssetsPath() + "fonts/Antihero.ttf", 60, false);
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
    }

    @Override
    public void update(double deltaTime) {
        button.update((float) deltaTime);
    }

    @Override
    public void render() {


        //graphics.drawTextCentered("Nonogramas", LOGIC_WIDTH / 2, 90, testFont);
        button.render();

    }

    @Override
    public void handleInput(List<InputEvent> events) {
        for (InputEvent inputEvent : events) {
            int proccesedX = graphics.windowsXPositionToLogicXPosition(inputEvent.x);
            int proccesedY = graphics.windowsYPositionToLogicYPosition(inputEvent.y);

            button.handleInput(proccesedX, proccesedY, inputEvent.type);
            //pointer.handleInput(proccesedX, proccesedY, inputEvent.type);
        }
    }
}
