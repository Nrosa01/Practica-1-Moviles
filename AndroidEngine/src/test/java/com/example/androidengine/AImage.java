package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.engine.IImage;

import java.io.IOException;
import java.io.InputStream;

public class AImage implements IImage {

    Bitmap bitmap;

    public AImage(String pathToImage, AssetManager assetManager) throws IOException{
        InputStream is = null;
        try {
            is = assetManager.open(pathToImage);
        }catch (Exception e){
            e.printStackTrace();
        }
        bitmap = BitmapFactory.decodeStream(is);
    }
    public Bitmap getBitmap(){return bitmap;}
    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
