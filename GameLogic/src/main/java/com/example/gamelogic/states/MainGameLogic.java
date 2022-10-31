package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IState;
import com.example.engine.InputEvent;
import com.example.gamelogic.entities.Board;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.Entity;
import com.example.gamelogic.entities.IInteractableCallback;
import com.example.gamelogic.entities.NonogramBoard;
import com.example.gamelogic.entities.Pointer;
import com.example.gamelogic.levels.NonogramGenerator;

import java.util.List;

public class MainGameLogic extends AbstractState {

    String level;
    Pointer pointer;
    Board board;
    Button returnButton;
    IFont font;
    IImage arrow;

    public MainGameLogic(IEngine engine, String level) {
        super(engine);
        this.level = level;
        pointer = new Pointer(engine);
    }

    @Override
    public boolean init() {
        try {
            font = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 24, false);
            arrow = graphics.newImage(engine.getAssetsPath() + "images/arrow.png");

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
                        engine.setState(new SelectLevelLogic(engine));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            int rows = Integer.parseInt(level.split("x")[0]);
            int cols = Integer.parseInt(level.split("x")[1]);

            int[][] level = new int[][]
                    {
                            {1, 0, 1, 1},
                            {0, 1, 0, 1},
                            {1, 0, 1, 0},
                            {1, 1, 0, 0}
                    };
            level = NonogramGenerator.GenerateLevel(rows, cols);


            board = new NonogramBoard(engine, level, LOGIC_WIDTH - 20, 2, font);
            board.setPosX(LOGIC_WIDTH / 2);
            board.setPosY(LOGIC_HEIGHT / 2);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(double deltaTime) {
        board.update(deltaTime);
        returnButton.update(deltaTime);
        pointer.update(deltaTime);
    }

    @Override
    public void render() {
        graphics.drawText("Rendirse", 45, 35, font);
        returnButton.render();

        board.render();
        pointer.render();
    }

    @Override
    public void handleInput(List<InputEvent> events) {
        for (InputEvent inputEvent : events) {
            int proccesedX = graphics.windowsXPositionToLogicXPosition(inputEvent.x);
            int proccesedY = graphics.windowsYPositionToLogicYPosition(inputEvent.y);

            board.handleInput(proccesedX, proccesedY, inputEvent.type);
            returnButton.handleInput(proccesedX, proccesedY, inputEvent.type);
            pointer.handleInput(proccesedX, proccesedY, inputEvent.type);
        }
    }
}