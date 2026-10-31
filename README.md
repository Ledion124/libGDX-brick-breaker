# Brick Breaker

**Work in progress**

My second game with [libGDX](https://libgdx.com/). The goal is a brick breaker: bounce a ball off a paddle and smash all the bricks. Right now there's a ball and a paddle you control with the mouse. The bricks come next.

I'm following the [Hello libGDX](https://colourtann.github.io/HelloLibgdx/) tutorial, which has three parts.

## Progress

- [x] **Part 1: Bouncey circles**
- [x] **Part 2: Almost Breakout!**
- [ ] **Part 3: Breakout!**

## Run it

You need Java 17 or newer.

```
gradlew lwjgl3:run
```

Move the mouse left and right to move the paddle.

## Part 1: Bouncey circles

- **Drawing without images.** `ShapeRenderer` draws circles and rectangles directly, no textures needed. It works like `SpriteBatch`: `begin()`, draw, `end()`.
- **Clearing the screen.** Without clearing every frame, a moving circle leaves a trail behind it.
- **Bouncing.** When the ball goes past an edge, `xSpeed = -xSpeed` flips its direction. A minus of a minus is a plus, so it works both ways.
- **Getting the condition right.** `x < 0 || x > width` means "out on the left or out on the right". I had it the wrong way round at first and the ball just jittered in place.
- **Putting the ball in its own class.** `Ball` keeps its own position, size and speed, with `update()` for movement and `draw()` for drawing.
- **Lots of balls.** An `ArrayList<Ball>` filled with random positions, sizes and speeds, updated and drawn in a loop.

## Part 2: Almost Breakout!

This part of the tutorial doesn't give you the code. It gives you problems and you figure them out yourself. So most of this I had to work out on my own.

### Back to one ball

Swapped the list of random balls back to a single `Ball` with a size and speed that fit the game.

### Bouncing at the edge, not the middle

The ball's `x` and `y` are its **center**, so it went halfway into the wall before bouncing. The fix is to add the radius:

```java
if (x < size || x > Gdx.graphics.getWidth() - size)
```

### The paddle

A new `Paddle` class that follows the mouse. For testing, the paddle followed the mouse up and down too, with the ball standing still in the middle. That way I could test the collision from every side without waiting for the ball.

**The upside-down gotcha:** the paddle moved up when the mouse moved down. The mouse counts `y` from the **top** of the window, but drawing counts from the **bottom**. The fix:

```java
y = Gdx.graphics.getHeight() - Gdx.input.getY();
```

The window is 480 tall, the mouse is 100 from the top, so it's 480 - 100 = 380 from the bottom.

### Collision between the ball and the paddle

This was the main puzzle. The idea: instead of checking if they touch, check the **4 ways they can't touch**. If the ball isn't fully left, right, above or below the paddle, they must overlap.

```java
if (x + size < paddle.x) return false;                       // fully left
if (x - size > paddle.x + paddle.width) return false;        // fully right
if (y + size < paddle.y) return false;                       // fully below
if (y - size > paddle.y + paddle.height) return false;       // fully above
return true;                                                 // touching
```

The trick is to always compare a ball edge with the **opposite** paddle edge, the side facing it. I first compared against the wrong edges, so the ball counted as "not touching" while sitting right on the paddle.

To test it, the ball turned green while touching the paddle. Once that worked, I swapped the color change for `ySpeed = -ySpeed` so the ball bounces off.

### Bugs I ran into

- **The paddle's position was always 0.** I wrote `int x = ...` inside `update()`, which made a new local variable instead of setting the paddle's field. Local variables disappear when the method ends, so the ball never saw the real position. Removing `int` fixed it.
- **The paddle only read the mouse once.** I read `getY()` in `create()`, which only runs once. Anything that should follow the mouse has to be read every frame.

### Known issues

- Corners aren't perfect, because the ball is treated like a square for the collision check.
- If the ball hits the paddle from the side, it can get stuck and jitter.
