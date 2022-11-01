package com.example.desktopengine;

import com.example.engine.IInput;
import com.example.engine.InputEvent;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DesktopInput implements IInput {
    private List<InputEvent>[] eventos = null;
    private int listBufferCount = 2;
    private int listBufferIndex = 0;

    public DesktopInput()
    {
        eventos = new ArrayList[listBufferCount];
        for(int i = 0; i < eventos.length; i++)
            eventos[i] = new ArrayList<>();
    }

    public DesktopInput(int listBufferCount)
    {
        this.listBufferCount = listBufferCount;
        eventos = new ArrayList[listBufferCount];
        for(int i = 0; i < eventos.length; i++)
            eventos[i] = new ArrayList<>();
    }

    public void addEvent(MouseEvent event){
        InputTouchType tipo = null;

        if (event.getID() == MouseEvent.MOUSE_PRESSED)
            tipo = InputTouchType.TOUCH_DOWN;
        else if(event.getID() == MouseEvent.MOUSE_RELEASED)
            tipo = InputTouchType.TOUCH_UP;
        else if(event.getID() == MouseEvent.MOUSE_MOVED)
            tipo = InputTouchType.TOUCH_MOVE;
        else if(event.getID() == MouseEvent.MOUSE_DRAGGED)
            tipo = InputTouchType.TOUCH_MOVE;

        eventos[listBufferIndex].add(new InputEvent(event.getX(), event.getY(), 0, tipo));
    }

    public List<InputEvent> getEventList(){
        return eventos[listBufferIndex];
    }

    public void swapListBuffer()
    {
        listBufferIndex = (listBufferIndex + 1) % listBufferCount;
    }

    public void clear()
    {
        eventos[listBufferIndex].clear();
    }
}
