package com.example.desktopengine;

import com.example.engine.*;

public class DesktopEngine implements IEngine {
    IGraphics graphics;
    IAudio audio;
    IState state;

    public DesktopEngine(int wWidth, int wHeight, String wTittle)
    {

    }

    public void run()
    {
        
    }

    @Override
    public IGraphics getGraphics() {
        return null;
    }

    @Override
    public IAudio getAudio() {
        return null;
    }

    @Override
    public IState getState() {
        return null;
    }
}