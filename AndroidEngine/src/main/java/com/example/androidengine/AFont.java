package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.example.engine.IFont;

public class AFont implements IFont {
    Typeface tface;
    int WeightBold = 500;
    public AFont(String pathToFont,AssetManager assetManager,boolean isBold){
        tface = Typeface.createFromAsset(assetManager, pathToFont);
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
        return 0;
    }

    @Override
    public boolean isBold() {
        return tface.isBold();
    }
}
