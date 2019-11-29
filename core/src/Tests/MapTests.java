package Tests;

import static org.junit.Assert.*;
import org.junit.Test;

import com.kroy.game.Map;

import java.lang.reflect.Field;

public class MapTests 
{

	Map mapTest = new Map("MapForTesting");
	Class ReflectionClass = Map.class;

	@Test
	public void loadCSVTest()
	{
		int[][] expectedTileType = {{3,1,3,3},{1,1,1,1},{3,1,3,3},{3,1,1,1}};
		int[][] actualTyleTypes;



		try
		{
			Field mapDataReflection = ReflectionClass.getDeclaredField("mapData");
			mapDataReflection.setAccessible(true);

			actualTyleTypes = mapDataReflection.get(mapDataReflection);
		}
		catch (NoSuchFieldException e)
		{

		}
		catch (IllegalAccessException e)
		{

		}






	}
	
}
