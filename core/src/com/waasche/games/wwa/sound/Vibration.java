package com.waasche.games.wwa.sound;

public class Vibration extends AbstractPlayer {

    public Vibration(final boolean isPlay){
      soundOn = isPlay;
    }


    @Override
    public void playEnergyLoss() {
        playSound(null, 0, 0);
    }

    @Override
    public void playMenuMusic() {

    }

    @Override
    public void playMonsterKick() {
        playEnergyLoss();
    }

    @Override
    public void playGameOver() {

    }

    @Override
    public void playGameWinMusic() {

    }

    @Override
    public void playWeaponPick() {

    }

    @Override
    public void playEnergyFull() {

    }

    @Override
    public void playFireSound() {

    }
}
