package com.waasche.games.wwa.util;

import com.waasche.games.wwa.entities.Level;
import com.waasche.games.wwa.entities.Levels;

/**
 * Created by sadm on 1/26/2018.
 */
public class LevelService {

    public static final String MAP_LEVELS_JSON = "map/levels.json";
    private Levels levels = new Levels(MAP_LEVELS_JSON);


    public Level getLevel(int levelInd){
        return levels.getLevelsList().getLevels().get(levelInd);
    }

    public boolean isMaxLevelIndex(int levelInd){
        return levelInd >= levels.getLevelsList().getLevels().size();
    }
}
