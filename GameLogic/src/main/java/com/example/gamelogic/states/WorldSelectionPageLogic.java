package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IImage;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.IInteractableCallback;
import com.example.gamelogic.entities.Text;
import com.example.gamelogic.entities.WorldCard;
import com.example.gamelogic.levels.WorldLevelType;

public class WorldSelectionPageLogic extends AbstractState {
    WorldCard forest;
    WorldCard sea;
    WorldCard city;
    WorldCard animal;
    IFont textFont;
    IImage cardHolder;
    IImage tape;
    Button returnButton;
    IImage arrow;
    Text tittle;

    protected WorldSelectionPageLogic(IEngine engine) {
        super(engine);
    }

    @Override
    public boolean init() {

        engine.enableBanner(false);
        IImage forestImg = graphics.newImage(engine.getAssetsPath() + "images/forest.png");
        IImage seaImg = graphics.newImage(engine.getAssetsPath() + "images/sea.png");
        IImage cityImg = graphics.newImage(engine.getAssetsPath() + "images/city.png");
        IImage animalImg = graphics.newImage(engine.getAssetsPath() + "images/animals.png");
        cardHolder = graphics.newImage(engine.getAssetsPath() + "images/worldCard.png");
        tape = graphics.newImage(engine.getAssetsPath() + "images/tape.png");
        textFont = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 24, true);
        arrow = graphics.newImage(engine.getAssetsPath() + "images/arrow.png");

        IFont tittleFont = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Black.ttf", 20, false);

        tittle = new Text(engine, "Elige la categoría a la que quieres jugar", tittleFont, LOGIC_WIDTH / 2, (int) (LOGIC_HEIGHT * 0.2));
        addEntity(tittle);

        int buttonSize = (int) (LOGIC_WIDTH / 2.5);
        int gapSize = buttonSize / 8;
        int margin = (int) (LOGIC_WIDTH / 3.5);
        int heightStep = buttonSize + 25;
        int yStart = 250;

        int i = 0;
        int j = 0;
        this.forest =  new WorldCard(engine, margin + i * (gapSize + buttonSize / 2) + i * (buttonSize / 2), yStart + (heightStep * j), buttonSize, buttonSize,
                "0/20", "Bosque", cardHolder, forestImg, tape,textFont);
        this.forest.setCallback(new IInteractableCallback() {
            @Override
            public void onInteractionOccur() {
                try {
                    engine.setState(new WorldLevelSelectionPageLogic(engine, WorldLevelType.Forest));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        i = 1;
        j = 0;
        this.sea =   new WorldCard(engine, margin + i * (gapSize + buttonSize / 2) + i * (buttonSize / 2), yStart + (heightStep * j), buttonSize, buttonSize,
                "0/20", "Mar", cardHolder, seaImg, tape,textFont);
        this.sea.setCallback(new IInteractableCallback() {
            @Override
            public void onInteractionOccur() {
                try {
                    engine.setState(new WorldLevelSelectionPageLogic(engine, WorldLevelType.Sea));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        i = 0;
        j = 1;
        this.city =  new WorldCard(engine, margin + i * (gapSize + buttonSize / 2) + i * (buttonSize / 2), yStart + (heightStep * j), buttonSize, buttonSize,
                "0/20", "Ciudad", cardHolder, cityImg, tape,textFont);
        this.city.setCallback(new IInteractableCallback() {
            @Override
            public void onInteractionOccur() {
                try {
                    engine.setState(new WorldLevelSelectionPageLogic(engine, WorldLevelType.City));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        i = 1;
        j = 1;
        this.animal =  new WorldCard(engine, margin + i * (gapSize + buttonSize / 2) + i * (buttonSize / 2), yStart + (heightStep * j), buttonSize, buttonSize,
                "0/20", "Animales", cardHolder, animalImg, tape,textFont);
        this.animal.setCallback(new IInteractableCallback() {
            @Override
            public void onInteractionOccur() {
                try {
                    engine.setState(new WorldLevelSelectionPageLogic(engine, WorldLevelType.Animals));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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

        addEntity(returnButton);


        addEntity(this.forest);
        addEntity(this.sea);
        addEntity(this.city);
        addEntity(this.animal);
        return true;
    }
}
