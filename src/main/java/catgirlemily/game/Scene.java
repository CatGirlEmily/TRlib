package catgirlemily.game;

import catgirlemily.trlib.TREngine;

public interface Scene {
    void init();
    void update(TREngine renderer, double delta);
    void render(TREngine renderer);
    void onKeyPress(int keyCode);
}