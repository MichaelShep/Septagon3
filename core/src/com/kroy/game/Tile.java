package com.kroy.game;

/**
 * Used to handle info about each of the tiles in the game
 */

public class Tile {
    private int mapX;
    private int mapY;
    private String texName;
    private TileType type;
    private Character inhabitant;


    /**
     * Constructs a default Tile Object at 0,0
     */
    public Tile() {
        //default constructor
        mapX = 0;
        mapY = 0;
        texName = "grassTile.png";
        type = TileType.TILE_TYPES_GRASS;
        inhabitant = null;

    }

    /**
     * Constructs a Tile Object at newMapx, newMapY
     *
     * @param newMapX map position X
     * @param newMapY map position Y
     */
    public Tile(int newMapX, int newMapY) {

        mapX = newMapX;
        mapY = newMapY;
        texName = "grassTile.png";
        type = TileType.TILE_TYPES_GRASS;
        inhabitant = null;

    }

    /**
     * Constructs a Tile Object at newMapX, newMapY, with newType type
     *
     * @param newMapX            map position X
     * @param newMapY            map position Y
     * @param newTextureLocation The sprite location
     * @param newType            The enum type of the tile
     */
    public Tile(int newMapX, int newMapY, String newTextureLocation, TileType newType) {

        mapX = newMapX;
        mapY = newMapY;
        texName = newTextureLocation;
        type = newType;
        inhabitant = null;

    }


    //getters
    public int getMapX() {
        return mapX;
    }

    //setters
    public void setMapX(int newMapX) {
        this.mapX = mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int newMapY) {
        this.mapY = mapY;
    }

    public String getTexName() {
        return texName;
    }

    public void setTexName(String newTexName) {
        texName = newTexName;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType newType) {
        type = newType;
    }

    public Character getInhabitant() {
        return inhabitant;
    }

    public void setInhabitant(Character newInhabitant) {
        inhabitant = newInhabitant;
    }


}