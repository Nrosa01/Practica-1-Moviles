package com.example.engine;

import com.example.engine.utilities.FloatLerper;
import com.example.engine.utilities.LerpType;

import java.util.List;

public class StateManager implements IState {
    private IState currentState;
    private IState oldState;
    private IGraphics graphics;
    private FloatLerper lerper;
    boolean isOnTransition;

    public StateManager(IEngine engine, float transitionDuration) {
        graphics = engine.getGraphics();
        lerper = new FloatLerper(0, 255, transitionDuration, LerpType.EaseInOut);
        isOnTransition = false;
    }

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public void update(double deltaTime) {
        lerper.update(deltaTime);
        currentState.update(deltaTime);
    }

    @Override
    public void render() {
        if (isOnTransition) {
            graphics.setGraphicsAlpha(255 - (int) lerper.getValue());

            if (oldState != null)
                oldState.render();

            graphics.setGraphicsAlpha((int) lerper.getValue());

            if(lerper.isFinished())
            {
                isOnTransition = false;
                oldState = null; // Para que el recolector se encargue
            }
        }

        currentState.render();
        graphics.setGraphicsAlpha(255);
    }

    @Override
    public void handleInput(List<InputEvent> events) {
        currentState.handleInput(events);
    }

    public void setState(IState state) throws Exception {
        if (state.init()) {
            this.oldState = currentState;
            this.currentState = state;
            lerper.restart();
            isOnTransition = true;
        } else
            throw new Exception("State didn't init correctly");
    }

    public IState getState(){
        return this.currentState;
    }
}
