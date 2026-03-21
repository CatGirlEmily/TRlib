package catgirlemily.trlib.drawable;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Drawable;
import catgirlemily.trlib.type.Color;
import catgirlemily.trlib.type.Vector2;
import catgirlemily.trlib.util.Pattern;

/**
 * Rect - A basic rectangle composed of four Line objects.
 * Supports outline coloring and patterned filling with a separate color.
 */
public class Rect implements Drawable {
    protected final Vector2 v1, v2;
    protected int lineWidth;
    protected String pattern;
    protected boolean filled = false;
    protected String fillPattern = " ";
    protected Color color = Color.RESET;
    protected Color fillColor = Color.RESET;

    public Rect(Vector2 v1, Vector2 v2, int lineWidth, String pattern) {
        this.v1 = v1;
        this.v2 = v2;
        this.lineWidth = lineWidth;
        this.pattern = pattern;
    }

    /** Sets the outline color. */
    public Rect withColor(Color color) {
        this.color = color;
        return this;
    }

    /** Fluent API to enable and set filling pattern and its color. */
    public Rect withFilled(boolean filled, String fillPattern, Color fillColor) {
        this.filled = filled;
        this.fillPattern = fillPattern;
        this.fillColor = fillColor;
        return this;
    }

    /** Overload for quick filling with default color. */
    public Rect withFilled(boolean filled, String fillPattern) {
        return withFilled(filled, fillPattern, this.color);
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Render Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Rect draw(TREngine renderer) {
        // 1. Draw fill first (background layer)
        if (filled) drawFill(renderer);

        // 2. Calculate coordinates for edges
        Vector2 topRight = new Vector2(v2.x(), v1.y());
        Vector2 bottomLeft = new Vector2(v1.x(), v2.y());

        // 3. Draw outline lines using the primary color
        new Line(v1, topRight, pattern, lineWidth).withColor(color).draw(renderer);
        new Line(bottomLeft, v2, pattern, lineWidth).withColor(color).draw(renderer);
        new Line(v1, bottomLeft, pattern, lineWidth).withColor(color).draw(renderer);
        new Line(topRight, v2, pattern, lineWidth).withColor(color).draw(renderer);
    }

    /** Iterates through the area and fills it with the chosen pattern and fillColor. */
    protected void drawFill(TREngine renderer) {
        int minX = Math.min(v1.x(), v2.x());
        int maxX = Math.max(v1.x(), v2.x());
        int minY = Math.min(v1.y(), v2.y());
        int maxY = Math.max(v1.y(), v2.y());

        Pattern it = new Pattern(fillPattern);

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                renderer.drawPoint(x, y, it.next(), fillColor);
            }
        }
    }
}