package com.example.engine;

import java.awt.Color;

public abstract class AbstractGraphics implements IGraphics{
    protected int logicSizeX, logicSizeY;
    protected double scaleFactor = 1;
    protected int borderBarWidth, topBarHeight;

    protected void calculateScaleFactor(int width, int height) {
        double xFactor = width / (double) logicSizeX;
        double yFactor = height / (double) logicSizeY;

        int finalScreenWidth, finalScreenHeight;

        if (xFactor < yFactor) {
            scaleFactor = xFactor;

            finalScreenWidth = width;
            finalScreenHeight = (width * logicSizeY) / logicSizeX;
        }
        else {
            scaleFactor = yFactor;

            finalScreenWidth = (height * logicSizeX) / logicSizeY;
            finalScreenHeight = height;
        }

        borderBarWidth = (width - finalScreenWidth) / 2;
        topBarHeight = (height - finalScreenHeight) / 2;
    }

    protected void renderBorders()
    {
        // Tener en cuenta scale factor para el ancho
        int scaledBarWidth = (int)(borderBarWidth / scaleFactor);
        int scaledTopHeight = (int)(topBarHeight / scaleFactor);

        // Barra izquierda y derecha
        fillRectangle(-scaledBarWidth/2, logicSizeY/2, scaledBarWidth, logicSizeY);
        fillRectangle(scaledBarWidth/2 + logicSizeX, logicSizeY/2, scaledBarWidth, logicSizeY);

        // Barras arriba y abajo
        fillRectangle(logicSizeX/2, -scaledTopHeight/2, logicSizeX, scaledTopHeight);
        fillRectangle(logicSizeX/2, scaledTopHeight/2 + logicSizeY, logicSizeX, scaledTopHeight);
    }
}
