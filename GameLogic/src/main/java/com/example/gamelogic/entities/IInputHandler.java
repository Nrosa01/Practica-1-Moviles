package com.example.gamelogic.entities;

import com.example.engine.IInput;

public interface IInputHandler {
    void handleInput(int x, int y, IInput.InputTouchType touchType);
}
