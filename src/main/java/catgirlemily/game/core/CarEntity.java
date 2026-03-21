package catgirlemily.game.core;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.drawable.Sprite;
import catgirlemily.trlib.type.Vector2;

/**
 * CarEntity - Represents AI-controlled traffic vehicles.
 */
public class CarEntity {
    // --- Configuration & State ---
    private double x;
    private double y;
    private final double speed;
    private final int direction; // 1 for Right, -1 for Left

    private final Sprite sprite;
    private final Vector2 size = new Vector2(12, 4);

    /**
     * @param x Starting X position (World Space)
     * @param y Starting Y position (World Space)
     * @param speed Movement speed (e.g., 0.5 to 1.5)
     * @param direction 1 for Right, -1 for Left
     */
    public CarEntity(int x, int y, double speed, int direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;

        // Initialize car sprite with defined size
        this.sprite = new Sprite("src/main/resources/sprites/car1_0.png", new Vector2(x, y), size.x(), size.y());
    }

    /**
     * Updates car position and handles world wrapping/reset.
     */
    public void update(double delta) {
        // Apply horizontal movement based on direction and speed
        // To use frame-independent movement: x += direction * speed * delta * 60;
        x += direction * speed;
<<<<<<< HEAD

        // Reset position if car goes off-screen (World Wrap)
        // Adjust '420' based on your specific map width if needed
        if (direction == -1 && x < -size.x()) {
            x = 420; 
        } else if (direction == 1 && x > 420) {
            x = -size.x();
        }
=======
        if(x < -12) x = 420;
>>>>>>> 5571b7a7d519fb522f162706f96356f3ae576422
    }

    /**
     * Renders the car relative to the camera position.
     * @param cameraX The current horizontal offset of the camera.
     */
    public void render(TREngine renderer, int cameraX) {
        // Calculate screen coordinates (World to Screen Space)
        int renderX = (int)Math.round(x) - cameraX;
        int renderY = (int)Math.round(y);

        sprite.setPosition(new Vector2(renderX, renderY));
        sprite.draw(renderer);
    }

    // --- Getters for Collision Detection (AABB) ---
    
    public double getX() { return x; }
    public double getY() { return y; }
    public Vector2 getSize() { return size; }

    public void setPos(int x, int y) {
        sprite.setPosition(new Vector2(x, y));
    }
}