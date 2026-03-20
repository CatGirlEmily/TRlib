package catgirlemily.trlib.type;

/**
 * Pixel - A simple container for a character and its Color object.
 */
public record Pixel(char character, Color color) {
	public static final Pixel EMPTY = new Pixel(' ', Color.RESET);
}
