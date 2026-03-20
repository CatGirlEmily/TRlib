package catgirlemily.trlib.drawable;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.type.Color;
import catgirlemily.trlib.type.Vector2;
import catgirlemily.trlib.util.Pattern;

/**
 * StyledRect - Specialized rectangle allowing different patterns for each side and corner.
 * Useful for GUI boxes with box-drawing characters (╔═╗).
 */
public class StyledRect extends Rect {
    private String tl = "+", tr = "+", bl = "+", br = "+";
    private String top = "-", bottom = "-", left = "|", right = "|";

    public StyledRect(Vector2 v1, Vector2 v2) {
        super(v1, v2, 1, "");
    }

    public StyledRect withEdges(String t, String b, String l, String r) {
        this.top = t; this.bottom = b; this.left = l; this.right = r;
        return this;
    }

    public StyledRect withCorners(String topLeft, String topRight, String bottomLeft, String bottomRight) {
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

        // Draw horizontal edges
        Pattern topP = new Pattern(top);
        Pattern botP = new Pattern(bottom);
        for (int x = minX + 1; x < maxX; x++) {
            renderer.drawPoint(x, minY, topP.next(),Color.BLUE);
            renderer.drawPoint(x, maxY, botP.next(),Color.BLUE);
        }

        // Draw vertical edges
        Pattern leftP = new Pattern(left);
        Pattern rightP = new Pattern(right);
        for (int y = minY + 1; y < maxY; y++) {
            renderer.drawPoint(minX, y, leftP.next(), Color.BLUE);
            renderer.drawPoint(maxX, y, rightP.next(), Color.BLUE);
        }

        // Draw individual corners
        renderer.drawPoint(minX, minY, new Pattern(tl).next(), Color.BLUE);
        renderer.drawPoint(maxX, minY, new Pattern(tr).next(), Color.BLUE);
        renderer.drawPoint(minX, maxY, new Pattern(bl).next(), Color.BLUE);
        renderer.drawPoint(maxX, maxY, new Pattern(br).next(), Color.BLUE);
    }
}