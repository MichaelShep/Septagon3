package com.kroy.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kroy.game.Constants;
import com.kroy.game.MainClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = Constants.getResolutionWidth();
		config.height = Constants.getResolutionHeight();


		config.fullscreen = true;
		config.vSyncEnabled = true;
		config.title = "WARKROY";


		new LwjglApplication(new MainClass(), config);




	}
}
