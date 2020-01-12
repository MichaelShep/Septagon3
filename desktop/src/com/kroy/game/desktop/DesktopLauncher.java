package com.kroy.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kroy.game.Constants;
import com.kroy.game.MainClass;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = Constants.getResolutionWidth();
        config.height = Constants.getResolutionHeight();


        config.fullscreen = true;
        config.vSyncEnabled = true;
        config.title = "WARKROY";

        //this is a temporary fix. Don't look at it
        if (Constants.getResourceRoot().contains("Release")) {
            Constants.setResourceRoot(Constants.getResourceRoot() + "/assets/");
        }
        else
        {
            //Constants.setResourceRoot(Constants.getResourceRoot() + "/core/assets/");
        }

        new LwjglApplication(new MainClass(), config);


    }
}
