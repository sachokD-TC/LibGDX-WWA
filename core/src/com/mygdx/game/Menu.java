package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class Menu extends ApplicationAdapter implements GestureDetector.GestureListener {

    private Stage menuStage;
    private Sprite menuPic;
    private SpriteBatch menuSpriteBatch;
    private String[] menuElements = {"Start", "About", "Exit"};

    @Override
    public void create(){
        int ANDROID_WIDTH = Gdx.graphics.getWidth();
        int ANDROID_HEIGHT = Gdx.graphics.getHeight();
        menuSpriteBatch = new SpriteBatch();
        menuStage = new com.badlogic.gdx.scenes.scene2d.Stage();
        Camera camera = new OrthographicCamera(ANDROID_WIDTH, ANDROID_HEIGHT);
        menuStage.setViewport(new ScreenViewport(camera));
        menuPic = new Sprite(new Texture(Gdx.files.internal("pic/menu.png")));
        com.badlogic.gdx.scenes.scene2d.ui.Image actorMenuPic = new com.badlogic.gdx.scenes.scene2d.ui.Image(menuPic);
        actorMenuPic.setPosition(-ANDROID_WIDTH / 5, -ANDROID_HEIGHT / 1.8f);
        menuStage.addActor(actorMenuPic);
        final Actor startItem = getTextActor(-ANDROID_WIDTH /3, -ANDROID_HEIGHT/3, menuElements[0]);
        Actor aboutItem = getTextActor(-ANDROID_WIDTH /3, 0, menuElements[1]);
        Actor exitItem = getTextActor(-ANDROID_WIDTH / 3, 0, menuElements[2]);

        startItem.addListener( new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                new Wwa();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                startItem.setColor(Color.RED);
                menuStage.draw();
                return true;
            }

        });
        menuStage.addActor(startItem);
        menuStage.addActor(aboutItem);
        menuStage.addActor(exitItem);
    }

    private Label getTextActor(float xPos, float yPos, String text) {
        Label.LabelStyle textStyle = new Label.LabelStyle();;
        textStyle.font = new BitmapFont();
        Label label = new Label(text,textStyle);
        label.setFontScale(5f, 5f);
        label.setAlignment(Align.center);
        label.setPosition(xPos, yPos);
        label.setColor(Color.BLACK);
        return label;
    }

    @Override
    public void render(){
        Gdx.gl.glClearColor(239f / 255.0f, 224f / 255f, 55f / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuSpriteBatch.begin();
        menuStage.draw();
        menuSpriteBatch.end();
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
}
