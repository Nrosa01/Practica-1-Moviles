package com.example.desktopengine;

import com.example.engine.*;

import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class DesktopEngine implements IEngine {
    IGraphics graphics;
    IAudio audio;
    IState state;
    JFrame mView;
    BufferStrategy bufferStrategy;
    Thread renderThread;

    public DesktopEngine(int wWidth, int wHeight, String wTittle)
    {
        mView = new JFrame(wTittle);

        mView.setSize(wWidth, wHeight);
        mView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mView.setIgnoreRepaint(false);

        mView.setVisible(true);

        int tries = 100;
        while(tries-- > 0) {
            try {
                this.mView.createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (tries == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }

        this.bufferStrategy = this.mView.getBufferStrategy();
        // this.graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
    }

    public void run()
    {

    }

    @Override
    public IGraphics getGraphics() {
        return null;
    }

    @Override
    public IAudio getAudio() {
        return null;
    }

    @Override
    public IState getState() {
        return null;
    }
}