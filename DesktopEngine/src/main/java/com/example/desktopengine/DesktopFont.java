package com.example.desktopengine;

import com.example.engine.IFont;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DesktopFont implements IFont {
    Font font;

    public DesktopFont(String pathToFont, int size, boolean isBold) throws IOException, FontFormatException {
        try {
            InputStream is = new FileInputStream(pathToFont);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            font = font.deriveFont(isBold ? Font.BOLD : Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    Font getFont()
    {
        return font;
    }

    @Override
    public int getSize() {
        return font.getSize();
    }

    @Override
    public boolean isBold() {
        return font.isBold();
    }
}
