package com.kroy.game;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class Map {

    Tile[][] mapData;
    Station stationPosition;
    Tile[] fortressPositions;

    private int mapWidth,mapHeight;
    private int shiftX,shiftY;

    //this comment exists because i forgot to split my comits
    public Map(String fileName)
    {
        String directory = System.getProperty("user.dir") + "/core/src/Data/" + fileName;

        try
        {
            readMapCSV(directory);
        }
        catch (IOException e)
        {
            System.out.println(e + "Map Could not be read" );
            System.exit(0);
        }




        shiftX = (Constants.getResolutionWidth() - (mapWidth*Constants.getTileSize()))/2;
        shiftY = (Constants.getResolutionHeight() - (mapHeight*Constants.getTileSize()))/2;


    }


    void readMapCSV(String mapCSVFile) throws IOException
    {
        ArrayList<String[]> rowData = new ArrayList<String[]>();
        String row;
        //rowData = null;

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(mapCSVFile));

            while ((row = csvReader.readLine()) != null)
            {
                String[] data = row.split(",");
                rowData.add(data);
            }

            csvReader.close();
        }
        catch(FileNotFoundException e)
        {
            throw new FileNotFoundException();
        }
        catch (IOException e)
        {
            throw new IOException();
        }

        mapWidth = (rowData.get(0).length)-1;
        mapHeight = (rowData.size())-1;

        mapData = new Tile[mapWidth][mapHeight];
        generateMap(rowData);

    }


    public void generateMap(ArrayList<String[]> mapTileData)
    {
        int tileCode;

        for (int width = 0; width < mapWidth-1; width++)
        {
            for (int height = 0; height < mapHeight-1; height++)
            {
                System.out.println("WIDTH: " + width + " HEIGHT: " + height);
                tileCode = (Integer.parseInt(mapTileData.get(width)[height])-1);
                switch (tileCode)
                {
                    case 1:
                        mapData[width][height] = new Tile(width, height,mapTextures(tileCode), TileType.values()[tileCode]);
                    case 2:
                        mapData[width][height] = new Tile(width, height,mapTextures(tileCode), TileType.values()[tileCode]);
                    case 3:
                        mapData[width][height] = new Tile(width, height,mapTextures(tileCode), TileType.values()[tileCode]);
                    case 4:
                        mapData[width][height] = new Tile(width, height,mapTextures(tileCode), TileType.values()[tileCode]);
                    case 5:
                        mapData[width][height] = new Tile(width, height,mapTextures(tileCode), TileType.values()[tileCode]);
                    case 6:
                        mapData[width][height] = new Tile(width, height,mapTextures(tileCode), TileType.values()[tileCode]);
                }
            }
        }
    }


    private String mapTextures(int textureCode)
    {
        switch (textureCode) {
            case 1:
                return "roadTile.png";
            case 2:
                return "waterTile.png";
            case 3:
                return rndTexture("GreeneryTexture");
            case 4:
                return rndTexture("BuildingTexture");
            case 5:
                return "fortressTile.png";
            case 6:
                return "stationTile.png";
            default:
                throw new IllegalArgumentException(textureCode + " is not implemented in mapTextures()");
        }
    }


    String rndTexture(String textureType)
    {
        Random random = new Random();

        if (textureType == "GreeneryTexture"){
            return Constants.getGrassTexture()[random.nextInt(Constants.getGrassTexture().length)];
        }
        else if (textureType == "BuildingTexture"){
            return Constants.getGrassTexture()[random.nextInt(Constants.getBuildingTexture().length)];
        }

        else
        {
            throw new IllegalArgumentException(textureType + " not textureType");
        }



    }


/*

    private FireEngine getNewFireEngine(Tile characterPosition)
    {
        //encapsulates the balance
        int health = 100;
        int damage = 10;
        int range = 3;
        int speed = 3;
        int waterCapacity = 100;

        return new FireEngine(health,damage,range,characterPosition,speed,waterCapacity);


    }

    private Fortress getNewFortress(Tile characterPosition)
    {
        //encapsulates the balance
        int health = 100;
        int damage = 10;
        int range = 3;
        int speed = 3;
        int waterCapacity = 100;

        return new Fortress(health,damage,range,characterPosition,"fortress Name");


    }
 */





    //getters
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
















}
