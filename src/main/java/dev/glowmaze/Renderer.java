package dev.glowmaze;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/** Draws the maze, scaled to fit the window (spec rule 3), lit only around the lantern (rule 9). */
public final class Renderer extends JPanel {
    private final GameState state;

    public Renderer(GameState state) {
        this.state = state;
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        synchronized (state) {
            int tile = Math.max(1, Math.min(getWidth() / Maze.COLS, getHeight() / (Maze.ROWS + 1)));
            int light = Lantern.radius(state.secondsSinceFlare);
            System.out.println("frame light=" + light + " tile=" + tile);
            int pc = (int) Math.floor(state.px);
            int pr = (int) Math.floor(state.py);
            for (int r = 0; r < Maze.ROWS; r++) {
                for (int c = 0; c < Maze.COLS; c++) {
                    if (Math.abs(c - pc) + Math.abs(r - pr) > light) {
                        continue;
                    }
                    char t = Maze.LAYOUT[r].charAt(c);
                    if (state.maze.isWall(c, r)) {
                        g2.setColor(new Color(40, 50, 90));
                        g2.fillRect(c * tile, r * tile, tile, tile);
                    } else if (t == '.' || t == 'o') {
                        g2.setColor(t == 'o' ? Color.WHITE : new Color(255, 170, 60));
                        int size = t == 'o' ? tile / 2 : tile / 5;
                        g2.fillOval(c * tile + (tile - size) / 2, r * tile + (tile - size) / 2, size, size);
                    }
                }
            }
            g2.setColor(new Color(255, 220, 120));
            g2.fillOval((int) ((state.px - 0.5) * tile), (int) ((state.py - 0.5) * tile), tile, tile);
            g2.setColor(state.stunLeft > 0 ? new Color(120, 140, 255) : new Color(200, 60, 80));
            for (double[] s : state.shades) {
                g2.fillOval((int) ((s[0] - 0.5) * tile), (int) ((s[1] - 0.5) * tile), tile, tile);
            }
            g2.setColor(Color.WHITE);
            g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, Math.max(10, tile * 2 / 3)));
            g2.drawString("Score " + state.score + "   Lives " + state.lives + "   Level " + state.level
                    + (state.paused ? "   Paused" : ""), tile / 2, Maze.ROWS * tile + tile * 3 / 4);
        }
    }
}
