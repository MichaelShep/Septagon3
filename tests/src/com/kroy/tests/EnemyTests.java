package com.kroy.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.kroy.game.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class EnemyTests {

    Enemy testEnemy;

    @Before
    public void init(){
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl30 = Mockito.mock(GL30.class);

        Assets.loadGameAssets();
        testEnemy = new Enemy(false, 1,1);
        String directory = Gdx.files.getLocalStoragePath() + "assets/Data/MapForTesting.csv";
        Map testMap = new Map(directory);
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
        assertEquals(2, testFortress.getRange());
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

    }

    @Test
    public void testDistributesPatrols(){

    }

    @Test
    public void testDecideTarget(){

    }

    @Test
    public void testImproveFortress(){

    }



}
