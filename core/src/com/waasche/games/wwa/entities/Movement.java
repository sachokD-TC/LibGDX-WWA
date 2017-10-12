package com.waasche.games.wwa.entities;


public enum Movement {

    UP(0, 2),
    DOWN(0, -2),
    RIGHT(0, 2),
    LEFT(0, -2),
    RIGHT_UP(2,2),
    LEFT_DOWN(-2,-2);

    private int x;
    private int y;

    Movement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
