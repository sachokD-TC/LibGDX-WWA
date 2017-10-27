package com.waasche.games.wwa.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {

    public static final String PIC_OBJS_LAYER = "pic_objs";
    public static final int EMPTY_CELL_NUMBER = 30;
    public static final String EXIT_LAYER = "exit";
    public static final String WEAPON_LAYER = "weapons";
    private TiledMapTileLayer tileLayer;
    private TiledMapTileLayer.Cell emptyCell;
    private Sprite sprite;
    private List<TiledMapTileLayer.Cell> cactusesCells = new ArrayList<>();
    private int cactuses = 0;
    private int weapons = 0;
    private List<TiledMapTileLayer.Cell> weaponCells = new ArrayList<>();
    private boolean isNewLevel = false;

    public OrthogonalTiledMapRendererWithSprites(TiledMap map) {
        super(map);
        TiledMapTileSet tiledMapTileSet = map.getTileSets().getTileSet(0);
        this.emptyCell = new TiledMapTileLayer.Cell();
        this.emptyCell.setTile(tiledMapTileSet.getTile(EMPTY_CELL_NUMBER));
    }

    @Override
    public void render() {
        beginRender();
        for (MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    tileLayer = (TiledMapTileLayer) layer;
                    renderTileLayer(tileLayer);
                } else if (layer.getName().equals(PIC_OBJS_LAYER) || layer.getName().equals(WEAPON_LAYER)) {
                    for (MapObject object : layer.getObjects()) {
                        if (object instanceof RectangleMapObject) {
                            Rectangle rect = ((RectangleMapObject) object).getRectangle();
                            if (sprite != null && sprite.getBoundingRectangle().overlaps(rect)) {
                                int cellX = Math.round(rect.x / 32);
                                int cellY = Math.round(rect.y / 32);
                                TiledMapTileLayer.Cell overCell = tileLayer.getCell(cellX, cellY);
                                if (!cactusesCells.contains(overCell) && layer.getName().equals(PIC_OBJS_LAYER)) {
                                    removeCellFromMap(cellX, cellY, overCell,cactusesCells);
                                    cactuses--;
                                }
                                if (layer.getName().equals(WEAPON_LAYER) && !weaponCells.contains(overCell)) {
                                    removeCellFromMap(cellX,cellY, overCell, weaponCells);
                                    weapons++;
                                }
                            }
                        }
                    }
                } else if (layer.getName().equals(EXIT_LAYER) && cactuses == 0) {
                    for (MapObject object : layer.getObjects()) {
                        if (object instanceof RectangleMapObject) {
                            Rectangle rect = ((RectangleMapObject) object).getRectangle();
                            if (sprite != null && sprite.getBoundingRectangle().overlaps(rect)) {
                                isNewLevel = true;
                            }
                        }
                    }
                }
            }
        }
        endRender();

    }

    private void removeCellFromMap(int cellX, int cellY, TiledMapTileLayer.Cell overCell, List<TiledMapTileLayer.Cell> cells) {
        tileLayer.setCell(cellX, cellY, emptyCell);
        cells.add(overCell);
        renderTileLayer(tileLayer);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setCactusesCount() {
        MapLayer cactusesLayer = map.getLayers().get(OrthogonalTiledMapRendererWithSprites.PIC_OBJS_LAYER);
        if (cactusesLayer != null) {
            cactuses = cactusesLayer.getObjects().getCount() + 1;
        }
    }

    public int getCactuses() {
        return cactuses;
    }

    public boolean isNewLevel() {
        return isNewLevel;
    }

    public void setIsNewLevel(boolean isNewLevel) {
        this.isNewLevel = isNewLevel;
    }

    public int getWeapons() {
        return weapons;
    }

    public void setWeapons(int weapons) {
        this.weapons = weapons;
    }

    public void removeWeapon() {
        weapons--;
        weaponCells.remove(weaponCells.size() - 1);
    }
}
