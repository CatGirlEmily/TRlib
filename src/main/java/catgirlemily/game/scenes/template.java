package catgirlemily.game.scenes;

import catgirlemily.game.Game;
import catgirlemily.game.Scene;
import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.type.KeyCode;

public class template implements Scene {
    private final Game game;

    public template(Game game) {
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
