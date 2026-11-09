package dev.glowmaze;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/** Keys (spec rule 15): arrows move, P pauses, M mutes. */
public final class Input extends KeyAdapter {
    private final GameState state;

    public Input(GameState state) {
        this.state = state;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> state.want(ShadeBrain.UP);
            case KeyEvent.VK_LEFT -> state.want(ShadeBrain.LEFT);
            case KeyEvent.VK_DOWN -> state.want(ShadeBrain.DOWN);
            case KeyEvent.VK_RIGHT -> state.want(ShadeBrain.RIGHT);
            default -> {
            }
        }
        String key = KeyEvent.getKeyText(e.getKeyCode());
        if (key == "P") {
            state.togglePause();
        }
    }
}
