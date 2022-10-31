package com.example.gamelogic.states;

import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IState;

public abstract class AbstractState implements IState {
    protected IEngine engine;
    protected IGraphics graphics;
    protected IAudio audio;

    protected static final int LOGIC_WIDTH = 400;
    protected static final int LOGIC_HEIGHT = 600;

    protected AbstractState(IEngine engine)
    {
        this.engine = engine;
        this.graphics = engine.getGraphics();
        this.audio = engine.getAudio();

        graphics.setLogicSize(LOGIC_WIDTH, LOGIC_HEIGHT);
    }

}
