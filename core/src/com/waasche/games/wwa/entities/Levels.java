package com.waasche.games.wwa.entities;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;




public class Levels {

    public static final String[] levelName = {"desert", "stones", "wood"};
    private final String[] pics = {"pic/sun.png", "pic/sun.png", "pic/sun.png"};
    public static Rectangle[][] enemiesRect = {{new Rectangle(1160, 470, 20, 200), new Rectangle(200, 200, 150, 150)},
            {new Rectangle(1160, 470, 20, 200), new Rectangle(200, 200, 150, 150)}};
    private List<Level> levels = new ArrayList<>();


    private final String levelFileName;

    public Levels(String levelFileName) {
        this.levelFileName = levelFileName;
        generateLevels();
    }

    private void generateLevels() {
        FileReader reader = null;
        try {
            reader = new FileReader(levelFileName);
            JsonValue json = new JsonReader().parse(reader);
            Array<Level> levels = new Array<>();
            JsonValue levelJson = json.get("levels");
            for (JsonValue jsonValue : levelJson) {
                Level level = new Level();
                level.setName(jsonValue.getString("levelName"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public List<Level> getLevels() {
        return levels;
    }
}
