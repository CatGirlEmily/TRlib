package catgirlemily.game.core;

import catgirlemily.trlib.type.Vector2;
import catgirlemily.trlib.util.Clamp;

/**
 * Camera - Handles smooth screen scrolling following the player.
 */
public class Camera {
    // --- Configuration ---
    private double cameraX = 0;
    
    // The point (X) at which the player starts "pushing" the camera
    private final int targetThreshold = 110; 
    
    // Smoothness factor (0.01 - very slow, 0.2 - fast/standard, 1.0 - rigid)
    private final double smoothness = 0.2; 

    /**
     * Updates the camera position using Linear Interpolation (LERP).
     * @param playerPos Current world position of the player.
     * @param mapWidth Total width of the level/map.
     * @param screenWidth Width of the visible game window.
     */
    public void update(Vector2 playerPos, int mapWidth, int screenWidth) {
        // 1. Calculate Target: Determine where the camera wants to be
        double targetX = 0;
        if (playerPos.x() > targetThreshold) {
            targetX = playerPos.x() - targetThreshold;
        }

        // 2. LERP Logic: Move a fraction of the distance toward the target each frame
        // Formula: current = current + (target - current) * smoothness
        cameraX += (targetX - cameraX) * smoothness;

        // 3. Clamping: Prevent the camera from showing "the void" outside map bounds
        int maxCameraX = Math.max(0, mapWidth - screenWidth);
        cameraX = Clamp.clamp(cameraX, 0.0, (double)maxCameraX);
    }

    /**
     * @return Rounded integer value of camera X for pixel-perfect rendering.
     */
    public int getX() {
        return (int) Math.round(cameraX);
    }

    /**
     * Converts a World Space coordinate to a Screen Space coordinate.
     * @param worldPos Original position in the game world.
     * @return Position relative to what is currently visible on screen.
     */
    public Vector2 toScreenSpace(Vector2 worldPos) {
        return new Vector2(worldPos.x() - getX(), worldPos.y());
    }
}