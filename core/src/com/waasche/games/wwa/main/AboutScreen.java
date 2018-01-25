package com.waasche.games.wwa.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.waasche.games.wwa.util.MenuElementsUtil;


public class AboutScreen implements Screen {

    private MainClass mainClass;
    private String[] aboutElements = {"Graphics: Anton Husev,", "Div Relik", "Coding: Sachok Dmitry", "(C) Waasche Games Studio 2018"};
    private Image waascheImage = new Image(new Texture(Gdx.files.internal("pic/Waasche.png")));
    private Stage stage;

    public AboutScreen(MainClass mainClass) {
        Gdx.input.setCatchBackKey(true);
        this.mainClass = mainClass;
        Texture texture = new Texture(Gdx.files.internal("pic/menu.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image actorMenuPic = new com.badlogic.gdx.scenes.scene2d.ui.Image(texture);
        actorMenuPic.setScale((float) MainClass.ANDROID_WIDTH / texture.getWidth(), (float) MainClass.ANDROID_HEIGHT / texture.getHeight());
        actorMenuPic.setPosition(0, 0);
        stage = new Stage();
        stage.addActor(actorMenuPic);
    }

    @Override
    public void show() {
        waascheImage.setPosition(MainClass.ANDROID_WIDTH / 2.3f, MainClass.ANDROID_HEIGHT / 2 - MainClass.ANDROID_HEIGHT / 4.7f);
        waascheImage.setScale(2f, 2f);
        Label graphicText = MenuElementsUtil.getAboutTextLabel(MainClass.ANDROID_WIDTH / 5, MainClass.ANDROID_HEIGHT / 2 - MainClass.ANDROID_HEIGHT / 8, aboutElements[0]);
        Label graphicSecText = MenuElementsUtil.getAboutTextLabel(MainClass.ANDROID_WIDTH / 5 + MainClass.ANDROID_WIDTH / 18, MainClass.ANDROID_HEIGHT / 2 - MainClass.ANDROID_HEIGHT / 5, aboutElements[1]);
        Label codingText = MenuElementsUtil.getAboutTextLabel(MainClass.ANDROID_WIDTH / 5f, MainClass.ANDROID_HEIGHT / 2 - MainClass.ANDROID_HEIGHT / 3.7f, aboutElements[2]);
        Label copyrightText = MenuElementsUtil.getAboutTextLabel(MainClass.ANDROID_WIDTH / 3, MainClass.ANDROID_HEIGHT / 2 - MainClass.ANDROID_HEIGHT / 2.5f, aboutElements[3]);
        stage.addActor(waascheImage);
        stage.addActor(graphicText);
        stage.addActor(graphicSecText);
        stage.addActor(codingText);
        stage.addActor(copyrightText);
        Gdx.input.setInputProcessor(stage);
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mainClass.setCurrentScreen(new Menu(mainClass));
                mainClass.showCurrentScreen();
                return;
            }
        });
    }

    @Override
    public void render(float delta) {
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
