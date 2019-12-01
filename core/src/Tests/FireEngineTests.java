package Tests;

import static org.junit.Assert.*;

import com.kroy.game.Tile;
import org.junit.Test;
import com.kroy.game.FireEngine;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FireEngineTests
{
    Tile testTile = new Tile();
    FireEngine testFireEngine = new FireEngine(100,10,10, testTile,10,100,"fireEngineSprite.png");
    Class ReflectionClass = FireEngine.class;

    @Test
    public void testRefillAmount()
    {
        try
        {
            Field water = ReflectionClass.getDeclaredField("waterAmount");
            water.setAccessible(true);
            testFireEngine.refillAmount(50);
            assertEquals(50, water.get(testFireEngine));

            testFireEngine.refillAmount(5000);
            assertEquals(100, water.get(testFireEngine));
        }
        catch (NoSuchFieldException e)
        {

        }
        catch (IllegalAccessException e)
        {

        }


    }

    @Test
    public void testCanShoot()
    {

    }


    @Test
    public void testDeath()
    {
        testFireEngine.setHealth(0);
        assertTrue(testFireEngine.getDisabled());
    }

    @Test
    public void testTakeDamage()
    {
        try
        {
            testFireEngine.setHealth(100);
            Field healthReflection = ReflectionClass.getDeclaredField("health");
            Method takeDamageReflection = ReflectionClass.getDeclaredMethod("takeDamage");
            healthReflection.setAccessible(true);
            takeDamageReflection.setAccessible(true);
            takeDamageReflection.invoke(50,takeDamageReflection);
            assertEquals(50, healthReflection.get(testFireEngine));

            takeDamageReflection.invoke(5000,takeDamageReflection);
            assertEquals(0, healthReflection.get(testFireEngine));
        }
        catch (NoSuchFieldException e)
        {

        }
        catch (NoSuchMethodException e)
        {

        }
        catch (InvocationTargetException e)
        {

        }
        catch (IllegalAccessException e)
        {

        }
    }
}
