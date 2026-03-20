package catgirlemily.trlib.util;

/**
 * Pattern - A cycling character iterator for drawing textures.
 * Allows for repeating sequences of characters like "░▒▓".
 */
public class Pattern {
	private final String pattern;
	private int cursor = 0;

	public Pattern(String pattern) {
		// Fallback to '#' if pattern is null or empty to avoid errors
		this.pattern = (pattern == null || pattern.isEmpty()) ? "#" : pattern;
	}

	/** Returns the next character in the sequence and increments cursor. */
	public char next() {
		char c = pattern.charAt(cursor % pattern.length());
		cursor++;
		return c;
	}

	/** Resets the iteration to the beginning of the string. */
	public void reset() {
		cursor = 0;
	}
}
