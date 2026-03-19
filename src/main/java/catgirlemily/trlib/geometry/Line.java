package catgirlemily.trlib.geometry;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Drawable;
import catgirlemily.trlib.math.Vector2;

public class Line implements Drawable {
    private final Vector2 v1, v2;
    private int width;
    private char character;


    // Constructor //
    public Line(Vector2 v1, Vector2 v2, char character, int width) {
        this.v1 = v1;
        this.v2 = v2;
        this.character = character;
        this.width = width;
    }

    // main draw method
    @Override
    public void draw(TREngine renderer) {
        // calculate offset
        int startOffset = -(width / 2);

        for (int w = 0; w < width; w++) {
            int currentYOffset = startOffset + w;
            drawBresenham(renderer, currentYOffset);
        }
    }

    private void drawBresenham(TREngine renderer, int yOffset) {
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
            // use int for higher efficiency
            renderer.drawPoint(x1, y1, character);

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