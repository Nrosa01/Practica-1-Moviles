package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IState;
import com.example.engine.InputEvent;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.Entity;
import com.example.gamelogic.entities.IInteractableCallback;
import com.example.gamelogic.entities.Pointer;

import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectLevelLogic extends AbstractState {
    IFont font;
    IFont fontBold;
    IImage arrow;

    Button returnButton;
    Pointer pointer;
    int rows = 2, cols = 3;
    String[][] texts = {{"4x4", "5x5", "5x10"}, {"8x8", "10x10", "10x15"}};
    List<Entity> entities;

    public SelectLevelLogic(IEngine engine) {
        super(engine);

        if (!engine.supportsTouch())
            pointer = new Pointer(engine);
        entities = new ArrayList<>();
    }

    @Override
    public boolean init() {
        try {
            fontBold = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 24, true);
            font = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 24, false);
            arrow = graphics.newImage(engine.getAssetsPath() + "images/arrow.png");
            int buttonSize = LOGIC_WIDTH / 5;
            int gapSize = buttonSize / 2;

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    Button button = new Button((gapSize + buttonSize) * (col + 1) - gapSize, 200 + (120 * (row + 1)), buttonSize, buttonSize, engine);
                    button.setText(texts[row][col], fontBold);
                    final int finalRow = row;
                    final int finalCol = col;
                    button.setCallback(new IInteractableCallback() {
                        @Override
                        public void onInteractionOccur() {
                            try {
                                engine.setState(new MainGameLogic(engine, texts[finalRow][finalCol]));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });


                    entities.add(button);
                }
            }

            returnButton = new Button(25, 25, 30, 30, engine);
            returnButton.setImage(arrow);
            returnButton.setPadding(10, 10);
            returnButton.setBackgroundColor(0, 0, 0, 0);
            returnButton.setBorderSize(0);
            returnButton.setHoverColor(205, 205, 205);
            returnButton.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    try {
                        engine.setState(new StartMenuLogic(engine));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            entities.add(returnButton);
            if (pointer != null)
                entities.add(pointer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(double deltaTime) {
        for (Entity entity : entities)
            entity.update(deltaTime);
    }

    @Override
    public void render() {
        graphics.setScale(0.05, 0.05);
        graphics.drawImage(arrow, 25, 25);
        graphics.setScale(1, 1);
        graphics.drawText("Volver", 45, 35, font);
        graphics.drawTextCentered("Selecciona el tamaño del puzzle", LOGIC_WIDTH / 2, 200, font);

        for (Entity entity : entities)
            entity.render();
    }

    @Override
    public void handleInput(List<InputEvent> events) {
        for (InputEvent inputEvent : events) {
            int proccesedX = graphics.windowsXPositionToLogicXPosition(inputEvent.x);
            int proccesedY = graphics.windowsYPositionToLogicYPosition(inputEvent.y);

            for (Entity entity : entities)
                entity.handleInput(proccesedX, proccesedY, inputEvent.type);
        }
    }
}
