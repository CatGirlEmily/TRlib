package catgirlemily.trlib.util;

import catgirlemily.trlib.types.Vector2;

public class AABB {
    /**
     * Axis Aligned Bounding Box
     * Checks for collision between two Rect-like shapes
     */
    public static boolean check(Vector2 A1, Vector2 A2, Vector2 B1, Vector2 B2) {
        // 1. is A na prawo od B? (A1.x > B2.x)
        // 2. is A na lewo od B? (A2.x < B1.x)
        // 3. is A poniżej B? (A1.y > B2.y)
        // 4. is A powyżej B? (A2.y < B1.y)
        
        return A1.x() < B2.x() &&
               A2.x() > B1.x() &&
               A1.y() < B2.y() &&
               A2.y() > B1.y();  
    }
}