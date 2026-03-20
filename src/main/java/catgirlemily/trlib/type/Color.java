package catgirlemily.trlib.type;

/**
 * ColorType - Enum representing available ANSI colors.
 */
public enum Color {
    // Standard 8-16 colors
    RESET("\033[0m"),
    WHITE("\033[37m"),
    RED("\033[31m"),
    GREEN("\033[32m"),
    BLUE("\033[34m"),
    YELLOW("\033[33m"),
    CYAN("\033[36m"),
    MAGENTA("\033[35m"),
    
    // Bright variants
    BRIGHT_RED("\033[91m"),
    BRIGHT_GREEN("\033[92m");

    private final String ansiCode;

    Color(String ansiCode) {
        this.ansiCode = ansiCode;
    }

    /**
     * Helper to create custom RGB colors on the fly
     */
    public static String rgb(int r, int g, int b) {
        return String.format("\033[38;2;%d;%d;%dm", r, g, b);
    }

////////////////////////////////////////////////////////////////////////
    public String getAnsiCode() {
        return ansiCode;
    }
}