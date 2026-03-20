package catgirlemily.trlib;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import catgirlemily.trlib.core.WindowsAnsi;
import catgirlemily.trlib.types.Vector2;

/**
 * TREngine - Core rendering engine for terminal-based graphics.
 * Uses ANSI escape codes and double buffering for flicker-free output.
 */
public class TREngine {
    private int fps;
    private int width;
    private int height;
    private final char[][] buffer;
    private final BufferedWriter out;
    private boolean isCursorHidden = false;

    /**
     * Initialize the engine with terminal dimensions and target refresh rate.
     * @param width  - width in characters (columns)
     * @param height - height in lines (rows)
     * @param fps    - target frames per second
     */
    public TREngine(int width, int height, int fps) {
        this.width = width;
        this.height = height;
        this.fps = fps;
        this.buffer = new char[height][width];
        
        // StandardCharsets.UTF_16 allows rendering of special box-drawing characters
        this.out = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_16));

        // Enable Virtual Terminal Processing (Windows 10+)
        WindowsAnsi.enable();
        
        // Initial screen setup: Clear screen and move to home (0,0)
        System.out.print("\033[2J\033[H");
        System.out.flush();

        clear();
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// LL draw methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Draws a single character at specified coordinates.
     * Includes bounds checking to prevent ArrayOutOfBounds exceptions.
     */
    public void drawPoint(int x, int y, char character) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            buffer[y][x] = character;
        }
    }

    /**
     * Draws a single character using a Vector2 position.
     */
    public void drawPoint(Vector2 vector2, char character) {
        drawPoint(vector2.x(), vector2.y(), character);
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Core Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Fills the entire buffer with space characters.
     * Should be called at the beginning of every frame.
     */
    public void clear() {
        for (int i = 0; i < height; i++) {
            Arrays.fill(buffer[i], ' ');
        }
    }

    /**
     * Renders the current buffer to the terminal.
     * Uses '\033[H' to reset cursor position instead of clearing to avoid flickering.
     */
    public void display() {
        try {
            // Hide cursor once during the first render call
            if (!isCursorHidden) {
                out.write("\033[?25l");
                isCursorHidden = true;
            }

            // Move cursor to top-left corner (Home)
            out.write("\033[H");

            // Write all rows from the buffer
            for (int y = 0; y < height; y++) {
                out.write(buffer[y]);
                out.write("\n");
            }

            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Safely shuts down the engine and restores terminal state.
     */
    public void stop() {
        try {
            out.write("\033[?25h"); // Show cursor again
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Getters & Setters \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    public int getWidth()  { return width; }
    public int getHeight() { return height; }
    public int getFPS()    { return fps; }

    public void setFps(int newFps)       { this.fps = newFps; }
    public void setWidth(int newWidth)   { this.width = newWidth; }
    public void setHeight(int newHeight) { this.height = newHeight; }
}