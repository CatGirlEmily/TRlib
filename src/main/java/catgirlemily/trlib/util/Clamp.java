package catgirlemily.trlib.util;

public class Clamp {

    /** Clamps an integer value */
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /** Clamps a double value (kluczowe dla Twojego localPosX/Y) */
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /** Clamps a float value */
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    /** Clamps a long value */
    public static long clamp(long value, long min, long max) {
        return Math.max(min, Math.min(max, value));
    }
}