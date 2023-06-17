package com.example.desktopgame;

import com.example.desktopengine.DesktopEngine;
import com.example.engine.DataState;
import com.example.gamelogic.states.MainGameLogic;
import com.example.gamelogic.states.StartMenuLogic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;

public class DesktopGameMain {

    static String filename = "file.ser";
    static DesktopEngine desktopEngine = null;

    public static void main(String[] args) {
        //EXAMEN EJER 2=================================================
        DataState object1 = null;
        // Deserialization
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            // Method for deserialization of object
            object1 = (DataState)in.readObject();
            in.close();
            file.close();
            System.out.println("Object has been deserialized ");
        } catch(Exception ex) {
            System.out.println("Exception is caught");
        }

        //==================================
        desktopEngine = new DesktopEngine(1400, 800, "Nonogramas");

        try {
            desktopEngine.setState(new StartMenuLogic(desktopEngine));
            desktopEngine.getState().setDataState(object1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //============================================
        //Evento PC al cerrar ventana:
        JFrame view = new JFrame("Nonograma");
        view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        view.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                onClose();

                e.getWindow().dispose();
                System.out.println("JFrame Closed!");
            }
        });
        //============================================

        desktopEngine.resume();
    }

    // SERIALIZACION
    public static void onClose(){
        desktopEngine.getState().SaveData();

        DataState object = desktopEngine.getState().getDataStateInstance();
        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename) ;
            ObjectOutputStream out = new ObjectOutputStream(file) ;
            // Method for serialization of object
            out.writeObject(object) ;
            out.close() ;
            file.close() ;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}