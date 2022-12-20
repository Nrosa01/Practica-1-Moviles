package com.example.gamelogic.entities;

import com.example.engine.IEngine;
import com.example.engine.IImage;
import com.example.gamelogic.utilities.Color;

public abstract class Board extends Entity {
    protected int[][] board;
    protected long[][] timePressBoard;
    protected int rows, cols, gapSize;
    protected int paddingHorizontal, paddingVertical = 0;
    private Color boardBackgroundColor;
    IImage cellImg;

    protected int cellWidth, cellHeight, widthArea, heightArea;

    public Board(IEngine engine, int rows, int cols, int width, int gapSize) {
        super(engine);
        board = new int[rows][cols];
        timePressBoard = new long[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.width = width;
        this.gapSize = gapSize;
        boardBackgroundColor = new Color(255,255,255);
        init();
    }

    protected void init() {
        this.height = (width * rows / cols);

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
    public abstract void update(double deltaTime);

    @Override
    public void render() {
        //graphics.setColor(boardBackgroundColor.r, boardBackgroundColor.r, boardBackgroundColor.b);
        graphics.fillRectangle(posX, posY, width, height);
        graphics.setColor(123, 123, 123);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                this.OnCellRender(row, col);
                graphics.fillRectangle(getCellPosX(col), getCellPosY(row), cellWidth, cellHeight);
                drawImageInCell(getCellPosX(col), getCellPosY(row), cellWidth, cellHeight);
            }
        }
    }

    private void drawImageInCell(int row, int col, int cellWidth, int cellHeight)
    {
        if (cellImg != null)
        {
            double imageWidthRatio = ((double)cellWidth)  / cellImg.getWidth();
            double imageHeightRatio = ((double)cellHeight) / cellImg.getHeight();
            double scaleFactor;

            if (imageWidthRatio < imageHeightRatio) {
                scaleFactor = imageWidthRatio;
            }
            else {
                scaleFactor = imageHeightRatio;
            }

            graphics.setScale(scaleFactor, scaleFactor);
            graphics.drawImage(cellImg, row, col);
        }
        graphics.setScale(1, 1);
    }

    public void setCellImg(IImage image)
    {
        this.cellImg = image;
    }

    protected abstract void OnCellClicked(int row, int col);
    protected abstract void OnCellReleased(int row, int col) ;

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
        int[] cell = pointToCell(x, y);
        if (cell != null)
            OnCellReleased(cell[0], cell[1]);
    }


    @Override
    public void OnPointerMove(int x, int y) {

    }

    public void setBoardBackgroundColor(int r, int g, int b) {
        boardBackgroundColor.r = r;
        boardBackgroundColor.g = g;
        boardBackgroundColor.b = b;
        boardBackgroundColor.a = 255;
    }

    public int[][] getBoard(){
        return board;
    }

    public void SetBoard(int[][] b){
        board = b;
    }

    public void clear() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                    this.board[row][col] = 0;
            }
        }
    }
}
