package catgirlemily.demo;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.Trlib;
import catgirlemily.trlib.drawable.StyledRect;
import catgirlemily.trlib.types.Vector2;

public class AnsiTest extends Trlib {
    private char character = ' ';
    private final Vector2 v = new Vector2(20,20);

    public AnsiTest() { super(140, 55, 60); }

    @Override
    public void onUpdate(double delta) {
    }

    public static void testAnsi() {
    // \033[31m - Czerwony, \033[32m - Zielony, \033[0m - Reset
        System.out.println("\033[31mCzerwony\033[0m \033[32mZielony\033[0m");
    }

    @Override
    public void onRender(TREngine renderer) {
        testAnsi();
    }
        
    public static void main(String[] args) {
        new AnsiTest().start();
    }
}