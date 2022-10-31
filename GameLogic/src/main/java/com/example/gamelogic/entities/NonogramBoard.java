package com.example.gamelogic.entities;

import com.example.engine.IEngine;

public class NonogramBoard extends Board {
    int[][] nonogramCellStates;
    private final int numOfStates = 3;
    int borderBoardSize;

    public NonogramBoard(IEngine engine, int rows, int cols, int width, int gapSize) {
        // Calculate borderBoardSize, where the numbers of the nonogram will be placed
        super(engine, rows, cols, width, gapSize);
        borderBoardSize = (int) (width * 0.2);
        this.width = width - borderBoardSize;
        init();

        nonogramCellStates = new int[rows][cols];
    }

    @Override
    public void render() {
        super.render();
        // Render the border board left of the nonogram board and up of the nonogram board
        graphics.setColor(255, 255, 255);
        graphics.fillRectangle(posX - width/2 - borderBoardSize/2, posY, borderBoardSize, height);
        graphics.fillRectangle(posX, posY -height/2 - borderBoardSize/2, width, borderBoardSize);
        
    }

    @Override
    public  void setPosX(int x)
    {
        this.posX = x + borderBoardSize/2;
    }

    @Override
    public  void setPosY(int y)
    {
        this.posY = y + borderBoardSize/2;
    }

    @Override
    protected void OnCellClicked(int row, int col) {
        //System.out.println("Clicked on cell: " + row + " " + col);
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
