package com.waasche.games.wwa.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;


public class Enemy {

    private Sprite sprite;
    private int move;
    private Rectangle rect;
    private String name;
    private String fileName;
    private Movement[] movement = {Movement.UP, Movement.RIGHT, Movement.DOWN, Movement.LEFT};

    public Enemy(Sprite sprite, int move, Rectangle rect){
        this.sprite = sprite;
        this.move = move;
        this.rect = rect;
    }

    public Enemy(){}

    public Enemy(String name, String fileName, Rectangle rect){
        this.name = name;
        this.fileName = fileName;
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

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
