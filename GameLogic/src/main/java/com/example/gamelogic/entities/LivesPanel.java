package com.example.gamelogic.entities;

import com.example.engine.AnchorPoint;
import com.example.engine.IEngine;
import com.example.engine.IImage;

public class LivesPanel extends Entity{
    private int numLives = 3;
    private int currentLive = 0;
    private int liveSize;
    IImage fullLive;
    IImage emptyLive;
    SizedImage[] icons;

    public LivesPanel(IEngine engine, int x, int y, int width, int height, int numLifes, IImage fullLive, IImage emptyLive) {
        super(engine);
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.numLives = numLifes;
        this.fullLive = fullLive;
        this.emptyLive = emptyLive;

        // Lives size depends wheter the panel is wider or higher
        int biggerSide = width > height ? width : height;
        this.liveSize = biggerSide / numLifes;

        // Create the lives icons
        icons = new SizedImage[numLifes];

        // If the panel is horizontal, the lives will be placed horizontally starting from the left
        if (width > height) {
            int xStart = posX - (width / 2) + (liveSize / 2);
            for (int i = 0; i < numLifes; i++) {
                icons[i] = new SizedImage(engine, fullLive, xStart + (i * liveSize), posY, liveSize, liveSize);
            }
        }
        // If the panel is vertical, the lives will be placed vertically starting from the top
        else {
            int yStart = posY - (height / 2) + (liveSize / 2);
            for (int i = 0; i < numLifes; i++) {
                icons[i] = new SizedImage(engine, fullLive, posX, yStart + (i * liveSize), liveSize, liveSize);
            }
        }
    }

    @Override
    public void setAnchorPoint(AnchorPoint anchor)
    {
        super.setAnchorPoint(anchor);
        for (int i = 0; i < numLives; i++) {
            icons[i].setAnchorPoint(anchor);
        }
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        graphics.setAnchorPoint(this.anchorPoint);
        for (int i = 0; i < numLives; i++) {
            icons[i].render();
        }
        graphics.setAnchorPoint(AnchorPoint.None);

        //graphics.drawRectangle(posX, posY, width, height, 4);
    }

    @Override
    public void setAnchorPoint(AnchorPoint anchor)
    {
        super.setAnchorPoint(anchor);
        for (int i = 0; i < numLives; i++) {
            icons[i].setAnchorPoint(anchor);
        }
    }

    public int getNumLives(){
        return numLives;
    }
    public boolean isAlive()
    {
        return currentLive < icons.length;
    }

    public boolean takeLive()
    {
        if(currentLive >= icons.length)
            return false;

        icons[currentLive].setImage(emptyLive);
        currentLive++;
        return true;
    }

    public boolean restoreLive()
    {
        // Ya tiene todas las vidas
        if(currentLive == 0)
            return false;

        currentLive--;
        icons[currentLive].setImage(fullLive);

        return  true;
    }

    public void restoreLives()
    {
        currentLive = 0;

        for (int i = 0; i < numLives; i++) {
            icons[i].setImage(fullLive);
        }
    }

    @Override
    public void OnPointerDown(int x, int y) {

    }

    @Override
    public void OnPointerUp(int x, int y) {

    }

    @Override
    public void OnPointerMove(int x, int y) {

    }
}
