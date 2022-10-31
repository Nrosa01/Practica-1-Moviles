package com.example.gamelogic.entities;

import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IInput;

public abstract class Entity implements IInputHandler{
    protected int width = 0, height = 0, posX = 0, posY = 0;
    IEngine engine;
    protected IGraphics graphics;
    protected IAudio audio;

    public Entity(IEngine engine) {
        this.engine = engine;
        graphics = engine.getGraphics();
        audio = engine.getAudio();
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY =  posY;
    }


    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public abstract void update(double deltaTime);

    public abstract void render();

    @Override
    public void handleInput(int x, int y, IInput.InputTouchType touchType)
    {
        if(touchType == IInput.InputTouchType.TOUCH_MOVE)
            this.OnPointerMove(x,y);

        else if(touchType == IInput.InputTouchType.TOUCH_DOWN)
            this.OnPointerDown(x,y);

        else if(touchType == IInput.InputTouchType.TOUCH_UP)
            this.OnPointerUp(x,y);
    }

    public abstract void OnPointerDown(int x, int y);
    public abstract void OnPointerUp(int x, int y);
    public abstract void OnPointerMove(int x, int y);
}
