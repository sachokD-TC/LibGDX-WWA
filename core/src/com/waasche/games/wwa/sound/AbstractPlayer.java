package com.waasche.games.wwa.sound;


public abstract class AbstractPlayer {

    protected boolean soundOn = true;

    public abstract void play(String soundFile);

    void soundOn(){
        soundOn = true;
    }

    void soundOff(){
        soundOn = false;
    }

    public boolean isSoundOn(){
        return soundOn;
    }

   public abstract void playEnergyLoss();

}
