package catgirlemily.trlib.type;

/**
 * Color - Represents ANSI colors. 
 * Changed from enum to class to support both standard and dynamic RGB colors.
 */
public final class Color {
    // Standard 8-16 colors (Static Constants)
    public static final Color RESET = new Color("\033[0m");
    public static final Color WHITE = new Color("\033[37m");
    public static final Color RED = new Color("\033[31m");
    public static final Color GREEN = new Color("\033[32m");
    public static final Color BLUE = new Color("\033[34m");
    public static final Color YELLOW = new Color("\033[33m");
    public static final Color CYAN = new Color("\033[36m");
    public static final Color MAGENTA = new Color("\033[35m");
    public static final Color GRAY = new Color("\u001b[90m");
    
    // Bright variants
    public static final Color BRIGHT_RED = new Color("\033[91m");
    public static final Color BRIGHT_GREEN = new Color("\033[92m");

    private final String ansiCode;

    // Private constructor: only constants or rgb() can create colors
    private Color(String ansiCode) {
        this.ansiCode = ansiCode;
    }

    /**
     * Helper to create custom RGB colors on the fly.
     * Essential for PNG texture rendering.
     */
    public static Color rgb(int r, int g, int b) {
        return new Color(String.format("\033[38;2;%d;%d;%dm", r, g, b));
    }

////////////////////////////////////////////////////////////////////////
    public String getAnsiCode() {
        return ansiCode;
    }
}