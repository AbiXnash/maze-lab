package dev.glowmaze;

/** How long a flare stuns the shades (spec rule 10): 6 s on level 1, a second less each level, never under 2. */
public final class Stun {
    private Stun() {
    }

    public static double seconds(int level) {
        return Math.max(2, 6 - (Math.max(1, level) - 1));
    }
}
