package catgirlemily.trlib.types;

public record Vector2(int x, int y) {
    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }
}