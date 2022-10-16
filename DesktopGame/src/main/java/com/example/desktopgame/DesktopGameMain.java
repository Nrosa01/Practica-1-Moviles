package com.example.desktopgame;

import com.example.desktopengine.DesktopEngine;
import com.example.gamelogic.LogicTest;

public class DesktopGameMain {

    public static void main(String[] args) {
        DesktopEngine desktopEngine = new DesktopEngine(1920, 1080, "Nonogramas");
        LogicTest logicTest = new LogicTest(desktopEngine);

        try {
            desktopEngine.setState(logicTest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        desktopEngine.resume();
    }
}