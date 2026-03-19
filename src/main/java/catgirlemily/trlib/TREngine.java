package catgirlemily.trlib;

import java.util.Arrays;
import catgirlemily.trlib.math.Vector2;

public class TREngine {
    private int fps;
    private int width;
    private int height;
    private final char[][] buffer;
    
    /**
     * new TREngine constructor
     * @param width - width in characters
     * @param height - height in lines
     * @param fps - refresh rate
     */
    public TREngine(int width, int height, int fps) {
        this.width = width;
        this.height = height;
        this.fps = fps;
        this.buffer = new char[height][width];
        clear();
    }


/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// LL draw methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    public void drawPoint(int x, int y, char character) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            buffer[y][x] = character;
        }
    }

    public void drawPoint(Vector2 vector2, char character) {
        drawPoint(vector2.x(), vector2.y(), character);
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Core Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * fills the buffor with spaces
     */
    public void clear() {
        for (int i = 0; i < height; i++) {
            Arrays.fill(buffer[i], ' ');
        }
    }

    /**
     * Main render method.
     * Compiles bufor into a string and prints it to the console
     */
    public void display() {
        StringBuilder frame = new StringBuilder();
        
        for (int y = 0; y < height; y++) {
            frame.append(buffer[y]);
            frame.append('\n');
        }

        // print every single line at once.
        System.out.print(frame.toString());
        System.out.flush();
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Getters \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////
    public int getWidth() { 
        return width; 
    }

    public int getHeight() { 
        return height;
    }
    
    public int getFPS() {
        return fps;
    }

    // Methods
    public void setFps(int newFps) { fps = newFps; }
    public void setWidth(int newWidth) { width = newWidth; }
    public void setHeight(int newHeight) { height = newHeight; }
}