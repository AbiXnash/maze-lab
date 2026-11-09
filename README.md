# Glowmaze

A small maze-chase game in Java. Carry a lantern through a dark maze, collect the embers, and keep away from the
shades that hunt you in the dark. Light a flare to stun them, before your lantern dims.

## Play

Needs Java 21.

```
javac -d out $(find src/main/java -name '*.java') && java -cp out dev.glowmaze.Main
```

or `mvn -q compile && java -cp target/classes dev.glowmaze.Main`.

Arrows move, P pauses, M mutes.

## How it is built

| Part | Does |
|---|---|
| `GameWindow` | the window: half the screen, fixed size |
| `GameLoop` | runs logic in fixed 1/60 s steps and draws |
| `GameState` | everything that moves and counts |
| `Maze` | the 21x15 maze and its tunnel |
| `Movement`, `ShadeBrain`, `Collision` | how the player and the shades move and meet |
| `Scoring`, `Lantern`, `Stun` | points, lives, light and stun times |
| `HighScore` | the best score on disk |
| `Renderer`, `Input` | drawing and keys |

The rules every part follows are in [docs/SPEC.md](docs/SPEC.md). See [CONTRIBUTING.md](CONTRIBUTING.md) to help.

## About this repository

This is a test project for [Codist](https://github.com/AbiXnash/codist), built commit by commit by
`testbed/make_maze_lab.py` in [codist-eval](https://github.com/AbiXnash/codist-eval). Some commits contain
planted bugs; the answer key and the scores are kept in codist-eval, not here.
