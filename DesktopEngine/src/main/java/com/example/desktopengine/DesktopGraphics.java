package com.example.desktopengine;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DesktopGraphics implements IGraphics {

    Graphics2D graphics2D;

    public DesktopGraphics(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    @Override
    public IImage newImage(String pathToImage) {
        IImage image = null;
        try {
            image = new DesktopImage(ImageIO.read((new File(pathToImage))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    @Override
    public IFont newFont(String pathToFont) {
        return null;
    }

    @Override
    public void setLogicSize(int xSize, int ySize) {

    }

    @Override
    public void setColor(int r, int g, int b) {

    }

    @Override
    public void setFont() {

    }

    @Override
    public void clear(int r, int g, int b) {

    }

    @Override
    public void drawImage(IImage image, int x, int y) {
        this.graphics2D.drawImage(((DesktopImage)image).getImage(), x - image.getWidth()/2, y - image.getHeight()/2, null);
    }

    @Override
    public void drawRectangle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, int lineWidth) {

    }

    @Override
    public void fillRectangle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY) {

    }

    @Override
    public void drawLine(int fromX, int fromY, int toX, int toY) {

    }

    @Override
    public void drawText(String text, int x, int y, IFont font) {

    }
}
