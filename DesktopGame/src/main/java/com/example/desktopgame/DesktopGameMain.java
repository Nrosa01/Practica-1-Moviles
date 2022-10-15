package com.example.desktopgame;

import com.example.desktopengine.DesktopEngine;

public class DesktopGameMain {

    public static void main(String[] args) {
        DesktopEngine desktopEngine = new DesktopEngine(1920, 1080, "Nonogramas");
        desktopEngine.run();
    }
}