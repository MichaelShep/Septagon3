package com.kroy.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.kroy.game.Human;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class HumanTests {
    Human testHuman;

    @Before
    public void init(){
        //Mock the opengl classes using mockito so that libgdx opengl functions can be used
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl30 = Mockito.mock(GL30.class);

        testHuman = new Human(true, 4);
    }

    @Test
    /**
     * Tests the constructor of the Human class correctly assigns variables
     */
    public void testDefaultConstructor(){
        assertTrue(testHuman.isMyTurn());
        assertEquals(4, testHuman.getTeam().size());

    }
}
