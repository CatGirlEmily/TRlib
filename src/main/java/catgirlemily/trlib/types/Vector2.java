package catgirlemily.trlib.types;

/**
 * Vector2 - Simple immutable record for 2D coordinates.
 */
public record Vector2(int x, int y) {
    /** Returns a new Vector2 representing the sum of this and another vector. */
    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }
}