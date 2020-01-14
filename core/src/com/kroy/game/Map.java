package com.kroy.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeMap;

//import sun.jvm.hotspot.utilities.ObjectReader;


public class Map {

    private Tile[][] mapData;
    private Station stationPosition;


    private int mapWidth, mapHeight;
    private int shiftX, shiftY;

    //this comment exists because i forgot to split my comits
    public Map(String fileName) {
        //String directory = System.getProperty("user.dir") + "/core/src/Data/" + fileName;
        String directory = fileName;

        try {
            generateMap(readMapCSV(directory));
        } catch (IOException e) {
            System.out.println(e + " -- Map could not be read");
            System.exit(0);
        }


        shiftX = (Constants.getResolutionWidth() - (mapWidth * Constants.getTileSize())) / 2;
        shiftY = (Constants.getResolutionHeight() - (mapHeight * Constants.getTileSize())) / 2;


    }


    public ArrayList<String[]> readMapCSV(String mapCSVFile) throws IOException {
        ArrayList<String[]> rowData = new ArrayList<String[]>();
        String row;


        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(mapCSVFile));

            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                rowData.add(data);
            }

            csvReader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        }

        mapWidth = (rowData.get(0).length);
        mapHeight = (rowData.size());
        mapData = new Tile[mapHeight][mapWidth];

        return rowData;


    }


    public void generateMap(ArrayList<String[]> mapTileData) {

        int tileCode;
        for (int height = 0; height < mapHeight; height++) {
            for (int width = 0; width < mapWidth; width++) {
                //System.out.println("WIDTH: " + width + " HEIGHT: " + height);
                tileCode = (Integer.parseInt(mapTileData.get(height)[width]) - 1);

                if (tileCode == 0) {
                    int[] adjacentTiles = getAdjacentTileCodes(width, height, mapTileData);
                    mapData[height][width] = new Tile(width, height, mapRoadTextures(adjacentTiles), TileType.values()[tileCode]);
                } else if (tileCode == 5) {
                    stationPosition = new Station(width, height);
                    mapData[height][width] = stationPosition;
                } else {
                    mapData[height][width] = new Tile(width, height, mapTextures(tileCode), TileType.values()[tileCode]);
                }



                /*
                switch (tileCode)
                {
                    case 1:
                        int[] adjacentTiles = getAdjacentTileCodes(width,height,mapTileData);
                        mapData[height][width] = new Tile(width, height,mapRoadTextures(adjacentTiles), TileType.values()[tileCode]);
                        break;

                    case 2:
                        mapData[height][width] = new Tile(width, height,mapTextures(tileCode), TileType.values()[tileCode]);
                        break;
                    case 3:
                        mapData[height][width] = new Tile(width, height,mapTextures(tileCode), TileType.values()[tileCode]);
                        break;
                    case 4:
                        mapData[height][width] = new Tile(width, height,mapTextures(tileCode), TileType.values()[tileCode]);
                        break;
                    case 5:
                        mapData[height][width] = new Tile(width, height,mapTextures(tileCode), TileType.values()[tileCode]);
                        break;
                    case 6:
                        mapData[height][width] = new Tile(width, height,mapTextures(tileCode), TileType.values()[tileCode]);
                        break;
                    default:
                        mapData[height][width] = new Tile(width, height,mapTextures(tileCode), TileType.values()[tileCode]);
                        break;

                }
                         */
            }
        }

        System.out.println("Map Generated");

    }


    private String mapTextures(int textureCode) {
        switch (textureCode) {
            case 0:
                return "roadTile.png";
            case 1:
                return "waterTile.png";
            case 2:
                return rndTexture("GreeneryTexture");
            case 3:
                return rndTexture("BuildingTexture");
            case 4:
                return "lavaTile.png";
            case 5:
                return "stationTile.png";
            default:
                throw new IllegalArgumentException(textureCode + " is not implemented in mapTextures()");
        }
    }


    //ths is hideous
    private int[] getAdjacentTileCodes(int xPos, int yPos, ArrayList<String[]> mapTileData) {
        int[] adjacentTileCodes = new int[4];
        int tempValue;


        //System.out.println("WIDTH: " + xPos + " HEIGHT: " + yPos);


        if (yPos + 1 < mapHeight) {
            tempValue = Integer.parseInt(mapTileData.get(yPos + 1)[xPos]);
            adjacentTileCodes[0] = (tempValue == 1) ? 1 : 0;
        } else {
            adjacentTileCodes[0] = 0;
        }

        if (xPos + 1 < mapWidth) {
            tempValue = Integer.parseInt(mapTileData.get(yPos)[xPos + 1]);
            adjacentTileCodes[1] = (tempValue == 1) ? 1 : 0;
        } else {
            adjacentTileCodes[1] = 0;
        }

        if (yPos - 1 >= 0) {
            tempValue = Integer.parseInt(mapTileData.get(yPos - 1)[xPos]);
            adjacentTileCodes[2] = (tempValue == 1) ? 1 : 0;
        } else {
            adjacentTileCodes[2] = 0;
        }

        if (xPos - 1 >= 0) {
            tempValue = Integer.parseInt(mapTileData.get(yPos)[xPos - 1]);
            adjacentTileCodes[3] = (tempValue == 1) ? 1 : 0;
        } else {
            adjacentTileCodes[3] = 0;
        }

        return adjacentTileCodes;
    }


    //if someone can do this neater please do
    private String mapRoadTextures(int[] adjacentTileTypes) {
        switch ((adjacentTileTypes[0] + adjacentTileTypes[1] + adjacentTileTypes[2] + adjacentTileTypes[3])) {
            case 1: {
                if (adjacentTileTypes[0] == 1) {
                    return ("RoadVertical.png");
                } else if (adjacentTileTypes[1] == 1) {
                    return ("RoadHorizontal.png");
                } else if (adjacentTileTypes[2] == 1) {
                    return ("RoadVertical.png");
                } else {
                    return ("RoadHorizontal.png");
                }

            }
            case 2: {
                if (adjacentTileTypes[0] == 1) {
                    if (adjacentTileTypes[2] != 1) {
                        if (adjacentTileTypes[1] == 1) {
                            return ("RoadTwoWayRightUp.png");
                        } else {
                            return ("RoadTwoWayLeftUp.png");
                        }
                    } else {
                        return ("RoadVertical.png");
                    }
                } else if (adjacentTileTypes[2] == 1) {
                    if (adjacentTileTypes[1] == 1) {
                        return ("RoadTwoWayRightDown.png");
                    } else {
                        return ("RoadTwoWayLeftDown.png");
                    }
                } else {
                    return ("RoadHorizontal.png");
                }
            }
            case 3: {
                if (adjacentTileTypes[0] != 1) {
                    return ("RoadThreeWayNoUp.png");
                } else if (adjacentTileTypes[1] != 1) {
                    return ("RoadThreeWayNoRight.png");
                } else if (adjacentTileTypes[2] != 1) {
                    return ("RoadThreeWayNoDown.png");
                } else {
                    return ("RoadThreeWayNoLeft.png");
                }
            }

            case 4:
                return ("RoadFourWay.png");


            default:
                return ("BuildingTexture/TileBuild.png");
        }
    }


    String rndTexture(String textureType) {
        Random random = new Random();

        if (textureType == "GreeneryTexture") {
            return Constants.getGrassTexture()[random.nextInt(Constants.getGrassTexture().length)];
        } else if (textureType == "BuildingTexture") {
            return Constants.getBuildingTexture()[random.nextInt(Constants.getBuildingTexture().length)];
        } else {
            throw new IllegalArgumentException(textureType + " not textureType");
        }


    }


    public Tile[] getFortressTiles() {
        Tile[] locations = new Tile[Constants.getFortressCount()];

        for (int height = 0; height < mapHeight; height++) {
            for (int width = 0; width < mapWidth; width++) {

                if (mapData[height][width].getType() == TileType.TILE_TYPES_FORTRESS) {
                    for (int fortIndex = 0; fortIndex < locations.length; fortIndex++) {
                        if (locations[fortIndex] == null) {
                            locations[fortIndex] = mapData[height][width];
                            break;
                        }
                    }
                }

            }
        }

        return locations;

    }


/*

    private FireEngine getNewFireEngine(Tile characterPosition)
    {



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
    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getShiftX() {
        return shiftX;
    }

    public void setShiftX(int shiftX) {
        this.shiftX = shiftX;
    }

    public int getShiftY() {
        return shiftY;
    }

    public void setShiftY(int shiftY) {
        this.shiftY = shiftY;
    }

    public Tile[][] getMapData() {
        return mapData;
    }

    public Tile getStationPosition() {
        return stationPosition;
    }

    private double distanceBetween(Tile source, Tile target) {
        double distance = Math.sqrt(Math.pow(source.getMapX() - target.getMapX(), 2) + Math.pow((source.getMapY() - target.getMapY()), 2));
        return distance;

    }

    private TreeMap<Double, ArrayList<Tile>> getSortedTilesAbout(Tile hub) throws Exception {
        TreeMap<Double, ArrayList<Tile>> sortedRangeTiles = new TreeMap<Double, ArrayList<Tile>>();
        Tile referenceTile;
        for (int height = 0; height < mapHeight; height++) {
            for (int width = 0; width < mapWidth; width++) {
                referenceTile = mapData[height][width];
                if (!(referenceTile == hub)) {
                    double distance = distanceBetween(hub, referenceTile);
                    if (sortedRangeTiles.containsKey(distance)) {
                        sortedRangeTiles.get(distance).add(referenceTile);
                    } else {
                        sortedRangeTiles.put(distance, new ArrayList<Tile>(Arrays.asList(referenceTile)));
                    }
                }
            }
        }

        if (!sortedRangeTiles.isEmpty()) {
            return sortedRangeTiles;
        } else {
            throw new Exception("Distances from this hub could not be found");
        }

    }


    public Tile[] getNClosest(int n, Tile hub) {
        TreeMap<Double, ArrayList<Tile>> sortedRangeTiles;
        int tilesFound = 0;
        Tile[] outputTiles = new Tile[n];

        try {
            sortedRangeTiles = getSortedTilesAbout(hub);
            if (sortedRangeTiles.values().size() >= n) {
                for (ArrayList<Tile> bundledTile : sortedRangeTiles.values()) {
                    for (Tile tile : bundledTile) {
                        if (tilesFound < n) {
                            outputTiles[tilesFound] = tile;
                            tilesFound++;
                        } else {
                            return outputTiles;
                        }
                    }
                }


            } else {
                throw new Exception("Firetruck spawn number is greater than tiles available");
            }
        } catch (Exception e) {
            System.exit(0);
        }


        System.out.println("getNClosest Failed");
        return null;

    }

    public Tile[] getNClosest(int n, Tile hub, TileType type) {
        TreeMap<Double, ArrayList<Tile>> sortedRangePairs;
        int tilesFound = 0;
        Tile[] outputTiles = new Tile[n];

        try {
            sortedRangePairs = getSortedTilesAbout(hub);
            //ArrayList<Tile> sortedTiles = ((ArrayList<Tile>)sortedRangePairs.values());
            ArrayList<ArrayList<Tile>> sortedTiles = new ArrayList<ArrayList<Tile>>(sortedRangePairs.values());
            if (sortedTiles.size() >= n) {
                for (ArrayList<Tile> bundledTiles : sortedTiles) {
                    for (Tile tile : bundledTiles) {
                        if (tilesFound < n) {
                            if (tile.getType() == type) {
                                outputTiles[tilesFound] = tile;
                                tilesFound++;
                            }
                        } else {
                            return outputTiles;
                        }
                    }
                }


            } else {
                throw new Exception("Not enough tiles on map to satisfy parameters");
            }
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }


        System.out.println("getNClosest Failed");
        return null;

    }

    public void placeOnMap(Character placedObject) {
        placedObject.setPosition((placedObject.getLocation().getMapX() * Constants.getTileSize() + shiftX), (placedObject.getLocation().getMapY() * Constants.getTileSize() + shiftY));
    }

    public ArrayList<Tile> getWithRangeOfHub(Tile hub, int range) {
        TreeMap<Double, ArrayList<Tile>> sortedRangePairs;
        ArrayList<Tile> outputTiles = new ArrayList<Tile>();

        try {
            sortedRangePairs = getSortedTilesAbout(hub);
            for (double distance : sortedRangePairs.keySet()) {
                if (distance < range) {
                    for (Tile tile : sortedRangePairs.get(distance)) {
                        outputTiles.add(tile);
                    }
                }


            }


        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }

        return outputTiles;
    }

    public ArrayList<Tile> getWithRangeOfHub(Tile hub, int range, TileType type) {
        TreeMap<Double, ArrayList<Tile>> sortedRangePairs;
        ArrayList<Tile> outputTiles = new ArrayList<Tile>();

        try {
            sortedRangePairs = getSortedTilesAbout(hub);
            for (double distance : sortedRangePairs.keySet()) {
                if (distance < range) {
                    for (Tile tile : sortedRangePairs.get(distance)) {
                        if (tile.getType() == type) {
                            outputTiles.add(tile);
                        }
                    }
                }


            }


        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }

        return outputTiles;
    }

    public void setStationPosition(Station stationPosition) {
        this.stationPosition = stationPosition;
    }
}
