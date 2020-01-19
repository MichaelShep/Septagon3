package com.kroy.game;

import com.badlogic.gdx.assets.AssetManager;

import java.awt.*;


public final class Constants {

    //private static int RESOLUTION_WIDTH = 3840;
    //private static int RESOLUTION_HEIGHT = 2160;

    //private static int RESOLUTION_WIDTH = 1920;
    //private static int RESOLUTION_HEIGHT = 1080;


    //private static int RESOLUTION_WIDTH = 1280;
    //private static int RESOLUTION_HEIGHT = 720;

    private static int RESOLUTION_WIDTH = 640;
    private static int RESOLUTION_HEIGHT = 360;

    private static boolean FULLSCREEN = true;
    //private static boolean FULLSCREEN = false;


    private static int TILE_SIZE = 64;
    private static AssetManager MANAGER = new AssetManager();

    private static int FORTRESS_COUNT = 4;
    private static int FIREENGINE_COUNT = 2;


    private static int STATION_REFILL_AMOUNT = 2;
    private static int STATION_REPAIR_AMOUNT = 2;

    private static String[] GRASS_TEXTURES = {"GreeneryTexture/GrassTile1.png", "GreeneryTexture/BushTile1.png", "GreeneryTexture/GrassTile1.png", "GreeneryTexture/TreeTile1.png", "GreeneryTexture/GrassTile1.png", "GreeneryTexture/GrassTile1.png",};
    private static String[] BUILDING_TEXTURE = {"BuildingTexture/BuildingTile1.png", "BuildingTexture/BuildingTile2.png", "BuildingTexture/BuildingTile3.png", "BuildingTexture/BuildingTile4.png", "BuildingTexture/BuildingTile5.png", "BuildingTexture/BuildingTile6.png", "BuildingTexture/BuildingTile7.png", "BuildingTexture/BuildingTile8.png", "BuildingTexture/BuildingTile9.png",};

    private static String RESOURCE_ROOT = System.getProperty("user.dir");
    private static String MAP_FILE_NAME = "Data/yorkMap.csv";

    private static int STATION_RANGE = 3;

    private static String[] fortressNames = {"The Minster", "National Railway Museum", "Yorvik Centre", "The Shambles"};


    // this is another comment


    private Constants() {

    }


    public static int getResolutionWidth() {
        return RESOLUTION_WIDTH;
    }

    public static int getResolutionHeight() {
        return RESOLUTION_HEIGHT;
    }

    public static AssetManager getManager() {
        return MANAGER;
    }

    public static int getTileSize() {
        return TILE_SIZE;
    }

    public static int getFortressCount() {
        return FORTRESS_COUNT;
    }

    public static int getFireengineCount() {
        return FIREENGINE_COUNT;
    }

    public static String getMapFileName() {
        return MAP_FILE_NAME;
    }

    public static int getStationRefillAmount() {
        return STATION_REFILL_AMOUNT;
    }

    public static int getStationRepairAmount() {
        return STATION_REPAIR_AMOUNT;
    }

    public static String[] getGrassTexture() {
        return GRASS_TEXTURES;
    }

    public static String[] getBuildingTexture() {
        return BUILDING_TEXTURE;
    }

    public static String getResourceRoot() {
        return RESOURCE_ROOT;
    }

    public static void setResourceRoot(String newResourceRoot) {
        RESOURCE_ROOT = newResourceRoot;
    }

    public static boolean isFULLSCREEN() {
        return FULLSCREEN;
    }

    public static int getStationRange()
    {
        return STATION_RANGE;
    }

    public static String[] getFortressNames() {
        return fortressNames;
    }

    public static void setResolutionHeight(int resolutionHeight) {
        RESOLUTION_HEIGHT = resolutionHeight;
    }

    public static void setResolutionWidth(int resolutionWidth) {
        RESOLUTION_WIDTH = resolutionWidth;
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