package com.example.gamelogic.entities;

import com.example.engine.AnchorPoint;
import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IImage;
import com.example.engine.ISound;
import com.example.engine.utilities.FloatLerper;
import com.example.engine.utilities.LerpType;
import com.example.gamelogic.utilities.Color;

public class Button extends UIElement {
    private Color buttonColor;
    private Color borderColor;
    private Color buttonPressedColor;
    private Color buttonHoverColor;
    private Color textColor;
    ISound clickSound, hoverSound;
    private boolean soundWillPlay = true;

    private Color currentButtonColor;
    private int borderSize = 10;
    float scale = 1;
    FloatLerper scaleLerper;
    String buttonText;
    IFont font;
    IInteractableCallback callback;
    IImage image;
    int paddingHorizontal, paddingVertical;

    public Button(int x, int y, int width, int height, IEngine engine) {
        super(engine);

        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.graphics = engine.getGraphics();
        this.buttonText = "";
        this.font = null;
        scaleLerper = new FloatLerper(1, 1.2f, 0.1f, LerpType.EaseInOut);
        scaleLerper.setReversed(true);

        buttonColor = new Color(255, 255, 255, 255);
        borderColor = new Color(0, 0, 0, 255);
        buttonPressedColor = new Color(123, 123, 123, 255);
        buttonHoverColor =new Color(255, 255, 255, 255);
        textColor = new Color();
        currentButtonColor = buttonColor;

        clickSound = audio.newSound(engine.getAssetsPath() + "audio/menuClick.wav", "click");
        hoverSound = audio.newSound(engine.getAssetsPath() + "audio/menuHover.wav", "hover" + this.hashCode());
    }


    public void setSoundWillPlay(boolean s){
        soundWillPlay = s;
    }
    public void setImage(IImage image) {
        this.image = image;
    }

    public void setPadding(int paddingHorizontal, int paddingVertical) {
        this.paddingHorizontal = paddingHorizontal;
        this.paddingVertical = paddingVertical;
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
        borderColor.r = r;
        borderColor.g = g;
        borderColor.b = b;
        borderColor.a = 255;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }

    public void setBackgroundColor(int r, int g, int b) {
        buttonColor.r = r;
        buttonColor.g = g;
        buttonColor.b = b;
        buttonColor.a = 255;
    }

    public void setBackgroundColor(int r, int g, int b, int a) {
        buttonColor.r = r;
        buttonColor.g = g;
        buttonColor.b = b;
        buttonColor.a = a;
    }

    public void setPressedColor(int r, int g, int b) {
        buttonPressedColor.r = r;
        buttonPressedColor.g = g;
        buttonPressedColor.b = b;
        buttonHoverColor.a = 255;
    }

    public void setPressedColorColor(int r, int g, int b, int a) {
        buttonPressedColor.r = r;
        buttonPressedColor.g = g;
        buttonPressedColor.b = b;
        buttonPressedColor.a = a;
    }

    public void setHoverColor(int r, int g, int b) {
        buttonHoverColor.r = r;
        buttonHoverColor.g = g;
        buttonHoverColor.b = b;
        buttonHoverColor.a = 255;
    }

    public void setHoverColor(int r, int g, int b, int a) {
        buttonHoverColor.r = r;
        buttonHoverColor.g = g;
        buttonHoverColor.b = b;
        buttonHoverColor.a = a;
    }

    public void setTextColor(int r, int g, int b) {
        textColor.r = r;
        textColor.g = g;
        textColor.b = b;
        textColor.a = 255;
    }

    public void setTextColor(int r, int g, int b, int a) {
        textColor.r = r;
        textColor.g = g;
        textColor.b = b;
        textColor.a = a;
    }

    public void setText(String text, IFont font) {
        this.font = font;
        this.buttonText = text;
    }

    @Override
    public void render() {
        graphics.setAnchorPoint(this.anchorPoint);
        graphics.setScale(scale, scale);

        renderBorders();
        renderBackground();
        renderImage();
        renderText();

        graphics.setScale(1, 1);
        graphics.setAnchorPoint(AnchorPoint.None);
    }

    @Override
    public void OnHoverEnter() {
        currentButtonColor = buttonHoverColor;
        scaleLerper.setReversed(false);
        if(soundWillPlay)
            hoverSound.play();
    }

    @Override
    public void OnHoverExit() {
        currentButtonColor = buttonColor;
        scaleLerper.setReversed(true);
    }

    @Override
    public void OnTouchDown() {
        if(soundWillPlay)
            clickSound.play();
        currentButtonColor = this.buttonPressedColor;
        if (callback != null)
            callback.onInteractionOccur();
    }

    @Override
    public void OnTouchUp() {
        currentButtonColor = this.buttonColor;
    }

    @Override
    public void OnPointerMove(int x, int y) {

    }

    private void renderBackground() {
        graphics.setColor(currentButtonColor.r, currentButtonColor.g, currentButtonColor.b, currentButtonColor.a);
        graphics.fillRectangle(posX, posY, width, height);
    }

    private void renderBorders() {
        if (this.borderSize <= 0)
            return;

        graphics.setColor(borderColor.r, borderColor.g, borderColor.b, borderColor.a);
        graphics.drawRectangle(posX, posY, (int) (width), (int) (height), borderSize);
    }

    private void renderText() {
        if(buttonText.isEmpty() || font == null)
            return;

        graphics.setColor(0, 0, 0);
        graphics.drawTextCentered(buttonText, posX, posY, font);
    }

    private void renderImage() {
        if (image != null)
        {
            double imageWidthRatio = ((width-paddingHorizontal) * scale)  / image.getWidth();
            double imageHeightRatio = ((height - paddingVertical) * scale) / image.getHeight();
            double scaleFactor = scale;

            if (imageWidthRatio < imageHeightRatio) {
                scaleFactor = imageWidthRatio;
            }
            else {
                scaleFactor = imageHeightRatio;
            }

            graphics.setScale(scaleFactor, scaleFactor);
            graphics.drawImage(image, posX, posY);
        }
        graphics.setScale(scale, scale);
    }
}
