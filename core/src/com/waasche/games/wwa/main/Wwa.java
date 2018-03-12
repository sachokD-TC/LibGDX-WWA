package com.waasche.games.wwa.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.waasche.games.wwa.entities.Level;
import com.waasche.games.wwa.sound.AbstractPlayer;
import com.waasche.games.wwa.util.GameSettings;
import com.waasche.games.wwa.util.LevelService;
import com.waasche.games.wwa.util.Moves;
import com.waasche.games.wwa.view.EnemiesRenderer;
import com.waasche.games.wwa.view.OrthogonalTiledMapRendererWithSprites;
import com.waasche.games.wwa.view.TileEnemyRenderer;
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
    public static final String LIVES_LAYER_NAME = "Lives";
    private SpriteBatch batch;
    com.badlogic.gdx.scenes.scene2d.ui.Image gameOverPic;
    private Map<String, List<Texture>> cowboy = new HashMap<>();
    private String[] downPics = {"actor/front.png", "actor/front_1.png", "actor/front_2.png"};
    private String[] leftPics = {"actor/left.png", "actor/left_1.png", "actor/left_2.png"};
    private String[] rightPics = {"actor/right.png", "actor/right_1.png", "actor/right_2.png"};
    private String[] upPics = {"actor/back.png", "actor/back_1.png", "actor/back_2.png"};
    private final Sprite scorePic = new Sprite(new Texture("pic/scores_brown.png"));
    private final Texture bullet = new Texture(Gdx.files.internal("actor/bullet.png"));
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
    private Stage stage;
    private TouchPadListener fireTouchListener;
    private static float CONTROL_WIDTH;
    private static float CONTROL_HEIGHT;
    private Level level;
    private int levelInd;
    private MainClass mainClass;
    private EnemiesRenderer enemiesRenderer;
    private boolean bulletStart = false;
    private Sprite bulletSprite;
    private int bulletIncX;
    private int bulletIncY;
    private int bulletRide = 0;
    private List<TiledMapTileLayer.Cell> cactusesCells = new ArrayList<>();
    private List<TiledMapTileLayer.Cell> weaponCells = new ArrayList<>();
    private List<TiledMapTileLayer.Cell> energyCells = new ArrayList<>();
    private int cactuses = 0;
    private int weapons = 0;
    public static final int EMPTY_CELL_NUMBER = 30;
    private TiledMapTileLayer.Cell emptyCell;
    private boolean isNewLevel = false;
    private static final String EXIT_LAYER = "exit";
    private static final String PIC_OBJS_LAYER = "pic_objs";
    private static final String WEAPON_LAYER = "weapons";
    private AbstractPlayer player;
    private boolean isDrawPainInjury = false;
    private boolean isDrawPainObject = false;
    private Image bulletScore;
    private Image fireImage;
    private Image fireImageOff;
    private Actor painRectangle;
    private LevelService levelService = new LevelService();
    private Touchpad touchpad;

    public Wwa(int levelInd, MainClass mainClass) {
        this.levelInd = levelInd;
        level = levelService.getLevel(levelInd);
        this.mainClass = mainClass;
        this.player = mainClass.getPlayer();
    }


    private void prepareControls() {
        Skin touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("pic/circle.png"));
        Touchpad.TouchpadStyle ts = new Touchpad.TouchpadStyle();
        ts.background = touchpadSkin.getDrawable("touchBackground");
        touchpad = new Touchpad(10, ts);
        touchpad.setBounds(CONTROL_WIDTH - CONTROL_WIDTH / 2f, CONTROL_HEIGHT - CONTROL_HEIGHT / 2f, CONTROL_WIDTH, CONTROL_HEIGHT);
        fireImage = prepareImage(MainClass.ANDROID_WIDTH - scorePic.getWidth(), CONTROL_HEIGHT, "pic/fire.png");
        fireImageOff = prepareImage(MainClass.ANDROID_WIDTH - scorePic.getWidth(), CONTROL_HEIGHT, "pic/fire1.png");
        fireImageOff.setVisible(false);
        fireTouchListener = new TouchPadListener();
        fireImage.addListener(fireTouchListener);
        stage.addActor(fireImage);
        stage.addActor(fireImageOff);
        stage.addActor(touchpad);
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
        com.badlogic.gdx.scenes.scene2d.ui.Image actorScores = new com.badlogic.gdx.scenes.scene2d.ui.Image(scorePic);
        actorScores.setPosition(MainClass.ANDROID_WIDTH - scorePic.getWidth(), +scorePic.getHeight() * 0.2f);
        stage = new Stage();
        cactusesText = getTextActor(MainClass.ANDROID_WIDTH - scorePic.getWidth() * 0.7f, scorePic.getHeight() * 0.6f, "" + cactuses);
        energyText = getTextActor(MainClass.ANDROID_WIDTH - scorePic.getWidth() / 9, scorePic.getHeight() * 0.6f, "" + energy);
        bulletScore = new Image(bullet);
        bulletScore.setPosition(MainClass.ANDROID_WIDTH - scorePic.getWidth() * 0.55f, scorePic.getHeight() * 0.8f);
        bulletScore.setRotation(-90f);
        bulletScore.setScale(2f);
        bulletScore.setVisible(false);
        Pixmap pixmap = new Pixmap(mainClass.ANDROID_WIDTH, MainClass.ANDROID_HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 1, 0.5f);
        pixmap.fillRectangle(0, 0, mainClass.ANDROID_WIDTH, MainClass.ANDROID_HEIGHT);
        painRectangle = new Image(new Texture(pixmap));
        painRectangle.setVisible(false);
        stage.addActor(painRectangle);
        stage.addActor(actorScores);
        stage.addActor(energyText);
        stage.addActor(cactusesText);
        stage.addActor(bulletScore);
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
        scoreBarch.begin();
        tiledMapRenderer.render();
        stage.act();
        painRectangle.setVisible(isDrawPainObject || isDrawPainInjury);
        cactusesText.setText("" + cactuses);
        energyText.setText("" + energy);
        if (weapons != 0) {
            bulletScore.setVisible(true);
        }
        stage.draw();
        scoreBarch.end();
    }

    private boolean isCollide(TiledMap map, Sprite sprite, float cowboyX, float cowboyY) {
        boolean isCollide = false;
        isDrawPainInjury = false;
        camera.translate(cowboyX, cowboyY, 0);
        sprite.setPosition(camera.position.x, camera.position.y);
        for (MapLayer layer : map.getLayers()) {
            if (layer.getName().equals(PIC_OBJS_LAYER) || (layer.getName().equals(WEAPON_LAYER) && weapons == 0) || layer.getName().equals(LIVES_LAYER_NAME)) {
                for (MapObject object : layer.getObjects()) {
                    if (object instanceof RectangleMapObject) {
                        Rectangle rect = ((RectangleMapObject) object).getRectangle();
                        if (sprite != null && sprite.getBoundingRectangle().overlaps(rect)) {
                            int cellX = Math.round(rect.x / 32);
                            int cellY = Math.round(rect.y / 32);
                            TiledMapTileLayer groundLayer = (TiledMapTileLayer) map.getLayers().get(GROUND_LAYER);
                            TiledMapTileLayer.Cell overCell = groundLayer.getCell(cellX, cellY);
                            if (layer.getName().equals(PIC_OBJS_LAYER) && !cactusesCells.contains(overCell) && !overCell.equals(emptyCell)) {
                                player.playWeaponPick();
                                cactusesCells.add(overCell);
                                cactuses--;
                            }
                            if (layer.getName().equals(WEAPON_LAYER) && !weaponCells.contains(overCell) && !overCell.equals(emptyCell)) {
                                player.playWeaponPick();
                                weaponCells.add(overCell);
                                weapons++;
                            }
                            if (layer.getName().equals(LIVES_LAYER_NAME) && !energyCells.contains(overCell) && !overCell.equals(emptyCell)) {
                                player.playEnergyFull();
                                energyCells.add(overCell);
                                energy = 100;
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
                                isDrawPainInjury = true;
                                if (gameOverPic == null) {
                                    player.playEnergyLoss();
                                }
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
                            player.playEnergyFull();
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
            player.playWeaponPick();
        }
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        MainClass.ANDROID_WIDTH = Gdx.graphics.getWidth();
        MainClass.ANDROID_HEIGHT = Gdx.graphics.getHeight();
        CONTROL_HEIGHT = MainClass.ANDROID_HEIGHT / 4f;
        CONTROL_WIDTH = MainClass.ANDROID_WIDTH / 4f;
        camera = new OrthographicCamera(MainClass.ANDROID_WIDTH / 2, MainClass.ANDROID_HEIGHT / 2);
        setTileMap();
        camera.update();
        setupStage();
        pripareTextures(UP, upPics);
        pripareTextures(LEFT, leftPics);
        pripareTextures(RIGHT, rightPics);
        pripareTextures(DOWN, downPics);
        enemiesRenderer = new TileEnemyRenderer(level, batch, tiledMap);
        enemiesRenderer.setPlayer(player);
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
        if (touchpad.isTouched()) {
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
            player.playFireSound();
            bulletStart = true;
            fireImageOff.setVisible(true);
            fireImage.setVisible(false);
            bulletSprite = new Sprite(bullet);
            bulletSprite.setPosition(camera.position.x, camera.position.y);
            weapons--;
            bulletScore.setVisible(false);
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
                fireImageOff.setVisible(false);
                fireImage.setVisible(true);
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
        isDrawPainObject = enemiesRenderer.isCollide();
        if (isDrawPainObject && gameOverPic == null) {
            energy -= enemiesRenderer.getEnergyLoss();
            player.playMonsterKick();
        }
        batch.end();
        enemiesRenderer.setCowboySprite(cowboySprite);
        enemiesRenderer.render();
        stage.draw();
        stage.act();
        camera.update();
        if (isNewLevel) {
            if (!levelService.isMaxLevelIndex(levelInd + 1)) {
                GameSettings.setCompleted("" + (levelInd + 1));
                mainClass.setCurrentScreen(new Wwa(levelInd + 1, mainClass));
            } else {
                player.playGameWinMusic();
                mainClass.setCurrentScreen(new FinalScreen(mainClass));
            }
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
        float x = touchpad.getKnobPercentX();
        float y = touchpad.getKnobPercentY();
        if (java.lang.Math.abs(x) > java.lang.Math.abs(y)) {
            if (x > 0) {
                return Moves.RIGHT;
            } else {
                return Moves.LEFT;
            }
        } else {
            if (y > 0) {
                return Moves.UP;
            } else {
                return Moves.DOWN;
            }
        }
    }

    private void setGameOverPicture() {
        Texture texture = new Texture(Gdx.files.internal("pic/game_over.png"));
        gameOverPic = new com.badlogic.gdx.scenes.scene2d.ui.Image(texture);
        gameOverPic.setScale((float) MainClass.ANDROID_WIDTH / texture.getWidth(), (float) MainClass.ANDROID_HEIGHT / texture.getHeight());
        gameOverPic.setPosition(0, 0);
        player.playGameOver();
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
