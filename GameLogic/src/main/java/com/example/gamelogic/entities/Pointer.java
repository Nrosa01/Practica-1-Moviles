package com.example.gamelogic.entities;

import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.utilities.FloatLerper;
import com.example.engine.utilities.LerpType;

public class Pointer extends Entity {
    private int radius;
    private int r = 123, g = 123, b = 123, a = 123;
    private FloatLerper lerper;

    public Pointer(IEngine engine) {
        this(12, 0, 0, 0, 123, engine);
    }

    public Pointer(int radius, int r, int g, int b, int alpha, IEngine engine) {
        super(engine);
        this.radius = radius;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = alpha;
        lerper = new FloatLerper(1, 0.75f, 0.1f, LerpType.EaseInOut);
        lerper.setReversed(true);
        lerper.setPaused(false);
    }

    @Override
    public void update(double deltaTime) {
        lerper.update(deltaTime);

        if(lerper.isFinished())
            lerper.setReversed(true);
    }

    @Override
    public void render() {
        graphics.setColor(r, g, b, a);
        graphics.drawCircle(posX, posY, (int) (radius * lerper.getValue()));
    }

    @Override
    public void OnPointerDown(int x, int y) {
        lerper.setReversed(false);
    }

    @Override
    public void OnPointerUp(int x, int y) {

    }

    @Override
    public void OnPointerMove(int x, int y) {
        posX = x;
        posY = y;
    }
}
