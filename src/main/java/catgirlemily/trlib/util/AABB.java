package catgirlemily.trlib.util;

import catgirlemily.trlib.type.Vector2;

public class AABB {
    /**
     * Axis Aligned Bounding Box
     * Checks for collision between two Rect-like shapes
     */
    public static boolean check(Vector2 posA, Vector2 sizeA, Vector2 posB, Vector2 sizeB) {
        return posA.x() < posB.x() + sizeB.x() && // Lewa krawędź A < Prawa krawędź B
               posA.x() + sizeA.x() > posB.x() && // Prawa krawędź A > Lewa krawędź B
               posA.y() < posB.y() + sizeB.y() && // Górna krawędź A < Dolna krawędź B
               posA.y() + sizeA.y() > posB.y();   // Dolna krawędź A > Górna krawędź B
    }
}