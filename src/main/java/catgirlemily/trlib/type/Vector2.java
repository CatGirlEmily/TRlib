package catgirlemily.trlib.type;

/**
 * Vector2 - Mutable class for 2D coordinates.
 */
public class Vector2 {
    private int x, y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() { return x; }
    public int y() { return y; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }
}