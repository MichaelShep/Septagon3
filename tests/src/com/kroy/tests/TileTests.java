package com.kroy.tests;


import com.kroy.game.Tile;
import com.kroy.game.TileType;
import org.junit.Test;

import static org.junit.Assert.*;

public class TileTests 
{
    @Test
    public void testDefaultConstructor(){
        Tile testTile = new Tile();

        assertEquals(0,testTile.getMapX());
        assertEquals(0,testTile.getMapY());
        assertEquals("grassTile.png",testTile.getTexName());
        assertEquals(TileType.TILE_TYPES_GRASS,testTile.getType());
        assertNull(testTile.getInhabitant());

    }


	@Test	
	public void testXYConstructor()
	{
	    Tile testTile = new Tile(2,4);
	
	    assertEquals(2, testTile.getMapX());
	    assertEquals(4, testTile.getMapY());
	    assertEquals("grassTile.png",testTile.getTexName());
	    assertEquals(TileType.TILE_TYPES_GRASS,testTile.getType());
	    assertNull(testTile.getInhabitant());

	}


    @Test
    public void testXYTexTypeConstructor()
    {
        Tile testTile = new Tile(2,4,"turnip",TileType.TILE_TYPES_BUILDING);

        assertEquals(2, testTile.getMapX());
        assertEquals(4, testTile.getMapY());
        assertEquals("turnip",testTile.getTexName());
        assertEquals(TileType.TILE_TYPES_BUILDING,testTile.getType());
        assertNull(testTile.getInhabitant());

    }
	
}