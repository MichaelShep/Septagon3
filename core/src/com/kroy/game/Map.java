package com.kroy.game;

import java.io.*;





public class Map {

    Tile[][] mapData;
    Station stationPosition;
    Tile[] fortressPositions;

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

        stationPosition = null;
        fortressPositions = new Tile[Constants.getFortressCount()];

        try
        {
            generateMap();
        }
        catch (IOException e)
        {
            System.out.println(e + "Map Could not be generated" );
            System.exit(0);
        }
    }





    public void generateMap() throws IOException {

        String directory = System.getProperty("user.dir") + "\\core\\src\\Data\\" + Constants.getMapFileName();

        BufferedReader csvReader;

        String texture;

        int maxFortresses = fortressPositions.length;
        int fortressPlaced = 0;

        String rowData;
        int rowCount = Constants.getMapHeight()-1;
        int cellData = 0;

        try
        {

            csvReader = new BufferedReader(new FileReader(directory));
            System.out.println("File Loaded");
            while ((rowData = csvReader.readLine()) != null) {
                String[] data = rowData.split(",");
                // do something with the data
                for (int cell = 0; cell < data.length; cell++)
                {
                    Boolean placed = false;
                    cellData = Integer.parseInt(data[cell]);
                    texture = mapTextures(cellData);

                    if (texture == "fortressTile.png")
                    {
                        if (fortressPlaced >= maxFortresses)
                        {
                            texture = "grassTile.png";
                        }
                        else
                        {
                            mapData[cell][rowCount] = new Tile(cell,rowCount, texture, TileType.values()[cellData]);
                            for(int i=0; i<maxFortresses;i++)
                            {
                                if (fortressPositions[i] == null)
                                {
                                    fortressPositions[i] = mapData[cell][rowCount];
                                    break;
                                }
                            }

                            fortressPlaced++;
                            placed = true;
                        }

                    }
                    else if (texture == "stationTile.png")
                    {
                        if (stationPosition == null)
                        {
                            mapData[cell][rowCount] = new Station(cell,rowCount);
                            placed = true;
                        }
                    }

                    if (!(placed))
                    {
                        mapData[cell][rowCount] = new Tile(cell,rowCount, texture, TileType.values()[cellData]);
                    }


                }

                rowCount--;
            }
            csvReader.close();

            if (fortressPlaced < maxFortresses)
            {
                throw new IllegalArgumentException("Input Map does not contain enough fortresses as specified in Constants.Java");
            }



        }
        catch(FileNotFoundException e)
        {
            throw new FileNotFoundException(Constants.getMapFileName()+"\n");
        }
        catch (IOException e)
        {
            throw new IOException();
        }






    }


    private String mapTextures(int textureCode)
    {
        switch (textureCode) {
            case 0: {
                return "grassTile.png";
            }
            case 1: {
                return "roadTile.png";
            }
            case 2: {
                return "stationTile.png";
            }
            case 3:{
                return "fortressTile.png";
            }

        }

        throw new IllegalArgumentException(textureCode + " is not implemented in mapTextures()");

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
