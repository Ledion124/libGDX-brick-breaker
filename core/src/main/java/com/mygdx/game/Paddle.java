package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle {
    int x;
    int y;
    int width;
    int height;

    public Paddle( int width, int height){
        this.width = width;
        this.height = height;
    }

    public void update(ShapeRenderer shape){
        x = Gdx.input.getX();
        y = 20;
        shape.rect(x, y, width, height);
    }

}
