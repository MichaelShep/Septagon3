package Tests;

import com.kroy.game.Patrol;
import com.kroy.game.Tile;
import org.junit.Test;
import com.kroy.game.LibGdxTestMocker;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

@RunWith(LibGdxTestMocker.class)
public class PatrolTests { //Added by Septagon
    Tile testTile = new Tile();
    Patrol testPatrol = new Patrol(100, 10, 10, testTile, "PatrolSprite.png");
    Class ReflectionClass = Patrol.class;

    @Test
    public void testConstructor() {

        Patrol testPatrol = new Patrol(100, 10, 10, testTile, "PatrolSprite.png");
        assertEquals(testPatrol.getHealth(), 100);
        assertEquals(testPatrol.getDamage(), 10);
        assertEquals(testPatrol.getRange(), 10);
    }

    @Test
    public void testTransferTo() {
        Tile newLocation = new Tile(2,2);
        try {
            Method transferToReflection = ReflectionClass.getDeclaredMethod("transferTo");
            transferToReflection.setAccessible(true);
            transferToReflection.invoke(newLocation, transferToReflection);
            assertEquals(testTile.getInhabitant(),null);
            assertEquals(newLocation.getInhabitant(), testPatrol);

        } catch (NoSuchMethodException e) {

        } catch (InvocationTargetException e) {

        } catch (IllegalAccessException e) {

        }
    }

}
