package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.example.engine.IFont;

public class AFont implements IFont {
    Typeface tface;
    int size;

    public AFont(String pathToFont,AssetManager assetManager,int size,boolean isBold){
        tface = Typeface.createFromAsset(assetManager, pathToFont);
        this.size = size;

    }
    public Typeface getFont(){
        return tface;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isBold() {
        return tface.isBold();
    }
}
