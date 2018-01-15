package com.waasche.games.wwa.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.waasche.games.wwa.util.GameProgress;


public class Menu implements Screen {

    private int START_LEVEL = 0;
    private Stage menuStage;
    private SpriteBatch menuSpriteBatch;
    private Camera camera;
    private MainClass mainClass;
    private String[] menuElements = {"Start", "About", "Exit", "Rating - "};
    private int ANDROID_WIDTH;
    private int ANDROID_HEIGHT;

    public Menu(MainClass mainClass){
        this.mainClass = mainClass;
    }

    private Label getTextActor(float xPos, float yPos, String text) {
        Label.LabelStyle textStyle = new Label.LabelStyle();;
        textStyle.font = new BitmapFont();
        Label label = new Label(text,textStyle);
        label.setFontScale(5f, 5f);
        label.setAlignment(Align.center);
        label.setPosition(xPos, yPos);
        label.setBounds(xPos, yPos, text.length()*100f, 200f);
        label.setColor(Color.BLACK);
        label.setBounds(xPos,yPos, 100,100);
        return label;
    }

    @Override
    public void show() {
        ANDROID_WIDTH = Gdx.graphics.getWidth();
        ANDROID_HEIGHT = Gdx.graphics.getHeight();
        menuSpriteBatch = new SpriteBatch();
        menuStage = new com.badlogic.gdx.scenes.scene2d.Stage();
        camera = new OrthographicCamera(ANDROID_WIDTH, ANDROID_HEIGHT);
        prepareStage();

    }

    private void prepareStage(){
        Texture texture = new Texture(Gdx.files.internal("pic/menu.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image actorMenuPic = new com.badlogic.gdx.scenes.scene2d.ui.Image(texture);
        actorMenuPic.setScale((float)ANDROID_WIDTH/texture.getWidth(), (float)ANDROID_HEIGHT/texture.getHeight());
        actorMenuPic.setPosition(0,0);
        menuStage.addActor(actorMenuPic);
        START_LEVEL = Integer.valueOf(GameProgress.getLastCopleted()).intValue();
        final Label startItem = getTextActor(ANDROID_WIDTH /2.5f, ANDROID_HEIGHT/2.2f + ANDROID_HEIGHT/12.5f, menuElements[0]);
        Actor aboutItem = getTextActor(ANDROID_WIDTH /2.5f, ANDROID_HEIGHT/2.2f - ANDROID_HEIGHT/12.5f, menuElements[1]);
        Actor ratingItem = getTextActor(ANDROID_WIDTH / 2.5f, ANDROID_HEIGHT/2.2f - ANDROID_HEIGHT/12.5f*3, menuElements[3] + START_LEVEL);
        Actor exitItem = getTextActor(ANDROID_WIDTH / 2.5f, ANDROID_HEIGHT/2.2f - ANDROID_HEIGHT/12.5f*5, menuElements[2]);
        addListeners(startItem, aboutItem, exitItem);
        menuStage.addActor(startItem);
        Gdx.input.setInputProcessor(menuStage);
        menuStage.addActor(startItem);
        menuStage.addActor(aboutItem);
        menuStage.addActor(ratingItem);
        menuStage.addActor(exitItem);
    }

    private void addListeners(final Actor startItemText, final Actor aboutItem, final Actor exitItem){
        startItemText.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                startItemText.setColor(Color.RED);
                mainClass.render();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                mainClass.setCurrentScreen(new Wwa(START_LEVEL, true, mainClass));
                mainClass.showCurrentScreen();
            }
        });
        exitItem.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                exitItem.setColor(Color.RED);
                dispose();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                exitItem.setColor(Color.BLACK);
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
