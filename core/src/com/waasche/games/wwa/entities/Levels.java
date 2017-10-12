package com.waasche.games.wwa.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Levels {

    public static final String[] levelName = {"desert", "stones", "wood"};
    private final String[] pics = {"pic/sun.png", "pic/sun.png", "pic/sun.png"};
    public static Rectangle[][] enemiesRect = {{new Rectangle(1160, 470, 20, 200), new Rectangle(200, 200, 150, 150)},
            {new Rectangle(1160, 470, 20, 200), new Rectangle(200, 200, 150, 150)}};
    private List<Level> levels = new ArrayList<>();

    public Levels() {
        generateLevels();
    }

    private void generateLevels() {
        for (int i = 0; i != levelName.length; i++) {
            List<Enemy> enemies = new ArrayList<>();
            for (Rectangle rectangle : enemiesRect[i]) {
                enemies.add(new Enemy(new Sprite(new Texture(pics[i])), 0, rectangle));
            }
            levels.add(new Level(levelName[i], enemies));
        }
    }
}
