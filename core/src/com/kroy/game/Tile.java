package com.kroy.game;

public class Tile
{
    private int mapX;
    private int mapY;
    private String texName;

    Tile()
    {
        //default constructor

    }

    Tile(int newMapX, int newMapY)
    {

        mapX = newMapX;
        mapY = newMapY;
        texName = "grassTile.png";

    }

    Tile(int newMapX, int newMapY, String textureLocation)
    {

        mapX = newMapX;
        mapY = newMapY;
        texName = textureLocation;

    }

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

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public void setTexName(String texName) {
        this.texName = texName;
    }


    //add inhabitant when implemented character.










}