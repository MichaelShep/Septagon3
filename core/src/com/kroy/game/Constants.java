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

    private static int FORTRESS_COUNT = 6;
    private static int FIREENGINE_COUNT = 5;
    private static int PATROL_COUNT = 6;

    private static int STATION_REFILL_AMOUNT = 2;
    private static int STATION_REPAIR_AMOUNT = 2;

    private static float MINIGAME_BASE_PLAYER_MOVEMENT_SPEED = 6.0f;
    private static float MINIGAME_BASE_ALIEN_MOVEMENT_SPEED = 5.0f;
    private static int MINIGAME_EDGE_BUFFER = 5;
    private static float MINIGAME_BULLET_SPEED = 12.0f;
    private static int MINIGAME_BULLET_WIDTH = 10;
    private static int MINIGAME_BULLET_HEIGHT = 20;
    private static int NUM_ENGINE_BULLETS = 10;

    private static int FORTRESS_DESTRUCTION_TIME = 300;

    private static String MAP_FILE_NAME = "Data/yorkMapFlipped.csv";

    private static int STATION_RANGE = 3;

    private static String[] fortressNames = {"The Minster", "National Railway Museum", "Jorvik Centre", "The Shambles", "Clifford's Tower", "York's Chocolate Story"};

    private static  int[][] fireEngineProfiles = {
            {16,5,4,4,4}, {18,4,4,7,2}, {14,6,4,2,5}, {12,7,4,3,4}, {16,5,4,3,6}
    };

    private static  int[][] fortressProfiles = {
            {2,3,5}, {5,1,6}, {7,2,4}, {4,4,5}, {3,4,5}, {1,5,4}
    };


    private static int [][] patrolProfiles = {
            {7,5,6}, {10,5,7}, {7,8,9}, {7,7,7}, {10,9,6}, {9,8,8}
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

    public static int getFortressDestructionTime() { return FORTRESS_DESTRUCTION_TIME; }

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