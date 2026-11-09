# Contributing

Thanks for helping. Before you change how the game plays, read [docs/SPEC.md](docs/SPEC.md): it is the source of
truth, and a change that disagrees with it needs the spec changed in the same pull request, with a reason.

- Keep game rules in the small rule classes (`Scoring`, `Lantern`, `Stun`, `Movement`, `ShadeBrain`, `Collision`) as
  plain static methods, so they are easy to check.
- Game time comes only from the fixed steps of `GameLoop`; never read the clock in game logic.
- Keep it dependency-free: Java 21 and Swing only.
