package com.example.gamelogic.states;

import com.example.engine.IEngine;
import com.example.gamelogic.utilities.DataToAccess;

import java.util.Map;

public class GetDataState extends AbstractState{

    public GetDataState(IEngine engine){
        super(engine);
        DataToAccess.Init(engine);

    }



    @Override
    public boolean init() {
        try {
            engine.setState(new StartMenuLogic(engine));
        }catch (Exception e){
             e.printStackTrace();
            return false;
        }
        return true;
    }
}
