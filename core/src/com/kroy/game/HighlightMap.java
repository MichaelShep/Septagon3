package com.kroy.game;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;

public class HighlightMap {

    private Tile[][] mapData;
    private int mapWidth, mapHeight;
    private boolean render;
    private int shiftX, shiftY;


    /**
     * Constructs a type of map to rendered on top of the game map
     * Shows highlights and regions where players can take actions
     *
     * @param width  how wide the map is
     * @param height how high the map is
     */
    public HighlightMap(int width, int height) {
        //default Constructor
        mapWidth = width;
        mapHeight = height;

        render = false;

        shiftX = (Constants.getResolutionWidth() - (mapWidth * Constants.getTileSize())) / 2;
        shiftY = (Constants.getResolutionHeight() - (mapHeight * Constants.getTileSize())) / 2;


        generateMap();

    }

    /**
     * Initialises the map data
     * Fills the with default blank tiles
     */
    public void generateMap() {

        mapData = new Tile[mapHeight][mapWidth];

        for (int height = 0; height < mapHeight; height++) {
            for (int width = 0; width < mapWidth; width++) {
                mapData[height][width] = new Tile(width, height, "HighlightTexture/blank.png", TileType.TILE_TYPES_HIGHLIGHT);
            }
        }


    }

    /**
     * returns the map to its original state
     */
    public void resetMap() {
        for (int height = 0; height < mapHeight; height++) {
            for (int width = 0; width < mapWidth; width++) {
                mapData[height][width].setTexName("HighlightTexture/blank.png");
            }
        }

    }

    /**
     * Turns a high light on to show a move the player can do
     * Attacks get priority
     *
     * @param x    the x coordinate of interrogation
     * @param y    the y coordinate of interrogation
     * @param type the corresponding type of tile on the game map
     */
    public void setTile(int x, int y, TileType type) {
        if (type == TileType.TILE_TYPES_FORTRESS) {
            mapData[y][x].setTexName("HighlightTexture/attack.png");
        } else if (type == TileType.TILE_TYPES_ROAD) {
            mapData[y][x].setTexName("HighlightTexture/move.png");
        }


    }

    public void removeUnreachable()
    {
        ArrayList<Tile> moveTiles = new ArrayList<>();
        for (int height = 0; height < mapHeight; height++) {
            for (int width = 0; width < mapWidth; width++) {
                if(mapData[height][width].getTexName() == "HighlightTexture/move.png")
                {
                    moveTiles.add(mapData[height][width]);
                }
            }
        }

        for (Tile move: moveTiles)
        {
            if (!tileReachable(move))
            {
                move.setTexName("HighlightTexture/blank.png");
            }
        }


    }

    public Boolean tileReachable(Tile startTile)
    {
        ArrayList<Tile> tilesVisited = new ArrayList<>();
        int[][] shiftValues = {
                {0,1},{0,-1},{1,0},{-1,0}
        };

        Boolean addedTileFlag = true;

        tilesVisited.add(startTile);

        while (addedTileFlag)
        {
            addedTileFlag = false;
            for (Tile queryTile: new ArrayList<Tile>(tilesVisited))
            {
                for (int[] shiftValue: shiftValues)
                {
                    Tile adjacentTile;
                    try
                    {
                        adjacentTile = mapData[queryTile.getMapY()+shiftValue[0]][queryTile.getMapX()+shiftValue[1]];
                        if (!tilesVisited.contains(adjacentTile))
                        {
                            if (adjacentTile.getTexName().equals("HighlightTexture/move.png"))
                            {
                                tilesVisited.add(adjacentTile);
                                addedTileFlag = true;
                            }
                            if (adjacentTile.getTexName().equals("HighlightTexture/selected.png"))
                            {
                                return true;
                            }
                        }
                    }
                    catch (IndexOutOfBoundsException e)
                    {
                        System.out.println("queryTile was on the edge of the map");
                    }


                }
            }
        }

        return false;
    }








    public Tile[][] getMapData() {
        return mapData;
    }

    public void setMapData(Tile[][] mapData) {
        this.mapData = mapData;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
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


}
