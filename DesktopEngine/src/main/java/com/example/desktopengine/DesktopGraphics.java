package com.example.desktopengine;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;

import java.awt.Graphics2D;

public class DesktopGraphics implements IGraphics {

    Graphics2D graphics2D;

    public DesktopGraphics(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    @Override
    public IImage newImage(String pathToImage) {
        return null;
    }

    @Override
    public IFont newFont(String pathToFont) {
        return null;
    }

    @Override
    public void setLogicSize(int xSize, int ySize) {

    }

    @Override
    public void setColor() {

    }

    @Override
    public void setFont() {

    }

    @Override
    public void drawImage(IImage image, int x, int y) {

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
