package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
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


public class Menu extends ApplicationAdapter {

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
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(menuStage);
        final Label startItemText = getTextActor(-ANDROID_WIDTH /3, -ANDROID_HEIGHT/3, menuElements[0]);
        InputProcessor menuInputProcessor = new MenuInputProcessor(Color.RED, startItemText);
        inputMultiplexer.addProcessor(menuInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
        Actor aboutItem = getTextActor(-ANDROID_WIDTH /3, 0, menuElements[1]);
        Actor exitItem = getTextActor(-ANDROID_WIDTH / 3, 0, menuElements[2]);
        menuStage.addActor(startItemText);
        menuStage.addActor(aboutItem);
        menuStage.addActor(exitItem);
        startItemText.setWidth(100);
        startItemText.setHeight(100);
        startItemText.setBounds(0,0, 100,100);

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

}
