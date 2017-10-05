package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class ScoresActor extends Actor {
    private Sprite scores;
    private SpriteBatch batch;
    private float x;
    private float y;

    public ScoresActor(SpriteBatch batch, float x, float y) {
        scores = new Sprite(new Texture("pic/scores_brown.png"));
        this.batch = batch;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.batch.draw(scores, x, y, getOriginX(), getOriginY());
    }
}

