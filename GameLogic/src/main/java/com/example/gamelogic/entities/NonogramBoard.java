package com.example.gamelogic.entities;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.gamelogic.utilities.Color;

public class NonogramBoard extends Board {
    int[][] nonogramCellStates;
    private final int numOfStates = 3;
    int borderBoardSize;
    private Color borderColor;
    private int borderWidth = 2;
    int[][] solvedPuzzle;
    String[] rowsText;
    String[] colsText;
    IFont font;

    public NonogramBoard(IEngine engine, int[][] solvedPuzzle, int width, int gapSize, IFont font) {
        super(engine, solvedPuzzle.length, solvedPuzzle[0].length, width, gapSize);

        borderBoardSize = (int) (width * 0.2);
        this.width = width - borderBoardSize;
        this.solvedPuzzle = solvedPuzzle;
        init();

        nonogramCellStates = new int[rows][cols];
        borderColor = new Color();

        generateRowsText();
        generateColsText();

        this.font = font;
    }

    private void generateRowsText()
    {
        rowsText = new String[rows];

        for (int row = 0; row < rows; row++) {
            int count = 0;
            String text = "";
            for (int col = 0; col < cols; col++) {
                if (solvedPuzzle[row][col] == 1) {
                    count++;
                } else {
                    if (count > 0) {
                        text += count + " ";
                        count = 0;
                    }
                }
            }
            if (count > 0) {
                text += count + " ";
            }
            rowsText[row] = text;
        }
    }

    private void generateColsText()
    {   
        colsText = new String[cols];
        
        for (int col = 0; col < cols; col++) {
            int count = 0;
            String text = "";
            for (int row = 0; row < rows; row++) {
                if (solvedPuzzle[row][col] == 1) {
                    count++;
                } else {
                    if (count > 0) {
                        text += count + " ";
                        count = 0;
                    }
                }
            }
            if (count > 0) {
                text += count + " ";
            }
            colsText[col] = text;
        }
    }

    @Override
    public void render() {
        // Draw the border board
        super.render();
        RenderTextArea();
        RenderBordersStroke();
    }

    private void RenderTextArea()
    {
        // Render text background
        graphics.setColor(255, 255, 255);
        graphics.fillRectangle(posX - width/2 - borderBoardSize/2, posY, borderBoardSize, height);
        graphics.fillRectangle(posX, posY -height/2 - borderBoardSize/2, width, borderBoardSize);

        graphics.setColor(0, 0, 0);

        // Render rows text from right to left
        for (int row = 0; row < rows; row++) {
            String text = rowsText[row];
            int textWidth = graphics.getStringWidth(text, font);
            int textPosX = posX - width/2 - borderBoardSize/2 + (borderBoardSize - textWidth)/2;
            int textPosY = getCellPosY(row);
            graphics.drawTextCentered(text, textPosX, textPosY, font);
        }

        // Render cols text from bottom to top
        for (int col = 0; col < cols; col++) {
            String text = colsText[col];
            String[] texts = text.split(" ");

            int numOfTexts = texts.length;
            for(int i = 0; i < numOfTexts; i++) {
                String textToRender = texts[(numOfTexts - 1) - i];
                int textHeight = graphics.getFontHeight(font);
                int textPosX = getCellPosX(col);
                int textPosY = posY - height/2 - borderBoardSize/2 + (borderBoardSize - textHeight)/2 - (i * textHeight);
                graphics.drawTextCentered(textToRender, textPosX, textPosY, font);
            }

        }
    }

    private void RenderBordersStroke()
    {
        // Render borders on top of board
        graphics.setColor(borderColor.r, borderColor.g, borderColor.b);
        graphics.drawRectangle(posX, posY, width, height, borderWidth);
        graphics.drawRectangle(posX - width/2 - borderBoardSize/2, posY, borderBoardSize, height, borderWidth);
        graphics.drawRectangle(posX, posY -height/2 - borderBoardSize/2, width, borderBoardSize, borderWidth);
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

    public void setBorderColor(int r, int g, int b) {
        borderColor.r = r;
        borderColor.g = g;
        borderColor.b = b;
        borderColor.a = 255;
    }

    @Override
    protected void OnCellRender(int row, int col) {
        setColorGivenState(nonogramCellStates[row][col]);
    }
}
