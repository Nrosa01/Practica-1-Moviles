package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;

import java.io.File;
import java.io.IOException;

public class AGraphics implements IGraphics {
    private SurfaceHolder holder;
    private Paint paint;
    AssetManager assetManager;
    public AGraphics(SurfaceHolder holder, Paint paint, AssetManager assetManager){
        this.holder = holder;
        this.paint = paint;
        this.assetManager = assetManager;
    }
    @Override
    public IImage newImage(String pathToImage) {
        IImage image = null;
        try {
            image = new AImage(pathToImage,this.assetManager);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    @Override
    public IFont newFont(String pathToFont, int size, boolean isBold) {
        IFont font;
        font = new AFont(pathToFont,assetManager);
        this.paint.setTextSize(size);
        return font;
    }

   /* public IFont newFont(String pathToFont) {
        IFont font;
        font = new AFont(pathToFont,assetManager);
        return font;
    }*/

    @Override
    public void setLogicSize(int xSize, int ySize) {

    }

    @Override
    public void setColor(int r, int g, int b) {

    }

    @Override
    public void setColor(int r, int g, int b, int a) {

    }

    @Override
    public void setFont() {

    }

    @Override
    public void clear(int r, int g, int b) {

    }

    @Override
    public void drawImage(IImage image, int x, int y) {
        Canvas canvas = holder.lockCanvas();
        Paint paint = new Paint();
        canvas.drawBitmap(((AImage)image).getBitmap(), x, y, paint);
        this.holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void drawRectangle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, int lineWidth) {

    }

    @Override
    public void fillRectangle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY) {

    }

    @Override
    public void drawLine(int fromX, int fromY, int toX, int toY) {

    }

    @Override
    public void drawCircle(int xPos, int yPos, int radius) {

    }

    //implementar size en interfaz
    @Override
    public void drawText(String text, int x, int y, IFont font) {
        Canvas canvas = this.holder.lockCanvas();
        Paint paint = new Paint();//**//*
        paint.setTypeface(((AFont)font).getFont());
        //paint.setTextSize(size);
        canvas.drawText(text, x, y, paint);
        this.holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void drawTextCentered(String text, int x, int y, IFont font) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getLogicWidth() {
        return 0;
    }

    @Override
    public int getLogicHeight() {
        return 0;
    }

    @Override
    public void setGraphicsAlpha(int alpha) {

    }

    @Override
    public void translate(double x, double y) {

    }

    @Override
    public void scale(double x, double y) {

    }

    @Override
    public void rotate(double angleDegrees) {

    }

    @Override
    public int logicXPositionToWindowsXPosition(int x) {
        return 0;
    }

    @Override
    public int logicYPositionToWindowsYPosition(int x) {
        return 0;
    }

    @Override
    public int windowsXPositionToLogicXPosition(int x) {
        return 0;
    }

    @Override
    public int windowsYPositionToLogicYPosition(int x) {
        return 0;
    }

    @Override
    public void setTranslation(double x, double y) {

    }

    @Override
    public void setScale(double x, double y) {

    }

    @Override
    public void setRotation(double angleDegrees) {

    }

    @Override
    public void resetTranslation() {

    }

    @Override
    public void resetScale() {

    }

    @Override
    public void resetRotation() {

    }

    @Override
    public void resetTransform() {

    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }
}
