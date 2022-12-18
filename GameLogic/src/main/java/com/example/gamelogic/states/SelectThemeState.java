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

    protected int NUM_THEMES = 3;
    private boolean[] unlockedThemes = {true, false, false}; //el primero esta desbloqueado por defecto
    protected Color[][] themes = {
            {new Color(255, 255, 255),new Color(123, 123, 123), new Color(123, 123, 255), new Color(255, 123, 123)},
            {new Color(0, 255, 0),new Color(255, 255, 0), new Color(123, 123, 255), new Color(255, 123, 123)},
            {new Color(255, 0, 0),new Color(0, 255, 255), new Color(123, 123, 255), new Color(255, 123, 123)}};

    public void DesbloquearColor(int i){
        if(i < NUM_THEMES) // 2 < 3
            unlockedThemes[i] = true;
    }

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

            for (int col = 0; col < NUM_THEMES; col++) {
                int buttonY = graphics.isLandscape() ? 320 : 195;
                int buttonX = graphics.isLandscape() ? (gapSize + buttonSize) * (col + 1) - gapSize : 110 + (gapSize + buttonSize) * (col + 1) - gapSize;
                Button button = new Button(buttonX, buttonY, buttonSize, buttonSize, engine);
                button.setBackgroundColor(themes[col][0].r, themes[col][0].g, themes[col][0].b);
                if (unlockedThemes[col] == true) {
                    button.setImage(unlockedImg);
                    //button.setText(texts[row][col], fontBold);
                    //final int finalRow = row;
                    final int finalCol = col;
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
                }

                else{
                    button.setImage(lockedImg);
                }

                addEntity(button);
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
