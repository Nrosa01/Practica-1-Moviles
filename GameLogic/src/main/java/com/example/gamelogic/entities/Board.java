package com.example.gamelogic.entities;

import com.example.engine.IEngine;
import com.example.gamelogic.states.MainGameLogic;

public abstract class Board extends Entity {
    protected int[][] board;
    protected int rows, cols, gapSize;
    protected int paddingHorizontal, paddingVertical = 0;

    protected int cellWidth, cellHeight, widthArea, heightArea;

    public Board(IEngine engine, int rows, int cols, int width, int gapSize) {
        super(engine);
        board = new int[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.width = width;
        this.gapSize = gapSize;
        init();
    }

    protected void init() {
        this.height = (int) (width * rows / cols);

        widthArea = width - (gapSize + paddingHorizontal);
        heightArea = height - (gapSize + paddingVertical);

        cellWidth = (widthArea - (cols - 1) * (gapSize)) / cols;
        cellHeight = (heightArea - (rows - 1) * (gapSize)) / rows;

        // Sometimes, the cellWidth and cellHeight with the gap wont cover the whole width and height, so we add padding
        // to space the cells evenly
        paddingHorizontal = (widthArea - (cols - 1) * (gapSize) - cols * cellWidth) / 2;
        paddingVertical = (heightArea - (rows - 1) * (gapSize) - rows * cellHeight) / 2;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        graphics.setColor(255, 123, 123);
        graphics.fillRectangle(posX, posY, width, height);
        graphics.setColor(123, 123, 123);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                this.OnCellRender(row, col);
                graphics.fillRectangle(getCellPosX(col), getCellPosY(row), cellWidth, cellHeight);
            }
        }
    }

    protected abstract void OnCellClicked(int row, int col);

    protected abstract void OnCellRender(int row, int col);

    int getCellPosX(int col) {
        return posX - widthArea / 2 + col * (cellWidth + gapSize) + cellWidth / 2 + paddingHorizontal;
    }

    int getCellPosY(int row) {
        return posY - heightArea / 2 + row * (cellHeight + gapSize) + cellHeight / 2 + paddingVertical;
    }

    // We don't need it, but it might be useful for someone else
    boolean isPointInsideCell(int pointX, int pointY, int row, int col) {
        return pointX >= getCellPosX(col) - cellWidth / 2 && pointX <= getCellPosX(col) + cellWidth / 2 &&
                pointY >= getCellPosY(row) - cellHeight / 2 && pointY <= getCellPosY(row) + cellHeight / 2;
    }

    int[] pointToCell(int pointX, int pointY) {
        int[] cell = new int[2];
        cell[0] = ((pointY - posY + heightArea / 2 - paddingVertical) / (cellHeight + gapSize));
        cell[1] = ((pointX - posX + widthArea / 2 - paddingHorizontal) / (cellWidth + gapSize));

        // If the point is outside the board, return null
        if (pointX < posX - widthArea / 2 || pointX > posX + widthArea / 2 ||
                pointY < posY - heightArea / 2 || pointY > posY + heightArea / 2)
            return null;
        else
            return cell;
    }

    @Override
    public void OnPointerDown(int x, int y) {
        int[] cell = pointToCell(x, y);
        if (cell != null)
            OnCellClicked(cell[0], cell[1]);
    }

    @Override
    public void OnPointerUp(int x, int y) {

    }

    @Override
    public void OnPointerMove(int x, int y) {

    }
}
