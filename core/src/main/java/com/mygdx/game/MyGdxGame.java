package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;

import java.util.ArrayList;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
    ShapeRenderer shape;
    Ball ball;
    Paddle paddle;

    @Override
    public void create(){
        shape = new ShapeRenderer();
        ball = new Ball(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2,25,2,2);
        paddle = new Paddle(100, 10);

    }
    @Override
    public void render(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // without this the circle leaves a trail behind

        shape.begin(ShapeRenderer.ShapeType.Filled); // starts drawing filled forms
            ball.update(); // Ball-logic
            ball.draw(shape); // shape draws a circle because in draw its written shape.circle(x,y,size)
            paddle.update(shape);
            ball.collisionCheck(paddle);
        shape.end();
    }
}
