package com.example.gamelogic.states;

import com.example.engine.AnchorPoint;
import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IImage;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.IInteractableCallback;
import com.example.gamelogic.entities.SizedImage;
import com.example.gamelogic.entities.Text;
import com.example.gamelogic.entities.WorldCard;
import com.example.gamelogic.levels.WorldLevelType;
import com.example.gamelogic.utilities.DataToAccess;

public class WorldSelectionPageLogic extends AbstractState {
    WorldCard levels[];
    WorldCard specialWorld;
    IFont textFont;
    IImage cardHolder;
    IImage tape;
    Button returnButton;
    IImage arrow;
    Text tittle;

    public WorldSelectionPageLogic(IEngine engine) {
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

        tittle = new Text(engine, "Elige la categoría a la que quieres jugar", tittleFont, LOGIC_WIDTH / 2, (int) (LOGIC_HEIGHT * 0.1));
        if(graphics.isPortrait())
            tittle.setPosY(0);
        addEntity(tittle);

        int buttonSize = graphics.isPortrait() ? (int) (LOGIC_WIDTH / 2.5) : (int) (LOGIC_WIDTH / 5);
        int gapSize = buttonSize / 8;
        int margin = graphics.isPortrait() ? (int) (LOGIC_WIDTH / 3.5) : (int) (LOGIC_WIDTH / 6.5);
        int heightStep = buttonSize + 25;
        int yStart = 150;

        levels = new WorldCard[4];
        String texts[] = new String[]{"Bosque", "Mar", "Ciudad", "Animales"};
        IImage images[] = new IImage[]{forestImg, seaImg, cityImg, animalImg};
        final WorldLevelType levelTypes[] = new WorldLevelType[]{WorldLevelType.Forest, WorldLevelType.Sea, WorldLevelType.City, WorldLevelType.Animals};

        for (int i = 0; i < 4; i++) {
            int j = i > 1 ? 1 : 0;
            int pos = i;
            if (!graphics.isPortrait())
                j = 0;
            else
                pos %= 2;

            int buttonX = margin + pos * (gapSize + buttonSize / 2) + pos * (buttonSize / 2);
            int buttonY = yStart + (heightStep * j);
            int completedLevels = 0;
            completedLevels = DataToAccess.getInstance().getInt(levelTypes[i].toString()) ;
            levels[i] = new WorldCard(engine, buttonX, buttonY, buttonSize, buttonSize,
                    completedLevels, texts[i], cardHolder, images[i], tape, textFont);
            final int index = i;
            levels[i].setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    try {
                        WorldLevelSelectionPageLogic worldSelectionPageLogic = new WorldLevelSelectionPageLogic(engine, levelTypes[index]);
                        engine.setState(worldSelectionPageLogic);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        int j = !graphics.isPortrait() ? 1 : 2;
        float pos = graphics.isPortrait() ? 0.5f : 1.5f;

        int buttonX = (int) (margin + pos * (gapSize + buttonSize / 2) + pos * (buttonSize / 2));
        int buttonY = yStart + (heightStep * j);
        boolean isDay = engine.getLumens() > 50;
        String text = isDay ? "Dia" : "Noche";

        IImage specialImage = engine.getGraphics().newImage(engine.getAssetsPath() + (isDay ? "images/day.png" : "images/night.png"));
        IImage specialBg = engine.getGraphics().newImage(engine.getAssetsPath() + (isDay ? "images/lightGradient.png" : "images/darkGradient.png"));
        int imageHeight = specialBg.getHeight();
        SizedImage specialBgImage = new SizedImage(engine, specialBg, LOGIC_WIDTH, 0, LOGIC_WIDTH * 2, LOGIC_WIDTH * 2);
        specialBgImage.setAnchorPoint(AnchorPoint.DownLeft);
        addEntity(specialBgImage);


        final WorldLevelType specialLevel = isDay ? WorldLevelType.Day : WorldLevelType.Night;
        int completedLevels = 0;
        completedLevels = DataToAccess.getInstance().getInt(specialLevel.toString()) ;

        specialWorld = new WorldCard(engine, buttonX, buttonY, buttonSize, buttonSize, completedLevels, text, cardHolder, specialImage, tape, textFont);


        specialWorld.setCallback(new IInteractableCallback() {
            @Override
            public void onInteractionOccur() {
                try {
                    WorldLevelSelectionPageLogic worldSelectionPageLogic = new WorldLevelSelectionPageLogic(engine, specialLevel);
                    engine.setState(worldSelectionPageLogic);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        addEntity(specialWorld);

        returnButton = new Button(25, 25, 30, 30, engine);
        returnButton.setImage(arrow);
        returnButton.setPadding(10, 10);
        returnButton.setBackgroundColor(0, 0, 0, 0);
        returnButton.setBorderSize(0);
        returnButton.setHoverColor(205, 205, 205);
        returnButton.setAnchorPoint(AnchorPoint.UpperLeft);
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


        for (int i = 0; i < levels.length; i++)
            addEntity(levels[i]);

        return true;
    }
}
