package dev.glowmaze;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/** The game's window: half the screen in each direction, centred, fixed size (spec rule 1). */
public final class GameWindow {
    private final GameState state;
    private final JFrame frame = new JFrame("Glowmaze");

    public GameWindow(GameState state) {
        this.state = state;
    }

    /** The window size for a screen: half its width and half its height. */
    public static Dimension sizeFor(int screenWidth, int screenHeight) {
        return new Dimension(screenWidth / 2, screenHeight / 2);
    }

    public void open() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Renderer renderer = new Renderer(state);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.add(renderer);
        frame.setSize(sizeFor(screen.width, screen.height));
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.addKeyListener(new Input(state));
        frame.setVisible(true);
        new GameLoop(state, renderer::repaint).start();
    }
}
