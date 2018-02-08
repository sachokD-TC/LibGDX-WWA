package com.waasche.games.wwa.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.waasche.games.wwa.util.MenuElementsUtil;

/**
 * Created by sadm on 1/25/2018.
 */
public class FinalScreen implements Screen{
    public static final String PIC_FINAL_SCREEN_JPG = "pic/finalScreen.jpg";
    private MainClass mainClass;
    private String[] finalTexts = {"Thanks for Playing", "Next levels", "Coming soon", "(C) Waasche Games Studio 2018"};
    private Stage stage;

    public FinalScreen(MainClass mainClass){
        this.mainClass = mainClass;
        Texture texture = new Texture(Gdx.files.internal(PIC_FINAL_SCREEN_JPG));
        com.badlogic.gdx.scenes.scene2d.ui.Image finalPicture = new com.badlogic.gdx.scenes.scene2d.ui.Image(texture);
        finalPicture.setScale((float) MainClass.ANDROID_WIDTH / texture.getWidth(), (float) MainClass.ANDROID_HEIGHT / texture.getHeight());
        finalPicture.setPosition(0, 0);
        stage = new Stage();
        stage.addActor(finalPicture);
    }

    @Override
    public void show() {
        Label thanksText = MenuElementsUtil.getFinalTextLabel(MainClass.ANDROID_WIDTH / 3, MainClass.ANDROID_HEIGHT / 2 + MainClass.ANDROID_HEIGHT / 3, finalTexts[0]);
        Label nextLevelText = MenuElementsUtil.getFinalTextLabel(MainClass.ANDROID_WIDTH / 3 + MainClass.ANDROID_WIDTH / 18, MainClass.ANDROID_HEIGHT / 2 + MainClass.ANDROID_HEIGHT / 3.5f, finalTexts[1]);
        Label comingSoonText = MenuElementsUtil.getFinalTextLabel(MainClass.ANDROID_WIDTH / 3f + MainClass.ANDROID_WIDTH/10f, MainClass.ANDROID_HEIGHT / 2 + MainClass.ANDROID_HEIGHT / 4.2f, finalTexts[2]);
        Label copyrightText = MenuElementsUtil.getFinalTextLabel(MainClass.ANDROID_WIDTH / 3, MainClass.ANDROID_HEIGHT / 2 - MainClass.ANDROID_HEIGHT / 2.5f, finalTexts[3]);
        copyrightText.setColor(Color.WHITE);
        stage.addActor(thanksText);
        stage.addActor(nextLevelText);
        stage.addActor(comingSoonText);
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
        stage.act();
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
