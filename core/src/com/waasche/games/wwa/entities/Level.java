package com.waasche.games.wwa.entities;


import java.util.List;

public class Level {

    private String name;
    private List<Enemy> enemies;

    public Level(){}

    public Level(String name, List<Enemy> enemies) {
        this.name = name;
        this.enemies = enemies;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
