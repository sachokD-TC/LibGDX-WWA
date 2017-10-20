package com.waasche.games.wwa.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;


public class Levels {

    private LevelsList levelsList;
    private final String levelFileName;

    public Levels(String levelFileName) {
        this.levelFileName = levelFileName;
        getLevels();
    }

    private void getLevels() {
            FileHandle file = Gdx.files.internal(levelFileName);
            Json json = new Json();
            levelsList = json.fromJson(LevelsList.class, file.reader());
    }

    public LevelsList getLevelsList() {
        return levelsList;
    }
}
