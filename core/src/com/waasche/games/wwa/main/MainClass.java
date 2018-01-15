package com.waasche.games.wwa.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.waasche.games.wwa.util.GameProgress;


public class MainClass extends ApplicationAdapter {

    private Screen currentScreen;

    @Override
    public void create(){
        currentScreen = new Menu(this);
        GameProgress.load();
        showCurrentScreen();
    }

    @Override
    public void render(){
        currentScreen.render(2f);
    }

    public void showCurrentScreen(){
        currentScreen.show();
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }
}