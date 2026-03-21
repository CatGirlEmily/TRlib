package catgirlemily.game.scenes;

import catgirlemily.game.Game;
import catgirlemily.game.core.Player;
import catgirlemily.game.util.Scene;
import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.drawable.Sprite;
import catgirlemily.trlib.type.Vector2;
import catgirlemily.trlib.util.Clamp;

public class TestStreet implements Scene {
    private final Game game;
    private Player player;
    private Sprite bg;
    private final int width = 420;
    private final int height = 55;
    
    
    // Offset kamery
    private int cameraX = 0;

    public TestStreet(Game game) {
        this.game = game;
    }

    @Override
    public void init() {
        player = new Player(20, 22); // Zaczynamy wcześniej, żeby widzieć moment dojścia do 110
        bg = new Sprite("src/main/resources/streets/-1.png", new Vector2(0,0), 420, 55);
    }

    @Override
    public void update(TREngine renderer, double delta) {
        player.handleInput(game, width, height);

        if (player.pos.x() > 110) {
            cameraX = player.pos.x() - 110;
        } else {
            cameraX = 0;
        }
        cameraX = Clamp.clamp(cameraX, 0, width - width/2);
    }

    @Override
    public void render(TREngine renderer) {
        bg.setPosition(new Vector2(-cameraX, 0));
        bg.draw(renderer);

        Vector2 screenPlayerPos = new Vector2(player.pos.x() - cameraX, player.pos.y());
        
        Vector2 originalPos = player.pos;
        player.pos = screenPlayerPos;
        player.render(renderer);
        player.pos = originalPos;

        renderer.drawString(10, 10, "teststreet | Camera: " + cameraX, null);
    }

    @Override
    public void onKeyPress(int vKey) {}
}