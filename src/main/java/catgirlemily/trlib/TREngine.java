package catgirlemily.trlib;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import catgirlemily.trlib.core.WindowsAnsiEnabler;
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

	public static InputStream input = System.in;

	public static boolean IsOnWindows = false;

    private String windowName = "Trlib Engine";
    private int currentBgColor = 0; // Default: Black
    private int currentFgColor = 7; // Default: White

    /**
     * Initialize the engine with terminal dimensions and target refresh rate.
     */
    public TREngine(int width, int height, int fps) {
        // Using setters to centralize initialization logic
        setWidth(width);
        setHeight(height);
        setFps(fps);

        this.charBuffer = new char[height][width];
        this.colorBuffer = new Color[height][width];
        
        this.out = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));

        // Enable Virtual Terminal Processing for Windows
        if(System.getProperty("os.name").toLowerCase().contains("win")) { IsOnWindows = true; }

		if(IsOnWindows) {
			WindowsAnsiEnabler.enable();
		}
		else {
			try {
				// NOTE: Floping wierd way of enableing raw mode in java
				Runtime.getRuntime().exec(new String[]{"sh", "-c", "stty -icanon -echo min 1 < /dev/tty"}).waitFor();
			}
			catch(IOException e) {

			}
			catch(InterruptedException ee) {

			}
		}
		
		System.out.print("\033[?1049h"); 
		System.out.flush();
        
        // Initial setup using engine utilities
        clearTerminal();
        setCursorVisible(false); // Hide cursor on startup
        clear();
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// LL draw methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Draws a single character at specified coordinates.
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

    /**
     * Draws a full string starting from (x, y).
     */
    public void drawString(int x, int y, String text, Color color) {
        for (int i = 0; i < text.length(); i++) {
            drawPoint(x + i, y, text.charAt(i), color);
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Core Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Fills buffers with default values.
     */
    public void clear() {
        for (int i = 0; i < height; i++) {
            Arrays.fill(charBuffer[i], ' ');
            Arrays.fill(colorBuffer[i], Color.RESET);
        }
    }

    /**
     * Renders the current buffer to the terminal.
     */
    public void display() {
        try {
            // Reset cursor to home position
            out.write("\033[H");

            for (int y = 0; y < height; y++) {
                Color lastColor = Color.RESET;

                for (int x = 0; x < width; x++) {
                    Color currentColor = colorBuffer[y][x];

                    if (currentColor != lastColor) {
                        out.write(currentColor.getAnsiCode());
                        lastColor = currentColor;
                    }
                    
                    out.write(charBuffer[y][x]);
                }

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
     * Restores terminal state before closing.
     */
    public void stop() {
		if(!IsOnWindows) {
			try {
				// NOTE: Floping wierd way of disablaying raw mode in java
				Runtime.getRuntime().exec(new String[]{"sh", "-c", "stty sane < /dev/tty"}).waitFor();
			}
			catch(IOException e) {

			}
			catch(InterruptedException ee) {

			}
		}

		System.out.print("\033[?1049l"); 
		System.out.flush();

        setCursorVisible(true);
        resetStyles();
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Getters & Setters \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    public int getWidth()  { return width; }
    public int getHeight() { return height; }
    public int getFPS()    { return fps; }
    public String getWindowName() { return windowName; }
    public int getBackgroundColor() { return currentBgColor; }
    public int getForegroundColor() { return currentFgColor; }
    public char getCharAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) return ' ';
        return charBuffer[y][x];
    }

    public Color getColorAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) return Color.RESET;
        return colorBuffer[y][x]; // Jeśli masz osobny bufor na kolory
    }

    public void setFps(int newFps)       { this.fps = newFps; }
    public void setWidth(int newWidth)   { this.width = newWidth; }
    public void setHeight(int newHeight) { this.height = newHeight; }

    /**
     * Changes the text on the terminal window's title bar.
     */
    public void setWindowName(String newName) {
        System.out.print("\033]0;" + newName + "\007");
        System.out.flush();
        this.windowName = newName;
    }

    /**
     * Sets the terminal's global background color (outside the buffer).
     * @param colorCode standard ANSI color (0-7)
     */
    public void setBackgroundColor(int colorCode) {
        this.currentBgColor = colorCode % 8;
        System.out.print("\033[4" + currentBgColor + "m"); 
        System.out.flush();
    }

    /**
     * Sets the terminal's global text color.
     * @param colorCode standard ANSI color (0-7)
     */
    public void setForegroundColor(int colorCode) {
        this.currentFgColor = colorCode % 8;
        System.out.print("\033[3" + currentFgColor + "m"); 
        System.out.flush();
    }

    /**
     * Resets all global terminal styles and colors.
     */
    public void resetStyles() {
        System.out.print("\033[0m");
        System.out.flush();
        this.currentBgColor = 0;
        this.currentFgColor = 7;
    }

    /**
     * Clears the terminal screen and resets cursor.
     */
    public void clearTerminal() {
        try {
            out.write("\033[2J\033[H");
            out.flush();
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Toggles the visibility of the blinking terminal cursor.
     */
    public void setCursorVisible(boolean visible) {
        try {
            out.write(visible ? "\033[?25h" : "\033[?25l");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
