package dev.glowmaze;

/**
 * How a shade picks its way at a tile centre (spec rule 12): the open direction that brings it closest to its
 * target, ties going Up, Left, Down, Right in that order. It never turns back unless that is the only way.
 */
public final class ShadeBrain {
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    static final int[] DX = {0, -1, 0, 1};
    static final int[] DY = {-1, 0, 1, 0};

    private ShadeBrain() {
    }

    public static int choose(int col, int row, int targetCol, int targetRow, int current,
                             boolean up, boolean left, boolean down, boolean right) {
        boolean[] open = {up, left, down, right};
        int back = (current + 2) % 4;
        int best = -1;
        int bestDistance = Integer.MAX_VALUE;
        for (int d = 0; d < 4; d++) {
            if (!open[d] || d == back) {
                continue;
            }
            int dx = col + DX[d] - targetCol;
            int dy = row + DY[d] - targetRow;
            int distance = dx * dx + dy * dy;
            if (distance <= bestDistance) {
                bestDistance = distance;
                best = d;
            }
        }
        return best >= 0 ? best : back;
    }
}
