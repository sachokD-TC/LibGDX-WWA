package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class Wwa extends ApplicationAdapter implements GestureDetector.GestureListener {
    public static final String UP = "up";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String DOWN = "down";
    private static final String BOXES_LAYER = "boxes";
    private SpriteBatch batch;
    private Map<String, List<Texture>> cowboy = new HashMap<>();
    private String[] downPics = {"actor/front.png", "actor/front_1.png", "actor/front_2.png"};
    private String[] leftPics = {"actor/left.png", "actor/left_1.png", "actor/left_2.png"};
    private String[] rightPics = {"actor/right.png", "actor/right_1.png", "actor/right_2.png"};
    private String[] upPics = {"actor/back.png", "actor/back_1.png", "actor/back_2.png"};
    private int indPic = 0;
    private OrthographicCamera camera;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    private boolean isFirstRender = true;
    private int startCounter = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        int ANDROID_WIDTH = Gdx.graphics.getWidth() / 2;
        int ANDROID_HEIGHT = Gdx.graphics.getHeight() / 2;
        camera = new OrthographicCamera(ANDROID_WIDTH, ANDROID_HEIGHT);
        tiledMap = new TmxMapLoader().load("map/desert.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap, ANDROID_WIDTH, ANDROID_HEIGHT);
        camera.update();
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
        float cowboyX = 0f;
        float cowboyY = 0f;
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) || accelTurn == Input.Keys.DPAD_LEFT) {
            cowboyX = -2;
            cowboyToDraw = cowboy.get(LEFT).get(indPic / 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) || accelTurn == Input.Keys.DPAD_RIGHT) {
            cowboyX = 2;
            cowboyToDraw = cowboy.get(RIGHT).get(indPic / 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) || accelTurn == Input.Keys.DPAD_UP) {
            cowboyY = 2;
            cowboyToDraw = cowboy.get(UP).get(indPic / 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN) || accelTurn == Input.Keys.DPAD_DOWN) {
            cowboyY = -2;
            cowboyToDraw = cowboy.get(DOWN).get(indPic / 10);
        }
        indPic += 1;
        if (indPic >= cowboy.get(DOWN).size() * 10 - 1) {
            indPic = 0;
        }
        Gdx.gl.glClearColor(239f / 255.0f, 224f / 255f, 55f / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        if (isFirstRender) {
            cowboyX = 2;
            cowboyY = 2;
            startCounter += 1;
            if (startCounter > 20) {
                isFirstRender = false;
            }
        }
        if (tiledMapRenderer.isStepBack() && !isFirstRender) {
            camera.translate(-cowboyX * 5, -cowboyY * 5);
        } else {
            camera.translate(cowboyX, cowboyY);
        }
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.begin();
        Sprite sprite = new Sprite(cowboyToDraw);
        sprite.setPosition(camera.position.x, camera.position.y);
        sprite.draw(batch);

        batch.end();
        tiledMapRenderer.setSprite(sprite);
        camera.update();

    }

    private int getAccelTurn() {
        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();
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

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.translate(deltaX, 0, 0);
        camera.update();
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    private boolean checkCollision(TiledMap map, Sprite sprite) {
        boolean isCollide = false;
        for (MapLayer layer : map.getLayers()) {
            if (layer.getName().equals(BOXES_LAYER)) {
                for (MapObject object : layer.getObjects()) {
                    if (object instanceof RectangleMapObject) {
                        Rectangle rect = ((RectangleMapObject) object).getRectangle();
                        if (sprite != null && rect.overlaps(sprite.getBoundingRectangle())) {
                            isCollide = true;
                        }
                    }
                    if (object instanceof EllipseMapObject) {
                        Ellipse ellipseObject = ((EllipseMapObject) object).getEllipse();
                        if (sprite != null && ellipseObject.contains(sprite.getX(), sprite.getY())) {
                            isCollide = true;
                        }
                    }
                }
            }

        }
        return isCollide;
    }
}
