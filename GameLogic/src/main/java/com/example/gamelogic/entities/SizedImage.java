package com.example.gamelogic.entities;

import com.example.engine.AnchorPoint;
import com.example.engine.IEngine;
import com.example.engine.IImage;

public class SizedImage extends Entity {
    private IImage image;
    private float scale = 1;

    public SizedImage(IEngine engine, IImage image, int x, int y, int width, int height) {
        super(engine);
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    @Override
    public void update(double deltaTime) {

    }

    public void setImage(IImage image) {
        this.image = image;
    }

    @Override
    public void render() {
        double imageWidthRatio = ((width) * scale) / image.getWidth();
        double imageHeightRatio = ((height) * scale) / image.getHeight();
        double scaleFactor = scale;

        if (imageWidthRatio < imageHeightRatio) {
            scaleFactor = imageWidthRatio;
        } else {
            scaleFactor = imageHeightRatio;
        }

        graphics.setAnchorPoint(this.anchorPoint);
        graphics.setScale(scaleFactor, scaleFactor);
        graphics.drawImage(image, posX, posY);

        graphics.setScale(scale, scale);
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
