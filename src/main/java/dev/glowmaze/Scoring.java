package dev.glowmaze;

/** Points and lives (spec rules 6 and 7). */
public final class Scoring {
    public static final int EMBER = 10;
    public static final int FLARE = 50;
    public static final int STUN = 100;
    public static final int EXTRA_LIFE_EVERY = 10_000;
    public static final int MAX_LIVES = 5;

    private Scoring() {
    }

    /** Points for all the shades one flare has stunned so far: 100 for each. */
    public static int stunBonus(int stunnedByThisFlare) {
        return STUN * Math.max(0, stunnedByThisFlare);
    }

    /** Lives after the score goes from before to after: one more for each 10,000 passed, at most five. */
    public static int livesAfter(int lives, int before, int after) {
        int gained = after / EXTRA_LIFE_EVERY - before / EXTRA_LIFE_EVERY;
        return Math.min(MAX_LIVES, lives + Math.max(0, gained));
    }
}
