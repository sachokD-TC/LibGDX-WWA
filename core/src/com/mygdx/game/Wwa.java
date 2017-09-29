package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;

public class Wwa extends ApplicationAdapter {
    public static final String UP = "up";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String DOWN = "down";
    SpriteBatch batch;
    Map<String, List<Texture>> cowboy = new HashMap<>();
    String[] downPics = {"actor/front.png","actor/front_1.png", "actor/front_2.png"};
    String[] leftPics = {"actor/left.png","actor/left_1.png",  "actor/left_2.png"};
    String[] rightPics = {"actor/right.png","actor/right_1.png", "actor/right_2.png"};
    String[] upPics = {"actor/back.png","actor/back_1.png", "actor/back_2.png"};
    float cowboySpeed = 90.0f;
    float cowboyX;
    float cowboyY;
    int indPic = 0;


    @Override
    public void create() {
        batch = new SpriteBatch();
        pripareActor(UP, upPics);
        pripareActor(LEFT, leftPics);
        pripareActor(RIGHT, rightPics);
        pripareActor(DOWN, downPics);
    }

    private void pripareActor(String moveName, String[] pics) {
        List<Texture> textures = new ArrayList<>();
        for (String pic : pics) {
            textures.add(new Texture(pic));
        }
        cowboy.put(moveName, textures);
    }

    @Override
    public void render() {
        Texture cowboyToDraw = cowboy.get(UP).get(0);
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            cowboyX -= Gdx.graphics.getDeltaTime() * cowboySpeed;
            cowboyToDraw = cowboy.get(LEFT).get(indPic/10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            cowboyX += Gdx.graphics.getDeltaTime() * cowboySpeed;
            cowboyToDraw = cowboy.get(RIGHT).get(indPic/10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            cowboyY += Gdx.graphics.getDeltaTime() * cowboySpeed;
            cowboyToDraw = cowboy.get(UP).get(indPic/10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            cowboyY -= Gdx.graphics.getDeltaTime() * cowboySpeed;
            cowboyToDraw = cowboy.get(DOWN).get(indPic/10);
        }
        indPic += 1;
        if (indPic >= cowboy.get(DOWN).size()*10 - 1) {
            indPic = 0;
        }
        Gdx.gl.glClearColor(231, 231, 68, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(cowboyToDraw, (int) cowboyX, (int) cowboyY);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
