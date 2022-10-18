package com.example.engine;

public interface IGraphics {
    IImage newImage(String pathToImage);
    IFont newFont(String pathToFont);
    void setLogicSize(int xSize, int ySize);
    void setColor(int r, int g, int b);
    void setFont();
    void clear(int r, int g, int b);
    void drawImage(IImage image, int x, int y);
    void drawRectangle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, int lineWidth);
    void fillRectangle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY);
    void drawLine(int fromX, int fromY, int toX, int toY);
    void drawText(String text, int x, int y, IFont font);
    int getWidth();
    int getHeight();

    void translate(double x, double y);
    void scale(double x, double y);
    void rotate(double angleDegrees);

    void setTranslation(double x, double y);
    void setScale(double x, double y);
    void setRotation(double angleDegrees);

    void resetTranslation();
    void resetScale();
    void resetRotation();

    void resetTransform();

    void save();
    void restore();
}
