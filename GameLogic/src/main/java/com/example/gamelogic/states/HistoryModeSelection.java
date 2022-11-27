package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IImage;
import com.example.engine.InputEvent;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.IInteractableCallback;
import com.example.gamelogic.entities.WorldCard;

import java.util.List;

public class HistoryModeSelection extends AbstractState {
    WorldCard forest;
    WorldCard sea;
    WorldCard city;
    WorldCard animal;
    IFont textFont;
    IImage cardHolder;
    IImage tape;

    protected HistoryModeSelection(IEngine engine) {
        super(engine);
    }

    @Override
    public boolean init() {
        IImage forestImg = graphics.newImage(engine.getAssetsPath() + "images/forest.png");
        IImage seaImg = graphics.newImage(engine.getAssetsPath() + "images/beach.png");
        IImage cityImg = graphics.newImage(engine.getAssetsPath() + "images/city.png");
        IImage animalImg = graphics.newImage(engine.getAssetsPath() + "images/animals.png");
        cardHolder = graphics.newImage(engine.getAssetsPath() + "images/worldCard.png");
        tape = graphics.newImage(engine.getAssetsPath() + "images/tape.png");
        textFont = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 24, true);

        int buttonSize = (int) (LOGIC_WIDTH / 2.5);
        int gapSize = buttonSize / 8;
        int margin = (int) (LOGIC_WIDTH / 3.5);
        int heightStep = buttonSize + 25;
        int yStart = 250;

        int i = 0;
        int j = 0;
        this.forest =  new WorldCard(engine, margin + i * (gapSize + buttonSize / 2) + i * (buttonSize / 2), yStart + (heightStep * j), buttonSize, buttonSize,
                "0/20", "Bosque", cardHolder, forestImg, tape,textFont);

        i = 1;
        j = 0;
        this.sea =   new WorldCard(engine, margin + i * (gapSize + buttonSize / 2) + i * (buttonSize / 2), yStart + (heightStep * j), buttonSize, buttonSize,
                "0/20", "Mar", cardHolder, seaImg, tape,textFont);

        i = 0;
        j = 1;
        this.city =  new WorldCard(engine, margin + i * (gapSize + buttonSize / 2) + i * (buttonSize / 2), yStart + (heightStep * j), buttonSize, buttonSize,
                "0/20", "Ciudad", cardHolder, cityImg, tape,textFont);

        i = 1;
        j = 1;
        this.animal =  new WorldCard(engine, margin + i * (gapSize + buttonSize / 2) + i * (buttonSize / 2), yStart + (heightStep * j), buttonSize, buttonSize,
                "0/20", "Animales", cardHolder, animalImg, tape,textFont);

        addEntity(this.forest);
        addEntity(this.sea);
        addEntity(this.city);
        addEntity(this.animal);
        return true;
    }
}
