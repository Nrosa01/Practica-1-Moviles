package com.example.desktopengine;

import com.example.engine.AbstractGraphics;
import com.example.engine.IFont;
import com.example.engine.IImage;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JFrame;

public class DesktopGraphics extends AbstractGraphics {

    Graphics2D graphics2D;
    JFrame jFrame;
    Color currentColor;

    // Para guardar las transformaciones entre buffers
    AffineTransform currentTransform;
    AffineTransform defaultTransform;
    BufferStrategy bufferStrategy;

    private double scaleX = 1;
    private double scaleY = 1;

    // Offsets del jframe
    protected int topInsetOffset, borderInsetOffset;

    public DesktopGraphics(JFrame jFrame, int wWidth, int wHeight) {
        this.jFrame = jFrame;
        jFrame.setSize(wWidth, wHeight);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setIgnoreRepaint(false);
        jFrame.setVisible(true);

        setLogicSize(wWidth, wHeight);

        int tries = 100;
        while (tries-- > 0) {
            try {
                this.jFrame.createBufferStrategy(2);
                break;
            } catch (Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (tries == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }

        this.bufferStrategy = jFrame.getBufferStrategy();

        this.graphics2D = ((Graphics2D) bufferStrategy.getDrawGraphics());
        currentColor = new Color(0, 0, 0, 0);
        currentTransform = graphics2D.getTransform();
        defaultTransform = new AffineTransform();

        jFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                handleWindowsResize();
            }
        });

        calculateBorderOffset();
        resizeFrameToAddInsets();
        calculateScaleFactor();
    }

    void handleWindowsResize() {
        calculateScaleFactor();
    }

    void calculateScaleFactor() {
        int canvasWidth = getWidth() - jFrame.getInsets().left - jFrame.getInsets().right;
        int canvasHeight = getHeight() - jFrame.getInsets().top - jFrame.getInsets().bottom;

        super.calculateScaleFactor(canvasWidth, canvasHeight);
        setScale(1, 1);
    }

    void calculateBorderOffset() {
        // Taking in account screen insets
        topInsetOffset = jFrame.getInsets().top;
        borderInsetOffset = jFrame.getInsets().left;
    }

    void resizeFrameToAddInsets() {
        int width = 1400;
        //int width = getWidth();
        int newWidth = width + jFrame.getInsets().left + jFrame.getInsets().right;

        int height = 800;
        //int height = getHeight();
        int newHeight = height + jFrame.getInsets().top + jFrame.getInsets().bottom;

        if (width == 0 || height == 0)
            return;

        jFrame.setSize(newWidth, newHeight);
    }

    void updateGraphics() {
        this.graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
    }

    void prepareFrame() {
        save();
        updateGraphics();
        restore(); // Restaurar
        //clear(255, 255, 255);
        clear(130, 130, 130);
    }

    void finishFrame() {
        Color current = currentColor;
        setColor(255,255,255);
        renderBorders();
        currentColor = current;

        bufferStrategy.getDrawGraphics().dispose();
    }

    boolean swapBuffer() {
        if (bufferStrategy.contentsRestored()) {
            return false; //ha ido mal
        }
        //Display Buffer
        this.bufferStrategy.show();

        return !this.bufferStrategy.contentsLost(); //ha ido bien?
    }

    @Override
    public IImage newImage(String pathToImage) {
        IImage image = null;
        try {
            image = new DesktopImage(pathToImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }


    @Override
    public IFont newFont(String pathToFont, int size, boolean isBold) {
        IFont font = null;

        try {
            font = new DesktopFont(pathToFont, size, isBold);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        return font;
    }

    @Override
    public void setLogicSize(int xSize, int ySize) {
        this.logicSizeX = xSize;
        this.logicSizeY = ySize;
    }

    @Override
    public void setColor(int r, int g, int b) {
        currentColor = new Color(r, g, b);
        graphics2D.setColor(currentColor);
    }

    @Override
    public void setColor(int r, int g, int b, int a) {
        currentColor = new Color(r, g, b, a);
        graphics2D.setColor(currentColor);
    }

    @Override
    public void setFont() {

    }

    @Override
    public void clear(int r, int g, int b) {
        graphics2D.setTransform(defaultTransform);
        graphics2D.setBackground(new Color(r, g, b, 255));
        graphics2D.clearRect(0, 0, getWidth(), getHeight());
        graphics2D.setTransform(currentTransform);
    }

    @Override
    public void drawImage(IImage image, int x, int y) {
        int processedX = logicXPositionToWindowsXPosition(x - ((int)(image.getWidth() * scaleX) / 2));
        int processedY = logicYPositionToWindowsYPosition(y - ((int)(image.getHeight() * scaleY) / 2));
        this.graphics2D.drawImage(((DesktopImage) image).getImage(), processedX, processedY, null);
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height, int lineWidth) {
        int processedX = logicXPositionToWindowsXPosition(x - ((int)(width * scaleX) / 2));
        int processedY = logicYPositionToWindowsYPosition(y - ((int)(height * scaleY) / 2));
        graphics2D.setStroke(new BasicStroke(lineWidth));
        graphics2D.drawRect(processedX, processedY, width, height);
    }

    @Override
    public void fillRectangle(int x, int y, int width, int height) {
        int processedX = logicXPositionToWindowsXPosition(x - ((int)(width * scaleX) / 2));
        int processedY = logicYPositionToWindowsYPosition(y - ((int)(height * scaleY) / 2));
        graphics2D.fillRect(processedX, processedY, width, height);
    }

    @Override
    public void drawLine(int fromX, int fromY, int toX, int toY) {

    }

    @Override
    public void drawCircle(int xPos, int yPos, int radius) {
        int processedX = logicXPositionToWindowsXPosition(xPos - radius);
        int processedY = logicYPositionToWindowsYPosition(yPos - radius);
        graphics2D.fillOval(processedX, processedY, radius * 2, radius * 2);
    }

    @Override
    public void drawText(String text, int x, int y, IFont font) {
        int processedX = logicXPositionToWindowsXPosition(x);
        int processedY = logicYPositionToWindowsYPosition(y);

        graphics2D.setFont(((DesktopFont) font).getFont());
        graphics2D.drawString(text, processedX, processedY);
    }

    @Override
    public void drawTextCentered(String text, int x, int y, IFont font) {
        graphics2D.setFont(((DesktopFont) font).getFont());
        FontMetrics fm = graphics2D.getFontMetrics();

        int processedX = logicXPositionToWindowsXPosition(x -  ((int)(fm.stringWidth(text) * scaleX) / 2));
        int processedY = logicYPositionToWindowsYPosition(y - ((int)(fm.getHeight() * scaleY) / 2) + (int)(fm.getAscent() * scaleY));

        graphics2D.drawString(text, processedX, processedY);
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
    public int getLogicWidth() {
        return logicSizeX;
    }

    @Override
    public int getLogicHeight() {
       return logicSizeY;
    }

    @Override
    public void setGraphicsAlpha(int alpha) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha/255.0f));
    }

    @Override
    public void scale(double x, double y) {
        graphics2D.scale(x, y);
        save();
    }

    @Override
    public void translate(double x, double y) {
        graphics2D.translate(x, y);
        save();
    }

    @Override
    public void rotate(double angleDegrees) {
        graphics2D.rotate(Math.toRadians(angleDegrees));
        save();
    }

    @Override
    public int logicXPositionToWindowsXPosition(int x) {
        return (int) ((x + (int) (borderBarWidth / scaleFactor) + (int) (borderInsetOffset / scaleFactor)) / scaleX);
    }

    @Override
    public int logicYPositionToWindowsYPosition(int y) {
        return (int) ((y + (int) (topBarHeight / scaleFactor) + (int) (topInsetOffset / scaleFactor)) / scaleY);
    }

    @Override
    public int windowsXPositionToLogicXPosition(int x) {
        return (int) ((x - borderBarWidth - borderInsetOffset) / scaleFactor);
    }

    @Override
    public int windowsYPositionToLogicYPosition(int y) {
        return (int) ((y - topBarHeight - topInsetOffset) / scaleFactor);
    }

    @Override
    public void setScale(double x, double y) {
        scaleX = x;
        scaleY = y;

        currentTransform.setToScale((x * scaleFactor) / currentTransform.getScaleX(), (y * scaleFactor) / currentTransform.getScaleY());

        apply();
        save();
    }

    @Override
    public void setTranslation(double x, double y) {

        currentTransform.setToTranslation(x - currentTransform.getTranslateX(), y - currentTransform.getTranslateY());
        apply();
        save();
    }

    @Override
    public void setRotation(double angleDegrees) {
        currentTransform.setToRotation(Math.toRadians(angleDegrees));
        apply();
        save();
    }

    @Override
    public void resetScale() {
        setScale(1, 1);
    }

    @Override
    public void resetTranslation() {
        setTranslation(0, 0);

        // Volver a trasladar para tener en cuenta la barra
        translate(jFrame.getInsets().left, jFrame.getInsets().top);
    }

    public boolean isInsideLogicCanvas(int windowsX, int windowsY) {

        int logicX = windowsXPositionToLogicXPosition(windowsX);
        int logicY = windowsYPositionToLogicYPosition(windowsY);
        return !(logicX < 0 || logicX > logicSizeX ||
                logicY < 0 || logicY > logicSizeY);
    }

    @Override
    public void resetRotation() {
        setRotation(0);
    }

    @Override
    public void resetTransform() {
        resetTranslation();
        resetScale();
        resetRotation();
    }

    @Override
    public void save() {
        currentTransform = graphics2D.getTransform();
    }

    private void apply() {
        graphics2D.transform(currentTransform);
    }

    @Override
    public void restore() {
        if (currentTransform != null)
            graphics2D.setTransform(currentTransform);
    }
}
