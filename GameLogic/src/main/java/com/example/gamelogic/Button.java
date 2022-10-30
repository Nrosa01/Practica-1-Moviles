package com.example.gamelogic;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.utilities.FloatLerper;
import com.example.engine.utilities.LerpType;

public class Button extends UIElement {
    int colorR = 255, colorG = 255, colorB = 255;
    int borderR = 0, borderG = 0, borderB = 0, boderSize = 10;
    float darker = 1;
    int alpha = 255;
    float scale = 1;
    FloatLerper scaleLerper;
    String buttonText;
    IFont font;
    IInteractableCallback callback;

    public Button(int x, int y, int width, int height, String buttonText, IFont buttonFont, IEngine engine) {
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.graphics = engine.getGraphics();
        this.buttonText = buttonText;
        this.font = buttonFont;
        scaleLerper = new FloatLerper(1, 1.2f, 0.1f, LerpType.EaseInOut);
    }

    public void setCallback(IInteractableCallback callback) {
        this.callback = callback;
    }

    @Override
    public void update(double deltaTime) {
        scaleLerper.update(deltaTime);
        scale = scaleLerper.getValue();
    }

    public void setBorderColor(int r, int g, int b) {
        borderR = r;
        borderG = g;
        borderB = b;
    }

    public void setBorderSize(int boderSize)
    {
        this.boderSize = boderSize;
    }

    public void setColor(int r, int g, int b) {
        colorR = r;
        colorG = g;
        colorB = b;
    }

    public void setColor(int r, int g, int b, int a) {
        colorR = r;
        colorG = g;
        colorB = b;
        alpha = a;
    }

    @Override
    public void render() {
        graphics.setScale(scale, scale);

        // Draw border
        graphics.setColor((int) (borderB * darker), (int) (borderG * darker), (int) (borderB * darker), alpha);
        graphics.drawRectangle(posX, posY, (int) (width), (int) (height), boderSize);

        // Draw rectable
        graphics.setColor((int) (colorR * darker), (int) (colorG * darker), (int) (colorB * darker), alpha);
        graphics.fillRectangle(posX, posY, (int) (width), (int) (height));

        // Draw text
        graphics.setColor(0, 0, 0);
        graphics.drawTextCentered(buttonText, posX, posY, font);

        // Restore scale
        graphics.setScale(1, 1);
    }

    @Override
    public void OnHoverEnter() {
        scaleLerper.setReversed(false);
    }

    @Override
    public void OnHoverExit() {
        scaleLerper.setReversed(true);
    }

    @Override
    public void OnTouchDown() {
        darker = 0.5f;
        if (callback != null)
            callback.onInteractionOccur();
    }

    @Override
    public void OnTouchUp() {
        darker = 1;
    }
}
