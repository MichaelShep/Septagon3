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

    /**
     * Constructs a new game Map Object
     * @param fileName the file root of the map data
     */
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

    /**
     * Read the map data from CSV
     * @param mapCSVFile the csv file with the data
     * @return return a List of string which represent each row of data
     * @throws IOException if the file cannot be opened
     */
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


    /**
     * turn the string data from CSV into Tile Data
     * @param mapTileData a list of Strings with numbers corresponding to enum TILE_TYPE
     */
    public void generateMap(ArrayList<String[]> mapTileData) {

        int tileCode;
        for (int height = 0; height < mapHeight; height++) {
            for (int width = 0; width < mapWidth; width++) {
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

            }
        }
    }

    /**
     * A function to allocate texture paths to TILE_TYPES
     * @param textureCode the texture code you want the corresponding root for
     * @return the texture root for that texture code
     */
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


    /**
     * maps tile codes based on the tiles that surround them
     * @param xPos the x position of the query tile
     * @param yPos the y position of the query tile
     * @param mapTileData the tile data of the maps
     * @return a list of adjacent tile codes
     */
    private int[] getAdjacentTileCodes(int xPos, int yPos, ArrayList<String[]> mapTileData) {
        int[] adjacentTileCodes = new int[4];
        int tempValue;

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


    /**
     * gives a corresponding orientated road texture based on a set of surrounding tiles
     * @param adjacentTileTypes the list of adjacent tile codes
     * @return the road texture for a tile that is surrounded by those inputs
     */
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

    /**
     * gets a random texture if the type passed is in a pool category
     * @param textureType the type you want query
     * @return a texture in the pool of type passed
     */
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

    /**
     * find all the tiles that have fortresses
     * @return a list of tiles where fortresses are situated
     */
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

    /**
     * find the distance between two tiles
     * @param source the first tile
     * @param target the second tile
     * @return the distance between them
     */
    private double distanceBetween(Tile source, Tile target) {
        double distance = Math.sqrt(Math.pow(source.getMapX() - target.getMapX(), 2) + Math.pow((source.getMapY() - target.getMapY()), 2));
        return distance;

    }

    /**
     * find all the tiles around a point sorted by distance
     * @param hub the central location of the sort
     * @return return an array list of tiles sorted by the distance away from the hub
     * @throws Exception the ranges could not be computed
     */
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

    /**
     * gets the n closest tiles to a hub tile
     * @param n the number of tiles you want back
     * @param hub the central location of the search
     * @return and array of n tiles
     */
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

    /**
     * gets the n closest tiles to a hub tile of a certain tile type
     * @param n the number of tiles you want back
     * @param hub the central location of the search
     * @return and array of n tiles
     */
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

    /**
     * places a character onto the map
     * @param placedObject the character you want to place
     */
    public void placeOnMap(Character placedObject) {
        placedObject.setPosition((placedObject.getLocation().getMapX() * Constants.getTileSize() + shiftX), (placedObject.getLocation().getMapY() * Constants.getTileSize() + shiftY));
    }

    /**
     * get a list of tiles that must be within a specific range of the hub
     * @param hub the centre of the search
     * @param range the maximum range surrounding tiles can be
     * @return a list of tiles with range of the hub
     */
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

    /**
     * get a list of tiles that must be within a specific range of the hub and of a specific type
     * @param hub the centre of the search
     * @param range the maximum range surrounding tiles can be
     * @return a list of tiles with range of the hub
     */
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
