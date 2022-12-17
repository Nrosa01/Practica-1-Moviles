package com.example.gamelogic.entities;

import com.example.engine.AnchorPoint;
import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.gamelogic.utilities.Color;

public class Text extends Entity{
    private String text;
    private Color backgroundColor;
    IFont font;
    private int bgWidth, bgHeight;

    public Text(IEngine engine, String text, IFont font, int x, int y) {
        super(engine);
        this.posX = x;
        this.posY = y;
        this.text = text;
        this.font = font;
        this.width = (int) (graphics.getStringWidth(text, font) * 1.3);
        this.height = (int) (graphics.getFontHeight(font) * 2.3);
        this.bgWidth = width;
        this.bgHeight = height;
        backgroundColor = new Color(0,0,0,0);
    }

    public void setBackgruondSize(int width, int height)
    {
        if(width != -1)
            this.bgWidth = width;

        if(height != -1)
            this.bgHeight = height;
    }

    public void setBackgroundColor(Color color)
    {
        this.backgroundColor = color;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        graphics.setAnchorPoint(this.anchorPoint);
        graphics.setColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        graphics.fillRectangle(posX, posY, bgWidth, bgHeight);
        graphics.setColor(0,0,0,255);
        graphics.drawTextCentered(text, posX, posY, font);
        graphics.setAnchorPoint(AnchorPoint.None);
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
