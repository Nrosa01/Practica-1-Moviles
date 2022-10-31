package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.example.engine.IFont;

public class AFont implements IFont {
    Typeface tface;

    public AFont(String pathToFont,AssetManager assetManager){
        tface = Typeface.createFromAsset(assetManager, pathToFont);

    }
    public Typeface getFont(){
        return tface;
    }
    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public boolean isBold() {
        return false;
    }
}
