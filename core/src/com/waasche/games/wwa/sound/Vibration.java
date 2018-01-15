package com.waasche.games.wwa.sound;

import com.badlogic.gdx.Gdx;


public class Vibration extends AbstractPlayer {

    public static final int VIBRATE_MSEC = 500;

    public Vibration(final boolean isPlay){
      soundOn = isPlay;
    }

    @Override
    public void play(String soundFile) {
        if(soundOn){
            Gdx.input.vibrate(VIBRATE_MSEC);
        }
    }

    @Override
    public void playEnergyLoss() {
        play("");
    }
}
