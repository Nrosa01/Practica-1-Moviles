package com.example.engine;

public interface IGraphics {
    IImage newImage();
    IFont newFont();
    void setResolution();
    void setColor();
    void setFont();
    void drawImage();
    void drawRectangle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, int lineWidth);
    void fillRectangle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY);
    void drawLine(int fromX, int fromY, int toX, int toY);
    void drawText();
}
