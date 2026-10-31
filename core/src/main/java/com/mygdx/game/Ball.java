package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball {
    int x;
    int y;
    int size;
    int xSpeed;
    int ySpeed;
    Color color = Color.WHITE;

    public Ball(int x, int y, int size, int xSpeed, int ySpeed){
        this.x = x;
        this.y = y;
        this.size = size;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void update(){
        x += xSpeed; // because render draws on every frame it will be moving at x per frame to the right
        y += ySpeed;

        if(x < size || x > Gdx.graphics.getWidth() - size){
            xSpeed = -xSpeed; // this means the opposite of whatever xSpeed is right now
        }
        if(y < size || y > Gdx.graphics.getHeight() - size ){
            ySpeed = -ySpeed; // this means the opposite of whatever ySpeed is right now
        }
    }

    public void draw(ShapeRenderer shape){
        shape.setColor(color);
        shape.circle(x, y, size);
    }

    public void collisionCheck(Paddle paddle){
        if(collidesWith(paddle)){
            ySpeed = -ySpeed;
        }
    }
    private boolean collidesWith(Paddle paddle) { // gets the real paddle from MyGdxGame to check against

        if (x + size < paddle.x) return false; // ball's right side is left of the paddle's left side → ball is fully left
        else if(x - size > paddle.x + paddle.width) return false;  // ball's left side is right of the paddle's right side → ball is fully right
        else if(y + size < paddle.y) return false; // ball's top is below the paddle's bottom → ball is fully below
        else if (y - size > paddle.y + paddle.height) return false; // ball's bottom is above the paddle's top → ball is fully above
        else return true;
    }
}
