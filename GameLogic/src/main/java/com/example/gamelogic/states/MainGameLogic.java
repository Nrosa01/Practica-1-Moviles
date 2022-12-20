package com.example.gamelogic.states;

import static com.example.gamelogic.levels.WorldLevelType.Animals;
import static com.example.gamelogic.levels.WorldLevelType.City;
import static com.example.gamelogic.levels.WorldLevelType.Forest;
import static com.example.gamelogic.levels.WorldLevelType.Sea;

import com.example.engine.AnchorPoint;
import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IImage;
import com.example.engine.InputEvent;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.Callback;
import com.example.gamelogic.entities.IInteractableCallback;
import com.example.gamelogic.entities.LivesPanel;
import com.example.gamelogic.entities.NonogramBoard;
import com.example.gamelogic.levels.NonogramGenerator;
import com.example.gamelogic.levels.WorldLevelType;
import com.example.gamelogic.utilities.DataToAccess;
import com.example.gamelogic.utilities.EventHandler;
import com.example.gamelogic.utilities.EventManager;
import com.example.gamelogic.utilities.Listener;
import com.example.gamelogic.utilities.events.OnDamaged;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainGameLogic extends AbstractState implements Listener {

    String level;
    NonogramBoard board;
    Button returnButton;
    Button watchVid;
    Button winReturnButton;
    Button nextLevelButton;
    Button shareButton;
    IFont font;
    IFont boardFont;
    IFont congratsFont;
    IImage arrow;
    IImage search;

    IImage fullLive;
    IImage emptyLive;
    LivesPanel livesPanel;
    boolean gameWin = false;
    boolean random = false;
    IInteractableCallback returnCallback;
    IInteractableCallback watchVidCallback;
    IInteractableCallback nextLevelCallback;

    int numLevel;
    WorldLevelType type;
    int row;
    int lives = -1;

    boolean savedBoard = false;
    int[][] savedBoardState;
    int[][] savedBoardSol;


    private void InitRandomGame(final IEngine engine, String level) {

        this.level = level;
        this.random = true;

        returnCallback = new IInteractableCallback() {
            @Override
            public void onInteractionOccur() {
                try {
                    SelectLevelLogic selectLogic = new SelectLevelLogic(engine);

                    engine.setState(selectLogic);

                    //engine.setState(new WorldLevelSelectionPageLogic(engine,type));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        final String level_ = level;
        nextLevelCallback = new IInteractableCallback() {
            @Override
            public void onInteractionOccur() {
                try {

                    MainGameLogic logic = new MainGameLogic(engine, level_);

                    engine.setState(logic);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void InitHistoryGame(final IEngine engine, int numLevel, final WorldLevelType type) {
        this.row = numLevel / 5;
        this.numLevel = numLevel;
        this.type = type;
        this.level = getLevelName(numLevel, row);


        final IEngine engineAux = this.engine;
        this.watchVidCallback = new IInteractableCallback() {
            @Override
            public void onInteractionOccur() {
                try {
                    livesPanel.restoreLive();
                    engineAux.showVid();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        returnCallback = new IInteractableCallback() {
            @Override
            public void onInteractionOccur() {
                try {
                    WorldLevelSelectionPageLogic selectLogic = new WorldLevelSelectionPageLogic(engine, type);

                    engine.setState(selectLogic);

                    //engine.setState(new WorldLevelSelectionPageLogic(engine,type));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        final int nextLevel = numLevel + 1;

        nextLevelCallback = new IInteractableCallback() {
            @Override
            public void onInteractionOccur() {
                try {

                    MainGameLogic logic = new MainGameLogic(engine, nextLevel, type);

                    engine.setState(logic);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

    }

    public MainGameLogic(final IEngine engine, String level) {
        super(engine);
        InitRandomGame(engine, level);

    }

    public MainGameLogic(final IEngine engine ,String level,int[][] boardState, int[][] solvedLevel, int lives) {
        super(engine);

        this.savedBoard = true;

        savedBoardSol = solvedLevel;
        savedBoardState = boardState;

        this.lives = lives;

        this.InitRandomGame(engine,level);

    }




    public MainGameLogic(final IEngine engine, int numLevel,final WorldLevelType type, int[][] boardState,int lives) {

        super(engine);

        this.savedBoard = true;
        this.savedBoardState = boardState;

        this.lives = lives;

        InitHistoryGame(engine,numLevel,type);



    }


    public MainGameLogic(final IEngine engine, int numLevel, final WorldLevelType type) {


        super(engine);
        InitHistoryGame(engine, numLevel, type);

    }

    public void setReturnCallback(IInteractableCallback returnCallback) {
        if (returnButton != null)
            returnButton.setCallback(returnCallback);

        this.returnCallback = returnCallback;
    }


    @EventHandler
    public void onDamaged(OnDamaged eventArgs) {
        livesPanel.takeLive();
        if (!livesPanel.isAlive()) {
            board.clear();
            livesPanel.restoreLives();
        }
    }

    @Override
    public boolean init() {
        engine.enableBanner(false);
        try {


            EventManager.register(this);

            font = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 24, false);
            boardFont = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 18, true);
            congratsFont = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 36, true);
            arrow = graphics.newImage(engine.getAssetsPath() + "images/arrow.png");
            search = graphics.newImage(engine.getAssetsPath() + "images/search.png");
            IImage share = graphics.newImage(engine.getAssetsPath() + "images/share.png");
            emptyLive = graphics.newImage(engine.getAssetsPath() + "images/heart-empty.png");
            fullLive = graphics.newImage(engine.getAssetsPath() + "images/heart-full.png");


            shareButton = new Button(0, LOGIC_HEIGHT / 2 + 10, 40, 40, engine);
            shareButton.setAnchorPoint(AnchorPoint.Middle);
            shareButton.setImage(share);
            shareButton.setBorderSize(0);
            shareButton.setBackgroundColor(0,0,0,0);
            shareButton.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    if (numLevel >= 0) {
                        int level = (numLevel + 1);
                        engine.shareText("Me he pasado el nivel " + level + " de la categoría " + type.toString() + " en Nonogram. ¡Descargatelo gratis!");
                    } else
                        engine.shareText("Me he pasado el nivel " + level + " en Nonogram. ¡Descargatelo gratis!");

                }
            });

            if (!graphics.isPortrait()) {
                shareButton.setAnchorPoint(AnchorPoint.DownLeft);
                shareButton.setPosX(40);
                shareButton.setPosY(-40);
            }

            int numLifes = 3;
            int livesPanelWidth = Math.min(LOGIC_WIDTH, LOGIC_HEIGHT) / 4;
            int livesPanelHeight = livesPanelWidth / numLifes;
            int livesPanelYPos = -livesPanelHeight / 2 - 10;
            int livesPanelXPos = -livesPanelWidth / 2 - 10;


            livesPanel = new LivesPanel(engine, livesPanelXPos, livesPanelYPos, livesPanelWidth, livesPanelHeight, numLifes, fullLive, emptyLive);
            livesPanel.setAnchorPoint(AnchorPoint.DownRight);
            if(lives != -1)
                livesPanel.setNumLives(lives);
            //addEntity(livesPanel);

            returnButton = new Button(25, 25, 30, 30, engine);
            returnButton.setImage(arrow);
            returnButton.setPadding(10, 10);
            returnButton.setBackgroundColor(0, 0, 0, 0);
            returnButton.setBorderSize(0);
            returnButton.setHoverColor(205, 205, 205);
            returnButton.setAnchorPoint(AnchorPoint.UpperLeft);
            returnButton.setCallback(returnCallback);

            watchVid = new Button(25, -10, 30, 30, engine);
            watchVid.setImage(arrow);
            watchVid.setPadding(10, 10);
            watchVid.setBackgroundColor(0, 0, 0, 0);
            watchVid.setBorderSize(0);
            watchVid.setHoverColor(205, 205, 205);
            watchVid.setAnchorPoint(AnchorPoint.DownLeft);
            watchVid.setCallback(watchVidCallback);

            winReturnButton = new Button(LOGIC_WIDTH / 2, LOGIC_HEIGHT - 75, 100, 50, engine);
            winReturnButton.setText("Volver", font);
            winReturnButton.setBackgroundColor(0, 0, 0, 0);
            winReturnButton.setBorderSize(0);
            winReturnButton.setHoverColor(205, 205, 205);
            winReturnButton.setCallback(returnCallback);


            nextLevelButton = new Button(LOGIC_WIDTH / 2, LOGIC_HEIGHT - 35, 100, 50, engine);
            nextLevelButton.setText("Siguiente Nivel", font);
            nextLevelButton.setBackgroundColor(0, 0, 0, 0);
            nextLevelButton.setBorderSize(0);
            nextLevelButton.setHoverColor(205, 205, 205);
            nextLevelButton.setCallback(nextLevelCallback);


            int[][] level;
            if (random) {
                if (savedBoard)
                    level = savedBoardSol;
                else
                    level = loadLevel();
            } else
                level = loadLevel();

            if (level == null) return false;


            final int numDesbloq = numLevel + 2;
            int boardWidth = Math.min(LOGIC_WIDTH, LOGIC_HEIGHT) - 20;

            board = new NonogramBoard(engine, level, boardWidth, 2, boardFont, new Callback() {
                @Override
                public void callback() {
                    if (numLevel + 1 > 1) {
                        //no hay paleta para day y night
                        if(type.ordinal() < 5) {
                            DataToAccess.getInstance().setBool(type.toString() + "Palette", true);
                            int theme = type.ordinal() + 1;
                            unlockedThemes[theme] = true;
                        }
                    }

                    if (!random) {
                        final int n ;
                        if(type == WorldLevelType.Day || type == WorldLevelType.Night)
                            n = numDesbloq-5;
                        else
                            n = numDesbloq;
                        DataToAccess.getInstance().setMaxLevel(type.toString(), n);

                    }
                }
            });
            if (savedBoard)
                board.SetBoard(savedBoardState);
            board.setColors(backgroundColor, defaultColor, freeColor, figureColor);
            board.setPosX(LOGIC_WIDTH / 2);
            board.setPosY(LOGIC_HEIGHT / 2);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            EventManager.unregister(this);
            return false;
        }
    }

    private int[][] loadLevel() {
        if (random) {
            int rows = Integer.parseInt(level.split("x")[0]);
            int cols = Integer.parseInt(level.split("x")[1]);
            return NonogramGenerator.GenerateLevel(rows, cols);
        } else {
            InputStream is = engine.openFile(level);
            if (is == null)
                return null;

            // Read width and height
            try {
                String file = "";
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.endsWith(" "))
                        line += " ";
                    file += line;
                }

                String[] fileDivied = file.split(" ");
                int width = Integer.parseInt(fileDivied[0]);
                int height = Integer.parseInt(fileDivied[1]);

                int[][] level = new int[width][height];
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        level[i][j] = Integer.parseInt(fileDivied[i * height + j + 2]);
                    }
                }

                br.close();
                is.close();
                return level;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        board.update(deltaTime);

        if (!board.getIsWin()) {
            returnButton.update(deltaTime);
        } else {
            if (!graphics.isPortrait()) {
                int boardWidth = Math.min(LOGIC_WIDTH, LOGIC_HEIGHT) / 2 - 20;
                board.setWidth(boardWidth);
            }

            winReturnButton.update(deltaTime);

            nextLevelButton.update(deltaTime);
        }
        watchVid.update(deltaTime);
    }

    @Override
    public void render() {
        super.render();
        board.render();

        graphics.setColor(0, 0, 0);
        if (!board.getIsWin()) {
            graphics.setAnchorPoint(AnchorPoint.UpperLeft);
            graphics.drawText("Rendirse", 45, 33, font);
            graphics.setAnchorPoint(AnchorPoint.None);

            returnButton.render();
            watchVid.render();
            livesPanel.render();
        } else {
            graphics.drawTextCentered("¡Enhorabuena!", LOGIC_WIDTH / 2, 50, congratsFont);
            winReturnButton.render();

            if (!random)
                nextLevelButton.render();
            shareButton.render();
        }
    }

    @Override
    public void handleInput(List<InputEvent> events) {
        for (InputEvent inputEvent : events) {
            int proccesedX = graphics.windowsXPositionToLogicXPosition(inputEvent.x);
            int proccesedY = graphics.windowsYPositionToLogicYPosition(inputEvent.y);

            board.handleInput(proccesedX, proccesedY, inputEvent.type);

            if (!board.getIsWin()) {
                watchVid.handleInput(proccesedX, proccesedY, inputEvent.type);
                returnButton.handleInput(proccesedX, proccesedY, inputEvent.type);
            } else {
                if (winReturnButton != null)
                    winReturnButton.handleInput(proccesedX, proccesedY, inputEvent.type);
                nextLevelButton.handleInput(proccesedX, proccesedY, inputEvent.type);
                shareButton.handleInput(proccesedX, proccesedY, inputEvent.type);
            }

        }
    }

    private String getLevelName(int index, int i) {
        int cells = (i + 1) * 5;
        String typeToLower = type.toString().toLowerCase();
        String filename = typeToLower + cells + "x" + cells + "-" + ((index % 5) + 1) + ".txt";
        return "levels/" + typeToLower + "/" + filename;
    }

    @Override
    public void saveState() {
        super.saveState();


        int[][] boardState = board.getBoard();
        int[][] solution = board.getSolvedPuzzle();


        int row = boardState.length;
        int col = solution[0].length;


        Integer[][] bI = new Integer[row][col];
        Integer[][] sP = new Integer[row][col];
        for (int x = 0; x < row; x++)
            for (int y = 0; y < col; y++) {
                bI[x][y] = boardState[x][y];
                sP[x][y] = solution[x][y];
            }

        engine.addSimpleData("random", random);
        engine.add2DArrayData("board", bI);
        engine.addSimpleData("lives", livesPanel.getNumLives());
        if (random) {
            engine.add2DArrayData("boardSolution", sP);
            engine.addSimpleData("level", level);
        } else {
            engine.addSimpleData("numLevel", numLevel);
            engine.addSimpleData("type", type.ordinal());
        }

    }


}