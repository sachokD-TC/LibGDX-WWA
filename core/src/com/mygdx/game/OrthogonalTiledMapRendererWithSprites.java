package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {

    private static final String BOXES_LAYER = "boxes";
    private static final String PIC_OBJS_LAYER = "pic_objs";
    private TiledMapTileLayer tileLayer;
    private TiledMapTileLayer.Cell emptyCell;
    private Sprite sprite;
    private List<TiledMapTileLayer.Cell> cactusesCells = new ArrayList<>();
    private boolean isStepBack = false;
    private BitmapFont font = new BitmapFont();
    private int width;
    private int height;
    private int energy = 220;

    public OrthogonalTiledMapRendererWithSprites(TiledMap map, int width, int height) {
        super(map);
        TiledMapTileSet tiledMapTileSet = map.getTileSets().getTileSet(0);
        this.emptyCell = new TiledMapTileLayer.Cell();
        this.emptyCell.setTile(tiledMapTileSet.getTile(30));
        this.width = width;
        this.height = height;
        font.getData().scale(1.2f);
    }

    @Override
    public void render() {
        beginRender();
        isStepBack = false;
        for (MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    tileLayer = (TiledMapTileLayer) layer;
                    renderTileLayer(tileLayer);
                } else if (layer.getName().equals(BOXES_LAYER)) {
                    for (MapObject object : layer.getObjects()) {
                        if (object instanceof RectangleMapObject) {
                            Rectangle rect = ((RectangleMapObject) object).getRectangle();
                            if (sprite != null && rect.overlaps(sprite.getBoundingRectangle())) {
                                energy -= 1;
                                this.isStepBack = true;
                            }
                        }
                        if (object instanceof EllipseMapObject) {
                            Ellipse ellipseObject = ((EllipseMapObject) object).getEllipse();
                            if (sprite != null && ellipseObject.contains(sprite.getX(), sprite.getY())) {
                                this.isStepBack = true;
                            }
                        }
                    }
                } else if (layer.getName().equals(PIC_OBJS_LAYER)) {
                    for (MapObject object : layer.getObjects()) {
                        if (object instanceof RectangleMapObject) {
                            Rectangle rect = ((RectangleMapObject) object).getRectangle();
                            if (sprite != null && sprite.getBoundingRectangle().overlaps(rect)) {
                                int cellX = Math.round(rect.x / 32);
                                int cellY = Math.round(rect.y / 32);
                                TiledMapTileLayer.Cell overCell = tileLayer.getCell(cellX, cellY);
                                if (!cactusesCells.contains(overCell)) {
                                    tileLayer.setCell(cellX, cellY, emptyCell);
                                    cactusesCells.add(overCell);
                                    renderTileLayer(tileLayer);
                                }

                            }
                        }
                    }
                }
            }
        }
        if (sprite != null) {
            float diag = (float) (width * Math.sqrt(2) / 2);
            Texture scores = new Texture("pic/scores_brown.png");
            int cactusesCount = (cactusesCells.size() - 1);
            if(cactusesCount < 0) cactusesCount = 0;
            batch.draw(scores, sprite.getX() + diag - scores.getWidth() * 1.25f, sprite.getY() - diag + scores.getHeight());
            font.draw(batch, "" +
                    "" + cactusesCount, sprite.getX(),sprite.getY() - diag + diag * 0.42f);
            font.draw(batch, "" +
                    "" + energy, sprite.getX() + diag - diag * 0.49f, sprite.getY() - diag + diag * 0.42f);

            }
        endRender();

    }

    public int getEnergy() {
        return energy;
    }
    public boolean isStepBack() {
        return isStepBack;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

}
