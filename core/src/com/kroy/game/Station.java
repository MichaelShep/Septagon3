package com.kroy.game;

import com.badlogic.gdx.utils.Array;

public class Station extends Tile {
    private int repairTime;
    private int refillTime;


    Station(int X, int Y) {
        super(X, Y, "stationTile.png", TileType.TILE_TYPES_STATION);

        repairTime = Constants.getStationRepairAmount();
        refillTime = Constants.getStationRefillAmount();

    }




    public int getRepairTime() {
        return repairTime;
    }

    public int getRefillTime() {
        return refillTime;
    }



    public void repairSurrounding(Array<Tile> SurroundingTiles)
    {

    }


    public void refillSurrounding(Array<Tile> SurroundingTiles)
    {


    }



}
