package com.kroy.game;

public class Station extends Tile {
    private int repairTime;
    private int refillTime;


    Station(int X, int Y) {
        super(X, Y, "stationTile.png", TileType.TILE_TYPES_STATION);

        repairTime = Constants.getStationRepairAmount();
        refillTime = Constants.getStationRefillAmount();

    }

    private int getRepairTime() {
        return repairTime;
    }

    private int getRefillTime() {
        return refillTime;
    }


}
