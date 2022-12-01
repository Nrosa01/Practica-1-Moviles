package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.ISound;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.IInteractableCallback;

public class StartMenuLogic extends AbstractState {
    IFont mainFont;
    IFont secondaryFont;
    Button quickGame;
    Button historyMode;

    public StartMenuLogic(IEngine engine) {
        super(engine);
    }

    @Override
    public boolean init() {
        try {
            int separation = 35;
            mainFont = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 36, true);
            secondaryFont = graphics.newFont(engine.getAssetsPath() + "fonts/Roboto-Regular.ttf", 24, false);
            quickGame = new Button(LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2 + separation, 300, 35, engine);
            quickGame.setText("Partida Rápida", secondaryFont);
            quickGame.setBackgroundColor(0, 0, 0, 0);
            quickGame.setBorderSize(0);
            quickGame.setHoverColor(200, 200, 200);
            quickGame.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    try {
                        engine.setState(new SelectLevelLogic(engine));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            historyMode = new Button(LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2 - separation, 300, 35, engine);
            historyMode.setText("Modo Historia", secondaryFont);
            historyMode.setBackgroundColor(0, 0, 0, 0);
            historyMode.setBorderSize(0);
            historyMode.setHoverColor(200, 200, 200);
            historyMode.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    try {
                        engine.setState(new WorldSelectionPageLogic(engine));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            addEntity(quickGame);
            addEntity(historyMode);

            ISound sound = audio.newMusic(engine.getAssetsPath() + "audio/bgMusic.wav", "musicBg");
            sound.setVolume(1f);
            sound.play(); //It only plays if it's not alrady playing
          

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void render() {
        graphics.drawTextCentered("Nonogramas", LOGIC_WIDTH / 2, 90, mainFont);
        super.render();
    }
}
