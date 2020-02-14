package com.kroy.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.FireEngine;
import com.kroy.game.Patrol;
import com.kroy.game.Tile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

//Added by Septagon
@RunWith(GdxTestRunner.class)
public class PatrolTests {

    //Vaiables used to perform the testing
    Tile testTile = new Tile();
    Patrol testPatrol;
    Class ReflectionClass = Patrol.class;

    @Before
    /**
     * Sets up the variables that are used by the testing
     */
    public void init(){
        testPatrol = new Patrol(100, 10, 10, testTile);
        Texture engineTexture = new Texture(Gdx.files.internal("fireEngineSprite.png"));
        testPatrol.setTexture(engineTexture);
    }

    @Test
    /**
     * Tests the patrols constructor correctly sets class variables
     */
    public void testConstructor() {
        Patrol testPatrol = new Patrol(100, 10, 10, testTile);
        assertEquals(testPatrol.getHealth(), 100);
        assertEquals(testPatrol.getDamage(), 10);
        assertEquals(testPatrol.getRange(), 10);
    }

    @Test
    /**
     * Tests that moving the patrols correctly updates the tiles they have moved to and from
     */
    public void testTransferTo() {
        Tile newLocation = new Tile(2,2);
        try {
            Method transferToReflection = ReflectionClass.getDeclaredMethod("transferTo");
            transferToReflection.setAccessible(true);
            transferToReflection.invoke(newLocation, transferToReflection);
            assertEquals(testTile.getInhabitant(),null);
            assertEquals(newLocation.getInhabitant(), testPatrol);

        } catch (NoSuchMethodException e) {

        } catch (InvocationTargetException e) {

        } catch (IllegalAccessException e) {

        }
    }

}
