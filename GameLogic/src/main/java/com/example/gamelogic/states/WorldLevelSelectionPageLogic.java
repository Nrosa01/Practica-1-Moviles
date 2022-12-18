package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IImage;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.IInteractableCallback;
import com.example.gamelogic.entities.SizedImage;
import com.example.gamelogic.entities.Text;
import com.example.gamelogic.levels.WorldLevelType;
import com.example.gamelogic.utilities.Color;
import com.example.gamelogic.utilities.DataToAccess;

import javax.xml.crypto.Data;

import jdk.tools.jmod.Main;

public class WorldLevelSelectionPageLogic extends AbstractState {
    Button returnButton;
    IImage arrow;
    IImage lockedImg;
    IImage unlockedImg;
    SizedImage backgroundImg;
    Button levels[][];
    IFont tittleFont; // TODO: Crear una entidad que sea simplemente un texto
    String text;
    WorldLevelType type;
    Text tittleText;

    int unlockedLevels = 1;


    protected WorldLevelSelectionPageLogic(IEngine engine, WorldLevelType type) {
        super(engine);
        text = type.toString();
        this.type = type;
    }



    @Override
    public boolean init() {
        try {
            unlockedLevels = DataToAccess.getInstance().getInt(type.toString()) ;


            tittleFont = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 24, true);
            int textY = graphics.isPortrait() ? (LOGIC_HEIGHT / 2) - 100 :  (LOGIC_HEIGHT / 2) - 125;
            tittleText = new Text(engine, text, tittleFont, LOGIC_WIDTH / 2, textY);
            tittleText.setBackgroundColor(new Color(255,255,255,169));
            tittleText.setBackgruondSize(LOGIC_WIDTH, -1);
            lockedImg = graphics.newImage(engine.getAssetsPath() + "images/lock.png");
            unlockedImg = graphics.newImage(engine.getAssetsPath() + "images/unlock.png");

            int width = 0, height = 0;
            createBackground();
            addEntity(backgroundImg);
            addEntity(tittleText);

            int xPos;
            int yPos;

            width = (LOGIC_WIDTH - (5 * 10)) / 5;
            if(!graphics.isPortrait())
                width /= 2;
            height = width;
            int spacing = (LOGIC_WIDTH - (5 * width)) / 6;
            if(!graphics.isPortrait())
                spacing /= 4;

            levels = new Button[4][5];


            for (int i = 0; i <=  unlockedLevels/5 ; i++) {
                for (int j = 0; j < Math.min(unlockedLevels - (i * 5), 5); j++) {

                    // Calculate the x and y position of the button
                    xPos = (j * width) + ((j + 1) * spacing) + width / 2;
                    yPos = (i * height) + ((i + 1) * spacing) + (LOGIC_HEIGHT / 2);

                    if(!graphics.isPortrait())
                    {
                        xPos += LOGIC_WIDTH / 5.5;
                        yPos -= LOGIC_HEIGHT / 8 + 20;
                    }

                    // Create the button
                    levels[i][j] = new Button(xPos, yPos, width, height, engine);
                    levels[i][j].setBorderSize(8);
                    levels[i][j].setBorderColor(183, 210, 79);
                    levels[i][j].setBackgroundColor(0, 0, 0, 169);
                    levels[i][j].setPadding(5, 5);
                    levels[i][j].setImage(unlockedImg);





                    final int numLevel = i * 5 +j ;

                    levels[i][j].setCallback(new IInteractableCallback() {
                        @Override
                        public void onInteractionOccur() {
                            try {
                                MainGameLogic logic = new MainGameLogic(engine,numLevel,type,false, new IInteractableCallback() {
                                    @Override
                                    public void onInteractionOccur() {
                                        try {
                                            engine.setState(new WorldLevelSelectionPageLogic(engine, type));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                engine.setState(logic);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    addEntity(levels[i][j]);
                }
            }


            for (int i = unlockedLevels/5; i <  4; i++) {
                for (int j = Math.max(unlockedLevels - (i * 5), 0); j < 5; j++) {

                    // Calculate the x and y position of the button
                    xPos = (j * width) + ((j + 1) * spacing) + width / 2;
                    yPos = (i * height) + ((i + 1) * spacing) + (LOGIC_HEIGHT / 2);

                    if(!graphics.isPortrait())
                    {
                        xPos += LOGIC_WIDTH / 5.5;
                        yPos -= LOGIC_HEIGHT / 8 + 20;
                    }

                    // Create the button
                    levels[i][j] = new Button(xPos, yPos, width, height, engine);
                    levels[i][j].setBorderSize(8);
                    levels[i][j].setBorderColor(183, 210, 79);
                    levels[i][j].setBackgroundColor(0, 0, 0, 169);
                    levels[i][j].setPadding(5, 5);
                    levels[i][j].setImage(lockedImg);

                    addEntity(levels[i][j]);
                }
            }


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
                        engine.setState(new WorldSelectionPageLogic(engine));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            addEntity(returnButton);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void createBackground()
    {
        IImage bg = graphics.newImage("images/" + text.toLowerCase() + ".png");
        int wWidth = graphics.getWidth();
        int wHeiht = graphics.getHeight();
        int maximum = Math.min(wWidth, wHeiht);
        backgroundImg = new SizedImage(engine, bg, LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2, maximum, maximum);
    }




}
