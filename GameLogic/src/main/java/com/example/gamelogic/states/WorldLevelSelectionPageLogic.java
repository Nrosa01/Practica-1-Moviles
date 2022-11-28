package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.engine.IImage;
import com.example.gamelogic.entities.Button;
import com.example.gamelogic.entities.IInteractableCallback;
import com.example.gamelogic.levels.WorldLevelType;

public class WorldLevelSelectionPageLogic extends AbstractState{
    Button returnButton;
    IImage arrow;

    protected WorldLevelSelectionPageLogic(IEngine engine, WorldLevelType type) {
        super(engine);
    }

    @Override
    public boolean init() {
        try
        {
            arrow = graphics.newImage(engine.getAssetsPath() + "images/arrow.png");
            returnButton = new Button(25, 25, 30, 30, engine);
            returnButton.setImage(arrow);
            returnButton.setPadding(10, 10);
            returnButton.setBackgroundColor(0, 0, 0, 0);
            returnButton.setBorderSize(0);
            returnButton.setHoverColor(205, 205, 205);
            returnButton.setCallback(new IInteractableCallback() {
                @Override
                public void onInteractionOccur() {
                    try {
                        engine.setState(new WorldSelectionPageLogic(engine));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            addEntity(returnButton);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
