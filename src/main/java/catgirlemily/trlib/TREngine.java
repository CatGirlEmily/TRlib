package catgirlemily.trlib;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import catgirlemily.trlib.core.WindowsAnsi;
import catgirlemily.trlib.type.Color;
import catgirlemily.trlib.type.Vector2;

/**
 * TREngine - Core rendering engine for terminal-based graphics.
 * Uses ANSI escape codes and double buffering for flicker-free output.
 */
public class TREngine {
    private int fps;
    private int width;
    private int height;
    private final char[][] charBuffer;
    private final Color[][] colorBuffer;

    private final BufferedWriter out;

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
        this.charBuffer = new char[height][width];
        this.colorBuffer = new Color[height][width];
        
        this.out = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));

        // virtual terminal processing
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
    public void drawPoint(int x, int y, char character, Color color) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            charBuffer[y][x] = character;
            colorBuffer[y][x] = (color == null) ? Color.RESET : color;
        }
    }

    /**
     * Draws a single character using a Vector2 position.
     */
    public void drawPoint(Vector2 vector2, char character, Color color) {
        drawPoint(vector2.x(), vector2.y(), character, color);
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
            Arrays.fill(charBuffer[i], ' ');
            Arrays.fill(colorBuffer[i], Color.RESET);
        }
    }

    /**
     * Renders the current buffer to the terminal.
     * Uses '\033[H' to reset cursor position instead of clearing to avoid flickering.
     */
    public void display() {
        try {
            // hide cursor
            out.write("\033[?25l");

            // Move cursor to top-left corner (Home)
            out.write("\033[H");

            // Write all rows from the buffer
            for (int y = 0; y < height; y++) {
                Color lastColor = Color.RESET;

                for (int x = 0; x < width; x++) {
                    Color currentColor = colorBuffer[y][x];

                    // Optimize: change color only when necessary
                    if (currentColor != lastColor) {
                        out.write(currentColor.getAnsiCode());
                        lastColor = currentColor;
                    }
                    
                    out.write(charBuffer[y][x]);
                }

                // Reset color at the end of each line to prevent bleeding
                if (lastColor != Color.RESET) {
                    out.write(Color.RESET.getAnsiCode());
                }
                
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