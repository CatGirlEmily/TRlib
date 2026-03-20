package catgirlemily.trlib.drawable;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Drawable;
import catgirlemily.trlib.type.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ASCIISprite - Represents a multi-line ASCII art loaded from a text file.
 */
public class ASCIISprite implements Drawable {
    private int x, y;
    private String[] lines;
    private Color color;
    private boolean transparent; // If true, spaces won't overwrite existing characters

    /**
     * Initializes a new sprite from a file.
     * @param x Screen X coordinate (top-left by default)
     * @param y Screen Y coordinate (top-left by default)
     * @param filePath Path to the .txt file containing ASCII art
     * @param color Default color for the entire sprite
     */
    public ASCIISprite(int x, int y, String filePath, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.transparent = true; 
        this.lines = loadFile(filePath);
    }

    /**
     * Reads the file line by line and stores it in the lines array.
     */
    private String[] loadFile(String path) {
        List<String> content = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        } catch (IOException e) {
            System.err.println("[Sprite] Load Error: " + path);
            return new String[]{"[FILE_ERROR]"};
        }
        return content.toArray(new String[0]);
    }

    /**
     * Renders the sprite to the TREngine buffer.
     */
    @Override
    public void draw(TREngine renderer) {
        for (int i = 0; i < lines.length; i++) {
            String currentLine = lines[i];
            
            if (transparent) {
                // Draw character by character to handle transparency
                for (int charIdx = 0; charIdx < currentLine.length(); charIdx++) {
                    char c = currentLine.charAt(charIdx);
                    if (c != ' ') { // Skip spaces
                        renderer.drawPoint(x + charIdx, y + i, c, color);
                    }
                }
            } else {
                // Faster batch draw if transparency is disabled
                renderer.drawString(x, y + i, currentLine, color);
            }
        }
    }

    /**
     * Centers the sprite's position based on its dimensions and screen size.
     */
    public void center(int screenWidth, int screenHeight) {
        int spriteWidth = 0;
        for (String line : lines) {
            spriteWidth = Math.max(spriteWidth, line.length());
        }
        this.x = (screenWidth - spriteWidth) / 2;
        this.y = (screenHeight - lines.length) / 2;
    }

    // Getters & Setters
    public void setPosition(int x, int y) { this.x = x; this.y = y; }
    public void setTransparent(boolean transparent) { this.transparent = transparent; }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() {
        int max = 0;
        for (String line : lines) max = Math.max(max, line.length());
        return max;
    }
    public int getHeight() { return lines.length; }
}