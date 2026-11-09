package dev.glowmaze;

/** Runs the game: logic in fixed steps of 1/60 s, drawing after each pass (spec rule 2). */
public final class GameLoop implements Runnable {
    public static final double STEP = 1.0 / 60.0;
    private static final int MAX_STEPS = 5;
    private final GameState state;
    private final Runnable draw;
    private volatile boolean running;

    public GameLoop(GameState state, Runnable draw) {
        this.state = state;
        this.draw = draw;
    }

    public void start() {
        running = true;
        Thread thread = new Thread(this, "game-loop");
        thread.setDaemon(true);
        thread.start();
    }

    /** How many fixed steps fit in the time saved up, at most MAX_STEPS so one slow frame cannot snowball. */
    public static int steps(double saved) {
        return Math.min(MAX_STEPS, (int) (saved / STEP));
    }

    /** The time left after running that many steps, kept for the next frame. */
    public static double leftover(double saved, int steps) {
        return steps > 0 ? 0 : saved;
    }

    @Override
    public void run() {
        double saved = 0;
        long last = System.nanoTime();
        while (running) {
            long now = System.nanoTime();
            saved += (now - last) / 1e9;
            last = now;
            int n = steps(saved);
            for (int i = 0; i < n; i++) {
                state.update(STEP);
            }
            saved = leftover(saved, n);
            draw.run();
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
