package catgirlemily.trlib.drawable;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Drawable;
import catgirlemily.trlib.type.Vector2;
import catgirlemily.trlib.type.Color;
import catgirlemily.trlib.util.Pattern;

/**
 * Line - Draws a line between two points using Bresenham's algorithm.
 * Supports patterns, line thickness and ANSI colors.
 */
public class Line implements Drawable {
    private final Vector2 v1, v2;
    private final String pattern;
    private final int width;
    private Color color = Color.RESET;

    public Line(Vector2 v1, Vector2 v2, String pattern, int width) {
        this.v1 = v1;
        this.v2 = v2;
        this.pattern = pattern;
        this.width = width;
    }

    public Line withColor(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public void draw(TREngine renderer) {
        Pattern it = new Pattern(pattern);
        
        // Calculate the direction of the line to determine thickness orientation
        int dx = Math.abs(v2.x() - v1.x());
        int dy = Math.abs(v2.y() - v1.y());

        int startOffset = -(width / 2);

        for (int w = 0; w < width; w++) {
            int currentOffset = startOffset + w;
            
            // Logic fix: 
            // If the line is more vertical (dy > dx), shift thickness in X axis.
            // If the line is more horizontal (dx >= dy), shift thickness in Y axis.
            if (dy > dx) {
                drawBresenham(renderer, it, currentOffset, 0);
            } else {
                drawBresenham(renderer, it, 0, currentOffset);
            }
        }
    }

    /**
     * Modified Bresenham's to support X and Y offsets.
     */
    private void drawBresenham(TREngine renderer, Pattern it, int xOffset, int yOffset) {
        int x1 = v1.x() + xOffset;
        int y1 = v1.y() + yOffset;
        int x2 = v2.x() + xOffset;
        int y2 = v2.y() + yOffset;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            renderer.drawPoint(x1, y1, it.next(), color);

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