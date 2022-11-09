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


        // Calcular tamaño lógico al principio
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
        topInsetOffset = jFrame.getInsets().top;
        borderInsetOffset = jFrame.getInsets().left;
    }

    void resizeFrameToAddInsets() {
        int width = getWidth();
        int newWidth = width + jFrame.getInsets().left + jFrame.getInsets().right;

        int height = getHeight();
        int newHeight = height + jFrame.getInsets().top + jFrame.getInsets().bottom;

        if (width == 0 || height == 0)
            return;

        jFrame.setSize(newWidth, newHeight);
    }

    // Ajustar el graphics2d actual para despues de cambiar el buffer
    void updateGraphics() {
        this.graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
    }

    void prepareFrame() {
        save();
        updateGraphics();
        restore(); // Restaurar
        //clear(255, 255, 255);
        clear(255, 255, 255);
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
    public void setFont(IFont font) {

    }

    @Override
    public void clear(int r, int g, int b) {
        graphics2D.setTransform(defaultTransform);
        graphics2D.setBackground(new Color(r, g, b, 255));
        graphics2D.clearRect(0, 0, getWidth(), getHeight());
        graphics2D.setTransform(currentTransform);
    }

    // Dibuja una imagen teniendo en cuenta la escala con centro como pivote
    @Override
    public void drawImage(IImage image, int px, int py) {
        int processedX = logicXPositionToWindowsXPosition(px - ((int)(image.getWidth() * scaleX) / 2));
        int processedY = logicYPositionToWindowsYPosition(py - ((int)(image.getHeight() * scaleY) / 2));
        this.graphics2D.drawImage(((DesktopImage) image).getImage(), processedX, processedY, null);
    }

    // Dibuja el borde de un rectángulo teniendo en cuenta la escala con centro como pivote
    @Override
    public void drawRectangle(int px, int py, int width, int height, int lineWidth) {
        int processedX = logicXPositionToWindowsXPosition(px - ((int)(width * scaleX) / 2));
        int processedY = logicYPositionToWindowsYPosition(py - ((int)(height * scaleY) / 2));
        graphics2D.setStroke(new BasicStroke(lineWidth));
        graphics2D.drawRect(processedX, processedY, width, height);
    }

    // Dibuja un rectángulo relleno teniendo en cuenta la escala con centro como pivote
    @Override
    public void fillRectangle(int px, int py, int width, int height) {
        int processedX = logicXPositionToWindowsXPosition(px - ((int)(width * scaleX) / 2));
        int processedY = logicYPositionToWindowsYPosition(py - ((int)(height * scaleY) / 2));
        graphics2D.fillRect(processedX, processedY, width, height);
    }

    @Override
    public void drawLine(int fromX, int fromY, int toX, int toY) {

    }

    // Dibujar un circulo centrado en px py de radio radius teniendo en cuenta la escala
    @Override
    public void drawCircle(int px, int py, int radius) {
        int processedX = logicXPositionToWindowsXPosition(px - radius);
        int processedY = logicYPositionToWindowsYPosition(py - radius);
        graphics2D.fillOval(processedX, processedY, radius * 2, radius * 2);
    }

    // Dibuja un texto teniendo en cuenta la escala con la parte izquierda como pivote del texto
    @Override
    public void drawText(String text, int px, int py, IFont font) {
        int processedX = logicXPositionToWindowsXPosition(px);
        int processedY = logicYPositionToWindowsYPosition(py);

        graphics2D.setFont(((DesktopFont) font).getFont());
        graphics2D.drawString(text, processedX, processedY);
    }

    // Dibuja un texto teniendo en cuenta la escala con el centro como pivote del texto
    @Override
    public void drawTextCentered(String text, int px, int py, IFont font) {
        int processedX = logicXPositionToWindowsXPosition(px -  ((int)(getStringWidth(text, font) * scaleX) / 2));
        int processedY = logicYPositionToWindowsYPosition(py - ((int)(getFontHeight(font) * scaleY) / 2) + (int)(getFontAscent(font) * scaleY));

        graphics2D.drawString(text, processedX, processedY);
    }

    // Obtiene el ancho en pixeles de pantalla de un texto dada una fuente
    @Override
    public int getStringWidth(String text, IFont font) {
        graphics2D.setFont(((DesktopFont) font).getFont());
        FontMetrics fm = graphics2D.getFontMetrics();
        return fm.stringWidth(text);
    }


    // Obtiene el alto de una fuente en pixeles de pantalla
    @Override
    public int getFontHeight(IFont font) {
        graphics2D.setFont(((DesktopFont) font).getFont());
        FontMetrics fm = graphics2D.getFontMetrics();
        return fm.getHeight();
    }

    // Obtiene el ascent de una fuente en pixeles de pantalla
    @Override
    public int getFontAscent(IFont font) {
        graphics2D.setFont(((DesktopFont) font).getFont());
        FontMetrics fm = graphics2D.getFontMetrics();
        return fm.getAscent();
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
    public void scale(double sx, double sy) {
        graphics2D.scale(sx, sy);
        save();
    }

    @Override
    public void translate(double px, double py) {
        graphics2D.translate(px, py);
        save();
    }

    @Override
    public void rotate(double angleDegrees) {
        graphics2D.rotate(Math.toRadians(angleDegrees));
        save();
    }

    @Override
    public int logicXPositionToWindowsXPosition(int px) {
        return (int) ((px + (int) (borderBarWidth / scaleFactor) + (int) (borderInsetOffset / scaleFactor)) / scaleX);
    }

    @Override
    public int logicYPositionToWindowsYPosition(int py) {
        return (int) ((py + (int) (topBarHeight / scaleFactor) + (int) (topInsetOffset / scaleFactor)) / scaleY);
    }

    @Override
    public int windowsXPositionToLogicXPosition(int px) {
        return (int) ((px - borderBarWidth - borderInsetOffset) / scaleFactor);
    }

    @Override
    public int windowsYPositionToLogicYPosition(int py) {
        return (int) ((py - topBarHeight - topInsetOffset) / scaleFactor);
    }

    @Override
    public void setScale(double sx, double sy) {
        scaleX = sx;
        scaleY = sy;

        currentTransform.setToScale((sx * scaleFactor) / currentTransform.getScaleX(), (sy * scaleFactor) / currentTransform.getScaleY());

        apply();
        save();
    }

    @Override
    public void setTranslation(double px, double py) {

        currentTransform.setToTranslation(px - currentTransform.getTranslateX(), py - currentTransform.getTranslateY());
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
