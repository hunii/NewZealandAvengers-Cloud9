package nz.ac.aut.ense701.gameModel;

import org.junit.Test;

/**
 * The test class ToolTest.
 *
 * @author  AS
 * @version 2011
 */

public class ToolTest extends junit.framework.TestCase
{
    Tool trap;
    Position position;
    Island island;
/**
     * Default constructor for test class ToolTest
     */
    public ToolTest()
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
        position = new Position(island, 2,3);
        trap = new Tool(position, "Trap", "A predator trap", 2.0, 3.0);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @Override
    protected void tearDown()
    {
        trap = null;
        island = null;
        position = null;
    }
    
    @Test
    public void testIsBrokenandBreak(){
        assertFalse("Should not be broken", trap.isBroken());
        trap.setBroken();
        assertTrue("Should  be broken", trap.isBroken());
    }
    
    @Test
    public void testIsTrap(){
        assertTrue("Should  be trap", trap.isTrap());
    }
    
    @Test
    public void testIsScrewdriver(){
        Tool screwdriver = new Tool(position, "Screwdriver", "A useful screwdriver", 2.0, 3.0);
        assertTrue("Should  be screwdriver", screwdriver.isScrewdriver());
    }
    
    @Test
    public void testFix(){
        trap.setBroken();
        assertTrue("Should  be broken", trap.isBroken());
        trap.fix();
        assertFalse("Should  not be broken", trap.isBroken());
    }
    
    /**
     * Test of toString method, of class Item for this descendant.
     */
    @Test
    public void testGetStringRepresentation() {
        assertEquals("T",trap.getStringRepresentation());
    }
}
