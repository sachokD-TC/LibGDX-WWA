package com.waasche.games.wwa.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class AboutScreen implements Screen {

    private MainClass mainClass;
    private String[] aboutElements = {"Graphics: Anton Husev", "Coding: Sachok Dmitry", "(C) Waasche Games Studio 2018"};
    private Image waascheImage = new Image(new Texture(Gdx.files.internal("pic/Waasche.png")));
    private Stage stage;

    public AboutScreen(MainClass mainClass){
        this.mainClass = mainClass;
        Texture texture = new Texture(Gdx.files.internal("pic/menu.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image actorMenuPic = new com.badlogic.gdx.scenes.scene2d.ui.Image(texture);
        actorMenuPic.setScale((float)MainClass.ANDROID_WIDTH/texture.getWidth(), (float)MainClass.ANDROID_HEIGHT/texture.getHeight());
        actorMenuPic.setPosition(0,0);
        stage = new Stage();
        stage.addActor(actorMenuPic);
    }

    @Override
    public void show() {
        waascheImage.setPosition(MainClass.ANDROID_WIDTH/2, MainClass.ANDROID_HEIGHT/2 - MainClass.ANDROID_HEIGHT/8);
        stage.addActor(waascheImage);
    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act();
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
