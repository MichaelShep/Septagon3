package Tests;

import com.kroy.game.Map;
import com.kroy.game.Tile;
import com.kroy.game.TileType;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MapTests {

    Map mapTest = new Map("MapForTesting");
    Class ReflectionClass = Map.class;
    String directory = System.getProperty("user.dir") + "\\core\\src\\Data\\" + "MapForTesting";

    @Test
    public void loadCSVTest() {

        int[][] expectedTileType = {{3, 1, 3, 3}, {1, 1, 1, 1}, {3, 1, 3, 3}, {3, 1, 1, 1}};
        ArrayList<String[]> actualTyleTypes = new ArrayList<String[]>();

        try {
            Method readMapCSVReflection = ReflectionClass.getDeclaredMethod("readMapCSV");
            readMapCSVReflection.setAccessible(true);

            Object returnedValue = readMapCSVReflection.invoke(directory, ReflectionClass);
            actualTyleTypes = (ArrayList<String[]>) returnedValue;
        } catch (NoSuchMethodException e) {

        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {

        }
        assertEquals(expectedTileType, actualTyleTypes);
    }


    @Test
    public void genertaeMapTest() {
        int expectedX = 1;
        int expectedY = 0;
        String expectedTexture = "roadTile.png";
        TileType expectedTileType = TileType.TILE_TYPES_ROAD;


        try {
            Field mapDataReflection = ReflectionClass.getDeclaredField("mapData");
            mapDataReflection.setAccessible(true);

            Object tileData = mapDataReflection.get(mapTest);
            Tile[][] temp = (Tile[][]) tileData;
            Tile testTile = temp[0][1];

            assertEquals(expectedX, testTile.getMapX());
            assertEquals(expectedY, testTile.getMapY());
            assertEquals(expectedTexture, testTile.getTexName());
            assertEquals(expectedTileType, testTile.getType());

        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }

    }

    @Test
    public void mapRoadTileTest() {
        Map mapRoadTest = new Map("MapForRoadTest.csv");

        try {
            Field mapDataReflection = ReflectionClass.getDeclaredField("mapData");
            mapDataReflection.setAccessible(true);

            Object tileData = mapDataReflection.get(mapRoadTest);
            Tile[][] temp = (Tile[][]) tileData;
            Tile fourWayRoad = temp[1][1];

            Tile RoadThreeWayNoUp = temp[0][9];
            Tile RoadThreeWayNoRight = temp[1][6];
            Tile RoadThreeWayNoDown = temp[2][9];
            Tile RoadThreeWayNoLeft = temp[1][4];

            Tile RoadTwoWayRightUp = temp[2][0];
            Tile RoadTwoWayLeftUp = temp[1][2];
            Tile RoadTwoWayRightDown = temp[0][1];
            Tile RoadTwoWayLeftDown = temp[0][2];

            Tile RoadVertical = temp[1][5];
            Tile RoadHorizontal = temp[1][9];

            assertEquals("fourWayRoad.csv", fourWayRoad.getTexName());

            assertEquals("RoadThreeWayNoUp.csv", RoadThreeWayNoUp.getTexName());
            assertEquals("RoadThreeWayNoRight.csv", RoadThreeWayNoRight.getTexName());
            assertEquals("RoadThreeWayNoDown.csv", RoadThreeWayNoDown.getTexName());
            assertEquals("RoadThreeWayNoLeft.csv", RoadThreeWayNoLeft.getTexName());

            assertEquals("RoadTwoWayRightUp.csv", RoadTwoWayRightUp.getTexName());
            assertEquals("RoadTwoWayLeftUp.csv", RoadTwoWayLeftUp.getTexName());
            assertEquals("RoadTwoWayRightDown.csv", RoadTwoWayRightDown.getTexName());
            assertEquals("RoadTwoWayLeftDown.csv", RoadTwoWayLeftDown.getTexName());

            assertEquals("RoadVertical.csv", RoadVertical.getTexName());
            assertEquals("RoadHorizontal.csv", RoadHorizontal.getTexName());

        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }
    }

    @Test
    public void distanceBetweenTest() {
        Tile testTile1 = new Tile(1, 1);
        Tile testTile2 = new Tile(4, 5);

        double expectedDistance = 5;
        double actualDistance;

        try {
            Method distanceBetweenReflection = ReflectionClass.getDeclaredMethod("distanceBetween");
            distanceBetweenReflection.setAccessible(true);

            Object returnedValue = distanceBetweenReflection.invoke(directory, ReflectionClass);
            actualDistance = (double) returnedValue;

            assertEquals(expectedDistance, actualDistance);
        } catch (NoSuchMethodException e) {

        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {

        }
    }

/*

    @Test
    public void getNClosestTest()
    {
        Tile tile1 = new Tile();
        Tile[] expectedClosest = {tile1};

        Tile hub = new Tile(1,1);
        //Tile[] actualClosest = mapTest.getNClosest(1, hub);

        //assertEquals(expectedClosest, actualClosest);
    }

 */

}