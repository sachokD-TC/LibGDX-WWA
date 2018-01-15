package com.waasche.games.wwa.main;

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
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.waasche.games.wwa.entities.Level;
import com.waasche.games.wwa.entities.Levels;
import com.waasche.games.wwa.sound.AbstractPlayer;
import com.waasche.games.wwa.sound.Vibration;
import com.waasche.games.wwa.util.GameProgress;
import com.waasche.games.wwa.util.Moves;
import com.waasche.games.wwa.view.EnemiesRenderer;
import com.waasche.games.wwa.view.OrthogonalTiledMapRendererWithSprites;
import com.waasche.games.wwa.view.TouchPadListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wwa implements Screen {
    public static final String UP = "up";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String DOWN = "down";
    public static final String INJURY_LAYER = "injury";
    private static final String BOXES_LAYER = "boxes";
    public static final String GROUND_LAYER = "Ground";
    private SpriteBatch batch;
    com.badlogic.gdx.scenes.scene2d.ui.Image gameOverPic;
    private Map<String, List<Texture>> cowboy = new HashMap<>();
    private String[] downPics = {"actor/front.png", "actor/front_1.png", "actor/front_2.png"};
    private String[] leftPics = {"actor/left.png", "actor/left_1.png", "actor/left_2.png"};
    private String[] rightPics = {"actor/right.png", "actor/right_1.png", "actor/right_2.png"};
    private String[] upPics = {"actor/back.png", "actor/back_1.png", "actor/back_2.png"};
    final Plane xzPlane = new Plane(new Vector3(0, 0, 0), 0);
    final Vector3 intersection = new Vector3();
    private int indPic = 0;
    private OrthographicCamera camera;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    private boolean isFirstRender = true;
    private int startCounter = 0;
    private int energy = 100;
    private Label cactusesText;
    private Label energyText;
    private SpriteBatch scoreBarch;
    private Sprite scorePic;
    private Stage stage;
    private TouchPadListener controleTouchListener;
    private TouchPadListener fireTouchListener;
    private static int ANDROID_WIDTH;
    private static int ANDROID_HEIGHT;
    private static float CONTROL_WIDTH;
    private static float CONTROL_HEIGHT;
    private Level level;
    private int levelInd;
    private MainClass mainClass;
    private EnemiesRenderer enemiesRenderer;
    private boolean bulletStart = false;
    private Texture bullet;
    private Sprite bulletSprite;
    private int bulletIncX;
    private int bulletIncY;
    private int bulletRide = 0;
    private List<TiledMapTileLayer.Cell> cactusesCells = new ArrayList<>();
    private List<TiledMapTileLayer.Cell> weaponCells = new ArrayList<>();
    private int cactuses = 0;
    private int weapons = 0;
    public static final int EMPTY_CELL_NUMBER = 30;
    private TiledMapTileLayer.Cell emptyCell;
    private boolean isNewLevel = false;
    private static final String EXIT_LAYER = "exit";
    private static final String PIC_OBJS_LAYER = "pic_objs";
    private static final String WEAPON_LAYER = "weapons";
    private AbstractPlayer player;
    private Image controleImage;


    public Wwa(int levelInd, boolean soundOn, MainClass mainClass) {
        this.levelInd = levelInd;
        Levels levels = new Levels("map/levels.json");
        level = levels.getLevelsList().getLevels().get(levelInd);
        this.mainClass = mainClass;
        player = new Vibration(soundOn);
    }


    private void prepareControls() {
        controleImage = prepareImage(CONTROL_WIDTH, CONTROL_HEIGHT, "pic/circle.jpg");
        controleImage.setSize(CONTROL_WIDTH, CONTROL_HEIGHT);
        Image fireImage = prepareImage(ANDROID_WIDTH - scorePic.getWidth(), CONTROL_HEIGHT, "pic/fire.png");
        controleTouchListener = new TouchPadListener();
        controleImage.addListener(controleTouchListener);
        fireTouchListener = new TouchPadListener();
        fireImage.addListener(fireTouchListener);
        stage.addActor(controleImage);
        stage.addActor(fireImage);
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
        actorScores.setPosition(ANDROID_WIDTH - scorePic.getWidth(), +scorePic.getHeight() * 0.2f);
        stage = new Stage();
        cactusesText = getTextActor(ANDROID_WIDTH - scorePic.getWidth() * 0.7f, scorePic.getHeight() * 0.6f, "" + cactuses);
        energyText = getTextActor(ANDROID_WIDTH - scorePic.getWidth() / 9, scorePic.getHeight() * 0.6f, "" + energy);
        stage.addActor(actorScores);
        stage.addActor(energyText);
        stage.addActor(cactusesText);
        prepareControls();
    }

    private Label getTextActor(float xPos, float yPos, String text) {
        Label.LabelStyle textStyle = new Label.LabelStyle();
        textStyle.font = new BitmapFont();
        Label label = new Label(text, textStyle);
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
            if (layer.getName().equals(PIC_OBJS_LAYER) || (layer.getName().equals(WEAPON_LAYER) && weapons == 0)) {
                for (MapObject object : layer.getObjects()) {
                    if (object instanceof RectangleMapObject) {
                        Rectangle rect = ((RectangleMapObject) object).getRectangle();
                        if (sprite != null && sprite.getBoundingRectangle().overlaps(rect)) {
                            int cellX = Math.round(rect.x / 32);
                            int cellY = Math.round(rect.y / 32);
                            TiledMapTileLayer groundLayer = (TiledMapTileLayer) map.getLayers().get(GROUND_LAYER);
                            TiledMapTileLayer.Cell overCell = groundLayer.getCell(cellX, cellY);
                            if (layer.getName().equals(PIC_OBJS_LAYER) && !cactusesCells.contains(overCell) && !overCell.equals(emptyCell)) {
                                cactusesCells.add(overCell);
                                cactuses--;
                            }
                            if (layer.getName().equals(WEAPON_LAYER) && !weaponCells.contains(overCell) && !overCell.equals(emptyCell)) {
                                weaponCells.add(overCell);
                                weapons++;
                            }
                            groundLayer.setCell(cellX, cellY, emptyCell);
                        }
                    }
                }
            }
            if (layer.getName().equals(BOXES_LAYER) || layer.getName().equals(INJURY_LAYER)) {
                for (MapObject object : layer.getObjects()) {
                    if (object instanceof RectangleMapObject) {
                        Rectangle rect = ((RectangleMapObject) object).getRectangle();
                        if (sprite != null && sprite.getBoundingRectangle().overlaps(rect)) {
                            isCollide = true;
                            if (layer.getName().equals(INJURY_LAYER)) {
                                energy -= 1;
                                player.playEnergyLoss();
                            }
                        }
                    }
                }
            }
            if (layer.getName().equals(EXIT_LAYER)) {
                for (MapObject object : layer.getObjects()) {
                    if (object instanceof RectangleMapObject) {
                        Rectangle rect = ((RectangleMapObject) object).getRectangle();
                        if (sprite != null && sprite.getBoundingRectangle().overlaps(rect) && cactuses == 0) {
                            isNewLevel = true;
                        }
                    }
                }
            }
        }
        return isCollide;
    }


    public void setCactusesCount(TiledMap map) {
        MapLayer cactusesLayer = map.getLayers().get(Wwa.PIC_OBJS_LAYER);
        if (cactusesLayer != null) {
            cactuses = cactusesLayer.getObjects().getCount();
        }
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        ANDROID_WIDTH = Gdx.graphics.getWidth();
        ANDROID_HEIGHT = Gdx.graphics.getHeight();
        CONTROL_HEIGHT = ANDROID_HEIGHT / 5.4f;
        CONTROL_WIDTH = ANDROID_WIDTH / 5.4f;
        camera = new OrthographicCamera(ANDROID_WIDTH / 2, ANDROID_HEIGHT / 2);
        setTileMap();
        camera.update();
        setupStage();
        pripareTextures(UP, upPics);
        pripareTextures(LEFT, leftPics);
        pripareTextures(RIGHT, rightPics);
        pripareTextures(DOWN, downPics);
        bullet = new Texture(Gdx.files.internal("actor/bullet.png"));
        enemiesRenderer = new EnemiesRenderer(level, batch);
    }

    @Override
    public void render(float delta) {
        Texture cowboyToDraw = cowboy.get(DOWN).get(0);
        float cowboyX = 0f;
        float cowboyY = 0f;
        if (energy <= 0) {
            setGameOverPicture();
        }
        Moves move = Moves.NONE;
        if (controleTouchListener.isTouchDown()) {
            move = getMoveByTouchedCircle();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) || move.equals(move.LEFT)) {
            cowboyX = -2;
            cowboyToDraw = cowboy.get(LEFT).get(indPic / 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) || move.equals(move.RIGHT)) {
            cowboyX = 2;
            cowboyToDraw = cowboy.get(RIGHT).get(indPic / 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) || move.equals(move.UP)) {
            cowboyY = 2;
            cowboyToDraw = cowboy.get(UP).get(indPic / 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN) || move.equals(move.DOWN)) {
            cowboyY = -2;
            cowboyToDraw = cowboy.get(DOWN).get(indPic / 10);
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || fireTouchListener.isTouchDown()) && weapons != 0) {
            bulletStart = true;
            bulletSprite = new Sprite(bullet);
            bulletSprite.setPosition(camera.position.x, camera.position.y);
            weapons--;
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) || move.equals(move.RIGHT)) {
                bulletIncX = 10;
                bulletIncY = 0;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) || move.equals(move.LEFT)) {
                bulletSprite.setRotation(180);
                bulletIncX = -10;
                bulletIncY = 0;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) || move.equals(move.UP)) {
                bulletIncX = 0;
                bulletIncY = 10;
                bulletSprite.setRotation(90);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN) || move.equals(move.DOWN)) {
                bulletIncX = 0;
                bulletIncY = -10;
                bulletSprite.setRotation(-90);
            }
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
                setCactusesCount(tiledMap);
                isFirstRender = false;
            }
        }
        Sprite cowboySprite = new Sprite(cowboyToDraw);
        if (bulletStart) {
            float bulletX = bulletSprite.getX();
            float bulletY = bulletSprite.getY();
            if (bulletRide > 100) {
                bulletStart = false;
                bulletRide = 0;
            } else {
                enemiesRenderer.setBulletX(bulletX + bulletIncX);
                enemiesRenderer.setBulletY(bulletY + bulletIncY);
                bulletSprite.setPosition(bulletX + bulletIncX, bulletY + bulletIncY);
                bulletRide++;
            }
        }
        if (!isCollide(tiledMap, cowboySprite, cowboyX, cowboyY) || isFirstRender) {
            cowboySprite.setPosition(camera.position.x, camera.position.y);
            camera.translate(cowboyX, cowboyY);
            tiledMapRenderer.setView(camera);
        } else {
            camera.translate(-cowboyX, -cowboyY);
            cowboySprite.setPosition(camera.position.x, camera.position.y);
        }

        drawStage();
        batch.begin();
        if (bulletStart) {
            bulletSprite.draw(batch);
        }
        cowboySprite.draw(batch);
        batch.end();
        enemiesRenderer.setCowboySprite(cowboySprite);
        enemiesRenderer.render();
        stage.draw();
        stage.act();
        camera.update();
        if (enemiesRenderer.isCollide()) {
            energy -= 1;
            player.playEnergyLoss();
        }
        if (isNewLevel) {
            GameProgress.setCompleted("" + (levelInd+1));
            mainClass.setCurrentScreen(new Wwa(levelInd + 1, player.isSoundOn(), mainClass));
            mainClass.showCurrentScreen();
            this.dispose();
        }
        if (energy <= 0 && gameOverPic != null && gameOverPic.isVisible()) {
            stage.draw();
            stage.act();
            camera.update();
        }
    }


    private Moves getMoveByTouchedCircle() {
        float x = Gdx.input.getX() - (CONTROL_WIDTH + 25);
        int z = Gdx.graphics.getHeight() - Gdx.input.getY() - (int)(CONTROL_HEIGHT +28);
        if (java.lang.Math.abs(x) > java.lang.Math.abs(z)) {
            if (x < 0) {
                return Moves.LEFT;
            } else {
                return Moves.RIGHT;
            }
        } else {
            if (z < 0) {
                return Moves.DOWN;
            } else {
                return Moves.UP;
            }
        }
    }

    private void setGameOverPicture() {
        Texture texture = new Texture(Gdx.files.internal("pic/game_over.png"));
        gameOverPic = new com.badlogic.gdx.scenes.scene2d.ui.Image(texture);
        gameOverPic.setScale((float) ANDROID_WIDTH / texture.getWidth(), (float) ANDROID_HEIGHT / texture.getHeight());
        gameOverPic.setPosition(0, 0);
        gameOverPic.addListener(new ClickListener() {
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        mainClass.setCurrentScreen(new Menu(mainClass));
                                        mainClass.showCurrentScreen();
                                    }
                                }
        );
        stage.addActor(gameOverPic);
    }

    private void setTileMap() {
        tiledMap = new TmxMapLoader().load("map/" + level.getFileName());
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
        TiledMapTileSet tiledMapTileSet = tiledMap.getTileSets().getTileSet(0);
        this.emptyCell = new TiledMapTileLayer.Cell();
        this.emptyCell.setTile(tiledMapTileSet.getTile(EMPTY_CELL_NUMBER));
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
