package com.kroy.tests;

import com.kroy.game.Station;
import com.kroy.game.TileType;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class StationTests {

    @Test
    /**
     * Tests that the constructor of the station correctly assigns class variable values
     */
    public void testDefaultConstructor(){
        Station testStation = new Station(2,4);


        assertEquals(2, testStation.getMapX());
        assertEquals(4, testStation.getMapY());
        assertEquals("stationTile.png",testStation.getTexName());
        assertEquals(TileType.TILE_TYPES_STATION,testStation.getType());
        assertNull(testStation.getInhabitant());
    }



}
