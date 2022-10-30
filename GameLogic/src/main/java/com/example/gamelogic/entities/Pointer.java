package com.example.gamelogic.entities;

import com.example.engine.IEngine;
import com.example.engine.IGraphics;

public class Pointer extends  UIElement {
    private int radius;
    int r = 123, g = 123, b = 123, a = 123;

    public Pointer(IEngine engine) {
        this(12,0 ,0 ,0 ,123, engine);
    }

    public Pointer(int radius, int r, int g, int b, int alpha, IEngine engine) {
        super(engine);
        this.radius = radius;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = alpha;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        graphics.setColor(r,g,b,a);
        graphics.drawCircle(posX, posY, radius);
    }

    @Override
    public void OnHoverEnter() {

    }

    @Override
    public void OnHoverExit() {

    }

    @Override
    public void OnTouchDown() {

    }

    @Override
    public void OnTouchUp() {

    }

    @Override
    public void OnTouchMove(int x, int y) {
        posX = x;
        posY = y;
    }
}
