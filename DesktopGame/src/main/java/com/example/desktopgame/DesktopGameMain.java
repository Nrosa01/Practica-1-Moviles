package com.example.desktopgame;

import com.example.desktopengine.DesktopEngine;
import com.example.gamelogic.states.UITest;

public class DesktopGameMain {

    public static void main(String[] args) {
        DesktopEngine desktopEngine = new DesktopEngine(1400, 800, "Nonogramas");
        //DesktopEngine desktopEngine = new DesktopEngine(1280, 640, "Nonogramas");
        UITest logicTest = new UITest(desktopEngine);

        try {
            desktopEngine.setState(logicTest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        desktopEngine.resume();
    }
}