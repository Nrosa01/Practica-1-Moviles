package com.example.gamelogic;

import com.example.engine.IAudio;
import com.example.engine.IGraphics;

public abstract class Entity {
    protected int width, height, posX, posY;
    protected IGraphics graphics;
    protected IAudio audio;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public abstract void update(double deltaTime);

    public abstract void render();
}
