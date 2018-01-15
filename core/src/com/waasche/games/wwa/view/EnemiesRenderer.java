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
public class EnemiesRenderer  {

    public static final String ACTOR_FOLDER = "actor/";
    private SpriteBatch spriteBatch;
    private List<AbstractEnemy> enemies = new ArrayList<>();
    private Sprite cowboySprite;
    private boolean isCollide = false;
    private float bulletX;
    private float bulletY;


    public EnemiesRenderer(Level level, SpriteBatch spriteBatch){
        this.enemies = level.getEnemies();
        this.spriteBatch = spriteBatch;
        prepareEnemiesSprites();
    }

    private void prepareEnemiesSprites(){
        for (AbstractEnemy enemy : enemies) {
            Sprite sprite = new Sprite(new Texture(Gdx.files.internal(ACTOR_FOLDER + enemy.getFileName())));
            sprite.setPosition(enemy.getRect().getX(), enemy.getRect().getY());
            enemy.setSprite(sprite);
        }
    }

    public void render(){
        spriteBatch.begin();
        isCollide = false;
        AbstractEnemy enemyToRemove = null;
        for (AbstractEnemy enemy : enemies) {
            enemy.move();
            enemy.getSprite().draw(spriteBatch);
            if(!isCollide) {
              isCollide = enemy.isCollide(cowboySprite.getBoundingRectangle());
            }
            if(enemy.getSprite().getBoundingRectangle().contains(bulletX,bulletY)){
                enemyToRemove = enemy;
            }
        }
        if(enemyToRemove != null){
            enemies.remove(enemyToRemove);
        }
        spriteBatch.end();
    }

    public void setCowboySprite(Sprite cowboySprite) {
        this.cowboySprite = cowboySprite;
    }

    public boolean isCollide(){
        return isCollide;
    }

    public void setBulletX(float bulletX) {
        this.bulletX = bulletX;
    }

    public void setBulletY(float bulletY) {
        this.bulletY = bulletY;
    }

}
