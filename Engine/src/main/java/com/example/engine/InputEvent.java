package com.example.engine;

public class InputEvent {
    public int x, y;
    public IInput.InputTouchType type;
    //pubñic InputKeyType key;
    public int index;

    public InputEvent(int x, int y, int index, IInput.InputTouchType touch) {
        this.x = x;
        this.y = y;
        this.type = touch;
        this.index = index;
    }
}
