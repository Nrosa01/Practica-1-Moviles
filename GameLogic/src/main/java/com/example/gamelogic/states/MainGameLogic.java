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
    NonogramBoard board;
    Button returnButton;
    Button checkButton;
    IFont font;
    IFont boardFont;
    IImage arrow;
    IImage search;

    public MainGameLogic(IEngine engine, String level) {
        super(engine);
        this.level = level;
        pointer = new Pointer(engine);
    }

    @Override
    public boolean init() {
        try {
            font = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 24, false);
            boardFont = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 12, false);
            arrow = graphics.newImage(engine.getAssetsPath() + "images/arrow.png");
            search = graphics.newImage(engine.getAssetsPath() + "images/search.png");

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

            checkButton = new Button(LOGIC_WIDTH - 30 - graphics.getStringWidth("Comprobar", font), 25, 30, 30, engine);
            checkButton.setImage(search);
            checkButton.setPadding(10, 10);
            checkButton.setBackgroundColor(0, 0, 0, 0);
            checkButton.setBorderSize(0);
            checkButton.setHoverColor(205, 205, 205);
            checkButton.setPressedColor(150,150,150);
            checkButton.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                   board.checkSolution();
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
            //level = NonogramGenerator.GenerateLevel(rows, cols);


            board = new NonogramBoard(engine, level, LOGIC_WIDTH - 20, 2, boardFont);
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
        graphics.drawText("Comprobar", LOGIC_WIDTH - graphics.getStringWidth("Comprobar", font) - 10, 33, font);
        graphics.drawText("Rendirse", 45, 33, font);

        returnButton.render();
        checkButton.render();

        board.render();
        pointer.render();
    }

    @Override
    public void handleInput(List<InputEvent> events) {
        for (InputEvent inputEvent : events) {
            int proccesedX = graphics.windowsXPositionToLogicXPosition(inputEvent.x);
            int proccesedY = graphics.windowsYPositionToLogicYPosition(inputEvent.y);

            board.handleInput(proccesedX, proccesedY, inputEvent.type);
            checkButton.handleInput(proccesedX, proccesedY, inputEvent.type);
            returnButton.handleInput(proccesedX, proccesedY, inputEvent.type);
            pointer.handleInput(proccesedX, proccesedY, inputEvent.type);
        }
    }
}