package com.example.androidengine;

import android.view.MotionEvent;

import com.example.engine.IInput;
import com.example.engine.InputEvent;

import java.util.ArrayList;
import java.util.List;

public class AInput  implements IInput {
    private List<InputEvent>[] eventos = null;
    private int listBufferCount = 2;
    private int listBufferIndex = 0;

    public AInput()
    {
        eventos = new ArrayList[listBufferCount];
        for(int i = 0; i < eventos.length; i++)
            eventos[i] = new ArrayList<>();
    }

    public AInput(int listBufferCount)
    {
        this.listBufferCount = listBufferCount;
        eventos = new ArrayList[listBufferCount];
        for(int i = 0; i < eventos.length; i++)
            eventos[i] = new ArrayList<>();
    }

    public void addEvent(MotionEvent event){
        InputTouchType tipo = null;

        if (event.getAction() == MotionEvent.ACTION_DOWN)
            tipo = InputTouchType.TOUCH_DOWN;
        else if(event.getAction() == MotionEvent.ACTION_UP)
            tipo = InputTouchType.TOUCH_UP;
        else if(event.getAction() == MotionEvent.ACTION_MOVE)
            tipo = InputTouchType.TOUCH_MOVE;
        //else if(event.getAction() == MotionEvent.ACTION_MOVE)
         //   tipo = InputTouchType.TOUCH_MOVE;

        eventos[listBufferIndex].add(new InputEvent((int)event.getX(), (int)event.getY(), 0, tipo));
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
