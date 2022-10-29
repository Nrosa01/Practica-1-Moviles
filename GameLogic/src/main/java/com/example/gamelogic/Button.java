package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IGraphics;

public class Button extends UIElement{
    int colorR = 255, colorG = 255, colorB = 255;
    float darker = 1;
    int alpha = 255;
    float scale = 1;

    public Button(int x, int y, int width, int height, IEngine engine)
    {
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.graphics = engine.getGraphics();
    }


    @Override
    public void update(float deltaTime) {

    }

    public void setColor(int r, int g, int b)
    {
        colorR = r;
        colorG = g;
        colorB = b;
    }

    public void setColor(int r, int g, int b, int a)
    {
        colorR = r;
        colorG = g;
        colorB = b;
        alpha = a;
    }

    @Override
    public void render() {
//        graphics.sca
        graphics.save();
        graphics.setScale(scale, scale);
        graphics.setColor((int)(colorR * darker), (int)(colorG * darker), (int)(colorB * darker), alpha);
        graphics.fillRectangle(posX, posY, (int)(width * scale), (int)(height * scale));
        graphics.restore();
    }

    @Override
    public void OnHoverEnter() {
        scale = 1.5f;
    }

    @Override
    public void OnHoverExit() {
        scale = 1;
    }

    @Override
    public void OnTouchDown() {
        darker = 0.5f;
    }

    @Override
    public void OnTouchUp() {
        darker = 1;
    }
}
