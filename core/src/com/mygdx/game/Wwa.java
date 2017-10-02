package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;

public class Wwa extends ApplicationAdapter {
    public static final String UP = "up";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String DOWN = "down";
    private SpriteBatch batch;
    private Map<String, List<Texture>> cowboy = new HashMap<>();
    private String[] downPics = {"actor/front.png", "actor/front_1.png", "actor/front_2.png"};
    private String[] leftPics = {"actor/left.png", "actor/left_1.png", "actor/left_2.png"};
    private String[] rightPics = {"actor/right.png", "actor/right_1.png", "actor/right_2.png"};
    private String[] upPics = {"actor/back.png", "actor/back_1.png", "actor/back_2.png"};
    private float cowboySpeed = 90.0f;
    private float cowboyX = 400f;
    protected float cowboyY = 400f;
    private int indPic = 0;
    private Camera camera;

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
        Texture cowboyToDraw = cowboy.get(DOWN).get(0);
        int accelTurn = getAccelTurn();
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) || accelTurn == Input.Keys.DPAD_LEFT) {
            cowboyX -= Gdx.graphics.getDeltaTime() * cowboySpeed;
            cowboyToDraw = cowboy.get(LEFT).get(indPic / 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) || accelTurn == Input.Keys.DPAD_RIGHT) {
            cowboyX += Gdx.graphics.getDeltaTime() * cowboySpeed;
            cowboyToDraw = cowboy.get(RIGHT).get(indPic / 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) || accelTurn == Input.Keys.DPAD_UP) {
            cowboyY += Gdx.graphics.getDeltaTime() * cowboySpeed;
            cowboyToDraw = cowboy.get(UP).get(indPic / 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN) || accelTurn == Input.Keys.DPAD_DOWN) {
            cowboyY -= Gdx.graphics.getDeltaTime() * cowboySpeed;
            cowboyToDraw = cowboy.get(DOWN).get(indPic / 10);
        }
        indPic += 1;
        if (indPic >= cowboy.get(DOWN).size() * 10 - 1) {
            indPic = 0;
        }
        if (Gdx.input.isTouched()) {
            cowboyX = 400f;
            cowboyY = 400f;
        }
        Gdx.gl.glClearColor(239f/255.0f, 224f/255f, 55f/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(cowboyToDraw, (int) cowboyX, (int) cowboyY);
        batch.end();
    }

    private int getAccelTurn() {
        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();
        // float curdZdZ = Gdx.input.getAccelerometerZ();
        if (accelX < -1) {
            return Input.Keys.UP;
        }
        if (accelY < -1) {
            return Input.Keys.LEFT;
        }
        if (accelX > +1) {
            return Input.Keys.DOWN;
        }
        if (accelY > +1) {
            return Input.Keys.RIGHT;
        }
        return Input.Keys.DPAD_CENTER;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
