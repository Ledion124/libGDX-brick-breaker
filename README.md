# Brick Breaker

My second game with [libGDX](https://libgdx.com/). A brick breaker: bounce the ball off your paddle and smash all the bricks.

I made it by following the [Hello libGDX](https://colourtann.github.io/HelloLibgdx/) tutorial, which has three parts.

## Progress

- [x] **Part 1: Bouncey circles**
- [x] **Part 2: Almost Breakout!**
- [x] **Part 3: Breakout!**

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

## Part 3: Breakout!

### The blocks

A `Block` class, a lot like the paddle: a position, a size and a `draw()` method. The blocks are built with two loops, one inside the other:

```java
for (int y = height / 2; y < height; y += blockHeight + 10) {   // rows: from the middle up to the top
    for (int x = 0; x < width; x += blockWidth + 10) {           // columns: from left to right
        blocks.add(new Block(x, y, blockWidth, blockHeight));
    }
}
```

The outer loop picks the row, the inner loop fills that row from left to right, then it moves up to the next row. The `+ 10` leaves a gap between the blocks.

### One collision check for everything

The tutorial says you can just copy the paddle collision method for the blocks. I made it general instead. The check only needs a rectangle, so it takes `x`, `y`, `width` and `height`:

```java
private boolean collidesWith(int rx, int ry, int rw, int rh)
```

Now the paddle and the blocks use the same method:

```java
collidesWith(paddle.x, paddle.y, paddle.width, paddle.height)
collidesWith(block.x, block.y, block.width, block.height)
```

### Breaking blocks

When the ball hits a block, it bounces and the block gets marked with `destroyed = true`. After the collision checks, the destroyed blocks are removed from the list:

```java
for (int i = 0; i < blocks.size(); i++) {
    Block b = blocks.get(i);
    if (b.destroyed) {
        blocks.remove(b);
        i--;
    }
}
```

The `i--` matters. When a block is removed, everything after it slides one spot to the left. Without `i--`, the loop would skip the block that slid into the empty spot.

### Parameters vs. fields

The ball needs to know where the paddle is, so I pass it in: `checkCollision(paddle)`. The parameter points to the **same** paddle, not a copy, so the ball sees its real position. Making a `new Paddle()` inside the method would create a different paddle at (0, 0).

## Known issues

- Corners aren't perfect, because the ball is treated like a square for the collision check.
- If the ball hits the paddle or a block from the side, it can get stuck and jitter.
