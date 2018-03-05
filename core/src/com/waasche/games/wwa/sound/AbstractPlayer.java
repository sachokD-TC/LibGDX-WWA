package com.waasche.games.wwa.sound;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;
import com.waasche.games.wwa.util.GameSettings;

public abstract class AbstractPlayer {

    public static final int VIBRATE_MSEC = 500;
    protected boolean soundOn = true;
    protected Music music;
    protected Sound sound;
    protected Timer timer = new Timer();

    public void playSound(Sound sound, float pitch, float delay) {
        this.sound = sound;
        if(GameSettings.isSoundOn()){
            sound.play(1.0f, pitch, 0.0f);
        } else {
            Gdx.input.vibrate(VIBRATE_MSEC);
        }
    }

    public void playMusic(Music music){
        this.music = music;
        if(GameSettings.isSoundOn()){
            music.play();
            music.setLooping(true);
        }
    }

    public void stopMusic(){
        if(music != null){
            music.dispose();
            music.stop();
        }
    }

    public void stopSound(){
        sound.stop();
    }

    public void pause(){
        if(music != null){
            music.pause();
        }
    }

    void soundOn(){
        soundOn = true;
    }

    void soundOff(){
        soundOn = false;
    }

    public abstract void playEnergyLoss();

    public abstract void playMenuMusic();

    public abstract void playMonsterKick();

    public abstract void playGameOver();

    public abstract void playGameWinMusic();

    public abstract void playWeaponPick();

    public abstract void playEnergyFull();

    public abstract void playFireSound();

}
