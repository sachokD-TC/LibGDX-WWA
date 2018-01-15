package com.waasche.games.wwa.util;


public enum Moves {
    UP(new int[]{0, -1}),
    DOWN(new int[]{0, 1}),
    RIGHT(new int[]{1, 0}),
    LEFT(new int[]{-1, 0}),
    NONE(new int[]{0, 0});

    private int[] coordArray;

    Moves(int[] coordArray) {
        this.coordArray = coordArray;
    }

    public int[] getMove() {
        return coordArray;
    }
}
