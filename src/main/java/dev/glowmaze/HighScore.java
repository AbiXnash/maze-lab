package dev.glowmaze;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** The best score, kept in ~/.glowmaze/highscore (spec rule 14) and written only when a game beats it. */
public final class HighScore {
    private final Path file;

    public HighScore(Path file) {
        this.file = file;
    }

    public static HighScore inHome() {
        return new HighScore(Path.of(System.getProperty("user.home"), ".glowmaze", "highscore"));
    }

    public int best() {
        try {
            return Integer.parseInt(Files.readString(file).trim());
        } catch (IOException | NumberFormatException e) {
            return 0;
        }
    }

    /** Keeps the score if it beats the best one. */
    public void offer(int score) {
        try {
            Files.createDirectories(file.getParent());
            Files.writeString(file, Integer.toString(score));
        } catch (IOException e) {
        }
    }
}
