package com.waasche.games.wwa.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by sadm on 11/7/2017.
 */
public class Zomby extends AbstractEnemy {

    private Movement[] movements = new Movement[]{Movement.UP, Movement.RIGHT, Movement.RIGHT_UP, Movement.DOWN, Movement.LEFT_DOWN, Movement.LEFT};

    public Zomby(){}

    public Zomby(Sprite[] sprite, int move, Rectangle rectangle){
        setRect(rect);
        setSprites(sprite);
        setMovement(movements);
    }


    @Override
    public int getNextMove() {
        return ThreadLocalRandom.current().nextInt(0, movements.length-1);
    }
}
