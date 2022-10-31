package com.example.gamelogic.entities;

import com.example.engine.IEngine;
import com.example.gamelogic.states.MainGameLogic;

public class Board extends Entity{
    int[][] board;
    int rows, cols, gapSize;

    public Board(IEngine engine, int rows, int cols, int width, int gapSize) {
        super(engine);
        board = new int[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.width = width;
        this.height = (int) (width * rows / cols);
        this.gapSize = gapSize;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        graphics.setColor(255,123,123);
        graphics.fillRectangle(posX, posY,width, height);
        graphics.setColor(123,123,123);

        // Instead of scaling the canvas, we simulate that the drawing area for the cells is smaller
        // by substracting gap. This way is easier to do.
        int widthArea = width - (gapSize);
        int heightArea = height - ( gapSize);

        int cellWidth = (widthArea - (cols-1)*(gapSize))/cols;
        int cellHeight = (heightArea - (rows-1)*(gapSize))/rows;

        // There are some cases where the space doesnt look right, but I dont know why
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int cellPosX = posX - widthArea/2 + col*(cellWidth+gapSize) + cellWidth/2;
                int cellPosY = posY - heightArea/2 + row*(cellHeight+gapSize) + cellHeight/2;
                graphics.fillRectangle(cellPosX, cellPosY, cellWidth, cellHeight);
            }
        }
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
