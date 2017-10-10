package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.com.mygdx.Levels;
import com.mygdx.game.com.mygdx.TouchPadListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wwa implements Screen {
    public static final String UP = "up";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String DOWN = "down";
    private SpriteBatch batch;
    private static final String BOXES_LAYER = "boxes";
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
    private int cactuses = 0;
    private int energy = 400;
    private Label cactusesText;
    private Label energyText;
    private SpriteBatch scoreBarch;
    private Sprite scorePic;
    private Stage stage;
    private TouchPadListener leftTouchListener;
    private TouchPadListener rightTouchListener;
    private TouchPadListener upTouchListener;
    private TouchPadListener downTouchListener;
    private static int ANDROID_WIDTH;
    private static int ANDROID_HEIGHT;
    private String level;
    private int levelInd;

    public Wwa(int levelInd){
        this.levelInd = levelInd;
        setLevel(levelInd);
    }

    private void setLevel(int levelInd){
        this.level = Levels.levels[levelInd];
    }

    private void prepareControls(){
        Image leftImage = prepareImage(100, 100, "pic/arrow_left.png");
        Image rightImage = prepareImage(280, 100, "pic/arrow_right.png");
        Image upImage = prepareImage(190, 190, "pic/arrow_up.png");
        Image downImage = prepareImage(190, 10, "pic/arrow_down.png");
        leftTouchListener = new TouchPadListener();
        leftImage.addListener(leftTouchListener);
        rightTouchListener = new TouchPadListener();
        rightImage.addListener(rightTouchListener);
        upTouchListener = new TouchPadListener();
        upImage.addListener(upTouchListener);
        downTouchListener = new TouchPadListener();
        downImage.addListener(downTouchListener);
        stage.addActor(leftImage);
        stage.addActor(rightImage);
        stage.addActor(upImage);
        stage.addActor(downImage);
        Gdx.input.setInputProcessor(stage);
    }

    private Image prepareImage(float x, float y, String imageName) {
        Image image = new Image(new Sprite(new Texture(Gdx.files.internal(imageName))));
        image.setPosition(x, y);
        image.setBounds(x, y, image.getWidth(), image.getHeight());
        return image;
    }

    private void setupStage() {
        scoreBarch = new SpriteBatch();
        scorePic = new Sprite(new Texture("pic/scores_brown.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image actorScores = new com.badlogic.gdx.scenes.scene2d.ui.Image(scorePic);
        actorScores.setPosition(ANDROID_WIDTH - scorePic.getWidth(), + scorePic.getHeight()*0.2f);
        stage = new Stage();
        cactusesText = getTextActor(ANDROID_WIDTH - scorePic.getWidth()*0.7f, scorePic.getHeight() * 0.6f, "" + cactuses);
        energyText = getTextActor(ANDROID_WIDTH - scorePic.getWidth()/9, scorePic.getHeight() * 0.6f, "" + energy);
        stage.addActor(actorScores);
        stage.addActor(energyText);
        stage.addActor(cactusesText);
        prepareControls();
    }

    private Label getTextActor(float xPos, float yPos, String text) {
        Label.LabelStyle textStyle = new Label.LabelStyle();;
        textStyle.font = new BitmapFont();
        Label label = new Label(text,textStyle);
        label.setFontScale(2f, 3f);
        label.setAlignment(Align.center);
        label.setPosition(xPos, yPos);
        return label;
    }

    private void pripareTextures(String moveName, String[] pics) {
        List<Texture> textures = new ArrayList<>();
        for (String pic : pics) {
            textures.add(new Texture(pic));
        }
        cowboy.put(moveName, textures);
    }

    private void drawStage() {
        tiledMapRenderer.render();
        stage.act();
        scoreBarch.begin();
        cactusesText.setText("" + cactuses);
        energyText.setText("" + energy);
        stage.draw();
        scoreBarch.end();
    }


    private boolean isCollide(TiledMap map, Sprite sprite, float cowboyX, float cowboyY) {
        boolean isCollide = false;
        camera.translate(cowboyX, cowboyY, 0);
        sprite.setPosition(camera.position.x, camera.position.y);
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

    @Override
    public void show() {
        batch = new SpriteBatch();
        ANDROID_WIDTH = Gdx.graphics.getWidth();
        ANDROID_HEIGHT = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(ANDROID_WIDTH/2, ANDROID_HEIGHT/2);
        setTileMap();
        camera.update();
        setupStage();
        pripareTextures(UP, upPics);
        pripareTextures(LEFT, leftPics);
        pripareTextures(RIGHT, rightPics);
        pripareTextures(DOWN, downPics);
    }

    @Override
    public void render(float delta) {
        Texture cowboyToDraw = cowboy.get(DOWN).get(0);
        float cowboyX = 0f;
        float cowboyY = 0f;
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) || leftTouchListener.isTouchDown()) {
            cowboyX = -2;
            cowboyToDraw = cowboy.get(LEFT).get(indPic / 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) || rightTouchListener.isTouchDown()) {
            cowboyX = 2;
            cowboyToDraw = cowboy.get(RIGHT).get(indPic / 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) || upTouchListener.isTouchDown()) {
            cowboyY = 2;
            cowboyToDraw = cowboy.get(UP).get(indPic / 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN) || downTouchListener.isTouchDown()) {
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
        Sprite sprite = new Sprite(cowboyToDraw);
        if (!isCollide(tiledMap, sprite, cowboyX, cowboyY) || isFirstRender) {
            sprite.setPosition(camera.position.x, camera.position.y);
            camera.translate(cowboyX, cowboyY);
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.setSprite(sprite);
            cactuses = tiledMapRenderer.getCactuses();

        } else {
            camera.translate(-cowboyX, -cowboyY);
            sprite.setPosition(camera.position.x, camera.position.y);
            energy-=1;
        }
        drawStage();
        batch.begin();
        sprite.draw(batch);
        batch.end();
        camera.update();
        if(tiledMapRenderer.isNewLevel()){
            new Wwa(levelInd++);
            this.dispose();
        }
    }

    private void setTileMap(){
        tiledMap = new TmxMapLoader().load("map/" + level + ".tmx");
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
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
