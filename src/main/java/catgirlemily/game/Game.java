package catgirlemily.game;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Trlib;
import catgirlemily.game.scenes.CoolWatermark;
import catgirlemily.game.util.Scene;

public class Game extends Trlib {
    private Scene activeScene;
    public static final boolean debug = true;

    public Game() {
        super(210, 55, 60);
        setScene(new CoolWatermark(this));
    }

    // setscene
    public void setScene(Scene scene) {
        this.activeScene = scene;
        this.activeScene.init();
    }

    @Override
    public void onRender(TREngine renderer) {
        renderer.setCursorVisible(false);
        if (activeScene != null) {
            activeScene.render(renderer);
        }
    }

    @Override
    public void onUpdate(TREngine renderer, double delta) {
        if (activeScene != null) {
            activeScene.update(renderer, delta);
        }
    }

    @Override
    public void onKeyPress(int keyCode) {
        if (activeScene != null) {
            activeScene.onKeyPress(keyCode);
        }
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
