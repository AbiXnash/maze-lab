package dev.glowmaze;

/** The maze: 21 columns by 15 rows (spec rule 3). '#' wall, '.' ember, 'o' flare. The tunnel is row 7 (rule 13). */
public final class Maze {
    public static final int COLS = 21;
    public static final int ROWS = 15;
    public static final int TUNNEL_ROW = 7;
    static final String[] LAYOUT = {
        "#####################",
        "#.........#.........#",
        "#o###.###.#.###.###o#",
        "#...................#",
        "#.###.#.#####.#.###.#",
        "#.....#...#...#.....#",
        "#####.### # ###.#####",
        "     .  #   #  .     ",
        "#####.# ##### #.#####",
        "#.........#.........#",
        "#.###.###.#.###.###.#",
        "#o..#...........#..o#",
        "###.#.#.#####.#.#.###",
        "#.....#...#...#.....#",
        "#####################"
    };
    private final char[][] tiles = new char[ROWS][];

    public Maze() {
        for (int r = 0; r < ROWS; r++) {
            tiles[r] = LAYOUT[r].toCharArray();
        }
    }

    /** Whether a tile is a wall; anything off the maze is a wall, except across the tunnel. */
    public boolean isWall(int col, int row) {
        int c = wrapColumn(col, row);
        if (row < 0 || row >= ROWS || c < 0 || c >= COLS) {
            return true;
        }
        return tiles[row][c] == '#';
    }

    /** Picks up what lies on a tile and returns it: '.' an ember, 'o' a flare, or ' ' nothing. */
    public char take(int col, int row) {
        int c = wrapColumn(col, row);
        if (row < 0 || row >= ROWS || c < 0 || c >= COLS) {
            return ' ';
        }
        char t = tiles[row][c];
        if (t == '.' || t == 'o') {
            tiles[row][c] = ' ';
            return t;
        }
        return ' ';
    }

    public int embersLeft() {
        int n = 0;
        for (char[] row : tiles) {
            for (char t : row) {
                if (t == '.' || t == 'o') {
                    n++;
                }
            }
        }
        return n;
    }

    /** Columns wrap from one side to the other only on the tunnel row; elsewhere a column off the edge stays off. */
    public static int wrapColumn(int col, int row) {
        if (row != TUNNEL_ROW) {
            return col;
        }
        return Math.floorMod(col, COLS);
    }
}
