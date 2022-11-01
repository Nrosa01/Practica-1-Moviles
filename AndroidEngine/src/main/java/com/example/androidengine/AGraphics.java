package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.MotionEvent;
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
        //HABRIA QUE HACER LA CONERSION de rgb a HEXADECIMAL
        paint.setColor(Color.parseColor("#FF0000"));
    }

    @Override
    public void setColor(int r, int g, int b, int a) {
        //HABRIA QUE HACER LA CONERSION de rgb a HEXADECIMAL
        paint.setColor(Color.parseColor("#FF0000"));
    }

    @Override
    public void setFont() {

    }

    @Override
    public int getStringWidth(String text, IFont font) {
        return 0;
    }

    @Override
    public int getFontHeight(IFont font) {
        return 0;
    }

    @Override
    public int getFontAscent(IFont font) {
        return 0;
    }

    @Override
    public void clear(int r, int g, int b) {
        //SURFACE VIEW IS CORRECTLY INITIALIZED
        if(holder.getSurface().isValid()){
            //current canvas displayed
            Canvas canvas = holder.lockCanvas();
            //PINTAR EL FONDO EN BLANCO-----------------
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            //accept canvas qe want to draw
            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void drawImage(IImage image, int x, int y) {
        //SURFACE VIEW IS CORRECTLY INITIALIZED
        if(holder.getSurface().isValid()){
            //current canvas displayed
            Canvas canvas = holder.lockCanvas();
            // Paint paint = new Paint(); //paint ya se creo en AndroidEngine & lo recibes en la constructora

            //draw           //recibe un bitmap
            canvas.drawBitmap(((AImage)image).getBitmap(), x, y, paint);

            //accept canvas qe want to draw
            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void drawRectangle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, int lineWidth) {
        //SURFACE VIEW IS CORRECTLY INITIALIZED
        if(holder.getSurface().isValid()){
            //current canvas displayed
            Canvas canvas = holder.lockCanvas();
            //PINTAR EL FONDO EN BLANCO-----------------
            paint.setStyle(Paint.Style.STROKE); //????????????????????????????????????????????????
            //paint.setColor(Color.WHITE);
            //canvas.drawPaint(paint);
            //draw
            canvas.drawRect(upperLeftX, upperLeftY, lowerRightX, lowerRightY, paint);

            //accept canvas qe want to draw
            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void fillRectangle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY) {
        //SURFACE VIEW IS CORRECTLY INITIALIZED
        if(holder.getSurface().isValid()){
            //current canvas displayed
            Canvas canvas = holder.lockCanvas();
            //PINTAR EL FONDO EN BLANCO-----------------
            paint.setStyle(Paint.Style.FILL);
            //paint.setColor(Color.WHITE);
            //canvas.drawPaint(paint);
            //draw
            canvas.drawRect(upperLeftX, upperLeftY, lowerRightX, lowerRightY, paint);

            //accept canvas qe want to draw
            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void drawLine(int fromX, int fromY, int toX, int toY) {
        //SURFACE VIEW IS CORRECTLY INITIALIZED
        if(holder.getSurface().isValid()){
            //current canvas displayed
            Canvas canvas = holder.lockCanvas();
            //PINTAR EL FONDO EN BLANCO-----------------
            //paint.setStyle(Paint.Style.STROKE);
            //paint.setColor(Color.WHITE);
            //canvas.drawPaint(paint);
            //draw
            //EN VEZ DE DIBUJAR UN TRIANGULO, DIBUJO SOLO 1 ARISTA
            Point a = new Point(fromX, fromY);
            Point b = new Point(toX, toY);

            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.lineTo(a.x, a.y);
            path.lineTo(b.x, b.y);
            path.close();

            canvas.drawPath(path, paint);

            //accept canvas qe want to draw
            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void drawCircle(int xPos, int yPos, int radius) {
        //SURFACE VIEW IS CORRECTLY INITIALIZED
        if(holder.getSurface().isValid()){
            //current canvas displayed
            Canvas canvas = holder.lockCanvas();
            //PINTAR EL FONDO EN BLANCO-----------------
            //paint.setStyle(Paint.Style.FILL);
            //paint.setColor(Color.WHITE);
            //canvas.drawPaint(paint);
            //draw
            canvas.drawCircle(xPos, yPos, radius, paint);

            //accept canvas qe want to draw
            holder.unlockCanvasAndPost(canvas);
        }
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
