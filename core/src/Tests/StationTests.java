package Tests;

import com.kroy.game.FireEngine;
import com.kroy.game.Station;
import com.kroy.game.TileType;
import org.junit.Test;

import static org.junit.Assert.*;

public class StationTests {

    @Test
    public void testDefaultConstructor(){
        Station testStation = new Station(2,4);


        assertEquals(2, testStation.getMapX());
        assertEquals(4, testStation.getMapY());
        assertEquals("stationTile.png",testStation.getTexName());
        assertEquals(TileType.TILE_TYPES_STATION,testStation.getType());
        assertNull(testStation.getInhabitant());
    }

    @Test
    public  void  testDestroyTime(){ //Added by Septagon
        Station testStation = new Station(2,4);
        assertTrue(testStation.destructionTimer());
    }



}
