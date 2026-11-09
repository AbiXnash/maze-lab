package dev.glowmaze;

import javax.swing.SwingUtilities;

/** Starts Glowmaze. */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameWindow(new GameState(1)).open());
    }
}
