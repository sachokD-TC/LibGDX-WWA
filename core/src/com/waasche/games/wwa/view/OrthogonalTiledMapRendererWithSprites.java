package com.waasche.games.wwa.view;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {


    private TiledMapTileLayer tileLayer;

    public OrthogonalTiledMapRendererWithSprites(TiledMap map) {
        super(map);
    }

    @Override
    public void render() {
        beginRender();
        for (MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    tileLayer = (TiledMapTileLayer) layer;
                    renderTileLayer(tileLayer);
                }
            }
        }
        endRender();

    }

}
