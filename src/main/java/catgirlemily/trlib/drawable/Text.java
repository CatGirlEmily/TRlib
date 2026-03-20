package catgirlemily.trlib.drawable;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Drawable;
import catgirlemily.trlib.type.Color;

public class Text implements Drawable {
    protected int x, y;
    protected String text;
    protected Color color;

    public Text(int x, int y, String text, Color color) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.color = color;
    }

    @Override
    public void draw(TREngine renderer) {
        // Wykorzystujemy metodę, którą dodaliśmy do TREngine
        renderer.drawString(x, y, text, color);
    }
    
    // Gettery/Settery (dla animacji)
    public void setText(String text) { this.text = text; }
}