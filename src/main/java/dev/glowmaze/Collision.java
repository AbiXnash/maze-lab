package dev.glowmaze;

/** A shade catches the player when their centres are less than half a tile apart (spec rule 11). */
public final class Collision {
    private Collision() {
    }

    public static boolean hits(double px, double py, double sx, double sy) {
        return (int) px == (int) sx && (int) py == (int) sy;
    }
}
