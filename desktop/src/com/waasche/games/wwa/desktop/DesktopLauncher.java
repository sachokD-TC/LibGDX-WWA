package com.waasche.games.wwa.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.waasche.games.wwa.main.MainClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Wild west adventure";
		config.width = 1920/2;
        config.height = 1080/2;
		new LwjglApplication(new MainClass(), config);
	}
}
