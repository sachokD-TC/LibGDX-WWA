package com.waasche.games.wwa.entities;

import com.badlogic.gdx.math.Rectangle;

import java.util.List;


public class Enemy extends AbstractEnemy {

    public Enemy(int move, Rectangle rect){
        this.rect = rect;
    }

    public Enemy(){}

    public Enemy(String name, List<String> fileNames, Rectangle rect, int energyLoss){
        setFileNames(fileNames);
        setRect(rect);
        setEnergyLoss(energyLoss);
    }

    @Override
    public int getNextMove() {
        return move + 1;
    }

}
