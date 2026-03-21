package catgirlemily.game.core;

import catgirlemily.trlib.type.Vector2;
import catgirlemily.trlib.util.Clamp;

public class Camera {
    private double cameraX = 0;
    private final int targetThreshold = 110; 
    
    // Współczynnik gładkości (0.01 - bardzo powolna, 0.2 - szybka, 1.0 - sztywna)
    private final double smoothness = 0.2; 

    public void update(Vector2 playerPos, int mapWidth, int screenWidth) {
        double targetX = 0;
        if (playerPos.x() > targetThreshold) {
            targetX = playerPos.x() - targetThreshold;
        }

        // 2. LERP: cameraX = cameraX + (target - cameraX) * smoothness
        // To sprawia, że kamera pokonuje 'smoothness' dystansu do celu w każdej klatce
        cameraX += (targetX - cameraX) * smoothness;

        // 3. Clampowanie (żeby nie widzieć "nicości" poza mapą)
        int maxCameraX = Math.max(0, mapWidth - screenWidth);
        cameraX = Clamp.clamp(cameraX, 0.0, (double)maxCameraX);
    }

    public int getX() {
        // Zwracamy int do renderowania, ale wewnątrz trzymamy double dla płynności
        return (int) Math.round(cameraX);
    }

    public Vector2 toScreenSpace(Vector2 worldPos) {
        return new Vector2(worldPos.x() - getX(), worldPos.y());
    }
}