package com.kroy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

/**
 * File used to store all the constants in the game
 */

public final class Constants {
    //Full size values
    private static int RESOLUTION_WIDTH = 1920;
    private static int RESOLUTION_HEIGHT = 1080;
    private static int ACTUAL_SCREEN_WIDTH = 2048;
    private static int ACTUAL_SCREEN_HEIGHT = 1196;

    //Half size values
    /*
    private static int RESOLUTION_WIDTH = 1920/2;
    private static int RESOLUTION_HEIGHT = 1080/2;
    private static int ACTUAL_SCREEN_WIDTH = 2048/2;
    private static int ACTUAL_SCREEN_HEIGHT = 1196/2;*/


    private static boolean FULLSCREEN = false;

    private static int TILE_SIZE = 64;
    private static AssetManager MANAGER = new AssetManager();

    private static int FORTRESS_COUNT = 6;
    private static int FIREENGINE_COUNT = 5;
    private static int PATROL_COUNT = 2;

    private static int STATION_REFILL_AMOUNT = 2;
    private static int STATION_REPAIR_AMOUNT = 2;

    private static float MINIGAME_BASE_PLAYER_MOVEMENT_SPEED = 6.0f;
    private static float MINIGAME_BASE_ALIEN_MOVEMENT_SPEED = 5.0f;
    private static int MINIGAME_EDGE_BUFFER = 5;
    private static float MINIGAME_BULLET_SPEED = 12.0f;
    private static int MINIGAME_BULLET_WIDTH = 10;
    private static int MINIGAME_BULLET_HEIGHT = 20;
    private static int NUM_ENGINE_BULLETS = 10;

    private static int FORTRESS_DESTRUCTION_TIME = 480;

    private static String[] GRASS_TEXTURES = {"GreeneryTexture/GrassTile1.png", "GreeneryTexture/BushTile1.png", "GreeneryTexture/GrassTile1.png", "GreeneryTexture/TreeTile1.png", "GreeneryTexture/GrassTile1.png", "GreeneryTexture/GrassTile1.png",};
    private static String[] BUILDING_TEXTURE = {"BuildingTexture/BuildingTile1.png", "BuildingTexture/BuildingTile2.png", "BuildingTexture/BuildingTile3.png", "BuildingTexture/BuildingTile4.png", "BuildingTexture/BuildingTile5.png", "BuildingTexture/BuildingTile6.png", "BuildingTexture/BuildingTile7.png", "BuildingTexture/BuildingTile8.png", "BuildingTexture/BuildingTile9.png",};
    private static String[] MINIGAME_TEXTURES = {"MiniGameTexture/ethan.png", "MiniGameTexture/michael.png", "MiniGameTexture/thanh.png"};

    private static String MAP_FILE_NAME = "Data/yorkMapFlipped.csv";

    private static int STATION_RANGE = 3;

    private static String[] fortressNames = {"The Minster", "National Railway Museum", "Yorvik Centre", "The Shambles", "Clifford's Tower", "York's Chocolate Story"};

    private static  int[][] fireEngineProfiles = {
            {16,5,5,8,4}, {18,4,4,14,2}, {14,6,6,5,5}, {12,7,6,6,4}, {16,5,4,6,6}
    };

    private static  int[][] fortressProfiles = {
            {2,3,2}, {5,1,3}, {7,2,3}, {4,4,4}, {3,4,2}, {1,5,4}
    };


    private static int [][] patrolProfiles = {
            {7,5,6}, {9,4,4}
    };

    private Constants() {
    }

    public static int getResolutionWidth() {
        return RESOLUTION_WIDTH;
    }

    public static int getResolutionHeight() {
        return RESOLUTION_HEIGHT;
    }

    public static int getActualScreenWidth() { return ACTUAL_SCREEN_WIDTH; }

    public static int getActualScreenHeight() { return ACTUAL_SCREEN_HEIGHT; }

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

    public static int getFortressDestructionTime() { return FORTRESS_DESTRUCTION_TIME; }

    public static String getResourceRoot() {
        String WORKING_DIRECTORY = Gdx.files.getLocalStoragePath();
        String resources_root = Gdx.files.absolute(WORKING_DIRECTORY).toString() + "/core/assets/";
        return resources_root;
    }

    public static float getMinigameBasePlayerMovementSpeed() { return MINIGAME_BASE_PLAYER_MOVEMENT_SPEED; }

    public static float getMinigameBaseAlienMovementSpeed() { return MINIGAME_BASE_ALIEN_MOVEMENT_SPEED; }

    public static int getMinigameEdgeBuffer() { return MINIGAME_EDGE_BUFFER; }

    public static float getMinigameBulletSpeed() { return MINIGAME_BULLET_SPEED; }

    public static int getMinigameBulletWidth() { return MINIGAME_BULLET_WIDTH; }

    public static int getMinigameBulletHeight() { return MINIGAME_BULLET_HEIGHT; }

    public static int getNumEngineBullets() { return NUM_ENGINE_BULLETS; }

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

    public static int[][] getFireEngineProfiles() {
        return fireEngineProfiles;
    }

    public static int[][] getFortressProfiles() {
        return fortressProfiles;
    }

    public static int[][] getPatrolProfiles(){ return patrolProfiles;}

}