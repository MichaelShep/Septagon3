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
import sun.jvm.hotspot.utilities.ConstantTag;

import static org.junit.Assert.*;

/**
 * Class to test all of the bullet functionality
 */
@RunWith(GdxTestRunner.class)
public class BulletTests {

    FireEngine testFireEngine;
    Fortress testFortress;

    @Before
    public void init(){
        testFireEngine = new FireEngine(100, 10, 10, new Tile(), 10, 100, "fireEngineSprite.png");
        testFireEngine.setTexture(new Texture(Gdx.files.internal("fireEngineSprite.png")));

        testFortress = new Fortress(100, 10, 10, new Tile(), "Test", "fireEngineSprite.png");
        testFireEngine.setTexture(new Texture(Gdx.files.internal("fireEngineSprite.png")));

        //Mock the opengl classes using mockito so that libgdx opengl functions can be used
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl30 = Mockito.mock(GL30.class);
    }

    @Test
    /**
     * Tests that the bullets constructor correctly assigns values to class variables and sets up functionality
     */
    public void testConstructor(){
        Bullet testBullet = new Bullet(testFireEngine, testFortress, true);

        //Test values assigned in the constructor are correct
        assertEquals(testFireEngine, testBullet.getAttacker());
        assertEquals(testFortress, testBullet.getTarget());

        //Test values calculated in constructor are correct
        float fireEngineXCoordinate = testFireEngine.getLocation().getMapX() * Constants.getTileSize();
        float fireEngineYCoordinate = testFireEngine.getLocation().getMapY() * Constants.getTileSize();
        float fortressXCoordinate = testFortress.getLocation().getMapX() * Constants.getTileSize();
        float fortressYCoordinate = testFortress.getLocation().getMapY() * Constants.getTileSize();

        assertEquals(fireEngineXCoordinate, testBullet.getxPosition(), 0);
        assertEquals(fireEngineYCoordinate, testBullet.getyPosition(), 0);
        assertEquals(fortressXCoordinate, testBullet.getTargetX(), 0);
        assertEquals(fortressYCoordinate, testBullet.getTargetY(), 0);
    }

    @Test
    /**
     * Tests that the bullets update method executes correctly
     */
    public void testUpdate(){
        Bullet testBullet = new Bullet(testFireEngine, testFortress, true);

        //Set the values of the shift x and y to be used for testing (arbitrary values)
        testBullet.setShiftX(20);
        testBullet.setShiftY(20);

        //Calls the update method (deltaTime value assigned 0 just for testing)
        testBullet.update(0);

        //Test whether calculated values are assigned correctly
        float fireEngineCentreX = testFireEngine.getLocation().getMapX() * Constants.getTileSize() + (Constants.getTileSize() / 2) + testBullet.getShiftX();
        float fireEngineCentreY = testFireEngine.getLocation().getMapY() * Constants.getTileSize() + (Constants.getTileSize() / 2) + testBullet.getShiftY();
        float fortressCentreX = testFortress.getLocation().getMapX() * Constants.getTileSize() + (Constants.getTileSize() / 2) + testBullet.getShiftX();
        float fortressCentreY = testFortress.getLocation().getMapY() * Constants.getTileSize() + (Constants.getTileSize() / 2) + testBullet.getShiftY();

        assertEquals(testBullet.getUpdateXPosition(), fireEngineCentreX, 0);
        assertEquals(testBullet.getUpdateYPosition(), fireEngineCentreY, 0);
        assertEquals(testBullet.getTargetX(), fortressCentreX, 0);
        assertEquals(testBullet.getTargetY(), fortressCentreY, 0);
    }

}
