package catgirlemily.demo;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Trlib;
import catgirlemily.trlib.drawable.Rect;
import catgirlemily.trlib.drawable.StyledRect;
import catgirlemily.trlib.type.Vector2;

public class AnsiTest extends Trlib {
	private int x = 0;
	private int dir = 0;
	private char character = ' ';
	private final Vector2 v = new Vector2(20,20);

	public AnsiTest() { super(210, 55, 60); }

	@Override
	public void onUpdate(TREngine renderer, double delta) {
		if (x >= 70) dir = -1;
		if (x <= 0) dir = 1;
		x += dir;
	}

	public static void testAnsi() {
	// \033[31m - Czerwony, \033[32m - Zielony, \033[0m - Reset
		System.out.println("\033[31mCzerwony\033[0m \033[32mZielony\033[0m");
	}
	@Override
		public void onKeyPress(int vKey) {
		
	}
	@Override
	public void onRender(TREngine renderer) {
		new Rect(new Vector2(x,0), new Vector2(130+x,50), 2, "seks").withFilled(true, "penis").draw(renderer);
	}
		
	public static void main(String[] args) {
		new AnsiTest().start();
	}
}
