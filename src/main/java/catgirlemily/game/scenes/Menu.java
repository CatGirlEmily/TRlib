package catgirlemily.game.scenes;

import java.security.Key;

import catgirlemily.game.Game;
import catgirlemily.game.Scene;
import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.drawable.Sprite;
import catgirlemily.trlib.type.KeyCode;
import catgirlemily.trlib.type.Vector2;

public class Menu implements Scene {
    private final Game game;
    private final Sprite bg = new Sprite("src/main/resources/menu/bg.png", new Vector2(0,15), 210, 55);
    private int selected = 0;

    public Menu(Game game) {
        this.game = game;
    }

    @Override
    public void init() {
        selected = 0;
    }

    @Override
    public void update(TREngine renderer, double delta) {}

    @Override
    public void render(TREngine renderer) {
        bg.draw(renderer);
        renderer.drawString(selected == 0 ? 15 : 10, 20, ">>> opcja 1", null);
        renderer.drawString(selected == 1 ? 15 : 10, 23, ">>> opcja 2", null);
        renderer.drawString(selected == 2 ? 15 : 10, 26, ">>> opcja 3", null);
        renderer.drawString(selected == 3 ? 15 : 10, 29, ">>> opcja 4", null);
        
        
    }

    @Override
    public void onKeyPress(int vKey) {
        if      (KeyCode.fromCode(vKey) == KeyCode.UP || KeyCode.fromCode(vKey) == KeyCode.W)   selected = Math.max(selected - 1, 0);
        else if (KeyCode.fromCode(vKey) == KeyCode.DOWN || KeyCode.fromCode(vKey) == KeyCode.S) selected = Math.min(selected + 1, 3);
        
    }
}
