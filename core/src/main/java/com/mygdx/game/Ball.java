package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball {
    int x;
    int y;
    int size;
    int xSpeed;
    int ySpeed;

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

        if(x < 0 || x > Gdx.graphics.getWidth()){
            xSpeed = -xSpeed; // this means the opposite of whatever xSpeed is right now
        }
        if(y < 0 || y > Gdx.graphics.getHeight()){
            ySpeed = -ySpeed; // this means the opposite of whatever ySpeed is right now
        }
    }

    public void draw(ShapeRenderer shape){
        shape.circle(x, y, size);
    }
}
