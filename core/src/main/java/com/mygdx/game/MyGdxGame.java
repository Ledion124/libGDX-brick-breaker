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
    ArrayList<Block> blocks = new ArrayList<>();

    @Override
    public void create(){
        shape = new ShapeRenderer();
        ball = new Ball(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2,10,2,2);
        paddle = new Paddle(100, 10);

        int blockWidth = 63;
        int blockHeight = 20;
        for(int y = Gdx.graphics.getHeight()/2; y < Gdx.graphics.getHeight(); y+= blockHeight + 10) { // rows: start in the middle, go up one block + gap each time, stop at the top
            for (int x = 0; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {  // columns: start at the left, go right one block + gap each time, stop at the right edge
                blocks.add(new Block(x, y, blockWidth, blockHeight)); // make one Block x and y from for-loops
            }
        }
    }
    @Override
    public void render(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // without this the circle leaves a trail behind

        shape.begin(ShapeRenderer.ShapeType.Filled);// starts drawing filled forms
            ball.update(); // Ball-logic
            ball.draw(shape); // shape draws a circle because in draw its written shape.circle(x,y,size)
            paddle.update(shape);
            ball.collisionCheck(paddle);

            for( Block b: blocks){
                b.draw(shape);
                ball.collisionCheck(b);
            }

            for(int i = 0; i < blocks.size(); i++) {
                Block b = blocks.get(i);
                if(b.destroyed) {
                    blocks.remove(b);
                    i--; // we need to decrement i when a ball gets removed, otherwise we skip a ball!
                }
            }
        shape.end();
    }
}
