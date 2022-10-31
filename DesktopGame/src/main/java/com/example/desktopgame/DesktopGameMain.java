package com.example.desktopgame;

import com.example.desktopengine.DesktopEngine;
import com.example.gamelogic.states.MainGameLogic;
import com.example.gamelogic.states.StartMenuLogic;

public class DesktopGameMain {

    public static void main(String[] args) {
        DesktopEngine desktopEngine = new DesktopEngine(1400, 800, "Nonogramas");

        try {
            desktopEngine.setState(new StartMenuLogic(desktopEngine));
        } catch (Exception e) {
            e.printStackTrace();
        }

        desktopEngine.resume();
    }
}