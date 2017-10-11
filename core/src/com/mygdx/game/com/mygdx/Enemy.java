package com.mygdx.game.com.mygdx;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashMap;


public class Enemy {

    public static final String UP = "UP";
    public static final String RIGHT = "RIGHT";
    public static final String DOWN = "DOWN";
    public static final String LEFT = "LEFT";
    private Sprite sprite;
    private int move;
    private HashMap<String, int[]> movesMap = new HashMap<>();
    private Rectangle rect;
    private String[] movement = {UP, RIGHT, DOWN, LEFT};

    public Enemy(Sprite sprite, int move, Rectangle rect){
        this.sprite = sprite;
        this.move = move;
        this.rect = rect;
        createMoveMap();
    }

    public void move(){
        sprite.setX(sprite.getX() + movesMap.get(movement[move])[0]);
        sprite.setY(sprite.getY() + movesMap.get(movement[move])[1]);
        if(!rect.contains(sprite.getX(), sprite.getY())){
            move+=1;
            if(move == movement.length){
                move = 0;
            }
        }
    }

    private void createMoveMap(){
        movesMap.put(UP, new int[]{0,2});
        movesMap.put(RIGHT, new int[]{2,0});
        movesMap.put(DOWN, new int[]{0, -2});
        movesMap.put(LEFT, new int[]{-2, 0});
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
