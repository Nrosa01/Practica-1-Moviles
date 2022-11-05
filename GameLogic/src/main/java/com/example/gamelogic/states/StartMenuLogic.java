package com.example.gamelogic.states;

import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IInput;
import com.example.engine.ISound;
import com.example.engine.IState;
import com.example.engine.InputEvent;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.IInteractableCallback;
import com.example.gamelogic.entities.Pointer;

import java.util.List;

public class StartMenuLogic extends AbstractState {
    IFont testFont;
    Button button;
    Pointer pointer;

    public StartMenuLogic(IEngine engine) {
        super(engine);
    }

    @Override
    public boolean init() {
        try {
            if (!engine.supportsTouch())
                pointer = new Pointer(engine);
            testFont = graphics.newFont(engine.getAssetsPath() + "fonts/Antihero.ttf", 24, false);
            button = new Button(LOGIC_WIDTH / 2, LOGIC_HEIGHT / 2, 100, 35, engine);
            button.setText("Jugar", testFont);
            button.setBackgroundColor(0, 0, 0, 0);
            button.setBorderSize(0);
            button.setHoverColor(200, 200, 200);
            button.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    try {
                        engine.setState(new SelectLevelLogic(engine));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            ISound sound = audio.newMusic(engine.getAssetsPath() + "audio/bgMusic.wav", "musicBg");
            sound.setVolume(0.25f);
            sound.play(); //It only plays if it's not alrady playing
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(double deltaTime) {
        button.update((float) deltaTime);

        if (pointer != null)
            pointer.update((float) deltaTime);
    }

    @Override
    public void render() {
        graphics.drawTextCentered("Nonogramas", LOGIC_WIDTH / 2, 90, testFont);

        button.render();
        if (pointer != null)
            pointer.render();
    }

    @Override
    public void handleInput(List<InputEvent> events) {
        for (InputEvent inputEvent : events) {
            int proccesedX = graphics.windowsXPositionToLogicXPosition(inputEvent.x);
            int proccesedY = graphics.windowsYPositionToLogicYPosition(inputEvent.y);

            button.handleInput(proccesedX, proccesedY, inputEvent.type);


            if (pointer != null)
                pointer.handleInput(proccesedX, proccesedY, inputEvent.type);
        }
    }
}
