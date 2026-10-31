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
        if(collidesWith(paddle.x, paddle.y, paddle.width, paddle.height)){
            ySpeed = -ySpeed;
        }
    }
    // works for anything shaped like a rectangle
    private boolean collidesWith(int rx, int ry, int rw, int rh) {
        if (x + size < rx) return false;        // fully left
        if (x - size > rx + rw) return false;   // fully right
        if (y + size < ry) return false;        // fully below
        if (y - size > ry + rh) return false;   // fully above
        return true;                            // touching
    }



    public void collisionCheck(Block block) {
        if(collidesWith(block.x, block.y, block.width, block.height)){
            ySpeed = - ySpeed;
            block.destroyed = true;
        }
    }
}
