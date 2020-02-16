package com.kroy.game;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

/***
 * Class used to handle info about the fireStation
 */

public class Station extends Tile {
    private int repairTime;
    private int refillTime;

    //Variables to keep track of whether the station has/should be destroyed [ID: S1]
    private boolean destroyed = false;


    /**
     * Constructs A Station Tile that repairs and refills surrounding tiles
     *
     * @param X The x position of the tile on the map
     * @param Y The y position of the tile on the map
     */
    public Station(int X, int Y) {
        super(X, Y, "stationTile.png", TileType.TILE_TYPES_STATION);
        this.setTexture(Assets.stationTileTexture);

        repairTime = Constants.getStationRepairAmount();
        refillTime = Constants.getStationRefillAmount();
    }


    //Getters
    public int getRepairTime() {
        return repairTime;
    }

    public int getRefillTime() {
        return refillTime;
    }


    /**
     * [ID: S2]
     * Destroy the station so engines cant use it to refill anymore
     */
    public void destroy() {
        destroyed = true;
        this.texName = "lavaTile.png";
        this.setTexture(Assets.lavaTileTexture);
    }

    /**
     * Repair tiles that have fire engines on
     *
     * @param surroundingTiles the tiles you want to repair
     */
    public void repairTiles(ArrayList<Tile> surroundingTiles) {
        for (Tile surroundingTile : surroundingTiles) {
            if(!destroyed) {
                if (surroundingTile.getInhabitant() instanceof FireEngine) {
                    surroundingTile.getInhabitant().setHealth(Math.min(surroundingTile.getInhabitant().getHealth() + Constants.getStationRepairAmount(), surroundingTile.getInhabitant().getMaxHealth()));
                 }
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
            if (surroundingTile.getInhabitant() instanceof FireEngine && !destroyed) {
                ((FireEngine) surroundingTile.getInhabitant()).setWaterAmount(Math.min(((FireEngine) surroundingTile.getInhabitant()).getWaterAmount() + Constants.getStationRefillAmount(), ((FireEngine) surroundingTile.getInhabitant()).getWaterCapacity()));
            }
        }
    }

    public boolean isDestroyed() { return destroyed; }

    public void setDestroyed(boolean destroyed) { this.destroyed = destroyed; }
}
