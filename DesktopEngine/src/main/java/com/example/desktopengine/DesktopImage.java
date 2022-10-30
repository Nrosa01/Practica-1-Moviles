package com.example.desktopengine;

import com.example.engine.IImage;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DesktopImage implements IImage {
    Image image;

    public DesktopImage(String imgPath) throws IOException {
        this.image = ImageIO.read((new File(imgPath)));
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
