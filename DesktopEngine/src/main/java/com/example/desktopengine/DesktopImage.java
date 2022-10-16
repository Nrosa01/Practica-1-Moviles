package com.example.desktopengine;

import com.example.engine.IImage;

import java.awt.Image;

public class DesktopImage implements IImage {
    Image image;

    public DesktopImage(Image img)
    {
        this.image = img;
    }

    Image getImage()
    {
        return image;
    }

    @Override
    public int getWidth() {
        return image.getWidth(null);
    }

    @Override
    public int getHeight() {
        return image.getHeight(null);
    }
}
