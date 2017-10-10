package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MainClass;
import com.mygdx.game.Menu;
import com.mygdx.game.Wwa;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Wild west adventure";
        config.height = 1080/2;
        config.width = 1920/2;
		new LwjglApplication(new MainClass(), config);
	}
}
