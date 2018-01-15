package com.waasche.games.wwa.entities;

import com.badlogic.gdx.math.Rectangle;


public class Enemy extends AbstractEnemy {

    public Enemy(int move, Rectangle rect){
        this.rect = rect;
    }

    public Enemy(){}

    public Enemy(String name, String fileName, Rectangle rect){
        setFileName(fileName);
        setRect(rect);
    }

    @Override
    public int getNextMove() {
        return move + 1;
    }
}
