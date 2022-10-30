package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IState;
import com.example.engine.InputEvent;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.Pointer;

import java.util.List;

public class SelectLevelLogic implements IState {
    IGraphics graphics;
    IEngine engine;

    IFont font;
    IFont fontBold;
    int LOGIC_WIDTH, LOGIC_HEIGHT;

    Pointer pointer;
    Button[][] buttons;
    String[][] texts = {{"4x4", "5x5", "5x10"}, {"8x8", "10x10", "10x15"}};

    public SelectLevelLogic(IEngine engine) {
        this.engine = engine;
        graphics = engine.getGraphics();
        LOGIC_WIDTH = graphics.getLogicWidth();
        LOGIC_HEIGHT = graphics.getLogicHeight();

        buttons = new Button[2][3];
        pointer = new Pointer(engine);
    }

    @Override
    public boolean init() {
        try {
            fontBold = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 24, true);
            font = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 24, false);
            int buttonSize = LOGIC_WIDTH / 5;
            int gapSize = buttonSize / 2;

            for (int row = 0; row < buttons.length; row++) {
                for (int col = 0; col < buttons[0].length; col++) {
                    buttons[row][col] = new Button((gapSize + buttonSize) * (col + 1) - gapSize, 200 + (120 * (row + 1)), buttonSize, buttonSize, texts[row][col], fontBold, engine);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(double deltaTime) {
        for (Button[] button : buttons)
            for (Button b : button)
                b.update(deltaTime);
    }

    @Override
    public void render() {
        graphics.drawTextCentered("Selecciona el tamaño del puzzle", LOGIC_WIDTH / 2, 200, font);

        for (Button[] button : buttons)
            for (Button b : button)
                b.render();

        pointer.render();
    }

    @Override
    public void handleInput(List<InputEvent> events) {
        for (InputEvent inputEvent : events) {
            int proccesedX = graphics.windowsXPositionToLogicXPosition(inputEvent.x);
            int proccesedY = graphics.windowsYPositionToLogicYPosition(inputEvent.y);

            pointer.handleInput(proccesedX, proccesedY, inputEvent.type);

            for (Button[] button : buttons)
                for (Button b : button)
                    b.handleInput(proccesedX, proccesedY, inputEvent.type);
        }
    }
}
