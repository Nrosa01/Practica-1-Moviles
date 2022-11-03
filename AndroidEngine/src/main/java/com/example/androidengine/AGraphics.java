package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import com.example.engine.AbstractGraphics;
import com.example.engine.IFont;
import com.example.engine.IImage;

import java.io.IOException;

public class AGraphics extends AbstractGraphics {
    private SurfaceHolder holder;
    private Paint paint;
    private AEngine engine;
    AssetManager assetManager;


    private double scaleX = 1;
    private double scaleY = 1;

    public AGraphics(SurfaceHolder holder, Paint paint, AssetManager assetManager, AEngine engine, int sWidth, int sHeight){
        this.holder = holder;
        this.paint = paint;
        this.assetManager = assetManager;
        this.engine = engine;

        setLogicSize(sWidth,sHeight);
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
        font = new AFont(pathToFont,assetManager,isBold);
        this.paint.setTextSize(size);
        return font;
    }

    @Override
    public void setLogicSize(int xSize, int ySize) {
        this.logicSizeX = xSize;
        this.logicSizeY = ySize;
    }

    void calculateScaleFactor() {
        /*int canvasWidth = getWidth() - jFrame.getInsets().left - jFrame.getInsets().right;
        int canvasHeight = getHeight() - jFrame.getInsets().top - jFrame.getInsets().bottom;*/
        //en android se obtiene ya el valor de la ventana de juego (dos mil ciento y algo(res-topbar) x 1080)
        super.calculateScaleFactor(this.logicSizeX, this.logicSizeY);
        setScale(1, 1);
    }

    @Override
    public void setColor(int r, int g, int b) {
        //HABRIA QUE HACER LA CONERSION de rgb a HEXADECIMAL
        /*Canvas canvas = engine.getCurrentCanvas();
        canvas.drawARGB(255,r,g,b); // ARGB*/
        paint.setColor(Color.parseColor("#FF0000"));
    }

    @Override
    public void setColor(int r, int g, int b, int a) {
        //HABRIA QUE HACER LA CONERSION de rgb a HEXADECIMAL
        /*Canvas canvas = engine.getCurrentCanvas();
        canvas.drawARGB(a,r,g,b); // ARGB*/
        paint.setColor(Color.parseColor("#FF0000"));
    }

    @Override
    public void setFont(IFont font) {
        paint.setTypeface(((AFont)font).getFont());
    }

    @Override
    public int getStringWidth(String text, IFont font) {

        Rect bounds = new Rect();
        setFont(font);
        paint.getTextBounds(text, 0, text.length(), bounds);

        int width = bounds.width();

        return width;
    }

    @Override
    public int getFontHeight(IFont font) {
        String ran = "a";

        Rect bounds = new Rect();
        setFont(font);
        paint.getTextBounds(ran, 0, ran.length(), bounds);

        int height = bounds.height();

        return height;
    }

    @Override
    public int getFontAscent(IFont font) {
        return 0;
    }

    @Override
    public void clear(int r, int g, int b) {
        //SURFACE VIEW IS CORRECTLY INITIALIZED
            //current canvas displayed
            Canvas canvas = engine.getCurrentCanvas();
            //PINTAR EL FONDO EN BLANCO-----------------
            paint.setStyle(Paint.Style.FILL);
            paint.setARGB(255,r,g,b);
            canvas.drawPaint(paint);


    }



    @Override
    public void drawImage(IImage image, int x, int y) {
        //SURFACE VIEW IS CORRECTLY INITIALIZED

            //current canvas displayed
            Canvas canvas = engine.getCurrentCanvas();
            // Paint paint = new Paint(); //paint ya se creo en AndroidEngine & lo recibes en la constructora

            //draw           //recibe un bitmap
            canvas.drawBitmap(((AImage)image).getBitmap(), x, y, paint);

    }

    @Override
    public void drawRectangle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, int lineWidth) {
        //SURFACE VIEW IS CORRECTLY INITIALIZED

            //current canvas displayed
            Canvas canvas = engine.getCurrentCanvas();
            //PINTAR EL FONDO EN BLANCO-----------------
            paint.setStyle(Paint.Style.STROKE); //????????????????????????????????????????????????

            //draw
            canvas.drawRect(upperLeftX, upperLeftY, lowerRightX, lowerRightY, paint);

    }

    @Override
    public void fillRectangle(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY) {

            //current canvas displayed
            Canvas canvas = engine.getCurrentCanvas();

            //PINTAR EL FONDO EN BLANCO-----------------
            paint.setStyle(Paint.Style.FILL);

            //draw
            canvas.drawRect(upperLeftX, upperLeftY, lowerRightX, lowerRightY, paint);



    }

    @Override
    public void drawLine(int fromX, int fromY, int toX, int toY) {

            //current canvas displayed
            Canvas canvas = engine.getCurrentCanvas();
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
            //holder.unlockCanvasAndPost(canvas);

    }

    @Override
    public void drawCircle(int xPos, int yPos, int radius) {
        //SURFACE VIEW IS CORRECTLY INITIALIZED

            Canvas canvas = engine.getCurrentCanvas();
            canvas.drawCircle(xPos, yPos, radius, paint);

    }

    //implementar size en interfaz
    @Override
    public void drawText(String text, int x, int y, IFont font) {
        Canvas canvas = engine.getCurrentCanvas();
            //esto no va
            //paint.setTypeface(((AFont)font).getFont());

        canvas.drawText(text, x, y, paint);

    }

    @Override
    public void drawTextCentered(String text, int x, int y, IFont font) {
        


        Canvas canvas = engine.getCurrentCanvas();
        //esto no va
        //paint.setTypeface(((AFont)font).getFont());

        canvas.drawText(text, x, y, paint);
    }

    @Override
    public int getWidth() {
        return this.logicSizeX;
    }

    @Override
    public int getHeight() {
        return this.logicSizeY;
    }

    @Override
    public int getLogicWidth() {
        return this.logicSizeX;
    }

    @Override
    public int getLogicHeight() {
        return this.logicSizeY;
    }

    //hay transiciones pero no van con int
    @Override
    public void setGraphicsAlpha(int alpha) {
        engine.getCurrentView().setAlpha(alpha);
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
