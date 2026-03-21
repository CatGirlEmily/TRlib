package catgirlemily.game.core;

import java.util.Random;
import catgirlemily.game.Game;
import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.drawable.Rect;
import catgirlemily.trlib.drawable.Sprite;
import catgirlemily.trlib.type.Color;
import catgirlemily.trlib.type.KeyCode;
import catgirlemily.trlib.type.Vector2;
import catgirlemily.trlib.util.AABB;

public class NPC {
    // --- Configuration & State ---
    private final Vector2 pos;
    private final Color color;
    private final Sprite exclamationSprite;
    private boolean isPickedUp = false;

    // Interaction zone size (area around the NPC where SPACE works)
    private final Vector2 interactionSize = new Vector2(10, 10);

    public NPC(int minX, int minY, int maxX, int maxY) {
        Random random = new Random();
        
        // Randomize spawn position within provided bounds
        int x = random.nextInt(Math.max(maxX - minX, 1)) + minX;
        int y = random.nextInt(Math.max(maxY - minY, 1)) + minY;
        
        this.pos = new Vector2(x, y);
        this.color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        
        // Load interaction hint sprite (Exclamation mark)
        this.exclamationSprite = new Sprite("src/main/resources/sprites/exc.png", pos, 2, 5);
    }

    /**
     * Logic for picking up the NPC when the player is nearby and presses SPACE.
     */
    public void checkPickup(Player player, Game game) {
        if (isPickedUp) return;

        // Check for interaction input
        if (game.isKeyDown(KeyCode.SPACE.getCode())) {
            
            // Define the interaction hitbox (centered around the NPC)
            Vector2 interactionPos = new Vector2(pos.x() - 4, pos.y() - 4);
            
            // Perform AABB collision check between Player and Interaction Zone
            if (AABB.check(player.pos, player.getHitbox(), interactionPos, interactionSize)) {
                isPickedUp = true;
                if (Game.debug) System.out.println("Passenger picked up!");
            }
        }
    }

    /**
     * Updates NPC logic.
     */
    public void update(double delta) {
        // Reserved for future AI or idle animations
    }

    /**
     * Renders the NPC and its interaction hint if not yet picked up.
     * @param cameraX The current horizontal offset of the camera.
     */
    public void render(TREngine renderer, int cameraX) {
        if (isPickedUp) return;

        // Translate world position to screen position
        int sx = pos.x() - cameraX;
        int sy = pos.y();

        // Draw NPC body (Rectangle 3x1)
        new Rect(new Vector2(sx, sy), new Vector2(sx + 3, sy + 1), 1, "@")
            .withColor(color)
            .draw(renderer);

        // Position and draw the exclamation hint above the NPC
        exclamationSprite.setPosition(new Vector2(sx + 1, sy - 6));
        exclamationSprite.draw(renderer);
    }

    // --- Getters ---

    public boolean isPickedUp() { 
        return isPickedUp; 
    }
}