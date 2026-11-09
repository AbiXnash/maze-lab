package dev.glowmaze;

/** The lantern's light in tiles (spec rule 9): 5 after a flare, one less every 20 seconds, never under 2. */
public final class Lantern {
    public static final int FULL = 5;
    public static final int MIN = 2;
    public static final double DIM_EVERY = 20.0;

    private Lantern() {
    }

    public static int radius(double secondsSinceFlare) {
        int r = FULL - (int) Math.floor(Math.max(0, secondsSinceFlare) / DIM_EVERY);
        return Math.max(MIN, r);
    }
}
