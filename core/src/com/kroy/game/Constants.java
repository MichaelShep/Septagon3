package com.kroy.game;

import com.badlogic.gdx.assets.AssetManager;

public final class Constants {

    private static int RESOLUTION_WIDTH = 1280;
    private static int RESOLUTION_HEIGHT = 720;

    private static int TILE_SIZE = 32;
    private  static AssetManager MANAGER = new AssetManager();




    private Constants()
    {

    }


    public static int getResolutionWidth(){
        return RESOLUTION_WIDTH;
    }
    public static int getResolutionHeight()
    {
        return RESOLUTION_HEIGHT;
    }

    public static AssetManager getManager()
    {
        return MANAGER;
    }

    public static int getTileSize()
    {
        return TILE_SIZE;
    }

}

/*
  private final static int RESOLUTION_WIDTH = 1280;
    private final static int RESOLUTION_HEIGHT = 720;

    private final static int TILE_SIZE = 32;

    private final static int MAP_WIDTH = ((RESOLUTION_WIDTH/4)*3)/TILE_SIZE;
    private final static int MAP_HEIGHT = (RESOLUTION_HEIGHT/TILE_SIZE)-1;


    private final static int MAPSHIFT_X = RESOLUTION_WIDTH/4;
    private final static int MAPSHIFT_Y = RESOLUTION_HEIGHT-(MAP_HEIGHT*TILE_SIZE)-(TILE_SIZE/2);

    private  static AssetManager manager = new AssetManager();

 */