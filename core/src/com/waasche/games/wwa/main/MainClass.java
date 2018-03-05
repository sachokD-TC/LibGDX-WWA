package com.waasche.games.wwa.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.waasche.games.wwa.resources.Assets;
import com.waasche.games.wwa.util.GameSettings;


public class MainClass extends ApplicationAdapter {

    public static int ANDROID_HEIGHT;
    static int ANDROID_WIDTH;
    private Screen currentScreen;

    @Override
    public void create(){
        Assets.load();
        GameSettings.load();
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