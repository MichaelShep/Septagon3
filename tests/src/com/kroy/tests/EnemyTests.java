package com.kroy.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.*;
import com.kroy.game.Character;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class EnemyTests {

    Enemy testEnemy;
    Map testMap;


    @Before
    public void init(){
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl30 = Mockito.mock(GL30.class);

        Assets.loadGameAssets();
        testEnemy = new Enemy(false, 1,1);
        String directory = Gdx.files.getLocalStoragePath() + "assets/Data/yorkMapFlipped.csv";
        testMap = new Map(directory);
    }

    @Test
    public  void testConstructor(){
        assertFalse(testEnemy.isMyTurn());
        assertEquals(1, testEnemy.getAliveCharacters());
        assertEquals(1, testEnemy.getPatrols().size());
    }

    @Test
    public void testCreateFortress(){
        Fortress testFortress = testEnemy.createFortress("TEST_FORTRESS", 0);
        assertEquals(2, testFortress.getHealth());
        assertEquals(3, testFortress.getDamage());
        assertEquals(5, testFortress.getRange());
    }

    @Test
    public void testCreatePatrol(){
        Patrol testPatrol = testEnemy.createPatrol(0);
        assertEquals(7, testPatrol.getHealth());
        assertEquals(5, testPatrol.getDamage());
        assertEquals(6, testPatrol.getRange());
    }

    @Test
    public void testCalculateTargets(){
        testEnemy = new Enemy(false, 6,1);
        testEnemy.distributeTeamLocation(testMap.getFortressTiles());
        Human testHuman = new Human(false, 1);
        testHuman.createFireEngine(0);
        testHuman.distributeTeamLocation(testMap.getNClosest(1, testMap.getFortressTiles()[0], TileType.TILE_TYPES_ROAD));
        HashMap testLocations = testEnemy.calculateTargets(testMap);

        assertTrue(testLocations.containsValue(testHuman.getTeam().get(0).getLocation()));

    }

    @Test
    public void testDistributesPatrols(){
        testEnemy = new Enemy(false, 6,1);
        testEnemy.distributeTeamLocation(testMap.getFortressTiles());
    }

    @Test
    public void testDecideTarget(){

    }

    @Test
    public void testImproveFortress(){
        testEnemy.createFortress("TEST_FORTRESS", 0);
        testEnemy.improveFortresses();
        Character testFortress = testEnemy.getTeam().get(0);
        assertEquals(4, testFortress.getHealth());
        assertEquals(4, testFortress.getDamage());
        assertEquals(5, testFortress.getRange());
    }



}
