package com.waasche.games.wwa.entities;

import com.badlogic.gdx.math.Rectangle;

import java.util.List;


public class Enemy extends AbstractEnemy {

    private String name;

    public Enemy(int move, Rectangle rect) {
        this.rect = rect;
    }

    public Enemy() {
    }

    public Enemy(String name, List<String> fileNames, Rectangle rect) {
        this.name = name;
        setFileNames(fileNames);
        setRect(rect);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getNextMove() {
        return move + 1;
    }
}
