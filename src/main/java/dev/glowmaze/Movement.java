package dev.glowmaze;

/** How fast things move and where they may turn (spec rules 4 and 8). */
public final class Movement {
    public static final double BASE_SPEED = 6.0;
    public static final int TURN_BUFFER_STEPS = 8;
    private static final double EPSILON = 1e-6;

    private Movement() {
    }

    /** Tiles per second on a level: 10% faster each level, no faster than level 5. */
    public static double speed(int level) {
        int l = Math.max(1, Math.min(level, 5));
        return BASE_SPEED * (1 + 0.1 * (l - 1));
    }

    /** Whether a coordinate is at the centre of a tile, where turning is allowed. */
    public static boolean atTileCenter(double pos) {
        return pos == Math.floor(pos) + 0.5;
    }

    /** Moves a coordinate by distance, stopping exactly on the next tile centre if it would pass it. */
    public static double advance(double pos, double distance) {
        double centre = Math.floor(pos) + 0.5;
        if (distance > 0 && pos < centre && pos + distance > centre) {
            return centre;
        }
        if (distance < 0 && pos > centre && pos + distance < centre) {
            return centre;
        }
        return pos + distance;
    }
}
