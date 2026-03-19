package catgirlemily.trlib.drawable;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Drawable;
import catgirlemily.trlib.types.Vector2;

public class Rect implements Drawable {
    protected final Vector2 v1, v2;
    protected int lineWidth;
    protected char character;
    protected boolean filled = false;
    protected char fillChar = ' ';

    public Rect(Vector2 v1, Vector2 v2, int lineWidth, char character) {
        this.v1 = v1;
        this.v2 = v2;
        this.lineWidth = lineWidth;
        this.character = character;
    }

    public Rect withFilled(boolean filled, char fillChar) {
        this.filled = filled;
        this.fillChar = fillChar;
        return this;
    }

    @Override
    public void draw(TREngine renderer) {
        if (filled) {
            drawFill(renderer);
        }

        Vector2 topRight = new Vector2(v2.x(), v1.y());
        Vector2 bottomLeft = new Vector2(v1.x(), v2.y());

        new Line(v1, topRight, character, lineWidth).draw(renderer);
        new Line(bottomLeft, v2, character, lineWidth).draw(renderer);
        new Line(v1, bottomLeft, character, lineWidth).draw(renderer);
        new Line(topRight, v2, character, lineWidth).draw(renderer);
    }

    protected void drawFill(TREngine renderer) {
        int minX = Math.min(v1.x(), v2.x());
        int maxX = Math.max(v1.x(), v2.x());
        int minY = Math.min(v1.y(), v2.y());
        int maxY = Math.max(v1.y(), v2.y());

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                renderer.drawPoint(x, y, fillChar);
            }
        }
    }
}