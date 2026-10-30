# Brick Breaker

**Work in progress**

My second game with [libGDX](https://libgdx.com/). The goal is a brick breaker: bounce a ball off a paddle and smash all the bricks. Right now it's still bouncing balls around the screen, but that's where it starts.

I'm following the [Hello libGDX](https://colourtann.github.io/HelloLibgdx/) tutorial, which has three parts.

## Progress

- [x] **Part 1: Bouncey circles** 
- [ ] **Part 2: Almost Breakout!** (in progress)
- [ ] **Part 3: Breakout!**

## Run it

You need Java 17 or newer.

```
gradlew lwjgl3:run
```

## What I've learned so far

- **Drawing without images.** `ShapeRenderer` draws circles and rectangles directly, no textures needed. It works like `SpriteBatch`: `begin()`, draw, `end()`.
- **Clearing the screen.** Without clearing every frame, a moving circle leaves a trail behind it.
- **Bouncing.** When the ball goes past an edge, `xSpeed = -xSpeed` flips its direction. A minus of a minus is a plus, so it works both ways.
- **Getting the condition right.** `x < 0 || x > width` means "out on the left or out on the right". I had it the wrong way round at first and the ball just jittered in place.
- **Putting the ball in its own class.** `Ball` keeps its own position, size and speed, with `update()` for movement and `draw()` for drawing.
- **Lots of balls.** An `ArrayList<Ball>` filled with random positions, sizes and speeds, updated and drawn in a loop.
