package com.kroy.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.kroy.game.Constants;
import com.kroy.game.FireEngine;
import com.kroy.game.Patrol;
import com.kroy.game.Tile;
import com.kroy.game.minigameHelpers.Alien;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class AlienTests
{
    FireEngine testFireEngine;
    Patrol testPatrol;
    Texture alienTexture;

    @Before
    /**
     * Function to set up values that will be used for testing
     */
    public void init(){
        testFireEngine = new FireEngine(100, 10, 10, new Tile(), 10, 100);
        Texture engineTexture = new Texture(Gdx.files.internal("fireEngineSprite.png"));
        testFireEngine.setTexture(engineTexture);

        testPatrol = new Patrol(100, 10, 10, new Tile());
        alienTexture = new Texture(Gdx.files.internal("MiniGameTexture/ethan.png"));

        //Mock the opengl classes using mockito so that libgdx opengl functions can be used
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl30 = Mockito.mock(GL30.class);
    }

    @Test
    /**
     * Tests that the alien constructor correctly sets up its values
     */
    public void testConstructor(){
        Alien alien = new Alien(100, new Rectangle(100, 100, 100, 100), testPatrol, alienTexture);

        assertEquals(alien.getHealth(), 100, 0);
        assertEquals(alien.getPlayableArea().x, 100, 0);
        assertEquals(alien.getPassedPatrol(), testPatrol);
        assertEquals(alien.getTexture(), alienTexture);
    }

    @Test
    /**
     * Tests that the initialise function correct assigns init values to class variables
     */
    public void testInit(){
        Alien alien = new Alien(100, new Rectangle(100, 100, 100, 100), testPatrol, alienTexture);

        alien.init();

        assertEquals(alien.getWidth(), Constants.getTileSize(), 0);
        assertEquals(alien.getHeight(), Constants.getTileSize(), 0);
        assertEquals(alien.getMovementSpeed(), Constants.getMinigameBaseAlienMovementSpeed() * testPatrol.getRange() * 2, 0);
    }

    @Test
    /**
     * Checks that the alien moves in the correct direction and by the correct amount when the move method is called
     */
    public void testMove(){
        Alien alien = new Alien(100, new Rectangle(100, 100, 100, 100), testPatrol, alienTexture);

        //Required call to init to set up values so that they can be used in the move method
        alien.init();

        float originalPosition = alien.getX();
        alien.move();

        //Checks that the alien correctly moves right originally (as left starts as set to false)
        assertEquals(alien.getX(), originalPosition + Constants.getMinigameBaseAlienMovementSpeed(), 0);

        alien.setLeft(true);
        alien.move();

        //Tests that if the alien is set to move left after already moving right, will move back to its original position
        assertEquals(alien.getX(), originalPosition, 0);
    }

    @Test
    /**
     * Tests that fire() correctly fires the aliens bullet
     */
    public void testFire(){
        Alien alien = new Alien(100, new Rectangle(100, 100, 100, 100), testPatrol, alienTexture);

        //Required call to init() to set up values
        alien.init();

        alien.fire();

        //Checks that the bullet is fired once the aliens fire method has been called
        assertEquals(alien.getBullet().isHasFired(), true);
    }

    @Test
    /**
     * Checks that the alien will set shouldMoveDown to true if it hits the edge of the playableArea
     */
    public void testCheckBounds(){
        Alien alien = new Alien(100, new Rectangle(100, 100, 100, 100), testPatrol, alienTexture);

        //Required call to init() to set up values
        alien.init();

        //Set the alien to the left of the playableArea
        alien.setX(alien.getPlayableArea().getX() + Constants.getMinigameEdgeBuffer());
        alien.checkBounds();

        assertEquals(alien.isShouldMoveDown(), true);

        //Reset shouldMoveDown and then put the alien in the middle of the screen, so shouldn't move down
        alien.setShouldMoveDown(false);
        alien.setX(alien.getPlayableArea().x + Constants.getMinigameEdgeBuffer() + 5);
        alien.checkBounds();

        assertEquals(alien.isShouldMoveDown(), false);
    }
}
