package com.kroy.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class BarManagerTests {

    Map mapTest;

    @Before
    public void init(){
        //Mock the opengl classes using mockito so that libgdx opengl functions can be used
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl30 = Mockito.mock(GL30.class);

        Assets.loadGameAssets();
        String directory = Gdx.files.getLocalStoragePath() + "assets/Data/MapForTesting.csv";
        mapTest = new Map(directory);
    }

    @Test
    public void testConstructor(){
        Human humanData = new Human(true, 5);
        Enemy enemyData = new Enemy(false, 5, 5);
        BarManager barManager = new BarManager(humanData, enemyData);

        assertEquals(humanData, barManager.getHumanData());
        assertEquals(enemyData, barManager.getEnemyData());
    }

    @Test
    public void testColourOfHealthBar(){
        FireEngine testFireEngine = new FireEngine(100, 10, 10, null, 10, 100, "fireEngineSprite.png");
        testFireEngine.setTexture(new Texture(Gdx.files.internal("fireEngineSprite.png")));

        Human humanData = new Human(true, 5);
        Enemy enemyData = new Enemy(false, 5, 5);
        BarManager barManager = new BarManager(humanData, enemyData);

        //Tests that when at full health, color of health bar is green
        testFireEngine.setHealth(testFireEngine.getMaxHealth());
        barManager.getColourOfBar(testFireEngine);
        assertEquals(Color.GREEN, barManager.getShapeRenderer().getColor());

        //Tests that when at half health, color of health bar is yellow
        testFireEngine.setHealth(testFireEngine.getMaxHealth() / 2);
        barManager.getColourOfBar(testFireEngine);
        assertEquals(Color.YELLOW, barManager.getShapeRenderer().getColor());

        //Tests that when at 1/4 health, color of health bar is red
        testFireEngine.setHealth(testFireEngine.getMaxHealth() / 4);
        barManager.getColourOfBar(testFireEngine);
        assertEquals(Color.RED, barManager.getShapeRenderer().getColor());
    }
}
