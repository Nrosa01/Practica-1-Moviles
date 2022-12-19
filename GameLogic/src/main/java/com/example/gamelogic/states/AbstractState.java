package com.example.gamelogic.states;

import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IState;
import com.example.engine.InputEvent;
import com.example.gamelogic.entities.Entity;
import com.example.gamelogic.entities.Pointer;
import com.example.gamelogic.utilities.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractState implements IState {
    protected IEngine engine;
    protected IGraphics graphics;
    protected IAudio audio;

    protected static int LOGIC_WIDTH = 400;
    protected static int LOGIC_HEIGHT = 600;

    private List<Entity> entities;
    private Pointer pointer;

    static protected Color backgroundColor = new Color(255, 255, 255);
    static protected Color defaultColor = new Color(123, 123, 123);
    static protected Color freeColor = new Color(123, 123, 255);
    static protected Color figureColor = new Color(255, 123, 123);

    protected int NUM_THEMES = 5;
    static protected boolean[] unlockedThemes = {true, false, false, false, false}; //el primero esta desbloqueado por defecto

    public void DesbloquearColor(int i){
        if(i < NUM_THEMES) // 2 < 3
            unlockedThemes[i] = true;
    }

    protected AbstractState(IEngine engine)
    {
        this.engine = engine;
        this.graphics = engine.getGraphics();
        this.audio = engine.getAudio();
        this.entities = new ArrayList<>();

        if(!graphics.isPortrait())
        {
            LOGIC_WIDTH = 600;
            LOGIC_HEIGHT = 400;
        }

        if (!engine.supportsTouch())
            pointer = new Pointer(engine);

        graphics.setLogicSize(LOGIC_WIDTH, LOGIC_HEIGHT);
    }

    @Override
    public void update(double deltaTime) {
        if(pointer != null)
            pointer.update(deltaTime);

        for (Entity entity : entities)
            entity.update(deltaTime);
    }

    @Override
    public void render() {
        for (Entity entity : entities)
            entity.render();

        if(pointer != null)
            pointer.render();
    }

    @Override
    public void handleInput(List<InputEvent> events) {
        for (InputEvent inputEvent : events) {
            int proccesedX = graphics.windowsXPositionToLogicXPosition(inputEvent.x);
            int proccesedY = graphics.windowsYPositionToLogicYPosition(inputEvent.y);

            for (Entity entity : entities)
                entity.handleInput(proccesedX, proccesedY, inputEvent.type);

            if(pointer != null)
                pointer.handleInput(proccesedX, proccesedY, inputEvent.type);
        }
    }

    protected void addEntity(Entity entity)
    {
        this.entities.add(entity);
    }

    protected void removeEntity(Entity entity)
    {
        this.entities.remove(entity);
    }

    public void saveState(){
        this.engine.setNameState((StatesNames.valueOf( this.getClass().getSimpleName())).ordinal());
    }
}
