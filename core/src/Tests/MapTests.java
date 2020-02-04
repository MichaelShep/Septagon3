package Tests;

import com.kroy.game.Map;
import com.kroy.game.Tile;
import com.kroy.game.TileType;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MapTests {


    Class ReflectionClass = Map.class;
    String directory = System.getProperty("user.dir").replace("\\", "/") + "/assets/Data/" + "MapForTesting.csv";
    Map mapTest = new Map(directory);

    @Test
    public void testLoadCSV() {

        String[][] expectedTileType = {{"3", "1", "3", "5"}, {"1", "1", "1", "1"}, {"3", "1", "3", "4"}, {"3", "1", "1", "6"}};
        try{
            ArrayList<String[]> actualTyleTypes = mapTest.readMapCSV(directory);
            assertEquals(expectedTileType, actualTyleTypes.toArray());
        }
        catch (IOException e){
            fail();
        }
    }


    @Test()
    public void testFileNotFoundExceptionLoadCSV(){
        String invalidDirectory = directory + "cat";
        try{
            ArrayList<String[]> actualTyleTypes = mapTest.readMapCSV(invalidDirectory);
            fail();
        }
        catch (IOException e){

        }
    }


    @Test
    public void testGenertaeMap() {
        int[] expectedX = {1,3,3};
        int[] expectedY = {3,3,0};
        String[] expectedTexture = {"RoadTwoWayRightDown.png","stationTile.png","lavaTile.png"};
        TileType[] expectedTileType = {TileType.TILE_TYPES_ROAD,TileType.TILE_TYPES_STATION,TileType.TILE_TYPES_FORTRESS};

        int numTestTiles = 3;

        Tile tile0 = mapTest.getMapData()[3][1];
        Tile tile1 = mapTest.getMapData()[3][3];
        Tile tile2 = mapTest.getMapData()[0][3];
        Tile[] tiles = {tile0,tile1,tile2};

        for (int i = 0; i <= numTestTiles - 1 ; i++){
            assertEquals(expectedX[i], tiles[i].getMapX());
            assertEquals(expectedY[i], tiles[i].getMapY());
            assertEquals(expectedTexture[i], tiles[i].getTexName());
            assertEquals(expectedTileType[i], tiles[i].getType());
        }

    }


    @Test
    public void testMapRoadTile() {
        String directory = System.getProperty("user.dir").replace("\\", "/") + "/assets/Data/" + "MapForRoadTest.csv";
        Map mapRoadTest = new Map(directory);

        Tile[][] testMapData = mapRoadTest.getMapData();
        Tile fourWayRoad = testMapData[1][1];

        Tile RoadThreeWayNoUp = testMapData[0][9];
        Tile RoadThreeWayNoRight = testMapData[1][6];
        Tile RoadThreeWayNoDown = testMapData[2][9];
        Tile RoadThreeWayNoLeft = testMapData[1][4];

        Tile RoadTwoWayRightUp = testMapData[2][0];
        Tile RoadTwoWayLeftUp = testMapData[1][2];
        Tile RoadTwoWayRightDown = testMapData[0][1];
        Tile RoadTwoWayLeftDown = testMapData[0][2];

        Tile RoadVertical = testMapData[1][5];
        Tile RoadHorizontal = testMapData[1][9];

        assertEquals("RoadFourWay.png", fourWayRoad.getTexName());

        assertEquals("RoadThreeWayNoDown.png", RoadThreeWayNoUp.getTexName());
        assertEquals("RoadThreeWayNoRight.png", RoadThreeWayNoRight.getTexName());
        assertEquals("RoadThreeWayNoUp.png", RoadThreeWayNoDown.getTexName());
        assertEquals("RoadThreeWayNoLeft.png", RoadThreeWayNoLeft.getTexName());

        assertEquals("RoadTwoWayRightDown.png", RoadTwoWayRightUp.getTexName());
        assertEquals("RoadTwoWayLeftDown.png", RoadTwoWayLeftUp.getTexName());
        assertEquals("RoadTwoWayRightUp.png", RoadTwoWayRightDown.getTexName());
        assertEquals("RoadTwoWayLeftUp.png", RoadTwoWayLeftDown.getTexName());

        assertEquals("RoadHorizontal.png", RoadVertical.getTexName());
        assertEquals("RoadVertical.png", RoadHorizontal.getTexName());
    }

    @Test
    public void testDistanceBetween() {
        Tile testTile1 = new Tile(1, 1);
        Tile testTile2 = new Tile(4, 5);

        double expectedDistance = 5;
        double actualDistance;

        try {
            Method distanceBetweenReflection = ReflectionClass.getDeclaredMethod("distanceBetween");
            distanceBetweenReflection.setAccessible(true);

            Object returnedValue = distanceBetweenReflection.invoke(directory, ReflectionClass);
            actualDistance = (double) returnedValue;

            //assertEquals(expectedDistance, actualDistance);
        } catch (NoSuchMethodException e) {

        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {

        }
    }



    @Test
    public void testGetNClosest()
    {

        Tile[][] testMapData = mapTest.getMapData();
        Tile testTile = testMapData[1][1];

        Tile tile2 = testMapData[1][0];
        Tile tile1 = testMapData[0][1];
        Tile tile3 = testMapData[1][2];
        Tile tile4 = testMapData[2][1];
        Tile[] expectedClosest = {tile1,tile2,tile3,tile4};


        Tile[] actualClosest = mapTest.getNClosest(4, testTile);

        assertEquals(expectedClosest, actualClosest);
    }

    @Test
    public void testGetNClosestTileType()
    {
        {
            TileType testTileType = TileType.TILE_TYPES_GRASS;
            Tile[][] testMapData = mapTest.getMapData();
            Tile testTile = testMapData[1][1];

            Tile tile1 = testMapData[0][0];
            Tile tile2 = testMapData[0][2];
            Tile tile3 = testMapData[2][0];
            Tile tile4 = testMapData[2][2];
            Tile[] expectedClosest = {tile1,tile2,tile3,tile4};


            Tile[] actualClosest = mapTest.getNClosest(4, testTile,testTileType);

            assertEquals(expectedClosest, actualClosest);
        }
    }

    @Test
    public void testGetWithRangeOfHub(){

        Tile[][] testMapData = mapTest.getMapData();
        Tile testTile = testMapData[1][1];

        Tile tile1 = testMapData[0][1];
        Tile tile2 = testMapData[1][0];
        Tile tile3 = testMapData[1][2];
        Tile tile4 = testMapData[2][1];
        Tile tile5 = testMapData[0][0];
        Tile tile6 = testMapData[0][2];
        Tile tile7 = testMapData[2][0];
        Tile tile8 = testMapData[2][2];
        Tile[] expectedWithRange = {tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8};

        ArrayList<Tile> actualWithRange = mapTest.getWithRangeOfHub(testTile,2);

        assertEquals(expectedWithRange, actualWithRange.toArray());
    }


    @Test
    public void testGetWithRangeOfHubTileType(){

        TileType tileType = TileType.TILE_TYPES_ROAD;

        Tile[][] testMapData = mapTest.getMapData();
        Tile testTile = testMapData[1][1];

        Tile tile1 = testMapData[0][1];
        Tile tile2 = testMapData[1][0];
        Tile tile3 = testMapData[1][2];
        Tile tile4 = testMapData[2][1];
        Tile[] expectedWithRange = {tile1, tile2, tile3, tile4};

        ArrayList<Tile> actualWithRange = mapTest.getWithRangeOfHub(testTile,2,tileType);

        assertEquals(expectedWithRange, actualWithRange.toArray());
    }

/*
     @Test(expected = Exception.class)
    public void testGetNClosestLargeNum()
    {

        Tile[] actualClosest = mapTest.getNClosest(100000, null);
    }


    @Test(expected = Exception.class)
    public void testGetNClosestTileTypeLargeNum()
    {
        {
            TileType testTileType = TileType.TILE_TYPES_GRASS;
            Tile[][] temp = mapTest.getMapData();
            Tile testTile = temp[1][1];

            Tile[] actualClosest = mapTest.getNClosest(100000, testTile,testTileType);
        }
    }
*/
}
