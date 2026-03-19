package catgirlemily.trlib.drawable;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Drawable;
import catgirlemily.trlib.types.Vector2;
import catgirlemily.trlib.util.Pattern;

public class Rect implements Drawable {
    protected final Vector2 v1, v2;
    protected int lineWidth;
    protected String pattern;
    protected boolean filled = false;
    protected String fillPattern = " ";

    public Rect(Vector2 v1, Vector2 v2, int lineWidth, String pattern) {
        this.v1 = v1;
        this.v2 = v2;
        this.lineWidth = lineWidth;
        this.pattern = pattern;
    }

    public Rect withFilled(boolean filled, String fillPattern) {
        this.filled = filled;
        this.fillPattern = fillPattern;
        return this;
    }

    @Override
    public void draw(TREngine renderer) {
        if (filled) {
            drawFill(renderer);
        }

        Vector2 topRight = new Vector2(v2.x(), v1.y());
        Vector2 bottomLeft = new Vector2(v1.x(), v2.y());

        new Line(v1, topRight, pattern, lineWidth).draw(renderer);
        new Line(bottomLeft, v2, pattern, lineWidth).draw(renderer);
        new Line(v1, bottomLeft, pattern, lineWidth).draw(renderer);
        new Line(topRight, v2, pattern, lineWidth).draw(renderer);
    }

    protected void drawFill(TREngine renderer) {
        int minX = Math.min(v1.x(), v2.x());
        int maxX = Math.max(v1.x(), v2.x());
        int minY = Math.min(v1.y(), v2.y());
        int maxY = Math.max(v1.y(), v2.y());

        // Tworzymy jeden iterator na całe wypełnienie
        Pattern it = new Pattern(fillPattern);

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                renderer.drawPoint(x, y, it.next());
            }
        }
    }
}