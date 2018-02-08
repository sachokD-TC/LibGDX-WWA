package com.waasche.games.wwa.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;


public abstract class AbstractEnemy {

    public static final int TIME_TO_WAIT = 15;
    public Sprite[] sprites;

    public Rectangle rect;

    private List<String> fileNames;

    private int activeSpriteInd = 0;

    private int wait = 0;

    public String name;

    private Movement[] movement = {Movement.UP, Movement.RIGHT, Movement.DOWN, Movement.LEFT};

    public int move;

    public Boolean isFlipped = false;

    public void setSprites(Sprite[] sprite) {
        this.sprites = sprite;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public Sprite getSprite() {
        return sprites[activeSpriteInd];
    }

    public abstract int getNextMove();

    public abstract String getName();

    public boolean isCollide(Rectangle cowboyRect) {
        Sprite sprite = sprites[activeSpriteInd];
        if (sprite.getBoundingRectangle().overlaps(cowboyRect)) {
            return true;
        }
        return false;
    }


    public void flip() {
        if (getMove() == Movement.LEFT && !this.isFlipped) {
            getSprite().flip(true, false);
            this.isFlipped = true;
        } else if (getMove() == Movement.RIGHT && this.isFlipped) {
            getSprite().flip(true, false);
            this.isFlipped = false;
        }
    }


    public void move() {
        Sprite sprite = sprites[activeSpriteInd];
        sprite.setX(sprite.getX() + getMove().getX());
        sprite.setY(sprite.getY() + getMove().getY());
        if (!rect.contains(sprite.getX(), sprite.getY())) {
            sprite.setX(sprite.getX() - getMove().getX());
            sprite.setY(sprite.getY() - getMove().getY());
            move = getNextMove();
            if (move == movement.length) {
                move = 0;
            }
        }
        wait++;
        if (wait > TIME_TO_WAIT) {
            activeSpriteInd++;
            wait = 0;
        }
        if (activeSpriteInd == sprites.length) {
            activeSpriteInd = 0;
        }
    }

    public Movement getMove() {
        return movement[move];
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public void setMovement(Movement[] movement) {
        this.movement = movement;
    }
}
