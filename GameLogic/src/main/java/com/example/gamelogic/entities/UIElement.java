package com.example.gamelogic.entities;

import com.example.engine.IInput;
import com.example.gamelogic.entities.Entity;
import com.example.gamelogic.entities.IInteractable;

public abstract class UIElement extends Entity implements IInteractable {
    private boolean alreadyOnHover = false;
    private boolean insideLastFrame = false;
    private boolean wasPressed = false;

    public void handleInput(int x, int y, IInput.InputTouchType touchType)
    {
        int posX = getPosX();
        int posY = getPosY();

        int width = getWidth();
        int height = getHeight();

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
