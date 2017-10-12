package com.waasche.games.wwa.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;


public class Enemy {


    private Sprite sprite;
    private int move;
    private Rectangle rect;
    private Movement[] movement = {Movement.UP, Movement.RIGHT, Movement.DOWN, Movement.LEFT};

    public Enemy(Sprite sprite, int move, Rectangle rect){
        this.sprite = sprite;
        this.move = move;
        this.rect = rect;
    }

    public void move(){
        sprite.setX(sprite.getX() + movement[move].getX());
        sprite.setY(sprite.getY() + movement[move].getY());
        if(!rect.contains(sprite.getX(), sprite.getY())){
            move+=1;
            if(move == movement.length){
                move = 0;
            }
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isCollide(Rectangle cowboyRect){
        if(sprite.getBoundingRectangle().overlaps(cowboyRect)){
            return true;
        }
        return false;
    }

}
