package catgirlemily.game.core;

import catgirlemily.game.Game;
import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.drawable.Sprite;
import catgirlemily.trlib.type.KeyCode;
import catgirlemily.trlib.type.Vector2;
import catgirlemily.trlib.util.Clamp;

public class Player {
    // --- Public Fields ---
    public Vector2 pos;         // Rounded position used for rendering/physics
    public boolean canMove = true;

    // --- Internal State ---
    private double localPosX;   // Precise X coordinate for smooth movement
    private double localPosY;   // Precise Y coordinate for smooth movement

    // --- Assets & Hitboxes ---
    private final String[] texture = {"src/main/resources/sprites/taxi.png", 
                                      "src/main/resources/sprites/taxi_left.png"
    };
    
    // Different hitboxes based on car orientation
    private final Vector2 hitboxStraight = new Vector2(8, 6);
    private final Vector2 hitboxSide     = new Vector2(12, 4);
    
    private Vector2 currentHitbox;
    private final Sprite spriteStraight;
    private final Sprite spriteRight;
    private final Sprite spriteLeft;
    private Sprite currentSprite;

    public Player(int startX, int startY) {
        this.localPosX = startX;
        this.localPosY = startY;
        this.pos = new Vector2(startX, startY);

        // Initialize sprites for different orientations
        this.spriteStraight = new Sprite(texture[0], pos, hitboxStraight.x(), hitboxStraight.y());
        this.spriteRight    = new Sprite(texture[0], pos, hitboxSide.x(), hitboxSide.y());
        this.spriteLeft     = new Sprite(texture[1], pos, hitboxSide.x(), hitboxSide.y());
        // Default state (facing side)
        this.currentSprite = spriteRight;
        this.currentHitbox = hitboxSide;
    }

    /**
     * Handles movement input, diagonal normalization, and world boundaries.
     */
    public void handleInput(Game game, int worldWidth, int worldHeight) {
        if (!canMove) return;

        // Determine base speed (debug sprint included)
        double speed = Game.debug && game.isKeyDown(KeyCode.SHIFT.getCode()) ? 5 : 2;

        // Normalize speed for diagonal movement (prevents moving faster when holding two keys)
        boolean movingVertical = game.isKeyDown(KeyCode.W.getCode()) || game.isKeyDown(KeyCode.S.getCode());
        boolean movingHorizontal = game.isKeyDown(KeyCode.A.getCode()) || game.isKeyDown(KeyCode.D.getCode());
        
        if (movingVertical && movingHorizontal) {
            speed /= 1.5; 
        }

        // Vertical Movement (W/S)
        if (game.isKeyDown(KeyCode.W.getCode())) {
            localPosY -= speed / 2; // Compensate for 2:1 terminal/screen ratio
            this.currentSprite = spriteStraight;
            this.currentHitbox = hitboxStraight;
        }
        if (game.isKeyDown(KeyCode.S.getCode())) {
            localPosY += speed / 2;
            this.currentSprite = spriteStraight;
            this.currentHitbox = hitboxStraight;
        }
        
        // Horizontal Movement (A/D)
        if (game.isKeyDown(KeyCode.A.getCode())) {
            localPosX -= speed;
            this.currentSprite = spriteLeft;
            this.currentHitbox = hitboxSide;
        } 
        else if (game.isKeyDown(KeyCode.D.getCode())) {
            localPosX += speed;
            this.currentSprite = spriteRight;
            this.currentHitbox = hitboxSide;
        } 

        // Apply world boundaries (Clamping)
        localPosX = Clamp.clamp(localPosX, 4.0, worldWidth - 4.0);
        localPosY = Clamp.clamp(localPosY, 1.0, worldHeight - 1.0);
        
        // Update the public Vector2 with rounded values for the engine
        this.pos = new Vector2((int) Math.round(localPosX), (int) Math.round(localPosY));
    }

    /**
     * Used for collision detection (AABB).
     */
    public Vector2 getHitbox() {
        return currentHitbox;
    }

    /**
     * Renders the player centered at its current position.
     */
    public void render(TREngine renderer) {
        if (Game.debug) {
            String debugInfo = String.format("LX: %.1f LY: %.1f | RX: %d", localPosX, localPosY, pos.x());
            renderer.drawString(0, 0, debugInfo, null);
        }
        
        int currentW = currentHitbox.x();
        int currentH = currentHitbox.y();
        
        // Center the sprite (Anchor point calculation)
        int renderX = pos.x() - (currentW / 2);
        int renderY = pos.y() - (currentH / 2);

        currentSprite.setPosition(new Vector2(renderX, renderY));
        currentSprite.draw(renderer);
    }

    // --- Helper Setters (Used for Teleportation/Scritped Events) ---

    public void setAllX(int newX) {
        pos = new Vector2(newX, pos.y());
        localPosX = newX;
    }

    public void setAllY(int newY) {
        pos = new Vector2(pos.x(), newY);
        localPosY = newY;
    }
}