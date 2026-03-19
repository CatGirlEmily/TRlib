package catgirlemily.trlib.geometry;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Drawable;
import catgirlemily.trlib.math.Vector2;

public class Rect implements Drawable {
    private final Vector2 v1, v2;
    private int lineWidth;
    private char character;

    public Rect(Vector2 v1, Vector2 v2, int lineWidth, char character) {
        this.v1 = v1;
        this.v2 = v2;
        this.lineWidth = lineWidth;
        this.character = character;
    }

    @Override
    public void draw(TREngine renderer) {
        // dwa pozostale wierzcholki
        Vector2 topRight = new Vector2(v2.x(), v1.y());
        Vector2 bottomLeft = new Vector2(v1.x(), v2.y());

        // Connect 4
        new Line(v1, topRight, character, lineWidth).draw(renderer);
        new Line(bottomLeft, v2, character, lineWidth).draw(renderer);
        new Line(v1, bottomLeft, character, lineWidth).draw(renderer);
        new Line(topRight, v2, character, lineWidth).draw(renderer);
    }
}