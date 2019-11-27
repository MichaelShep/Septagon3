package com.kroy.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ModelInfluencer;
import com.sun.org.apache.bcel.internal.Const;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class Map {

    Tile[][] mapData;
    private int mapWidth,mapHeight;
    private int shiftX,shiftY;

    private final int roadDensity = Constants.getRoadDensity();




    Map()
    {
        //default map

        mapWidth = Constants.getMapWidth();
        mapHeight = Constants.getMapHeight();
        mapData = new Tile[mapWidth][mapHeight];

        shiftX = (Constants.getResolutionWidth() - (mapWidth*Constants.getTileSize()))/2;
        shiftY = (Constants.getResolutionHeight() - (mapHeight*Constants.getTileSize()))/2;


        generateMap();
        placeRoads();
        placeFortresses();

    }





    public void generateMap() {

        for (int width = 0; width < mapWidth; width++) {
            for (int height = 0; height < mapHeight; height++) {
                mapData[width][height] = new Tile(width, height);
            }
        }
    }





    public int getMapWidth()
    {
        return  mapWidth;
    }

    public int getMapHeight()
    {
        return  mapHeight;
    }

    public int getShiftX()
    {
        return  shiftX;
    }

    public int getShiftY()
    {
        return  shiftY;
    }

    public Tile[][] getMapData()
    {
        return  mapData;
    }

    public void placeRoads()
    {
        int placePosition;

        for(int road=0; road < roadDensity; road++)
        {
            placePosition = (int)(Math.random()*(mapWidth-2))+1;
            for(int place=0; place < mapHeight; place++)
            {
                mapData[placePosition][place].setTexName("vertRoadTile.png");
            }
        }
        for(int road=0; road < roadDensity; road++)
        {
            placePosition = (int)(Math.random()*(mapHeight-2))+1;
            for(int place=0; place < mapWidth; place++)
            {
                if (placePosition > 0) {
                    if (mapData[place][placePosition - 1].getTexName() == "vertRoadTile.png" || mapData[place][placePosition - 1].getTexName() == "crossRoadTile.png") {
                        mapData[place][placePosition].setTexName("crossRoadTile.png");
                    }
                }
                if (placePosition < mapHeight-1)
                {
                    if (mapData[place][placePosition+1].getTexName() == "vertRoadTile.png" || mapData[place][placePosition+1].getTexName() == "crossRoadTile.png")
                    {
                        mapData[place][placePosition].setTexName("crossRoadTile.png");
                    }
                }

                if (!(mapData[place][placePosition].getTexName() == "crossRoadTile.png"))
                {
                    mapData[place][placePosition].setTexName("horiRoadTile.png");
                }

            }
        }
    }


    private void placeFortresses()
    {
        int fortressPlaced = 0;
        int attempts = 0;

        int hubX = 0;
        int hubY = 0;

        int placeX = 0;
        int placeY = 0;

        int distance = 0;

        int maxDistance = 10;

        while (fortressPlaced < Constants.getFortressCount())
        {
            if (attempts > 1000)
            {
                //throw exception
            }
            else
            {
                placeX = (int)(Math.random()*(mapWidth-1))+1;
                placeY = (int)(Math.random()*(mapHeight-1))+1;

                distance = (int)Math.sqrt(Math.pow((placeY - hubY),2) + Math.pow((placeX - hubX),2));

                if  (distance < maxDistance)
                {
                    if (mapData[placeX][placeY].getTexName() == "grassTile.png")
                    {
                        mapData[placeX][placeY].setTexName("fortressTile.png");
                        fortressPlaced += 1;
                    }
                }



                attempts += 1;

            }


        }





    }

    private  boolean nextToRoad(int x, int y)
    {
        //do this function;
        return true;

    }











}
