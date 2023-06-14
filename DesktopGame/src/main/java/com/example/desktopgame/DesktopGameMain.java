package com.example.desktopgame;

import com.example.desktopengine.DesktopEngine;
import com.example.gamelogic.states.MainGameLogic;
import com.example.gamelogic.states.StartMenuLogic;

import java.util.HashMap;
import java.util.Map;

public class DesktopGameMain {

    public static void main(String[] args) {
        //EXAMEN EJER 2====================
        Map<String, Object> savedValuesMap = new HashMap<>();
        //
        DesktopEngine desktopEngine = new DesktopEngine(1400, 800, "Nonogramas", savedValuesMap);

        try {
            desktopEngine.setState(new StartMenuLogic(desktopEngine));
        } catch (Exception e) {
            e.printStackTrace();
        }

        desktopEngine.resume();
    }
}