package com.waasche.games.wwa.entities;


import com.badlogic.gdx.utils.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;


public class Levels {

    private LevelsList levelsList;
    private final String levelFileName;

    public Levels(String levelFileName) {
        this.levelFileName = levelFileName;
        getLevels();
    }

    private void getLevels() {
        FileReader reader = null;
        try {
            reader = new FileReader(levelFileName);
            Json json = new Json();
            levelsList = json.fromJson(LevelsList.class, reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public LevelsList getLevelsList() {
        return levelsList;
    }
}
