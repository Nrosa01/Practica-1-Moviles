package com.example.gamelogic.entities;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IImage;
import com.example.engine.ISound;
import com.example.engine.utilities.FloatLerper;
import com.example.engine.utilities.LerpType;
import com.example.gamelogic.utilities.Color;
import com.example.gamelogic.utilities.Event;
import com.example.gamelogic.utilities.EventManager;
import com.example.gamelogic.utilities.events.OnDamaged;

import org.graalvm.compiler.replacements.Log;

import jdk.nashorn.internal.objects.annotations.Function;
import jdk.vm.ci.code.site.Call;

public class NonogramBoard extends Board {
    private float timeLongPress = 350;
    private final int numOfStates = 3;
    private int borderBoardSize;
    private Color borderColor;
    private int borderWidth = 2;
    private int[][] solvedPuzzle;
    private String[] rowsText;
    private String[] colsText;
    private IFont font;
    private FloatLerper endTransitionLerper;
    private FloatLerper wrongTilesTimer;
    private boolean isWin;
    private float borderBoardRatio = 0.2f;
    private int initialWidth;
    private Color textColor;
    private int missingCells, badCellNumber;
    private int maxRow = 0;
    private int maxCol = 0;
    IImage blockedCell;
    ISound winSound;
    ISound selectCell;
    Callback winCallBack;

    // Cada fila es un desbloqueable
    // cada columna es un color (background, not pressed, free, figure)

    private Color backgroundColor = new Color(255, 255, 255);
    private Color defaultColor = new Color(123, 123, 123);
    private Color freeColor = new Color(123, 123, 255);
    private Color figureColor = new Color(255, 123, 123);

    public void setColors(Color c, Color c1, Color c2, Color c3) {
        backgroundColor = c;
        defaultColor = c1;
        freeColor = c2;
        figureColor = c3;
    }


    public NonogramBoard(IEngine engine, int[][] solvedPuzzle, int width, int gapSize, IFont font, Callback winCallback) {
        super(engine, solvedPuzzle.length, solvedPuzzle[0].length, width, gapSize);

        this.winCallBack = winCallback;

        this.initialWidth = width;
        setWidth(width);
        this.solvedPuzzle = solvedPuzzle;
        borderColor = new Color();

        generateRowsText();
        generateColsText();
        calculateMaxRowAndMaxCol();

        this.font = font;
        endTransitionLerper = new FloatLerper(borderBoardRatio, 0, 0.55f, LerpType.EaseOut);
        wrongTilesTimer = new FloatLerper(0, 5, 10, LerpType.Linear); // Voy a usar esto como un timer
        wrongTilesTimer.setPaused(true);
        textColor = new Color();

        blockedCell = graphics.newImage(engine.getAssetsPath() + "images/blockedTile.png");
        winSound = audio.newSound(engine.getAssetsPath() + "audio/winSound.wav", "winSound");
        selectCell = audio.newSound(engine.getAssetsPath() + "audio/selectSound.wav", "selectSound");
    }

    public boolean getIsWin() {
        return isWin;
    }

    @Override
    public void setWidth(int newWidth) {
        borderBoardSize = (int) (newWidth * borderBoardRatio);
        this.width = newWidth - borderBoardSize;
        init();
    }

    private void generateRowsText() {
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

    private void generateColsText() {
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
        setBoardBackgroundColor(backgroundColor.r, backgroundColor.g, backgroundColor.b); //NO FUNCA ==> NEGRO??
        setBoardBackgroundColor(255, 0, 0); //NO FUNCA ==> AMARILLO?

        this.posX += borderBoardSize / 2;
        this.posY += borderBoardSize / 2;

        // Draw the border board
        super.render();
        RenderTextArea();
        RenderBordersStroke();

        this.posX -= borderBoardSize / 2;
        this.posY -= borderBoardSize / 2;

        renderErrorLabels();
    }

    private void renderErrorLabels() {
        //if (isWin || wrongTilesTimer.getPaused())
        return;

        //graphics.setColor(255, 50, 50);
        //graphics.drawTextCentered("Te faltan " + missingCells + " casillas", posX, posY - height / 2 - borderBoardSize, font);
        //graphics.drawTextCentered("Tienes mal " + badCellNumber + " casillas", posX, posY - height / 2 - borderBoardSize + graphics.getFontHeight(font), font);
    }

    private void RenderTextArea() {
        // Render text background
        graphics.setColor(backgroundColor.r, backgroundColor.g, backgroundColor.b); //255, 255, 255
        graphics.fillRectangle(posX - width / 2 - borderBoardSize / 2, posY, borderBoardSize, height);
        graphics.fillRectangle(posX, posY - height / 2 - borderBoardSize / 2, width, borderBoardSize);

        graphics.setColor(textColor.r, textColor.g, textColor.b, textColor.a);

        renderRowText();
        renderColText();
    }

    private void calculateMaxRowAndMaxCol() {
        for (int row = 0; row < rows; row++) {
            String[] aux = rowsText[row].split(" ");
            int count = aux.length;

            if (count > maxRow) {
                maxRow = count;
            }
        }

        for (int col = 0; col < cols; col++) {
            String[] aux = colsText[col].split(" ");
            int count = aux.length;

            if (count > maxCol) {
                maxCol = count;
            }
        }
    }

    private void renderRowText() {
        // We divide the row in maxRow areas, each text will be in one area from right to left
        int areaWidth = borderBoardSize / maxRow;

        // For each row, render character by character
        int rightestCellX = posX - width / 2 - areaWidth / 2;
        for (int row = 0; row < rows; row++) {
            String[] texts = rowsText[row].split(" ");
            int count = 0;
            for (int character = texts.length - 1; character >= 0; character--) {
                graphics.drawTextCentered(texts[character], rightestCellX - areaWidth * count, getCellPosY(row), font);
                count++;
            }
        }
    }

    private void renderColText() {
        // The same we did for rows, but now we do it for cols from bottom to top
        int areaHeight = borderBoardSize / maxCol;

        int bottomestCellY = posY - height / 2 - areaHeight / 2;
        for (int col = 0; col < cols; col++) {
            String[] texts = colsText[col].split(" ");
            int count = 0;
            for (int character = texts.length - 1; character >= 0; character--) {
                graphics.drawTextCentered(texts[character] + "", getCellPosX(col), bottomestCellY - areaHeight * count, font);
                count++;
            }
        }
    }

    private void RenderBordersStroke() {
        // Render borders on top of board ==> BLACK
        graphics.setColor(borderColor.r, borderColor.g, borderColor.b, borderColor.a);
        graphics.drawRectangle(posX, posY, width, height, borderWidth);
        graphics.drawRectangle(posX - width / 2 - borderBoardSize / 2, posY, borderBoardSize, height, borderWidth);
        graphics.drawRectangle(posX, posY - height / 2 - borderBoardSize / 2, width, borderBoardSize, borderWidth);
    }

    @Override
    protected void OnCellClicked(int row, int col) {
        if (isWin)
            return;
        //System.out.println("Clicked on cell: " + row + " " + col);
        timePressBoard[row][col] = System.currentTimeMillis();

        // System.out.println(row + " / "+ col);

    }

    @Override
    protected void OnCellReleased(int row, int col) {
        if (isWin)
            return;
        long releaseTime = System.currentTimeMillis();

      /*  System.out.println(releaseTime);
        System.out.println(timePressBoard[row][col]);*/
        System.out.println(releaseTime - timePressBoard[row][col]);


        if (System.currentTimeMillis() - timePressBoard[row][col] < timeLongPress) {
            // 0 nada, 1 pulsado, 2 bloqueada, 3 error
            if (board[row][col] != 0)
                board[row][col] = 0;
            else
                board[row][col] = 1;
        } else {
            board[row][col] = 2;
        }
        isWin = updateBoardState(true);


        if (isWin)
            winSound.play();
        else
            selectCell.play();

        timePressBoard[row][col] = 0;

        if (this.badCellNumber > 0) {
            final Event event = new OnDamaged();
            EventManager.callEvent(event);
        }
    }

    private void setColorGivenState(int state) {
        setCellImg(null);

        switch (state) {
            //DEFUALT COLOR ==> WASN'T PRESSED
            case 0:
                if (!isWin)
                    graphics.setColor(defaultColor.r, defaultColor.g, defaultColor.b); //Gray
                else
                    graphics.setColor(255, 255, 255);
                break;
            // FREE SQUARE WAS PRESSED
            case 1:
                graphics.setColor(freeColor.r, freeColor.g, freeColor.b); //Light Blue
                break;
            // ?????????????????????????????
            case 2:
                if (!isWin) {
                    graphics.setColor(23, 23, 23);
                    setCellImg(blockedCell);
                } else
                    graphics.setColor(255, 255, 255);
                break;
            // FIGURE SQUARE WAS PRESSED
            case 3:
                if (!isWin)
                    graphics.setColor(figureColor.r, figureColor.g, figureColor.b); //Light red
                else
                    graphics.setColor(255, 255, 255);
//            case 4:
//                if (!isWin)
//                    graphics.setColor(123, 255, 123);
//                elses
//                    graphics.setColor(255, 255, 255);


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
        setColorGivenState(board[row][col]);
    }

    // Updates board state, update missingCells and badCells, also returns true if win is satisfied
    private boolean updateBoardState(boolean updateStats) {
        boolean win = true;

        if (updateStats) {
            badCellNumber = 0;
            missingCells = 0;
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (solvedPuzzle[row][col] == 1 && board[row][col] != 1) {
                    if (updateStats)
                        missingCells++;
                    win = false;
                }

                if (solvedPuzzle[row][col] != 1 && (board[row][col] == 1 /*|| board[row][col] == 3*/)) {
                    if (updateStats)
                        badCellNumber++;
                    win = false;
                }
            }
        }

        if (win) {
            winCallBack.callback();
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (solvedPuzzle[row][col] != 1) {
                        board[row][col] = 0;
                    }
                }
            }
        } else {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (solvedPuzzle[row][col] != 1 && board[row][col] == 1) {
                        board[row][col] = 3;
                    }
                }
            }

            wrongTilesTimer.restart();
        }

        return win;
    }

    public void checkSolution() {
        if (isWin)
            return;

        updateBoardState(true);
    }

    @Override
    public void update(double deltaTime) {
        if (isWin) {
            if (!endTransitionLerper.isFinished()) {
                this.endTransitionLerper.update(deltaTime);
                borderBoardRatio = endTransitionLerper.getValue();
                setWidth(initialWidth);
                textColor.a = (int) (255 * (1.0f - endTransitionLerper.getProgress()));
                borderColor.a = (int) (255 * (1.0f - endTransitionLerper.getProgress()));
                super.init();
            } else {
                textColor.a = 0;
                borderColor.a = 0;
                borderBoardRatio = 0;
            }
        } else
            wrongTilesTimer.update(deltaTime);

        if (wrongTilesTimer.isFinished()) {
            wrongTilesTimer.restart();
            wrongTilesTimer.setPaused(true);

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (board[row][col] == 3) {
                        board[row][col] = 0;
                    }
                }
            }

            isWin = updateBoardState(false);
        }
    }

    @Override
    public void OnPointerDown(int x, int y) {
        this.posX += borderBoardSize / 2;
        this.posY += borderBoardSize / 2;

        super.OnPointerDown(x, y);

        this.posX -= borderBoardSize / 2;
        this.posY -= borderBoardSize / 2;
    }

    @Override
    public void OnPointerUp(int x, int y) {
        this.posX += borderBoardSize / 2;
        this.posY += borderBoardSize / 2;

        super.OnPointerUp(x, y);

        this.posX -= borderBoardSize / 2;
        this.posY -= borderBoardSize / 2;
    }
}
