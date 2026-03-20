package catgirlemily.game.scenes;

import catgirlemily.trlib.TREngine;
import catgirlemily.game.Game;
import catgirlemily.game.Scene;
import catgirlemily.trlib.drawable.Sprite;
import catgirlemily.trlib.type.Vector2;
import catgirlemily.trlib.type.KeyCode;

public class CoolWatermark implements Scene {
    private int frameCounter = 0;
    private final Game game;
    private Sprite[] frames;
    private final Vector2 watermarkV = new Vector2(40,5);
    private final Sprite a = new Sprite("src/main/resources/watermark/0.png", watermarkV, 150, 50);
    private final Sprite b = new Sprite("src/main/resources/watermark/1.png", watermarkV, 150, 50);
    private final Sprite c = new Sprite("src/main/resources/watermark/2.png", watermarkV, 150, 50);
    private final Sprite d = new Sprite("src/main/resources/watermark/3.png", watermarkV, 150, 50);
    private final Sprite e = new Sprite("src/main/resources/watermark/4.png", watermarkV, 150, 50);
    private final Sprite f = new Sprite("src/main/resources/watermark/5.png", watermarkV, 150, 50);
    
    public CoolWatermark(Game game) {
        this.game = game;
    }

    @Override
    public void init() {
        frames = new Sprite[]{ a, b, c, d, e, f };

    }

    @Override
    public void update(TREngine renderer, double delta) {
        if (frameCounter >= 150) game.setScene(new Menu(game));
    }

    @Override
    public void render(TREngine renderer) {
        int index = Math.min(frameCounter / 10, frames.length - 1);
        frames[index].draw(renderer);
    }

    @Override
    public void onKeyPress(int vKey) {
        if (KeyCode.fromCode(vKey) == KeyCode.F) frameCounter = 150;
    }
}