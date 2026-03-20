package catgirlemily.trlib.util;

public class Clamp {
    /**
     * Clamps the value between min and max
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static int clamp(int value, int min, int max) {
        return Math.max(value, Math.min(max, value));
    }
}
