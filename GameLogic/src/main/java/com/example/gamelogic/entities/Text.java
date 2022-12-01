package com.example.gamelogic.entities;

import com.example.engine.IEngine;
import com.example.engine.IFont;

public class Text extends Entity{
    private String text;
    IFont font;

    public Text(IEngine engine, String text, IFont font, int x, int y) {
        super(engine);
        this.posX = x;
        this.posY = y;
        this.text = text;
        this.font = font;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        graphics.drawTextCentered(text, posX, posY, font);
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
