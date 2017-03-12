package nz.ac.aut.ense701.gameModel;

import org.junit.Test;



/**
 * The test class HazardTest.
 *
 * @author  AS
 * @version 2011
 */
public class HazardTest extends junit.framework.TestCase
{
    Hazard fatal;
    Hazard nonFatal;
    Position position, position2;
    Island island;
    /**
     * Default constructor for test class AnimalTest
     */
    public HazardTest()
    {
    }

     /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Override
    protected void setUp()
    {
        island = new Island(5,5);
        position = new Position(island, 4,4);
        fatal = new Hazard(position, "Hole", "A very deep hole",  1.0);
        position2 = new Position(island, 1,3);
        nonFatal = new Hazard(position2, "Cliff", "A small cliff", 0.5);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @Override
    protected void tearDown()
    {
        fatal = null;
        nonFatal =  null;
        island = null;
        position = null;
    }

    @Test
    public void testGetImpact() {
        assertEquals(1.0, fatal.getImpact());
    }
    
    @Test
    public void testIsFatal()
    {
        assertTrue("Should be fatal", fatal.isFatal());
        assertFalse("Should not be fatal", nonFatal.isFatal());
    }
    
    @Test
    public void testIsBreakTrap(){
        Hazard trapBreak = new Hazard(position2, "Broken trap", "Your trap breaks", 0.0);
        
        assertTrue("Shoyuld be trap break hazard", trapBreak.isBreakTrap());
    }
            
    
    /**
     * Tests of Occupant methods
     * Need to test these in one of the descendants of abstract class Occupant
     */
    @Test
    public void testGetPosition(){
        assertEquals("Wrong position", position, fatal.getPosition());
    }
    
    @Test
    public void testSetPosition() {
        Position newPosition = new Position(island,1,2);
        fatal.setPosition(newPosition);
        assertEquals("Check position", newPosition, fatal.getPosition());
    }

    @Test
    public void testGetName() {
        assertEquals("Check name", "Hole", fatal.getName());
    }

    @Test
    public void testGetDescription()
    {
        assertEquals("Check description", "A very deep hole", fatal.getDescription());
    }

    @Test
    public void testGetStringRepresentationDangerous() {
        assertEquals("H",fatal.getStringRepresentation());
    }
    


}
