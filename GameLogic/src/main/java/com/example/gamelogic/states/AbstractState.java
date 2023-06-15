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

    //EXAMEN EJER 3
    protected static boolean contrarreloj = false;
    protected static double timeLeftInContrarreloj = 15 * 60; //segundos
    protected static double bestContrarreloj = 15 * 60; //segundos
    protected static String[] contrarrelojLevels = {"2x2", "2x3", "3x3", "3x4", "4x4"};
    protected static int currentContrarrelojLevel = 0;

    public static IState lastLevel = null;

    protected AbstractState(IEngine engine)
    {
        this.engine = engine;
        this.graphics = engine.getGraphics();
        this.audio = engine.getAudio();

        graphics.setLogicSize(LOGIC_WIDTH, LOGIC_HEIGHT);
    }

    //EJER 2 ==> CREO QUE NO HACE FALTA
    public void saveState(){
        //this.engine.setNameState((StatesNames.valueOf( this.getClass().getSimpleName())).ordinal());
    }

}
