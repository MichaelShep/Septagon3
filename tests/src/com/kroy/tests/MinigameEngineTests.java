package com.kroy.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.Constants;
import com.kroy.game.FireEngine;
import com.kroy.game.Tile;
import com.kroy.game.minigameHelpers.MinigameEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.*;


/**
 * JUnit tests for the MinigameEngine class
 */

@RunWith(GdxTestRunner.class)
public class MinigameEngineTests
{
    //Variables used to help perform testing
    FireEngine testFireEngine;
    MinigameEngine testMinigameEngine;
    Class reflectionClass = MinigameEngine.class;

    @Before
    /**
     * Function to set up values that will be used for testing
     */
    public void init(){
        testFireEngine = new FireEngine(100, 10, 10, new Tile(), 10, 100, "fireEngineSprite.png");

        //Mock the opengl classes using mockito so that libgdx opengl functions can be used
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl30 = Mockito.mock(GL30.class);

        testMinigameEngine = new MinigameEngine(testFireEngine);
        testMinigameEngine.init();
    }

    @Test
    /**
     * Tests that the constructor correctly assigns values
     */
    public void testConstructor(){
        MinigameEngine minigameEngine = new MinigameEngine(testFireEngine);
        assertEquals(minigameEngine.getPassedEngine(), testFireEngine);
    }

    @Test
    /**
     * Tests that the init function correctly assigns initial values to variables
     */
    public void testInit(){
        MinigameEngine minigameEngine = new MinigameEngine(testFireEngine);
        minigameEngine.init();

        assertEquals(minigameEngine.getWidth(), Constants.getTileSize(), 0);
        assertEquals(minigameEngine.getHeight(), Constants.getTileSize(), 0);
        assertEquals(minigameEngine.getTexture(), testFireEngine.getTexture());
        assertEquals(minigameEngine.getX(), -minigameEngine.getWidth() / 2, 0);
        assertEquals(minigameEngine.getY(), -(Gdx.graphics.getHeight() / 2) + 20, 0);
    }
}
