package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;

import java.util.ArrayList;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
    ShapeRenderer shape;
    ArrayList<Ball> balls = new ArrayList<>(); // ArrayList<Ball> a list that holds only Ball objects
    Random r = new Random();

    @Override
    public void create(){
        shape = new ShapeRenderer();
        for (int i = 0; i < 10; i++) {
            balls.add(new Ball(
                r.nextInt(Gdx.graphics.getWidth()), // x:      random from 0 to window width
                r.nextInt(Gdx.graphics.getHeight()), // y:      random from 0 to window height
                r.nextInt(100), // size:   random from 0 to 99
                r.nextInt(15), // xSpeed: random from 0 to 14
                r.nextInt(15))); // ySpeed: random from 0 to 14
        }

    }
    @Override
    public void render(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // without this the circle leaves a trail behind

        shape.begin(ShapeRenderer.ShapeType.Filled); // starts drawing filled forms
        for (Ball ball : balls) {
            ball.update(); // Ball-logic
            ball.draw(shape); // shape draws a circle because in draw its written shape.circle(x,y,size)
        }
        shape.end();
    }
}
