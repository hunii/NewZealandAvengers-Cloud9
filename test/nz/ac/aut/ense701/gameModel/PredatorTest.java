
package nz.ac.aut.ense701.gameModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AS
 * @version 2011
 */
public class PredatorTest {
    private Predator rat;
    private Position position;
    private Island island;
    
    
    public PredatorTest() {
    }
    
    @Before
    public void setUp() {
        island = new Island(5,5);
        position = new Position(island, 4,4);
        rat = new Predator(position, "Rat", "A norway rat");   
    }
    
    @After
    public void tearDown() {
        island = null;
        position = null;
        rat = null;   
    }

    /**
     * Test of getStringRepresentation method, of class Predator.
     */
    @Test
    public void testGetStringRepresentation() {
        String expResult = "P";
        String result = rat.getStringRepresentation();
        assertEquals(expResult, result);
    }
    
}
