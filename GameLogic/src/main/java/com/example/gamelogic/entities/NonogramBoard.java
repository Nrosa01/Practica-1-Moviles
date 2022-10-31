package com.example.gamelogic.entities;

import com.example.engine.IEngine;

public class NonogramBoard extends Board {
    int[][] nonogramCellStates;
    private final int numOfStates = 3;

    public NonogramBoard(IEngine engine, int rows, int cols, int width, int gapSize) {
        super(engine, rows, cols, width, gapSize);
        nonogramCellStates = new int[rows][cols];
    }

    @Override
    protected void OnCellClicked(int row, int col) {
        System.out.println("Clicked on cell: " + row + " " + col);
        nonogramCellStates[row][col] = (nonogramCellStates[row][col] + 1) % numOfStates;
    }

    private void setColorGivenState(int state) {
        switch (state) {
            case 0:
                graphics.setColor(123,123,123);
                break;
            case 1:
                graphics.setColor(123,123,255);
                break;
            case 2:
                graphics.setColor(23,23,23);
                break;
        }
    }

    @Override
    protected void OnCellRender(int row, int col) {
        setColorGivenState(nonogramCellStates[row][col]);
    }
}
