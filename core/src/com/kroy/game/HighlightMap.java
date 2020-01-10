package com.kroy.game;

public class HighlightMap {

    private Tile[][] mapData;
    private int mapWidth, mapHeight;
    private boolean render;
    private int shiftX, shiftY;


    public HighlightMap(int width, int height) {
        //default Constructor
        mapWidth = width;
        mapHeight = height;

        render = false;

        shiftX = (Constants.getResolutionWidth() - (mapWidth * Constants.getTileSize())) / 2;
        shiftY = (Constants.getResolutionHeight() - (mapHeight * Constants.getTileSize())) / 2;


        generateMap();

    }

    public void generateMap() {

        mapData = new Tile[mapHeight][mapWidth];

        for (int height = 0; height < mapHeight; height++) {
            for (int width = 0; width < mapWidth; width++) {
                mapData[height][width] = new Tile(width, height, "HighlightTexture/blank.png", TileType.TILE_TYPES_HIGHLIGHT);
            }
        }


    }

    public void resetMap() {
        for (int height = 0; height < mapHeight; height++) {
            for (int width = 0; width < mapWidth; width++) {
                mapData[height][width].setTexName("HighlightTexture/blank.png");
            }
        }

    }


    public void setTile(int x, int y, TileType type) {
        if (type == TileType.TILE_TYPES_ROAD) {
            mapData[y][x].setTexName("HighlightTexture/move.png");
        } else if (type == TileType.TILE_TYPES_FORTRESS) {
            mapData[y][x].setTexName("HighlightTexture/attack.png");
        }


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
