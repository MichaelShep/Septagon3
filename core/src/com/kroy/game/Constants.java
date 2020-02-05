package com.kroy.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.awt.*;


public final class Constants {
    private static int RESOLUTION_WIDTH = 1920;
    private static int RESOLUTION_HEIGHT = 1080;

    private static boolean FULLSCREEN = false;

    private static int TILE_SIZE = 64;
    private static AssetManager MANAGER = new AssetManager();

    private static int FORTRESS_COUNT = 6;
    private static int FIREENGINE_COUNT = 5;
    private static int PATROL_COUNT = 2;

    private static int STATION_REFILL_AMOUNT = 2;
    private static int STATION_REPAIR_AMOUNT = 2;

    private static float MINIGAME_BASE_PLAYER_MOVEMENT_SPEED = 6.0f;
    private static float MINIGAME_BASE_ALIEN_MOVEMENT_SPEED = 3.0f;
    private static int MINIGAME_EDGE_BUFFER = 5;
    private static float MINIGAME_BULLET_SPEED = 5.0f;
    private static int MINIGAME_BULLET_WIDTH = 10;
    private static int MINIGAME_BULLET_HEIGHT = 20;
    private static int NUM_ENGINE_BULLETS = 5;

    private static String[] GRASS_TEXTURES = {"GreeneryTexture/GrassTile1.png", "GreeneryTexture/BushTile1.png", "GreeneryTexture/GrassTile1.png", "GreeneryTexture/TreeTile1.png", "GreeneryTexture/GrassTile1.png", "GreeneryTexture/GrassTile1.png",};
    private static String[] BUILDING_TEXTURE = {"BuildingTexture/BuildingTile1.png", "BuildingTexture/BuildingTile2.png", "BuildingTexture/BuildingTile3.png", "BuildingTexture/BuildingTile4.png", "BuildingTexture/BuildingTile5.png", "BuildingTexture/BuildingTile6.png", "BuildingTexture/BuildingTile7.png", "BuildingTexture/BuildingTile8.png", "BuildingTexture/BuildingTile9.png",};
    private static String[] MINIGAME_TEXTURES = {"MiniGameTexture/ethan.png", "MiniGameTexture/michael.png", "MiniGameTexture/thanh.png"};

    private static String RESOURCE_ROOT = System.getProperty("user.dir");
    private static String MAP_FILE_NAME = "Data/yorkMapFlipped.csv";

    private static int STATION_RANGE = 3;

    private static String[] fortressNames = {"The Minster", "National Railway Museum", "Yorvik Centre", "The Shambles", "Clifford's Tower", "York's Chocolate Story"};

    private static  int[][] fireEngineProfiles = {
            {16,5,5,4,4}, {18,4,4,7,2}, {14,6,6,2,5}, {12,7,6,3,4}, {16,5,4,3,6}
    };

    private static  int[][] fortressProfiles = {
            {2,3,2}, {5,1,3}, {7,2,3}, {4,4,4}, {3,4,2}, {1,5,4}
    };


    private static int [][] patrolProfiles = {
            {5,5,3}, {6,4,2}
    };

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

    public  static  int getPatrolCount() { return PATROL_COUNT; }

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

    public static String[] getMinigameTextures() { return MINIGAME_TEXTURES; }

    public static String getResourceRoot() {
        return RESOURCE_ROOT;
    }

    public static float getMinigameBasePlayerMovementSpeed() { return MINIGAME_BASE_PLAYER_MOVEMENT_SPEED; }

    public static float getMinigameBaseAlienMovementSpeed() { return MINIGAME_BASE_ALIEN_MOVEMENT_SPEED; }

    public static int getMinigameEdgeBuffer() { return MINIGAME_EDGE_BUFFER; }

    public static float getMinigameBulletSpeed() { return MINIGAME_BULLET_SPEED; }

    public static int getMinigameBulletWidth() { return MINIGAME_BULLET_WIDTH; }

    public static int getMinigameBulletHeight() { return MINIGAME_BULLET_HEIGHT; }

    public static int getNumEngineBullets() { return NUM_ENGINE_BULLETS; }

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

    public static int[][] getFireEngineProfiles() {
        return fireEngineProfiles;
    }

    public static int[][] getFortressProfiles() {
        return fortressProfiles;
    }

    public static int[][] getPatrolProfiles(){ return patrolProfiles;}

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