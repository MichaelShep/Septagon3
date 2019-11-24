package com.kroy.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ModelInfluencer;

public class Map {

    Tile[][] mapData;
    private int mapWidth,mapHeight;
    private int shiftX,shiftY;

    private final int roadDensity = 4;




    Map()
    {
        //default map

        mapWidth = (((Constants.getResolutionWidth()/4)*3)/Constants.getTileSize()-1);
        mapHeight = (Constants.getResolutionHeight()/Constants.getTileSize())-2;
        mapData = new Tile[mapWidth][mapHeight];

        shiftX = (Constants.getResolutionWidth()/4)+2;
        shiftY = Constants.getResolutionHeight()-(mapHeight*Constants.getTileSize())-(Constants.getTileSize());

        generateMap();
        placeRoads();
        //placeFortresses();

    }





    public void generateMap() {

        for (int width = 0; width < mapWidth; width++) {
            for (int height = 0; height < mapHeight; height++) {
                mapData[width][height] = new Tile(width, height);
            }
        }
    }


    public void renderMap(SpriteBatch batch)
    {

        for(int width = 0; width < mapWidth; width++)
        {
            for(int height = 0; height < mapHeight; height++)
            {
                batch.draw(Constants.getManager().get(mapData[width][height].getTexName(), Texture.class),(width * Constants.getTileSize()) + shiftX,(height*Constants.getTileSize())+shiftY);
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

    public void placeRoads()
    {
        int placePosition;

        for(int road=0; road < roadDensity; road++)
        {
            placePosition = (int)(Math.random()*mapWidth);
            for(int place=0; place < mapHeight; place++)
            {
                mapData[placePosition][place].setTexName("vertRoadTile.png");
            }
        }
        for(int road=0; road < roadDensity; road++)
        {
            placePosition = (int)(Math.random()*mapHeight);
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
        mapData[5][5].setTexName("fortressTile.png");

    }









}
