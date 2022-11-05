package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.InputEvent;

import java.util.List;

public class clasederelleno extends AbstractState {
    public clasederelleno(IEngine engine) {
        super(engine);
    }

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        graphics.fillRectangle(0,0,400,30);
    }

    @Override
    public void handleInput(List<InputEvent> events) {

    }
}
