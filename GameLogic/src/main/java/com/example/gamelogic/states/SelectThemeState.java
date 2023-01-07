package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IImage;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.IInteractableCallback;
import com.example.gamelogic.entities.Text;
import com.example.gamelogic.utilities.Color;
import com.example.gamelogic.utilities.DataToAccess;

import java.util.Map;


public class SelectThemeState extends AbstractState{
    IFont mainFont;
    IImage arrow;

    Button returnButton;
    Button RightCornerButton;

    static int num_clicks = 0;


    private int rows = 2;
    private int cols = 3;

    public SelectThemeState(IEngine engine) {
        super(engine);
    }

    @Override
    public boolean init() {
        try{
            engine.enableBanner(true);
            Map<String, Boolean> mapa = DataToAccess.getInstance().getMapBool();

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
                        engine.setState(startMenu);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            addEntity(returnButton);

            // PULSAR ESQUINA INFERIOR
            int width = 200;
            int height = 100;
            RightCornerButton = new Button(LOGIC_WIDTH - width/2, LOGIC_HEIGHT - height / 8, width, height, engine);
            //RightCornerButton.setText("Esquina", secondaryFont);
            RightCornerButton.setBackgroundColor(0, 0, 0, 0);
            RightCornerButton.setBorderSize(0);
            RightCornerButton.setPressedColorColor(0,0,0,0);
            //RightCornerButton.setHoverColor(200, 200, 200);
            //RightCornerButton.setHoverColor(0, 0, 0, 0);
            RightCornerButton.setSoundWillPlay(false);
            RightCornerButton.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    try {
                        num_clicks += 1;
                        if(num_clicks == 3){
                            num_clicks = 0;
                            currentColor += 1;
                            if (currentColor == NUM_THEMES)
                                currentColor = 0;

                            DataToAccess.getInstance().setInt("CurrentColor", currentColor);
                            backgroundColor = themes[currentColor][0];
                            defaultColor = themes[currentColor][1];
                            freeColor = themes[currentColor][2];
                            figureColor = themes[currentColor][3];
                            graphics.setClearColor(themes[currentColor][0].r, themes[currentColor][0].g, themes[currentColor][0].b);
                        }

                        //WorldLevelType type = WorldLevelType.values()[t];
                        //int unlockedLevels = DataToAccess.getInstance().getInt(type.toString()) - 1; // queremos [0, unlockedLevels-1]

                        //MainGameLogic logic = new MainGameLogic(engine,unlockedLevels,type); //numLevel
                        //engine.setState(logic);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            addEntity(RightCornerButton);

            //BOTONES DE SELECCION
            int buttonSize = graphics.isPortrait() ? LOGIC_WIDTH / 5 : LOGIC_WIDTH / 8;
            int gapSize = buttonSize / 2;
            IImage unlockedImg = graphics.newImage(engine.getAssetsPath() + "images/unlock.png");
            IImage lockedImg = graphics.newImage(engine.getAssetsPath() + "images/lock.png");

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols && row == 0 || col < cols - 1 && row == 1; col++) {
                    int buttonY = graphics.isPortrait() ? 200 + (120 * (row + 1)) : 75 + (120 * (row + 1));
                    int buttonX = graphics.isPortrait() ? (gapSize + buttonSize) * (col + 1) - gapSize : 110 + (gapSize + buttonSize) * (col + 1) - gapSize;
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
                                    currentColor = finalCol;
                                    //engine.setState(new MainGameLogic(engine, texts[finalRow][finalCol]));
                                    DataToAccess.getInstance().setInt("CurrentColor", finalCol);
                                    backgroundColor = themes[finalCol][0];
                                    defaultColor = themes[finalCol][1];
                                    freeColor = themes[finalCol][2];
                                    figureColor = themes[finalCol][3];
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
