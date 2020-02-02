package com.kroy.game;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;



public class Station extends Tile {
    private int repairTime;
    private int refillTime;
    private Instant destructionTime;


    /**
     * Constructs A Station Tile that repairs and refills surrounding tiles
     *
     * @param X The x position of the tile on the map
     * @param Y The y position of the tile on the map
     */
    public Station(int X, int Y) {
        super(X, Y, "stationTile.png", TileType.TILE_TYPES_STATION);

        repairTime = Constants.getStationRepairAmount();
        refillTime = Constants.getStationRefillAmount();
        destructionTime=Instant.now();
    }


    public int getRepairTime() {
        return repairTime;
    }

    public int getRefillTime() {
        return refillTime;
    }


    /**
     * Count the time since the start of the game.
     */
    public boolean destructionTimer() {
        return (Duration.between(destructionTime, Instant.now()).getSeconds()) < 60;
    }

    /**
     * Repair tiles that have fire engines on
     *
     * @param surroundingTiles the tiles you want to repair
     */
    public void repairTiles(ArrayList<Tile> surroundingTiles) {
        for (Tile surroundingTile : surroundingTiles) {
            if (surroundingTile.getInhabitant() instanceof FireEngine && destructionTimer()) {
                surroundingTile.getInhabitant().setHealth(Math.min(surroundingTile.getInhabitant().getHealth() + Constants.getStationRepairAmount(), surroundingTile.getInhabitant().getMaxHealth()));
            }
        }
    }

    /**
     * Refill tiles that have fire engines on
     *
     * @param surroundingTiles the tiles you want to refill
     */
    public void refillTiles(ArrayList<Tile> surroundingTiles) {
        for (Tile surroundingTile : surroundingTiles) {
            if (surroundingTile.getInhabitant() instanceof FireEngine && destructionTimer()) {
                ((FireEngine) surroundingTile.getInhabitant()).setWaterAmount(Math.min(((FireEngine) surroundingTile.getInhabitant()).getWaterAmount() + Constants.getStationRefillAmount(), ((FireEngine) surroundingTile.getInhabitant()).getWaterCapacity()));
            }
        }
    }


}
