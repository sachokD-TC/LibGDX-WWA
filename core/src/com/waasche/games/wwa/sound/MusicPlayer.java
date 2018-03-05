package com.waasche.games.wwa.sound;

import com.waasche.games.wwa.resources.Assets;

/**
 * Created by sadm on 2/28/2018.
 */
public class MusicPlayer extends AbstractPlayer {

    @Override
    public void playEnergyLoss() {
        playSound(Assets.soundKickBox, 1f, 0f);
    }

    @Override
    public void playMenuMusic() {
        playMusic(Assets.musicMenu);
    }

    @Override
    public void playMonsterKick() {
        playSound(Assets.soundKickMonster, 1f, 0f);
    }

    @Override
    public void playGameOver() {
        playMusic(Assets.musicGameOver);
    }

    @Override
    public void playGameWinMusic() {
        playMusic(Assets.musicWinGame);
    }

    @Override
    public void playWeaponPick() {
        playSound(Assets.soundFirePick, 1f, 0f);
    }

    @Override
    public void playEnergyFull() {
        playSound(Assets.soundEnergyFull, 1f, 0f);
    }

    @Override
    public void playFireSound() { playSound(Assets.soundFire, 1f, 0f);}

}
