package com.waasche.games.wwa.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameProgress {

    public static final String COMPLETED = "completed";
    private static Preferences progress;

    public static void load() {
        progress = Gdx.app.getPreferences("GameProgress");
    }

    public static void setCompleted(String levelId) {
        progress.putString(COMPLETED, levelId);
        progress.flush();
    }

    public static String getLastCompleted(){
        if(progress.getString(COMPLETED).equals("")){
            return "0";
        }
        return progress.getString(COMPLETED);
    }

    public static void clear(){
        progress.clear();
    }

}
