package com.example.engine;

public interface IGraphics {
    IImage newImage(String pathToImage);
    IFont newFont(String pathToFont, int size, boolean isBold);

    void setLogicSize(int xSize, int ySize);
    void setColor(int r, int g, int b);
    void setColor(int r, int g, int b, int a);
    void setFont(IFont font);
    int getStringWidth(String text, IFont font);
    int getFontHeight(IFont font);
    int getFontAscent(IFont font);
    void clear(int r, int g, int b);
    void drawImage(IImage image, int x, int y);
    void drawRectangle(int x, int y, int width, int height, int lineWidth);
    void fillRectangle(int x, int y, int width, int height);
    void drawLine(int fromX, int fromY, int toX, int toY);
    void drawCircle(int xPos, int yPos, int radius);
    void drawText(String text, int x, int y, IFont font);
    void drawTextCentered(String text, int x, int y, IFont font);
    int getWidth();
    int getHeight();
    int getLogicWidth();
    int getLogicHeight();
    boolean isPortrait();

    void setGraphicsAlpha(int alpha);

    void translate(double x, double y);
    void scale(double x, double y);
    void rotate(double angleDegrees);

    int logicXPositionToWindowsXPosition(int x);
    int logicYPositionToWindowsYPosition(int x);

    int windowsXPositionToLogicXPosition(int x);
    int windowsYPositionToLogicYPosition(int x);

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
