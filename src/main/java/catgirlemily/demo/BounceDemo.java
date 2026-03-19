package catgirlemily.demo;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.Trlib;
import catgirlemily.trlib.geometry.Line;
import catgirlemily.trlib.geometry.Rect;
import catgirlemily.trlib.math.Vector2;

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
        new Rect(new Vector2(1,1), new Vector2(x,20), 1, '@').draw(renderer);
        
    }

    public static void main(String[] args) {
        new BounceDemo().start();
    }
}