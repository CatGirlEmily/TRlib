package catgirlemily.trlib.drawable;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Drawable;
import catgirlemily.trlib.types.Vector2;
import catgirlemily.trlib.util.Pattern;

/**
 * Line - Draws a line between two points using Bresenham's algorithm.
 * Supports patterns and line thickness.
 */
public class Line implements Drawable {
    private final Vector2 v1, v2;
    private final String pattern;
    private final int width;

    public Line(Vector2 v1, Vector2 v2, String pattern, int width) {
        this.v1 = v1;
        this.v2 = v2;
        this.pattern = pattern;
        this.width = width;
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Draw Logic \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void draw(TREngine renderer) {
        Pattern it = new Pattern(pattern);
        
        // Handles line thickness by shifting the Bresenham calculation
        int startOffset = -(width / 2);
        for (int w = 0; w < width; w++) {
            drawBresenham(renderer, it, startOffset + w);
        }
    }

    /**
     * Classic Bresenham's Line Algorithm.
     * Efficiently calculates points of a line using only integer addition/subtraction.
     */
    private void drawBresenham(TREngine renderer, Pattern it, int yOffset) {
        int x1 = v1.x();
        int y1 = v1.y() + yOffset;
        int x2 = v2.x();
        int y2 = v2.y() + yOffset;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            renderer.drawPoint(x1, y1, it.next());

            if (x1 == x2 && y1 == y2) break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }
}