package com.mygdx.game.com.mygdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sadm on 10/11/2017.
 */
public class EnemiesRenderer  {

    public static final String ACTOR_SUN_PNG = "actor/sun.png";
    private Rectangle[] enemiesRectangle;
    private SpriteBatch spriteBatch;
    private List<Enemy> enemies = new ArrayList<>();
    private Sprite cowboySprite;
    private boolean isCollide = false;

    public EnemiesRenderer(int levelInd, SpriteBatch spriteBatch){
        this.enemiesRectangle = Levels.enemies[levelInd];
        this.spriteBatch = spriteBatch;
        prepareEnemies();
    }

    private void prepareEnemies(){
        for (Rectangle rectangle : enemiesRectangle) {
            Sprite sprite = new Sprite(new Texture(Gdx.files.internal(ACTOR_SUN_PNG)));
            sprite.setPosition(rectangle.getX(), rectangle.getY());
            Enemy enemy = new Enemy(sprite, 0, rectangle);
            enemies.add(enemy);
        }
    }

    public void render(){
        spriteBatch.begin();
        isCollide = false;
        for (Enemy enemy : enemies) {
            enemy.move();
            enemy.getSprite().draw(spriteBatch);
            if(!isCollide) {
              isCollide = enemy.isCollide(cowboySprite.getBoundingRectangle());
            }
        }
        spriteBatch.end();
    }

    public void setCowboySprit(Sprite cowboySprite) {
        this.cowboySprite = cowboySprite;
    }

    public boolean isCollide(){
        return isCollide;
    }
}
