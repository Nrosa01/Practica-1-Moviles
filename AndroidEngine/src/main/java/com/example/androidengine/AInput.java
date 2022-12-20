package com.example.androidengine;

import android.view.MotionEvent;

import com.example.engine.IInput;
import com.example.engine.InputEvent;

import java.util.ArrayList;
import java.util.List;

//para que no se modifiquen los eventos mientras los estoy leyendo se dividen en 2 buffers, un buffer controla
//los eventos que hay que leer y otro controla los eventos de la iteracion actual
public class AInput implements IInput {
    //lista de eventos
    private List<InputEvent>[] eventos ;
    //numero de buffers de eventos que se va a usar
    private int listBufferCount = 2;
    //indice del buffer actual
    private int listBufferIndex = 0;

    public AInput()
    {
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
