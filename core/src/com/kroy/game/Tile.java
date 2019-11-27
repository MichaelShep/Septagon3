package com.kroy.game;

public class Tile
{
    private int mapX;
    private int mapY;
    private String texName;
    private TileType type;
    private Character inhabitant;


    
    // why are none of the constructors public?
    public Tile()
    {
        //default constructor
        mapX = 0;
        mapY = 0;
        texName = "grassTile.png";
        type = TileType.TILE_TYPES_GRASS;
        inhabitant = null;

    }

    public Tile(int newMapX, int newMapY)
    {

        mapX = newMapX;
        mapY = newMapY;
        texName = "grassTile.png";
        type = TileType.TILE_TYPES_GRASS;
        inhabitant = null;

    }

    public Tile(int newMapX, int newMapY, String newTextureLocation, TileType newType)
    {

        mapX = newMapX;
        mapY = newMapY;
        texName = newTextureLocation;
        type = newType;
        inhabitant = null;

    }


    //getters
    public int getMapX()
    {
        return mapX;
    }
    public int getMapY()
    {
        return mapY;
    }
    public  String getTexName()
    {
        return texName;
    }
    public TileType getType()
    {
        return type;
    }
    public Character getInhabitant()
    {
        return inhabitant;
    }

    //setters
    public void setMapX(int newMapX) {
        this.mapX = mapX;
    }
    public void setMapY(int newMapY) {
        this.mapY = mapY;
    }
    public void setTexName(String newTexName) {
        this.texName = texName;
    }
    public void setType(TileType newType)
    {
        type = newType;
    }
    public void setInhabitant(Character newInhabitant)
    {
        inhabitant = newInhabitant;
    }

    //add inhabitant when implemented character.










}