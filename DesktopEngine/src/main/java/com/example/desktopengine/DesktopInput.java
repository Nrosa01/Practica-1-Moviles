package com.example.desktopengine;

import com.example.engine.IInput;
import com.example.engine.InputEvent;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DesktopInput implements IInput {
    private List<InputEvent> eventos = new ArrayList<InputEvent>();

    public void addEvent(MouseEvent event){
        InputTouchType tipo = null;
        if (event.getID() == MouseEvent.BUTTON1_DOWN_MASK){
            tipo = InputTouchType.TOUCH_DOWN;
        }
        eventos.add(new InputEvent(0, 0, 0, tipo));
    }

    public List<InputEvent> getEventList(){
        return eventos;
    }

    public void clear()
    {
        eventos.clear();;
    }
}
