package catgirlemily.game.scenes;

import catgirlemily.game.Game;
import catgirlemily.game.util.Scene;
import catgirlemily.trlib.TREngine;

public class TestStreet implements Scene {
    private final Game game;

    public TestStreet(Game game) {
        this.game = game;
    }

    @Override
    public void init() {}

    @Override
    public void update(TREngine renderer, double delta) {}

    @Override
    public void render(TREngine renderer) {}

    @Override
    public void onKeyPress(int vKey) {}
}
