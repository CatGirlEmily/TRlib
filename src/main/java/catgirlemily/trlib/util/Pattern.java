package catgirlemily.trlib.util;

public class Pattern {
    private final String pattern;
    private int cursor = 0;

    public Pattern(String pattern) {
        this.pattern = (pattern == null || pattern.isEmpty()) ? "#" : pattern;
    }

    public char next() {
        char c = pattern.charAt(cursor % pattern.length());
        cursor++;
        return c;
    }

    public void reset() {
        cursor = 0;
    }
}