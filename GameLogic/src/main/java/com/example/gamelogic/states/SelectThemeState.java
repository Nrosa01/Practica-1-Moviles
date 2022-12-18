package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IImage;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.IInteractableCallback;
import com.example.gamelogic.entities.Text;
import com.example.gamelogic.utilities.Color;


public class SelectThemeState extends AbstractState{
    IFont mainFont;
    IImage arrow;

    Button returnButton;

    private int rows = 2;
    private int cols = 3;

    public SelectThemeState(IEngine engine) {
        super(engine);
    }

    @Override
    public boolean init() {
        try{
            arrow = graphics.newImage(engine.getAssetsPath() + "images/arrow.png");

            mainFont = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 25, true);
            Text selectText = new Text(engine, "Selecciona el tema", mainFont, LOGIC_WIDTH / 2, 90);
            addEntity(selectText);

            //BOTON RETORNO
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
                        StartMenuLogic startMenu = new StartMenuLogic(engine);
                        startMenu.setColors(backgroundColor, defaultColor, freeColor, figureColor);
                        engine.setState(startMenu);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            addEntity(returnButton);

            //BOTONES DE SELECCION
            int buttonSize = graphics.isLandscape() ? LOGIC_WIDTH / 5 : LOGIC_WIDTH / 8;
            int gapSize = buttonSize / 2;
            IImage unlockedImg = graphics.newImage(engine.getAssetsPath() + "images/unlock.png");
            IImage lockedImg = graphics.newImage(engine.getAssetsPath() + "images/lock.png");

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols && row == 0 || col < cols - 1 && row == 1; col++) {
                    int buttonY = graphics.isLandscape() ? 200 + (120 * (row + 1)) : 75 + (120 * (row + 1));
                    int buttonX = graphics.isLandscape() ? (gapSize + buttonSize) * (col + 1) - gapSize : 110 + (gapSize + buttonSize) * (col + 1) - gapSize;
                    Button button = new Button(buttonX, buttonY, buttonSize, buttonSize, engine);
                    final int finalCol = col + row * (rows+1);

                    button.setBackgroundColor(themes[finalCol][0].r, themes[finalCol][0].g, themes[finalCol][0].b);


                    if (unlockedThemes[finalCol] == true) {
                        button.setImage(unlockedImg);
                        //button.setText(texts[row][col], fontBold);
                        //final int finalRow = row;

                        button.setCallback(new IInteractableCallback() {
                            @Override
                            public void onInteractionOccur() {
                                try {
                                    //engine.setState(new MainGameLogic(engine, texts[finalRow][finalCol]));
                                    setColors(themes[finalCol][0], themes[finalCol][1], themes[finalCol][2], themes[finalCol][3]);
                                    graphics.setClearColor(themes[finalCol][0].r, themes[finalCol][0].g, themes[finalCol][0].b);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        button.setImage(lockedImg);
                    }

                    addEntity(button);
                }
            }

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void render() {
        super.render();
        //graphics.drawTextCentered("HOLA JAJAJAA", LOGIC_WIDTH / 2, 90, mainFont);
    }
}
