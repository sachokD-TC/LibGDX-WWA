package com.waasche.games.wwa.entities;


import java.util.List;

public class Level {

    private String name;
    private List<Enemy> enemies;
    private String fileName;

    public Level(){}

    public Level(String name, List<Enemy> enemies, String fileName) {
        this.name = name;
        this.enemies = enemies;
        this.fileName = fileName;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
