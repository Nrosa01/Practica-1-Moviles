package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.example.engine.IFont;

public class AFont implements IFont {
    Typeface tface;
    int size;
    int WeightBold = 500;
    public AFont(String pathToFont,AssetManager assetManager,int size,boolean isBold){
        tface = Typeface.createFromAsset(assetManager, pathToFont);
        this.size = size;
        /*if(isBold) {
            tface = Typeface.create(t, WeightBold, false);
        }*/
    }
    public Typeface getFont(){
        return tface;
    }
    //lo tiene el paint
    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isBold() {
        return tface.isBold();
    }
}
