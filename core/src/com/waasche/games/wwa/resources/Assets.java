package com.waasche.games.wwa.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by sadm on 2/28/2018.
 */
public class Assets {
    public static Sound soundKickBox;
    public static Sound soundKickMonster;
    public static Sound soundFire;
    public static Sound soundFirePick;
    public static Sound soundEnergyFull;
    public static Sound soundKillMonster;
    public static Music musicMenu;
    public static Music musicGameOver;
    public static Music musicWinGame;


    public static void load(){
        soundKickBox = Gdx.audio.newSound(Gdx.files.internal("sounds/kickbox.mp3"));
        soundKickMonster = Gdx.audio.newSound(Gdx.files.internal("sounds/kickmonster.mp3"));
        soundFire = Gdx.audio.newSound(Gdx.files.internal("sounds/fire.mp3"));
        soundFirePick = Gdx.audio.newSound(Gdx.files.internal("sounds/pickfire.mp3"));
        soundEnergyFull = Gdx.audio.newSound(Gdx.files.internal("sounds/energyfull.mp3"));
        soundKillMonster = Gdx.audio.newSound(Gdx.files.internal("sounds/dead_monster.mp3"));
        musicGameOver = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameover.mp3"));
        musicWinGame = Gdx.audio.newMusic(Gdx.files.internal("sounds/finalmusic.mp3"));
        musicMenu = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu.mp3"));
    }

}


