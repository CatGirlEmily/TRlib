package catgirlemily.demo;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.Trlib;

public class BounceDemo extends Trlib {
    int x = 0;

    public BounceDemo() { super(120, 55, 60); }

    @Override
    public void onUpdate(double delta) {
        x++;
        if (x > 120) x = 0;
    }

    @Override
    public void onRender(TREngine renderer) {
        renderer.drawPointLegacy(x, 5, '@');
        renderer.drawPointLegacy(x+2, 6, '!');
        
    }

    public static void main(String[] args) {
        new BounceDemo().start();
    }
}