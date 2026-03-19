package catgirlemily.demo;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.Trlib;
import catgirlemily.trlib.drawable.Line;
import catgirlemily.trlib.drawable.Rect;
import catgirlemily.trlib.drawable.StyledRect;
import catgirlemily.trlib.types.Vector2;

public class BounceDemo extends Trlib {
    int x = 0;

    public BounceDemo() { super(140, 55, 60); }

    @Override
    public void onUpdate(double delta) {
        x++;

        if (x > 210) x = 0;
    }

    @Override
    public void onRender(TREngine renderer) {
        new StyledRect(new Vector2(10, 5), new Vector2(50, 15))
    .withCorners('┌', '┐', '└', '┘') // Zaokrąglone lub proste rogi
    .withEdges('─', '─', '│', '│')     // Cienkie linie
    .withFilled(true, ' ')
    .draw(renderer);
        
    }

    public static void main(String[] args) {
        new BounceDemo().start();
    }
}