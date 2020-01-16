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
    public void loadCSVTest() {

        String[][] expectedTileType = {{"3", "1", "3", "5"}, {"1", "1", "1", "1"}, {"3", "1", "3", "4"}, {"3", "1", "1", "6"}};
        try{
            ArrayList<String[]> actualTyleTypes = mapTest.readMapCSV(directory);
            assertEquals(expectedTileType, actualTyleTypes.toArray());
        }
        catch (IOException e){
            fail();
        }
    }


    @Test(expected = FileNotFoundException.class)
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
    public void genertaeMapTest() {
        int[] expectedX = {1,3,3};
        int[] expectedY = {3,3,0};
        String[] expectedTexture = {"RoadTwoWayRightDown.png","stationTile.png","lavaTile.png"};
        TileType[] expectedTileType = {TileType.TILE_TYPES_ROAD,TileType.TILE_TYPES_STATION,TileType.TILE_TYPES_FORTRESS};


        try {
            int numTestTiles = 3;
            Field mapDataReflection = ReflectionClass.getDeclaredField("mapData");
            mapDataReflection.setAccessible(true);

            Object tileData = mapDataReflection.get(mapTest);
            Tile[][] temp = (Tile[][]) tileData;

            Tile tile0 = temp[3][1];
            Tile tile1 = temp[3][3];
            Tile tile2 = temp[0][3];
            Tile[] tiles = {tile0,tile1,tile2};

            for (int i = 0; i <= numTestTiles - 1 ; i++){
                assertEquals(expectedX[i], tiles[i].getMapX());
                assertEquals(expectedY[i], tiles[i].getMapY());
                assertEquals(expectedTexture[i], tiles[i].getTexName());
                assertEquals(expectedTileType[i], tiles[i].getType());

            }

        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }

    }


    @Test
    public void mapRoadTileTest() {
        String directory = System.getProperty("user.dir").replace("\\", "/") + "/assets/Data/" + "MapForRoadTest.csv";
        Map mapRoadTest = new Map(directory);

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

            //assertEquals(expectedDistance, actualDistance);
        } catch (NoSuchMethodException e) {

        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {

        }
    }



    @Test
    public void testGetNClosest()
    {

        try {
            Field mapDataReflection = ReflectionClass.getDeclaredField("mapData");
            mapDataReflection.setAccessible(true);

            Object tileData = mapDataReflection.get(mapTest);
            Tile[][] temp = (Tile[][]) tileData;
            Tile testTile = temp[1][1];

            Tile tile2 = temp[1][0];
            Tile tile1 = temp[0][1];
            Tile tile3 = temp[1][2];
            Tile tile4 = temp[2][1];
            Tile[] expectedClosest = {tile1,tile2,tile3,tile4};


            Tile[] actualClosest = mapTest.getNClosest(4, testTile);

            assertEquals(expectedClosest, actualClosest);


        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }
    }

     @Test(expected = Exception.class)
    public void testGetNClosestLargeNum()
    {

        try {
            Field mapDataReflection = ReflectionClass.getDeclaredField("mapData");
            mapDataReflection.setAccessible(true);

            Object tileData = mapDataReflection.get(mapTest);
            Tile[][] temp = (Tile[][]) tileData;
            Tile testTile = temp[1][1];

            Tile tile1 = temp[1][0];
            Tile tile2 = temp[0][1];
            Tile tile3 = temp[2][1];
            Tile tile4 = temp[2][1];
            Tile[] expectedClosest = {tile1,tile2,tile3,tile4};


            Tile[] actualClosest = mapTest.getNClosest(1000, null);

            assertEquals(expectedClosest, actualClosest);


        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }

    }

    @Test
    public void testGetNClosestTileType()
    {
        {
            TileType testTileType = TileType.TILE_TYPES_GRASS;
            try {
                Field mapDataReflection = ReflectionClass.getDeclaredField("mapData");
                mapDataReflection.setAccessible(true);

                Object tileData = mapDataReflection.get(mapTest);
                Tile[][] temp = (Tile[][]) tileData;
                Tile testTile = temp[1][1];

                Tile tile1 = temp[0][0];
                Tile tile2 = temp[0][2];
                Tile tile3 = temp[2][0];
                Tile tile4 = temp[2][2];
                Tile[] expectedClosest = {tile1,tile2,tile3,tile4};


                Tile[] actualClosest = mapTest.getNClosest(4, testTile,testTileType);

                assertEquals(expectedClosest, actualClosest);


            } catch (NoSuchFieldException e) {

            } catch (IllegalAccessException e) {

            }
        }
    }




}
