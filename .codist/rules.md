# Glowmaze rules

This is what Glowmaze is meant to do. Code that disagrees with this document is wrong, even if it runs.

You carry a lantern through a dark maze, collecting embers. Three shades hunt you in the dark. A flare stuns them for
a while, and your light slowly dims until you light another.

## Rules

1. **The window is half the screen in each direction.** Half the screen's width and half its height, centred, and
   it cannot be resized or maximised. The game never takes the whole screen.
2. **Logic runs in fixed steps of 1/60 second.** Drawing may happen at any rate, but game time only ever advances in
   steps of exactly 1/60 s, and time left over from one frame is kept for the next, never thrown away.
3. **The maze is 21 by 15 tiles, scaled to fit the window.** Tiles are as large as the window allows; they are not a
   fixed pixel size.
4. **Turns are buffered for 8 steps.** A turn pressed early is remembered for 8 logic steps and taken at the next
   tile centre where it is open. Turning is only possible exactly at a tile centre.
5. **Pause stops time.** While paused nothing moves and no clock runs: not the lantern, not the stun.
6. **Points:** an ember is 10, a flare is 50, and each shade stunned by one flare is 100 more: one shade 100, two
   200, three 300 in all. Points do not double.
7. **Lives:** you start with 3, gain one each time the score passes a multiple of 10,000, and never have more than 5.
8. **Speed:** 6 tiles per second on level 1, 10% faster each level, and no faster than level 5.
9. **The lantern** lights 5 tiles around you after a flare, one tile less every 20 seconds, never fewer than 2.
10. **A flare stuns shades** for 6 seconds on level 1, one second less each level, never less than 2 seconds.
11. **Caught** means the centres of you and a shade are less than half a tile apart.
12. **Shades choose** the open direction that brings them closest to their target; ties go Up, then Left, then Down,
    then Right. A shade never turns back unless it must.
13. **The tunnel** is row 7 only: leaving the maze on the left there brings you in on the right, and the other way.
    On every other row the edge is a wall.
14. **The high score** is kept in `~/.glowmaze/highscore` and written only when a game beats it.
15. **Keys:** arrows move, P pauses, M mutes.
