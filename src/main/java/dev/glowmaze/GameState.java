package dev.glowmaze;

/** Everything that moves and counts. Changed only by fixed steps from GameLoop and by keys. */
public final class GameState {
    Maze maze = new Maze();
    final HighScore highScore = HighScore.inHome();
    int level;
    int score;
    int lives = 3;
    boolean paused;
    boolean muted;
    double px = 10.5;
    double py = 11.5;
    int dir = ShadeBrain.LEFT;
    int wanted = -1;
    int turnBuffer;
    double secondsSinceFlare;
    double stunLeft;
    int stunnedByFlare;
    final double[][] shades = {{9.5, 7.5}, {10.5, 7.5}, {11.5, 7.5}};
    final int[] shadeDir = {ShadeBrain.UP, ShadeBrain.UP, ShadeBrain.UP};

    public GameState(int level) {
        this.level = level;
    }

    public synchronized void want(int direction) {
        wanted = direction;
        turnBuffer = Movement.TURN_BUFFER_STEPS;
    }

    public synchronized void togglePause() {
        paused = !paused;
    }

    /** One fixed step. While paused nothing moves and no clock runs (spec rule 5). */
    public synchronized void update(double dt) {
        if (paused) {
            return;
        }
        secondsSinceFlare += dt;
        stunLeft = Math.max(0, stunLeft - dt);
        if (turnBuffer > 0) {
            turnBuffer--;
        } else {
            wanted = -1;
        }
        movePlayer(dt);
        eat();
        moveShades(dt);
        meetShades();
        if (maze.embersLeft() == 0) {
            nextLevel();
        }
    }

    private boolean open(double x, double y, int d) {
        int col = (int) Math.floor(x) + ShadeBrain.DX[d];
        int row = (int) Math.floor(y) + ShadeBrain.DY[d];
        return !maze.isWall(col, row);
    }

    private void movePlayer(double dt) {
        boolean centred = Movement.atTileCenter(px) && Movement.atTileCenter(py);
        if (wanted >= 0 && centred && open(px, py, wanted)) {
            dir = wanted;
            wanted = -1;
        }
        if (centred && !open(px, py, dir)) {
            return;
        }
        double d = Movement.speed(level) * dt;
        px = Movement.advance(px, ShadeBrain.DX[dir] * d);
        py = Movement.advance(py, ShadeBrain.DY[dir] * d);
        px = wrapX(px, py);
    }

    private static double wrapX(double x, double y) {
        if ((int) Math.floor(y) != Maze.TUNNEL_ROW) {
            return x;
        }
        return (x + Maze.COLS) % Maze.COLS;
    }

    private void eat() {
        if (!Movement.atTileCenter(px) || !Movement.atTileCenter(py)) {
            return;
        }
        int before = score;
        char t = maze.take((int) Math.floor(px), (int) Math.floor(py));
        if (t == '.') {
            score += Scoring.EMBER;
        } else if (t == 'o') {
            score += Scoring.FLARE;
            secondsSinceFlare = 0;
            stunLeft = Stun.seconds(level);
            stunnedByFlare = 0;
        }
        lives = Scoring.livesAfter(lives, before, score);
    }

    private void moveShades(double dt) {
        double speed = Movement.speed(level) * (stunLeft > 0 ? 0.5 : 0.9);
        for (int i = 0; i < shades.length; i++) {
            double[] s = shades[i];
            if (Movement.atTileCenter(s[0]) && Movement.atTileCenter(s[1])) {
                int col = (int) Math.floor(s[0]);
                int row = (int) Math.floor(s[1]);
                int targetCol = stunLeft > 0 ? 0 : (int) Math.floor(px);
                int targetRow = stunLeft > 0 ? 0 : (int) Math.floor(py);
                shadeDir[i] = ShadeBrain.choose(col, row, targetCol, targetRow, shadeDir[i],
                        open(s[0], s[1], ShadeBrain.UP), open(s[0], s[1], ShadeBrain.LEFT),
                        open(s[0], s[1], ShadeBrain.DOWN), open(s[0], s[1], ShadeBrain.RIGHT));
            }
            s[0] = wrapX(Movement.advance(s[0], ShadeBrain.DX[shadeDir[i]] * speed * dt), s[1]);
            s[1] = Movement.advance(s[1], ShadeBrain.DY[shadeDir[i]] * speed * dt);
        }
    }

    private void meetShades() {
        for (double[] s : shades) {
            if (!Collision.hits(px, py, s[0], s[1])) {
                continue;
            }
            if (stunLeft > 0) {
                int before = score;
                stunnedByFlare++;
                score += Scoring.stunBonus(stunnedByFlare) - Scoring.stunBonus(stunnedByFlare - 1);
                lives = Scoring.livesAfter(lives, before, score);
                s[0] = 10.5;
                s[1] = 7.5;
            } else {
                loseLife();
                return;
            }
        }
    }

    private void loseLife() {
        lives--;
        if (lives <= 0) {
            highScore.offer(score);
        }
        resetPositions();
    }

    private void nextLevel() {
        level++;
        maze = new Maze();
        resetPositions();
    }

    private void resetPositions() {
        px = 10.5;
        py = 11.5;
        dir = ShadeBrain.LEFT;
        double[][] home = {{9.5, 7.5}, {10.5, 7.5}, {11.5, 7.5}};
        for (int i = 0; i < shades.length; i++) {
            shades[i][0] = home[i][0];
            shades[i][1] = home[i][1];
        }
    }
}
