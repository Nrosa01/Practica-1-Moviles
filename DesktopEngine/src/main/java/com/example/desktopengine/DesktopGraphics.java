package com.example.desktopengine;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class DesktopGraphics implements IGraphics {

    Graphics2D graphics2D;
    JFrame jFrame;
    int logicSizeX, getLogicSizeY;
    Color currentColor;

    public DesktopGraphics(Graphics2D graphics2D, JFrame jFrame) {
        this.graphics2D = graphics2D;
        this.jFrame = jFrame;
        currentColor = new Color(0,0,0,0);

        // Esto es para tener en cuenta la barra de titulo de la app
        // Intente tambien que se ajustara horizontalmente pero nada, imposible
        translate(0, jFrame.getInsets().top);
    }

    void setGraphics2D(Graphics2D graphics)
    {
        this.graphics2D = graphics;
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
        this.logicSizeX = xSize;
        this.getLogicSizeY = ySize;
    }

    @Override
    public void setColor(int r, int g, int b) {
        currentColor = new Color(r,g,b);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void clear(int r, int g, int b) {
        graphics2D.setBackground(new Color(r,g,b, 255));
        graphics2D.clearRect(0,0, getWidth(), getHeight());
    }

    @Override
    public void drawImage(IImage image, int x, int y) {
        this.graphics2D.drawImage(((DesktopImage)image).getImage(), x, y, null);
        //this.graphics2D.drawImage(((DesktopImage)image).getImage(), x - image.getWidth()/2, y - image.getHeight()/2, null);
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

    @Override
    public int getWidth() {
        return jFrame.getWidth();
    }

    @Override
    public int getHeight() {
        return jFrame.getHeight();
    }

    @Override
    public void translate(float x, float y) {
        graphics2D.translate(x,y);
    }

    @Override
    public void rotate(float angleDegrees)
    {
        graphics2D.rotate(Math.toRadians(angleDegrees));
    }

    @Override
    public void scale(float x, float y) {
        graphics2D.scale(x,y);
    }

    @Override
    public void save() {
    }

    @Override
    public void restore() {

    }
}
