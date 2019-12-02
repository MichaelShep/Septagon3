package com.kroy.game;

import com.badlogic.gdx.assets.AssetManager;

public final class Constants {



    private static int RESOLUTION_WIDTH = 1920;
    private static int RESOLUTION_HEIGHT = 1080;

    //private static int RESOLUTION_WIDTH = 1280;
    //private static int RESOLUTION_HEIGHT = 720;

    //private static int RESOLUTION_WIDTH = 640;
    //private static int RESOLUTION_HEIGHT = 360;




    private static int MAP_WIDTH = 32;
    private static int MAP_HEIGHT = 18;

    private static int ROAD_DENSITY = 7;

    //private static int TILE_SIZE = (int)Math.sqrt((RESOLUTION_WIDTH*RESOLUTION_HEIGHT)/(MAP_WIDTH*MAP_HEIGHT));
    private static int TILE_SIZE = 64;
    private  static AssetManager MANAGER = new AssetManager();

    private static int FORTRESS_COUNT = 4;
    private static int FIREENGINE_COUNT = 80;

    private static String MAP_FILE_NAME = "yorkMap.csv";

    private static int STATION_REFILL_AMOUNT = 10;
    private static int STATION_REPAIR_AMOUNT = 10;

    private static String[] GRASS_TEXTURES = {"GreeneryTexture/grassTile.png"};
    private static String[] BUILDING_TEXTURE = {"BuildingTexture/TileBuild.png"};

    private static String RESOURCE_ROOT = System.getProperty("user.dir") + "\\core\\assets\\";



	// this is another comment
	

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

    public static int getMapWidth()
    {
        return  MAP_WIDTH;
    }

    public  static  int getMapHeight()
    {
        return  MAP_HEIGHT;
    }

    public static  int getRoadDensity()
    {
        return ROAD_DENSITY;
    }

    public static int getFortressCount()
    {
        return FORTRESS_COUNT;
    }

    public static int getFireengineCount()
    {
        return FIREENGINE_COUNT;
    }

    public static String getMapFileName()
    {
        return MAP_FILE_NAME;
    }

    public static int getStationRefillAmount()
    {
        return STATION_REFILL_AMOUNT;
    }

    public static int getStationRepairAmount()
    {
        return STATION_REPAIR_AMOUNT;
    }

    public static String[] getGrassTexture(){ return GRASS_TEXTURES; }
    public static String[] getBuildingTexture(){ return BUILDING_TEXTURE; }
    public static String getResourceRoot()
    {
        return RESOURCE_ROOT;
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