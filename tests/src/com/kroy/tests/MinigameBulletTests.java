package com.kroy.tests;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kroy.game.Constants;
import com.kroy.game.FireEngine;
import com.kroy.game.Tile;
import com.kroy.game.minigameHelpers.MinigameBullet;
import com.kroy.game.minigameHelpers.MinigameEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class MinigameBulletTests
{
    //Variables used to help perform testing
    FireEngine testFireEngine;
    MinigameEngine testMinigameEngine;

    @Before
    /**
     * Function to set up values that will be used for testing
     */
    public void init(){
        testFireEngine = new FireEngine(100, 10, 10, new Tile(), 10, 100);
        Texture engineTexture = new Texture(Gdx.files.internal("fireEngineSprite.png"));
        testFireEngine.setTexture(engineTexture);

        //Mock the opengl classes using mockito so that libgdx opengl functions can be used
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl30 = Mockito.mock(GL30.class);

        testMinigameEngine = new MinigameEngine(testFireEngine);
        testMinigameEngine.init();
    }

    @Test
    /**
     * Tests that the bullet constructor correctly assigns variables
     */
    public void testConstructor(){
        MinigameBullet bullet = new MinigameBullet(testMinigameEngine, false);

        assertEquals(bullet.isUp(), false);
        assertEquals(bullet.getSprite(), testMinigameEngine);
    }

    @Test
    /**
     * Tests whether firing the bullet correctly updates its position and sets it to be fired
     */
    public void testFire(){
        MinigameBullet bullet = new MinigameBullet(testMinigameEngine, false);

        bullet.fire();

        //Checks that the bullets position has been set to its sprites position
        assertEquals(bullet.getX(), testMinigameEngine.getX() + testMinigameEngine.getWidth() / 2, 0);
        assertEquals(bullet.getY(), testMinigameEngine.getY() + testMinigameEngine.getHeight() / 2, 0);

        //Checks that the bullet now recognises that it has been fired
        assertEquals(bullet.isHasFired(), true);
    }

    @Test
    /**
     * Tests whether the bullets positions are correctly updated by the update function
     */
    public void testUpdate(){
        MinigameBullet bullet = new MinigameBullet(testMinigameEngine, true);

        bullet.update();

        //Checks that if the bullet has not fired, it is just stored at engines position
        assertEquals(bullet.getX(), testMinigameEngine.getX() + testMinigameEngine.getWidth() / 2, 0);
        assertEquals(bullet.getY(), testMinigameEngine.getY() + testMinigameEngine.getHeight() / 2, 0);

        bullet.fire();
        bullet.update();

        //Tests that when the bullet has been fired, the update function is correctly moving the bullet by
        //the value of velY
        assertEquals(bullet.getY(), testMinigameEngine.getY() + (testMinigameEngine.getHeight() / 2) + bullet.getVelY(), 0);

        bullet.setY(Constants.getResolutionHeight());
        bullet.update();

        //Checks that if the bullet goes off screen, the update method sets it back to the position of its sprite
        //and resets hasFired back to false
        assertEquals(bullet.getX(), testMinigameEngine.getX() + testMinigameEngine.getWidth() / 2, 0);
        assertEquals(bullet.getY(), testMinigameEngine.getY() + testMinigameEngine.getHeight() / 2, 0);
        assertEquals(bullet.isHasFired(), false);
    }
}
