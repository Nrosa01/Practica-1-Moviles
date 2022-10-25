package com.example.engine;

public abstract class AbstractGraphics implements IGraphics{
    protected int logicSizeX, logicSizeY;
    protected float scaleFactor = 1;
    protected int borderBarkWidth, topBarHeight;

    protected void calculateScaleFactor(int width, int height) {
        // getWidth y getHeight son el tamaño lógico de la pantalla (setLogicSize)
        float wFactor = width / (float) logicSizeX;
        float hFactor = height / (float) logicSizeY;

        int sizeScreenX, sizeScreenY;

        if (wFactor < hFactor) {
            scaleFactor = wFactor;

            sizeScreenX = width;
            sizeScreenY = (width * logicSizeY) / logicSizeX;
        }
        else {
            scaleFactor = hFactor;

            sizeScreenX = (height * logicSizeX) / logicSizeY;
            sizeScreenY = height;
        }

        borderBarkWidth = (width - sizeScreenX) / 2;
        topBarHeight = (height - sizeScreenY) / 2;
    }
}
