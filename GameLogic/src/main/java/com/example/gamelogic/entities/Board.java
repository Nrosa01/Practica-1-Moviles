package com.example.gamelogic.entities;

import com.example.engine.IEngine;
import com.example.gamelogic.states.MainGameLogic;

public class Board extends Entity{
    int[][] board;
    int rows, cols, gapSize;
    int paddingHorizontal, paddingVertical = 0;

    int cellWidth, cellHeight, widthArea, heightArea;

    public Board(IEngine engine, int rows, int cols, int width, int gapSize) {
        super(engine);
        board = new int[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.width = width;
        this.height = (int) (width * rows / cols);
        this.gapSize = gapSize;
        init();
    }

    private void init()
    {
        widthArea = width - (gapSize + paddingHorizontal);
        heightArea = height - (gapSize + paddingVertical);

        cellWidth = (widthArea - (cols-1)*(gapSize))/cols;
        cellHeight = (heightArea - (rows-1)*(gapSize))/rows;

        // Sometimes, the cellWidth and cellHeight with the gap wont cover the whole width and height, so we add padding
        // to space the cells evenly
        paddingHorizontal = (widthArea - (cols-1)*(gapSize) - cols*cellWidth)/2;
        paddingVertical = (heightArea - (rows-1)*(gapSize) - rows*cellHeight)/2;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        graphics.setColor(255,123,123);
        graphics.fillRectangle(posX, posY,width, height);
        graphics.setColor(123,123,123);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                graphics.fillRectangle( getCellPosX(col),  getCellPosY(row), cellWidth, cellHeight);
            }
        }
    }

    int getCellPosX(int col)
    {
        return posX - widthArea/2 + col*(cellWidth+gapSize) + cellWidth/2 + paddingHorizontal;
    }

    int getCellPosY(int row)
    {
        return posY - heightArea/2 + row*(cellHeight+gapSize) + cellHeight/2 + paddingVertical;
    }

    @Override
    public void OnPointerDown(int x, int y) {

    }

    @Override
    public void OnPointerUp(int x, int y) {

    }

    @Override
    public void OnPointerMove(int x, int y) {

    }
}
