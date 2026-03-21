package catgirlemily.game.scenes;

import catgirlemily.game.Game;
import catgirlemily.game.core.Camera;
import catgirlemily.game.core.CarEntity;
import catgirlemily.game.core.Player;
import catgirlemily.game.core.SystemCommand;
import catgirlemily.game.util.Scene;
import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.drawable.Sprite;
import catgirlemily.trlib.type.Vector2;
import catgirlemily.trlib.util.AABB;
import catgirlemily.trlib.util.Clamp;

public class HeteroStreet implements Scene {
    private final Game game;
    private Player player;
    private Sprite bg, bg2;
    private CarEntity a,b,c;
    private final int width = 420;
    private final int height = 55;
    private int autoMove = 0;
    private Camera camera = new Camera();
    
    // Offset
    private int cameraX = 0;

    public HeteroStreet(Game game) {
        this.game = game;
    }

    @Override
    public void init() {
        player = new Player(0, 35);
        bg = new Sprite("src/main/resources/streets/0.png", new Vector2(0,0), 420, 55);
        bg2 = new Sprite("src/main/resources/streets/0_1.png", new Vector2(0,0), 420, 55);
        a = new CarEntity(410, 25, 3, -1);
        player.canMove = true;
    }

    @Override
    public void update(TREngine renderer, double delta) {
        a.update(delta);
        autoMove++;
        if (autoMove < 30) {
            player.setAllX(player.pos.x() + Math.abs(autoMove - 30) / 5);
        }

        player.handleInput(game, width, height);

        if (player.pos.y() > 36) player.setAllY(36);
        if (player.pos.y() < 27) player.setAllY(27);
        
        camera.update(player.pos, width, renderer.getWidth());
        
        // Przesuwamy pozycję gracza do narożnika tylko na potrzeby testu kolizji
        Vector2 playerTopLeft = new Vector2(
            player.pos.x() - (player.getHitbox().x() / 2),
            player.pos.y() - (player.getHitbox().y() / 2)
        );

        if (AABB.check(playerTopLeft, player.getHitbox(), new Vector2((int)a.getX(), (int)a.getY()), a.getSize())) {
            
        }
    }

    @Override
    public void render(TREngine renderer) {
        bg.setPosition(new Vector2(-camera.getX(), 0));
        bg.draw(renderer);
        
        Vector2 screenPlayerPos = camera.toScreenSpace(player.pos);
        
        Vector2 originalPos = player.pos;
        player.pos = screenPlayerPos;
        player.render(renderer);
        player.pos = originalPos;

        a.render(renderer, camera.getX());

        // lamps
        bg2.setPosition(new Vector2(-camera.getX(), 0));
        bg2.draw(renderer);
    }

    @Override
    public void onKeyPress(int vKey) {}
}