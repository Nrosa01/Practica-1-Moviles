package com.example.engine;

public interface IGraphics {
    IImage newImage();
    IFont newFont();
    void setResolution();
    void setColor();
    void setFont();
    void drawImage();
    void drawRectangle();
    void fillRectangle();
    void drawLine();
    void drawText();
}
