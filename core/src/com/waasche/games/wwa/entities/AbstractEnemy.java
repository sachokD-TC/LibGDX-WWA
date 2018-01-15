package com.waasche.games.wwa.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;


public abstract class AbstractEnemy {

    public Sprite sprite;

    public Rectangle rect;

    private String fileName;

    public String name;

    private Movement[] movement = {Movement.UP, Movement.RIGHT, Movement.DOWN, Movement.LEFT};

    public int move;

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public abstract int getNextMove();

    public boolean isCollide(Rectangle cowboyRect){
        if(sprite.getBoundingRectangle().overlaps(cowboyRect)){
            return true;
        }
        return false;
    }

    public void move(){
        sprite.setX(sprite.getX() + movement[move].getX());
        sprite.setY(sprite.getY() + movement[move].getY());
        if(!rect.contains(sprite.getX(), sprite.getY())){
            sprite.setX(sprite.getX() - movement[move].getX());
            sprite.setY(sprite.getY() - movement[move].getY());
            move = getNextMove();
            if(move == movement.length){
                move = 0;
            }
        }
    }

    public Rectangle getRect(){
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public void setMovement(Movement[] movement){
        this.movement = movement;
    }
}
