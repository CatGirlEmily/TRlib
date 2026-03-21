package catgirlemily.game.scenes;

import catgirlemily.game.Game;
import catgirlemily.game.core.Camera;
import catgirlemily.game.core.CarEntity;
import catgirlemily.game.core.NPC;
import catgirlemily.game.core.Player;
import catgirlemily.game.util.Scene;
import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.drawable.Rect;
import catgirlemily.trlib.drawable.Sprite;
import catgirlemily.trlib.type.Color;
import catgirlemily.trlib.type.Vector2;
import catgirlemily.trlib.util.AABB;

public class HeteroStreet implements Scene {
    // --- Engine Core & Map Settings ---
    private final Game game;
    private final Camera camera = new Camera();
    private final int mapWidth = 420;
    private final int mapHeight = 55;
    
    // --- Entity Objects ---
    private Player player;
    private CarEntity trafficCar;
    private NPC passengerNpc;
    
    // --- Graphical Layers ---
    private Sprite backgroundBase;   // Road and pavement
    private Sprite backgroundDetail; // Street lamps and foreground elements
    
    // --- Utility Variables ---
    private int introFrameCounter = 0;
    private double currentDelta = 0;

    Rect NPCborder =  new Rect(new Vector2(100, 15), new Vector2(120, 30), 1, "@");


    public HeteroStreet(Game game) {
        this.game = game;
    }

    @Override
    public void init() {
        // Initialize Player at the starting position
        player = new Player(0, 35);
        player.canMove = true;

        // Load visual layers
        backgroundBase = new Sprite("src/main/resources/streets/0.png", new Vector2(0,0), 420, 55);
        backgroundDetail = new Sprite("src/main/resources/streets/0_1.png", new Vector2(0,0), 420, 55);
        
        // Spawn NPC and AI Traffic
        trafficCar = new CarEntity(410, 25, 3, -1);
        passengerNpc = new NPC(75, 39, 356, 39);
    }

    @Override
    public void update(TREngine renderer, double delta) {
        currentDelta = delta;

        // 1. Update NPC and AI entities
        trafficCar.update(delta);
        passengerNpc.update(delta); 

        // 2. Scripted Intro: Smoothly slide the player into the scene
        introFrameCounter++;
        if (introFrameCounter < 30) {
            player.setAllX(player.pos.x() + Math.abs(introFrameCounter - 30) / 5);
        }

        // 3. Player Logic: Input handling and Road Constraints
        player.handleInput(game, mapWidth, mapHeight);
        
        // Clamp Y position to stay within the road lanes
        if (player.pos.y() > 36) player.setAllY(36);
        if (player.pos.y() < 27) player.setAllY(27);
        
        // 4. Camera: Follow player (must be called after player movement)
        camera.update(player.pos, mapWidth, renderer.getWidth());
        
        // 5. Interaction Logic: Check if player picks up the passenger
        passengerNpc.checkPickup(player, game);

        // 6. Collision Detection: Prepare player hitbox for AABB test
        Vector2 pTopLeft = new Vector2(
            player.pos.x() - (player.getHitbox().x() / 2),
            player.pos.y() - (player.getHitbox().y() / 2)
        );

        // Check collision with the traffic car
        if (AABB.check(pTopLeft, player.getHitbox(), new Vector2((int)trafficCar.getX(), (int)trafficCar.getY()), trafficCar.getSize())) {
            player.canMove = false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
			game.setScene(new DeathScreen(game));
        }
    }

    @Override
    public void render(TREngine renderer) {
        int camX = camera.getX();

        // --- LAYER 1: Background (Road) ---
        backgroundBase.setPosition(new Vector2(-camX, 0));
        backgroundBase.draw(renderer);

        // --- LAYER 2: World Entities (NPCs & Traffic) ---
        trafficCar.render(renderer, camX);

        // --- LAYER 3: Player (Rendered in Screen Space) ---
        Vector2 worldPosBackup = player.pos;
        player.pos = camera.toScreenSpace(player.pos);
        player.render(renderer);
        player.pos = worldPosBackup; // Restore world position for physics

        // --- LAYER 4: Foreground (Street Lamps / Overlays) ---
        backgroundDetail.setPosition(new Vector2(-camX, 0));
        backgroundDetail.draw(renderer);

<<<<<<< Updated upstream
        passengerNpc.render(renderer, camX);

=======
        if(passengerNpc.isPickedUp()){
            NPCborder.draw(renderer);
        }
>>>>>>> Stashed changes
        // --- LAYER 5: Debug Info ---
        if (Game.debug) {
            renderer.drawString(30, 0, "FPS Delta: " + String.format("%.4f", currentDelta), Color.BRIGHT_GREEN);
            renderer.drawString(51, 0, "Camera X: " + camX, Color.CYAN);
        }
    }

    @Override
    public void onKeyPress(int vKey) {
        // Event-based input handling (if needed)
    }
}
