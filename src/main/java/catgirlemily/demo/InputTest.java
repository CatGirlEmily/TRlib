package catgirlemily.demo;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.Trlib;
import catgirlemily.trlib.drawable.StyledRect;
import catgirlemily.trlib.types.Vector2;

public class InputTest extends Trlib {
    private char character = ' ';
    private final Vector2 v = new Vector2(20,50);

    public InputTest() { super(140, 55, 60); }

    @Override
    public void onUpdate(double delta) {
    }

    @Override
    public void onRender(TREngine renderer) {
    renderer.drawPoint(v, character);
    }

    public static void main(String[] args) {
        new InputTest().start();
    }
}