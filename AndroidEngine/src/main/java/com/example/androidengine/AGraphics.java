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
    //dimensiones de pantalla
    int screenWidth;
    int screenHeight;
    //la escala a la que quiero pintar lo actual
    private double scaleX = 1;
    private double scaleY = 1;

    int r = 255;
    int g = 255;
    int b = 255;
    float alphaMultiplier = 1;

    public AGraphics(SurfaceHolder holder, Paint paint, AssetManager assetManager, AEngine engine, int sWidth, int sHeight){
        this.holder = holder;
        this.paint = paint;
        setColor(r,g,b,255); //LO PONEMOS A BLANCO POR DEFECTO
        this.assetManager = assetManager;
        this.engine = engine;

        screenWidth = sWidth;
        screenHeight = sHeight;
        setLogicSize(sWidth,sHeight);
    }

    //creacion de imagen (bitmap)
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
    //creacion de fuente (Typeface)
    @Override
    public IFont newFont(String pathToFont, int size, boolean isBold) {
        IFont font;
        font = new AFont(pathToFont,assetManager,size,isBold);
        return font;
    }

    //establezco el tamaño logico actual
    @Override
    public void setLogicSize(int xSize, int ySize) {
        this.logicSizeX = xSize;
        this.logicSizeY = ySize;
        calculateScaleFactor();
    }

    void calculateScaleFactor() {
        //calculo el factor de escalado
        super.calculateScaleFactor(this.screenWidth, this.screenHeight);
        setScale(1, 1);
    }

    //color con el que voy a "pintar" lo siguiente que muestre en pantalla
    @Override
    public void setColor(int r, int g, int b) {

        this.r = r;
        this.g = g;
        this.b = b;

        paint.setColor( Color.argb((int)(255 * alphaMultiplier), this.r, this.g, this.b) );
    }


    @Override
    public void setColor(int r, int g, int b, int a) {

        this.r = r;
        this.g = g;
        this.b = b;

        paint.setColor( Color.argb((int) (a * alphaMultiplier),this.r, this.g, this.b) );
    }

    //set de la fuente actual
    @Override
    public void setFont(IFont font) {
        paint.setTypeface(((AFont)font).getFont());
    }

    //calculo el ancho del rectangulo que podria contener al texto
    @Override
    public int getStringWidth(String text, IFont font) {

        paint.setTextSize((float)(((AFont)font).getSize() * scaleFactor * scaleX));

        Rect bounds = new Rect();
        setFont(font);
        paint.getTextBounds(text, 0, text.length(), bounds);

        int width = bounds.width();

        return (int)(width / scaleFactor);
    }
    //calculo el alto del rectangulo que podria contener al texto
    @Override
    public int getFontHeight(IFont font) {
        String ran = "a";

        paint.setTextSize((float)(((AFont)font).getSize() * scaleFactor * scaleX));

        Rect bounds = new Rect();
        setFont(font);
        paint.getTextBounds(ran, 0, ran.length(), bounds);

        int height = bounds.height();

        return  (int)(height / scaleFactor);
    }

    @Override
    public int getFontAscent(IFont font) {
        return 0;
    }

    //pinto toda la pantalla
    @Override
    public void clear(int r, int g, int b) {

        Canvas canvas = engine.getCurrentCanvas();
        //PINTAR EL FONDO EN BLANCO-----------------
        paint.setStyle(Paint.Style.FILL); //DEFAULT VALUE
        paint.setARGB(255,r,g,b);
        canvas.drawPaint(paint);

        paint.setARGB(255,this.r,this.g,this.b);

    }



    @Override
    public void drawImage(IImage image, int x, int y) {

        Canvas canvas = engine.getCurrentCanvas();

        //calculo de coordenadas fisicas desde coordenadas logicas
        int uLy = logicYPositionToWindowsYPosition(y );
        int uLx = logicXPositionToWindowsXPosition(x );

        int imageWidth = (int)(((AImage)image).getWidth() * scaleX * scaleFactor);
        int  imageHeight = (int)(((AImage)image).getHeight() * scaleY * scaleFactor);

        uLx -= imageWidth/ 2;
        uLy -=   imageHeight/  2;

        int dRx = imageWidth + uLx;
        int dRy = imageHeight + uLy;
        Rect rect = new Rect(uLx,uLy, dRx,dRy);

        //controlamos que la imagen se pinte con el color correcto(que el paint no tenga otro color)
        paint.setARGB((int)(alphaMultiplier * 255), 255, 255, 255);
        canvas.drawBitmap(((AImage)image).getBitmap(), null,rect,paint);
        //reseteamos el color
        paint.setARGB((int)(alphaMultiplier * 255), this.r, this.g, this.b);

    }

    @Override
    public void drawRectangle(int x, int y, int width, int height,int lineWidth )  {

        //calculo de coordenadas fisicas desde coordenadas logicas
        int upperLeftX, upperLeftY, lowerRightX, lowerRightY;
        width*= scaleX;
        height*= scaleY;
        upperLeftX = x - (width / 2);
        upperLeftY = y - (height / 2);
        lowerRightX = x + (width / 2);
        lowerRightY = y + (height / 2);

        int uLx = logicXPositionToWindowsXPosition(upperLeftX);
        int uLy = logicYPositionToWindowsYPosition(upperLeftY);
        int lRx = logicXPositionToWindowsXPosition(lowerRightX);
        int lRy = logicYPositionToWindowsYPosition(lowerRightY);

        //canvas actual
        Canvas canvas = engine.getCurrentCanvas();

        //pintado con lineas
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((int)(lineWidth * scaleFactor));
        //draw
        canvas.drawRect(uLx, uLy, lRx, lRy, paint);


        paint.setStyle((Paint.Style.FILL));
        paint.setStrokeWidth(0); //Pass 0 to stroke in hairline mode.
        //Hairlines always draws a single pixel independent of the canvas's matrix.
    }

    @Override
    public void fillRectangle(int x, int y, int width, int height) {

        //calculo de coordenadas fisicas desde coordenadas logicas
        int upperLeftX, upperLeftY, lowerRightX, lowerRightY;
        width*= scaleX;
        height *= scaleY;
        upperLeftX = x - (width / 2);
        upperLeftY = y - (height / 2);
        lowerRightX = x + (width / 2);
        lowerRightY = y + (height / 2);

        int uLx = logicXPositionToWindowsXPosition(upperLeftX);
        int uLy = logicYPositionToWindowsYPosition(upperLeftY);
        int lRx = logicXPositionToWindowsXPosition(lowerRightX);
        int lRy = logicYPositionToWindowsYPosition(lowerRightY);

        //canvas actual
        Canvas canvas = engine.getCurrentCanvas();

        paint.setStyle(Paint.Style.FILL); //FILL IS DEFAULT VALUE

        //draw
        canvas.drawRect(uLx, uLy, lRx, lRy, paint);

    }

    //se pintan los bordes necesarios para mantener la relacion de aspecto deseada
    public void renderBordersAndroid()
    {
        paint.setARGB((int)(255), 255, 255, 255); //BLANCO

        // Tener en cuenta scale factor para el ancho
        int scaledBarWidth = (int)Math.ceil(borderBarWidth / scaleFactor);
        int scaledTopHeight = (int)Math.ceil(topBarHeight / scaleFactor);

        // Barra izquierda y derecha
        fillRectangle(-scaledBarWidth/2, logicSizeY/2, scaledBarWidth, logicSizeY);
        fillRectangle(scaledBarWidth/2 + logicSizeX, logicSizeY/2, scaledBarWidth, logicSizeY);

        // Barras arriba y abajo
        fillRectangle(logicSizeX/2, -( scaledTopHeight/2 ) , logicSizeX, scaledTopHeight);
        fillRectangle(logicSizeX/2, scaledTopHeight/2 + logicSizeY, logicSizeX, scaledTopHeight);

        //RESET
        paint.setARGB((int)(alphaMultiplier * 255), this.r, this.g, this.b);

    }

    @Override
    public void drawLine(int fromX, int fromY, int toX, int toY) {

        //current canvas displayed
        Canvas canvas = engine.getCurrentCanvas();

        int uLx = logicXPositionToWindowsXPosition(fromX);
        int uLy = logicYPositionToWindowsYPosition(fromY);
        int lRx = logicXPositionToWindowsXPosition(toX);
        int lRy = logicYPositionToWindowsYPosition(toY);

        paint.setStyle(Paint.Style.STROKE); //FILL IS DEFAULT VALUE
        paint.setStrokeWidth((float)(10 * scaleX));
        canvas.drawLine(uLx, uLy, lRx, lRy, paint);
        paint.setStyle(Paint.Style.FILL); //FILL IS DEFAULT VALUE
    }

    @Override
    public void drawCircle(int xPos, int yPos, int radius) {
        //ARREGLO
        int x = logicXPositionToWindowsXPosition(xPos);
        int y = logicXPositionToWindowsXPosition(yPos);
        radius *= scaleX;
        //
        Canvas canvas = engine.getCurrentCanvas();
        canvas.drawCircle(x, y + radius, radius, paint);
    }


    @Override
    public void drawText(String text, int x, int y, IFont font) {
        Canvas canvas = engine.getCurrentCanvas();
        setFont(font);

        paint.setStyle(Paint.Style.FILL); //DEFAULT VALUE
        paint.setTextSize((float)(((AFont)font).getSize() * scaleFactor * scaleX));

         x = logicXPositionToWindowsXPosition(x);
         y = logicYPositionToWindowsYPosition(y );

        canvas.drawText(text, x, y, paint);
        //paint.reset();
    }

    @Override
    public void drawTextCentered(String text, int x, int y, IFont font) {
        Canvas canvas = engine.getCurrentCanvas();
     
        paint.setStyle(Paint.Style.FILL); //DEFAULT VALUE

        //calculo la posicion a partir de la que tengo que pintar para que este centrado el texto respecto a x,y
        int halfSwidth = (int)(((int)(getStringWidth(text, font))  )/ 2);
        int halfSheight = (int)(((int)(getFontHeight(font) )  ) / 2);
        int processedX = logicXPositionToWindowsXPosition(x- halfSwidth);
        int processedY = logicYPositionToWindowsYPosition(y + halfSheight  );

        canvas.drawText(text, processedX, processedY, paint);
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
        paint.setAlpha(alpha);
        alphaMultiplier = ((float) alpha/255);
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
        return (int) (((x + (int) (borderBarWidth / scaleFactor) ) ) * scaleFactor);

    }

    @Override
    public int logicYPositionToWindowsYPosition(int y) {
        return (int) (((y + (int) (topBarHeight / scaleFactor) ))* scaleFactor);

    }

    @Override
    public int windowsXPositionToLogicXPosition(int x) {
        return (int) (((x - borderBarWidth  ) / scaleFactor));
    }

    @Override
    public int windowsYPositionToLogicYPosition(int y) {
        return (int) (((y - topBarHeight  ) / scaleFactor));
    }

    @Override
    public void setTranslation(double x, double y) {

    }

    @Override
    public void setScale(double x, double y) {
        scaleX = x;
        scaleY = y;
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
