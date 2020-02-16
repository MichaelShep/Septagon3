package com.kroy.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.kroy.game.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RunWith(GdxTestRunner.class)
public class HumanTests {
    Human testHuman;

    @Before
    public void init(){
        testHuman = new Human (false, 3);
    }

    @Test
    public void testConstructor(){
        testHuman = new Human(false, 3);
        assertFalse(testHuman.isMyTurn());
        assertEquals(3, testHuman.getAliveCharacters());
        testHuman = new Human(true, 1);
        assertTrue(testHuman.isMyTurn());
        assertEquals(1, testHuman.getAliveCharacters());
    }

    @Test
    public void testCreateFireEngine(){
        FireEngine testEngine = testHuman.createFireEngine(0);
        assertEquals(16, testEngine.getMaxHealth());
        assertEquals(5, testEngine.getDamage());
        assertEquals(4, testEngine.getRange());
        assertEquals(4, testEngine.getSpeed());
        assertEquals(4, testEngine.getWaterCapacity());

        Human testHuman = new Human(false, 3);
    }
}
