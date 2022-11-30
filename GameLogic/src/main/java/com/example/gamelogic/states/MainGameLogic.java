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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainGameLogic extends AbstractState {

    String level;
    NonogramBoard board;
    Button returnButton;
    Button checkButton;
    Button winReturnButton;
    IFont font;
    IFont boardFont;
    IFont congratsFont;
    IImage arrow;
    IImage search;
    boolean gameWin = false;
    boolean random = true;

    public MainGameLogic(IEngine engine, String level) {
        super(engine);
        this.level = level;
    }

    public MainGameLogic(IEngine engine, String level, boolean random) {
        super(engine);
        this.level = level;
        this.random = random;
    }

    @Override
    public boolean init() {
        try {
            font = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 24, false);
            boardFont = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 18, true);
            congratsFont = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 36, true);
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
            checkButton.setPressedColor(150, 150, 150);
            checkButton.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    board.checkSolution();
                }
            });

            winReturnButton = new Button(LOGIC_WIDTH / 2, LOGIC_HEIGHT - 50, 100, 50, engine);
            winReturnButton.setText("Volver", font);
            winReturnButton.setBackgroundColor(0, 0, 0, 0);
            winReturnButton.setBorderSize(0);
            winReturnButton.setHoverColor(205, 205, 205);
            winReturnButton.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    try {
                        engine.setState(new SelectLevelLogic(engine));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            int[][] level = loadLevel();
            if (level == null)
                return false;

            board = new NonogramBoard(engine, level, LOGIC_WIDTH - 20, 2, boardFont);
            board.setPosX(LOGIC_WIDTH / 2);
            board.setPosY(LOGIC_HEIGHT / 2);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private int[][] loadLevel() {
        if (random) {
            int rows = Integer.parseInt(level.split("x")[0]);
            int cols = Integer.parseInt(level.split("x")[1]);
            return NonogramGenerator.GenerateLevel(rows, cols);
        } else {
            InputStream is = engine.openFile(level);
            if (is == null)
                return null;

            // Read width and height
            try {
                String file = "";
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.endsWith(" "))
                        line += " ";
                    file += line;
                }

                String[] fileDivied = file.split(" ");
                int width = Integer.parseInt(fileDivied[0]);
                int height = Integer.parseInt(fileDivied[1]);

                int[][] level = new int[width][height];
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        level[i][j] = Integer.parseInt(fileDivied[i * height + j + 2]);
                    }
                }

                br.close();
                is.close();
                return level;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        board.update(deltaTime);

        if (!board.getIsWin()) {
            returnButton.update(deltaTime);
        } else
            winReturnButton.update(deltaTime);
    }

    @Override
    public void render() {
        board.render();

        graphics.setColor(0, 0, 0);
        if (!board.getIsWin()) {
            graphics.drawText("Comprobar", LOGIC_WIDTH - graphics.getStringWidth("Comprobar", font) - 10, 33, font);
            graphics.drawText("Rendirse", 45, 33, font);

            returnButton.render();
            checkButton.render();
        } else {
            graphics.drawTextCentered("¡Enhorabuena!", LOGIC_WIDTH / 2, 50, congratsFont);
            winReturnButton.render();
        }
    }

    @Override
    public void handleInput(List<InputEvent> events) {
        for (InputEvent inputEvent : events) {
            int proccesedX = graphics.windowsXPositionToLogicXPosition(inputEvent.x);
            int proccesedY = graphics.windowsYPositionToLogicYPosition(inputEvent.y);

            board.handleInput(proccesedX, proccesedY, inputEvent.type);

            if (!board.getIsWin()) {
                checkButton.handleInput(proccesedX, proccesedY, inputEvent.type);

                returnButton.handleInput(proccesedX, proccesedY, inputEvent.type);
            } else
                winReturnButton.handleInput(proccesedX, proccesedY, inputEvent.type);

        }
    }
}