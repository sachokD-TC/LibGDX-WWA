package com.waasche.games.wwa.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.waasche.games.wwa.sound.AbstractPlayer;
import com.waasche.games.wwa.util.GameSettings;
import com.waasche.games.wwa.util.LevelService;
import com.waasche.games.wwa.util.MenuElementsUtil;


public class Menu implements Screen {

    private int START_LEVEL = 0;
    private Stage menuStage;
    private SpriteBatch menuSpriteBatch;
    private Camera camera;
    private MainClass mainClass;
    private String[] menuElements = {"Start", "About", "Clear Rating", "Rating - "};
    private Label ratingLabel;
    private AbstractPlayer player;

    public Menu(MainClass mainClass) {
        this.mainClass = mainClass;
        player = mainClass.getPlayer();
        player.playMenuMusic();
    }


    @Override
    public void show() {
        MainClass.ANDROID_WIDTH = Gdx.graphics.getWidth();
        MainClass.ANDROID_HEIGHT = Gdx.graphics.getHeight();
        menuSpriteBatch = new SpriteBatch();
        menuStage = new com.badlogic.gdx.scenes.scene2d.Stage();
        camera = new OrthographicCamera(MainClass.ANDROID_WIDTH, MainClass.ANDROID_HEIGHT);
        prepareStage();
    }

    private void prepareStage() {
        Texture texture = new Texture(Gdx.files.internal("pic/menu.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image actorMenuPic = new com.badlogic.gdx.scenes.scene2d.ui.Image(texture);
        actorMenuPic.setScale((float) MainClass.ANDROID_WIDTH / texture.getWidth(), (float) MainClass.ANDROID_HEIGHT / texture.getHeight());
        actorMenuPic.setPosition(0, 0);
        menuStage.addActor(actorMenuPic);
        START_LEVEL = Integer.valueOf(GameSettings.getLastCompleted());
        final Label startItem = MenuElementsUtil.getTextActor(MainClass.ANDROID_WIDTH / 2.5f, MainClass.ANDROID_HEIGHT / 2.2f + MainClass.ANDROID_HEIGHT / 15.5f, menuElements[0]);
        Actor aboutItem = MenuElementsUtil.getTextActor(MainClass.ANDROID_WIDTH / 2.5f, MainClass.ANDROID_HEIGHT / 2.2f - MainClass.ANDROID_HEIGHT / 15.5f, menuElements[1]);
        ratingLabel = MenuElementsUtil.getTextActor(MainClass.ANDROID_WIDTH / 2.5f, MainClass.ANDROID_HEIGHT / 2.2f - MainClass.ANDROID_HEIGHT / 15.5f * 3, menuElements[3] + START_LEVEL);
        Actor ratingItem = ratingLabel;
        Actor clearRating = MenuElementsUtil.getTextActor(MainClass.ANDROID_WIDTH / 2.5f, MainClass.ANDROID_HEIGHT / 2.2f - MainClass.ANDROID_HEIGHT / 15.5f * 5, menuElements[2]);
        addListeners(startItem, aboutItem, clearRating, ratingItem);
        menuStage.addActor(startItem);
        Gdx.input.setInputProcessor(menuStage);
        menuStage.addActor(startItem);
        menuStage.addActor(aboutItem);
        menuStage.addActor(ratingItem);
        menuStage.addActor(clearRating);
    }

    private void addListeners(final Actor startItemText, final Actor aboutItem, final Actor clearRating, final Actor rateItem) {
        startItemText.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                startItemText.setColor(Color.RED);
                mainClass.render();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!new LevelService().isMaxLevelIndex(START_LEVEL)) {
                    if (GameSettings.isSoundOn()) {
                        player.stopMusic();
                    }
                    mainClass.setCurrentScreen(new Wwa(START_LEVEL, mainClass));
                } else {
                    mainClass.setCurrentScreen(new FinalScreen(mainClass));
                }
                mainClass.showCurrentScreen();
            }
        });
        clearRating.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (GameSettings.isSoundOn()) {
                    player.pause();
                    player.playEnergyFull();
                }
                clearRating.setColor(Color.RED);
                GameSettings.clear();
                START_LEVEL = 0;
                ratingLabel.setText(menuElements[3] + START_LEVEL);
                ratingLabel.invalidate();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                clearRating.setColor(Color.BLACK);
            }
        });

        aboutItem.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainClass.setCurrentScreen(new AboutScreen(mainClass));
                mainClass.showCurrentScreen();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                clearRating.setColor(Color.BLACK);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(239f / 255.0f, 224f / 255f, 55f / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuSpriteBatch.begin();
        menuStage.draw();
        menuSpriteBatch.end();
        camera.update();
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
        menuStage.dispose();
        Gdx.app.exit();
    }
}
