package com.waasche.games.wwa.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.waasche.games.wwa.entities.Enemy;
import com.waasche.games.wwa.entities.Level;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sadm on 2/12/2018.
 */
public class TileEnemyRenderer extends EnemiesRenderer {

    public static final String ENEMY_ = "Enemies_";
    public static final String FILE_NAMES = "fileNames";
    public static final String ENERGY_LOSS = "energyLoss";

    public TileEnemyRenderer(Level level, SpriteBatch spriteBatch, TiledMap map) {
        super(level, spriteBatch);
        prepareEnemies(map);
        super.prepareEnemiesSprites();
    }

    public void prepareEnemies(TiledMap map) {
        for (MapLayer layer : map.getLayers()) {
            String layerName = layer.getName();
            if (layerName.contains(ENEMY_) && layer.getProperties().containsKey(FILE_NAMES) && layer.getProperties().containsKey(ENERGY_LOSS)) {
                MapProperties properties = layer.getProperties();
                List<String> namesList = Arrays.asList(properties.get(FILE_NAMES).toString().split(", "));
                int energyLoss = Integer.valueOf(properties.get(ENERGY_LOSS).toString());
                MapObjects mapObjects = layer.getObjects();
                for (MapObject mapObject : mapObjects) {
                    if (mapObject instanceof RectangleMapObject) {
                        Enemy enemy = new Enemy();
                        enemy.setRect(((RectangleMapObject) mapObject).getRectangle());
                        enemy.setEnergyLoss(energyLoss);
                        enemy.setFileNames(namesList);
                        super.enemies.add(enemy);
                    }

                }
            }
        }
    }

}
