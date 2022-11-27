package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.InputEvent;

import java.util.List;

public class HistoryModeSelection extends AbstractState {
    protected HistoryModeSelection(IEngine engine) {
        super(engine);
    }

    @Override
    public boolean init() {
        return false;
    }
}
