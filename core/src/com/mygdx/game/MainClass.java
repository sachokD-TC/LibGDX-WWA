package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;


public class MainClass extends ApplicationAdapter {

    private Screen currentScreen;

    @Override
    public void create(){
        currentScreen = new Menu(this);
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