package Tests;

import static org.junit.Assert.*;

import com.kroy.game.TileType;
import org.junit.Test;

import com.kroy.game.Map;
import com.kroy.game.Tile;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MapTests
{

    Map mapTest = new Map("MapForTesting");
    Class ReflectionClass = Map.class;
    String directory = System.getProperty("user.dir") + "\\core\\src\\Data\\" + "MapForTesting";

    @Test
    public void loadCSVTest()
    {
        int[][] expectedTileType = {{3,1,3,3},{1,1,1,1},{3,1,3,3},{3,1,1,1}};
        ArrayList<String[]> actualTyleTypes = new ArrayList<String[]>();

        try
        {
            Method readMapCSVReflection = ReflectionClass.getDeclaredMethod("readMapCSV");
            readMapCSVReflection.setAccessible(true);

            Object returnedValue = readMapCSVReflection.invoke(directory,ReflectionClass);
            actualTyleTypes = (ArrayList<String[]>) returnedValue;
        }
        catch (NoSuchMethodException e)
        {

        }
        catch (IllegalAccessException e)
        {

        }
        catch (InvocationTargetException e)
        {

        }

        assertEquals(expectedTileType, actualTyleTypes);
    }


    @Test
    public void genertaeMapTest()
    {
        int expectedX = 1;
        int expectedY = 0;
        String expectedTexture = "roadTile.png";
        TileType expectedTileType = TileType.TILE_TYPES_ROAD;


        try
        {
            Field mapDataReflection = ReflectionClass.getDeclaredField("mapData");
            mapDataReflection.setAccessible(true);

            Object tileData = mapDataReflection.get(mapTest);
            Tile[][] temp = (Tile[][])tileData;
            Tile testTile = temp[0][1];

            assertEquals(expectedX, testTile.getMapX());
            assertEquals(expectedY, testTile.getMapY());
            assertEquals(expectedTexture, testTile.getTexName());
            assertEquals(expectedTileType, testTile.getType());

        }
        catch (NoSuchFieldException e)
        {

        }
        catch (IllegalAccessException e)
        {

        }

    }


}