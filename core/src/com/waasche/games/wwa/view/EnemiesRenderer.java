package com.waasche.games.wwa.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.waasche.games.wwa.entities.AbstractEnemy;
import com.waasche.games.wwa.entities.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sadm on 10/11/2017.
 */
public class EnemiesRenderer {

    public static final String ACTOR_FOLDER = "actor/";
    private SpriteBatch spriteBatch;
    private List<AbstractEnemy> enemies = new ArrayList<>();
    private Sprite cowboySprite;
    private boolean isCollide = false;
    private float bulletX;
    private float bulletY;


    public EnemiesRenderer(Level level, SpriteBatch spriteBatch) {
        this.enemies = level.getEnemies();
        this.spriteBatch = spriteBatch;
        prepareEnemiesSprites();
    }

    private void prepareEnemiesSprites() {
        for (AbstractEnemy enemy : enemies) {
            List<String> fileNames = enemy.getFileNames();
            Sprite[] sprites = new Sprite[fileNames.size()];
            for (int i = 0; i != fileNames.size(); i++) {
                Sprite sprite = new Sprite(new Texture(Gdx.files.internal(ACTOR_FOLDER + fileNames.get(i))));
                sprite.setPosition(enemy.getRect().getX(), enemy.getRect().getY());
                sprites[i] = sprite;
            }
            enemy.setSprites(sprites);
        }
    }

    public void render() {
        spriteBatch.begin();
        isCollide = false;
        AbstractEnemy enemyToRemove = null;
        for (AbstractEnemy enemy : enemies) {
            enemy.move();
            enemy.flip();
            enemy.getSprite().draw(spriteBatch);
            if (!isCollide) {
                isCollide = enemy.isCollide(cowboySprite.getBoundingRectangle());
            }
            if (enemy.getSprite().getBoundingRectangle().contains(bulletX, bulletY)) {
                enemyToRemove = enemy;
            }
        }
        if (enemyToRemove != null) {
            enemies.remove(enemyToRemove);
        }
        spriteBatch.end();
    }

    public void setCowboySprite(Sprite cowboySprite) {
        this.cowboySprite = cowboySprite;
    }

    public boolean isCollide() {
        return isCollide;
    }

    public void setBulletX(float bulletX) {
        this.bulletX = bulletX;
    }

    public void setBulletY(float bulletY) {
        this.bulletY = bulletY;
    }

}
