package catgirlemily.trlib.drawable;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.types.Vector2;

public class StyledRect extends Rect {
    private char tl = '+', tr = '+', bl = '+', br = '+';     // corners
    private char top = '-', bottom = '-', left = '|', right = '|';     // walls

    public StyledRect(Vector2 v1, Vector2 v2) {
        super(v1, v2, 1, '+');
    }

    /**
     * Sets custom characters for edges (Top, Bottom, Left, Right)
     */
    public StyledRect withEdges(char t, char b, char l, char r) {
        this.top = t; this.bottom = b; this.left = l; this.right = r;
        return this;
    }

    /**
     * Sets custom characters for each corner
     */
    public StyledRect withCorners(char topLeft, char topRight, char bottomLeft, char bottomRight) {
        this.tl = topLeft; this.tr = topRight; 
        this.bl = bottomLeft; this.br = bottomRight;
        return this;
    }

    @Override
    public void draw(TREngine renderer) {
        if (filled) drawFill(renderer);

        int minX = Math.min(v1.x(), v2.x());
        int maxX = Math.max(v1.x(), v2.x());
        int minY = Math.min(v1.y(), v2.y());
        int maxY = Math.max(v1.y(), v2.y());

        // 1. Horizontal Edges
        for (int x = minX + 1; x < maxX; x++) {
            renderer.drawPoint(x, minY, top);
            renderer.drawPoint(x, maxY, bottom);
        }

        // 2. Vertical Edges
        for (int y = minY + 1; y < maxY; y++) {
            renderer.drawPoint(minX, y, left);
            renderer.drawPoint(maxX, y, right);
        }

        // 3. Corners
        renderer.drawPoint(minX, minY, tl); // Top-Left
        renderer.drawPoint(maxX, minY, tr); // Top-Right
        renderer.drawPoint(minX, maxY, bl); // Bottom-Left
        renderer.drawPoint(maxX, maxY, br); // Bottom-Right
    }
}