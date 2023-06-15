package com.example.gamelogic.states;

import com.example.engine.AnchorPoint;
import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.ISound;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.IInteractableCallback;
import com.example.gamelogic.levels.WorldLevelType;
import com.example.gamelogic.utilities.DataToAccess;

public class StartMenuLogic extends AbstractState {
    IFont mainFont;
    IFont secondaryFont;
    Button quickGame;
    Button historyMode;
    Button themeSelectButton;
    Button lastGameButton;



    public StartMenuLogic(IEngine engine) {
        super(engine);
    }

    @Override
    public boolean init() {
        try {
            DataToAccess.Init(engine);
            //Inicializar array de colores desbloqueados
            DataToAccess data = DataToAccess.getInstance();
            String[] keys = {"ForestPalette", "SeaPalette", "CityPalette", "AnimalsPalette", "DayPalette"}; //, "NightPalette"};
            for(int i = 1; i < keys.length; i++){ //No hay boton para el nightpalette
                    unlockedThemes[i] = data.getBool(keys[i-1]);
            }
            int color = data.getInt("CurrentColor");
            backgroundColor = themes[color][0];
            defaultColor = themes[color][1];
            freeColor = themes[color][2];
            figureColor = themes[color][3];
            graphics.setClearColor(themes[color][0].r, themes[color][0].g, themes[color][0].b);

            engine.enableBanner(true);
            int separation = 35;
            mainFont = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 36, true);
            secondaryFont = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 24, false);

            historyMode = new Button(LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2 - separation, 300, 35, engine);
            historyMode.setText("Modo Historia", secondaryFont);
            historyMode.setBackgroundColor(0, 0, 0, 0);
            historyMode.setBorderSize(0);
            historyMode.setHoverColor(200, 200, 200);
            historyMode.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    try {
                        WorldSelectionPageLogic worldSelectionPageLogic = new WorldSelectionPageLogic(engine);
                        engine.setState(worldSelectionPageLogic);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            quickGame = new Button(LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2 + separation, 300, 35, engine);
            quickGame.setText("Partida Rápida", secondaryFont);
            quickGame.setBackgroundColor(0, 0, 0, 0);
            quickGame.setBorderSize(0);
            quickGame.setHoverColor(200, 200, 200);
            quickGame.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    try {
                        SelectLevelLogic levelLogic = new SelectLevelLogic(engine);
                        engine.setState(levelLogic);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            themeSelectButton = new Button(LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2 + 3* separation, 300, 35, engine);
            themeSelectButton.setText("Seleccionar Tema", secondaryFont);
            themeSelectButton.setBackgroundColor(0, 0, 0, 0);
            themeSelectButton.setBorderSize(0);
            themeSelectButton.setHoverColor(200, 200, 200);
            themeSelectButton.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    try {
                        SelectThemeState themeState = new SelectThemeState(engine);
                        engine.setState(themeState);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            // LAST LEVEL ============================================================================
            last_level_played = data.getInt("LastLevelPlayed");
            last_type = data.getInt("LastTypePlayed");

            if(last_type != -1){
                lastGameButton = new Button(LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2 + 5* separation, 300, 35, engine);
                lastGameButton.setText("Última Partida", secondaryFont);
                lastGameButton.setBackgroundColor(0, 0, 0, 0);
                lastGameButton.setBorderSize(0);
                lastGameButton.setHoverColor(200, 200, 200);
                lastGameButton.setCallback(new IInteractableCallback() {
                    @Override
                    public void onInteractionOccur() {
                        try {
                            MainGameLogic logic = new MainGameLogic(engine, last_level_played, WorldLevelType.values()[last_type]);
                            engine.setState(logic);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                addEntity(lastGameButton); //IMPORTANTE GUARDAR EL BOTON
            }



            addEntity(quickGame);
            addEntity(historyMode);
            addEntity(themeSelectButton);

            ISound sound = audio.newMusic(engine.getAssetsPath() + "audio/bgMusic.wav", "musicBg");
            sound.setVolume(1f);
            sound.play(); //It only plays if it's not alrady playing
            engine.setMusic(sound);

            //PUNTUACION: ACCEDO A LOS DATOS GUARDADOS
            for (int i = 0; i < WorldLevelType.values().length; i++){
                String world = WorldLevelType.values()[i].toString();
                puntuaciones[i] = data.getInt("Puntuacion" + world);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void render() {
        super.render();
        //graphics.drawTextCentered(String.valueOf(engine.getLumens()), LOGIC_WIDTH / 2, 90, mainFont);
        graphics.drawTextCentered("Nonogramas", LOGIC_WIDTH / 2, 90, mainFont);
    }
}
