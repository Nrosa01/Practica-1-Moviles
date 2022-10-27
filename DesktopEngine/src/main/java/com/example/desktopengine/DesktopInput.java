package com.example.desktopengine;

import com.example.engine.IInput;
import com.example.engine.InputEvent;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DesktopInput implements IInput {
    private List<InputEvent> eventos = new ArrayList<>();

    public void addEvent(MouseEvent event){
        InputTouchType tipo = null;

        if (event.getID() == MouseEvent.MOUSE_CLICKED)
            tipo = InputTouchType.TOUCH_DOWN;
        else if(event.getID() == MouseEvent.MOUSE_RELEASED)
            tipo = InputTouchType.TOUCH_UP;
        else if(event.getID() == MouseEvent.MOUSE_MOVED)
            tipo = InputTouchType.TOUCH_MOVE;

        eventos.add(new InputEvent(event.getX(), event.getY(), 0, tipo));
    }

    public List<InputEvent> getEventList(){
        return eventos;
    }

    public void clear()
    {
        eventos.clear();;
    }
}
