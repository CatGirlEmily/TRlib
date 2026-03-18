package catgirlemily.trlib;

import java.util.Arrays;

import catgirlemily.trlib.math.Vector2;

public class TREngine {
    private int fps;
    private int width;
    private int height;
    private final char[][] buffer;
    
    /**
     * new TREngine builder
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

    /**
     * fills the buffor with spaces
     */
    public void clear() {
        for (int i = 0; i < height; i++) {
            Arrays.fill(buffer[i], ' ');
        }
    }

    /**
     * Draws a character on position specified using Vector2
     * Clamps value to not go out of bounds.
     */
    public void drawPoint(Vector2 vector2, char character) {
        int x = vector2.x();
        int y = vector2.y();

        if (x >= 0 && x < width && y >= 0 && y < height) {
            buffer[y][x] = character;
        }
    }

    /**
     * Draws a character on position specified using separate x and y value
     * Also clamps value to not go out of bounds.
     */
    public void drawPointLegacy(int x, int y, char character) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            buffer[y][x] = character;
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

    // Getters
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getFPS() { return fps; }

    // Methods
    public void setFps(int newFps) { fps = newFps; }
    public void setWidth(int newWidth) { width = newWidth; }
    public void setHeight(int newHeight) { height = newHeight; }
}