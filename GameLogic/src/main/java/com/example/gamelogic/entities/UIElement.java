package com.example.gamelogic.entities;

import com.example.engine.IEngine;
import com.example.engine.IInput;

public abstract class UIElement extends Entity implements IInputHandler, IInteractable {
    private boolean alreadyOnHover = false;
    private boolean insideLastFrame = false;
    private boolean wasPressed = false;

    @Override
    public void OnPointerDown(int x, int y) {}

    @Override
    public void OnPointerUp(int x, int y){}

    public UIElement(IEngine engine)
    {
        super(engine);
    }

    public void handleInput(int x, int y, IInput.InputTouchType touchType)
    {
        super.handleInput(x,y,touchType);
        boolean inside = isInside(x, y, posX, posY, width, height);

        if(inside)
        {
            if(!insideLastFrame && touchType == IInput.InputTouchType.TOUCH_MOVE)
            {
                OnHoverEnter();
                alreadyOnHover = true;
            }
            else if(touchType == IInput.InputTouchType.TOUCH_DOWN)
            {
                OnTouchDown();
                wasPressed = true;
            }

            insideLastFrame = true;
        }
        else
        {
            if(insideLastFrame)
            {
                OnHoverExit();
                alreadyOnHover = false;
            }
            insideLastFrame = false;
        }

        if(touchType == IInput.InputTouchType.TOUCH_UP && wasPressed)
        {
            OnTouchUp();
            wasPressed = false;
        }
    }

    private boolean isInside(int x, int y, int posX, int posY, int width, int height)
    {
        return x >= posX - width / 2 && x <= posX + width / 2 && y >= posY - height / 2 && y <= posY + height / 2;
    }
}
