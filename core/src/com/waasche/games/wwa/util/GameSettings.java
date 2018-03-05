package com.waasche.games.wwa.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameSettings {

    public static final String COMPLETED = "completed";
    public static final String SOUND = "Sound";
    private static Preferences progress;
    private static Preferences soundSettings;

    public static void load() {
        progress = Gdx.app.getPreferences("GameSettings");
        soundSettings = Gdx.app.getPreferences("Sound");
        setMusicOn();
    }

    public static void setCompleted(String levelId) {
        progress.putString(COMPLETED, levelId);
        progress.flush();
    }

    public static String getLastCompleted() {
        if (progress.getString(COMPLETED).equals("")) {
            return "0";
        }
        return progress.getString(COMPLETED);
    }


    public static void setMusicOn() {
        soundSettings.putBoolean(SOUND, true);
        soundSettings.flush();
    }

    public static void setMusicOff() {
        soundSettings.putBoolean(SOUND, false);
    }

    public static boolean isSoundOn() {
        return soundSettings.getBoolean(SOUND);
    }

    public static void clear() {
        progress.clear();
    }

}
