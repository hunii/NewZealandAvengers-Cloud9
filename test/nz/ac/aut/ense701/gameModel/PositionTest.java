package nz.ac.aut.ense701.gameModel;

import org.junit.Test;

/**
 * The test class PositionTest.
 *
 * @author AS
 * @version 2011
 */
public class PositionTest extends junit.framework.TestCase
{
    Position onIsland;
    Position notOnIsland;
    Island island;
    /**
     * Default constructor for test class PositionTest
     */
    public PositionTest()
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
        onIsland = new Position(island, 1,2) ;
        notOnIsland = Position.NOT_ON_ISLAND;
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @Override
    protected void tearDown()
    {
        island = null;
        onIsland = null;
        notOnIsland = null;       
    }

    @Test
    public void testPositionValidParametersOnIsland()
    {
        assertTrue(onIsland.isOnIsland());
    }

    @Test
    public void testPositionValidParameterNotOnIsland()
    {
        assertFalse(notOnIsland.isOnIsland());
    }

    @Test
    public void testIllegalArgumentNoIsland() throws Exception {
        try 
        {
            Position invalidPosition = new Position(null,0,0);
            fail("No exception thrown when island null.");
        }
        catch (IllegalArgumentException expected) 
        {
            assertTrue("Not expected exception message", expected.getMessage().contains("Island"));
        }
        
    }

    @Test
    public void testIllegalArgumentRowNegative() throws Exception {
        try 
        {
            Position invalidPosition = new Position(island,-1,0);
            fail("No exception thrown when row negative.");
        }
        catch (IllegalArgumentException expected) 
        {
            assertTrue("Not expected exception message", expected.getMessage().contains("Invalid row"));
        }
        
    }
    
    @Test
    public void testIllegalArgumentRowTooLarge() throws Exception {
        try 
        {
            Position invalidPosition = new Position(island,5,0);
            fail("No exception thrown when row too large.");
        }
        catch (IllegalArgumentException expected) 
        {
            assertTrue("Not expected exception message", expected.getMessage().contains("Invalid row"));
        }
        
    } 
    
    @Test
    public void testIllegalArgumentColumnNegative() throws Exception {
        try 
        {
            Position invalidPosition = new Position(island,1,-1);
            fail("No exception thrown when column negative.");
        }
        catch (IllegalArgumentException expected) 
        {
            assertTrue("Not expected exception message", expected.getMessage().contains("Invalid column"));
        }
        
    }
    
    @Test
    public void testIllegalArgumentColumnTooLarge() throws Exception {
        try 
        {
            Position invalidPosition = new Position(island,0,5);
            fail("No exception thrown when column too large.");
        }
        catch (IllegalArgumentException expected) 
        {
            assertTrue("Not expected exception message", expected.getMessage().contains("Invalid column"));
        }
        
    }
    
    @Test
    public void testGetColumn()
    {
        assertEquals(2, onIsland.getColumn());
    }    

    @Test
    public void testGetRow()
    {
        assertEquals(1, onIsland.getRow());
    } 

    @Test
    public void testRemoveFromIsland()
    {
        onIsland.removeFromIsland();
        assertFalse(onIsland.isOnIsland());
    }
    
    @Test
    public void testGetNewPositionNull()throws Exception {
        try 
        {
            Position newPosition = onIsland.getNewPosition(null);
            fail("No exception thrown when direction null.");
        }
        catch (IllegalArgumentException expected) 
        {
            assertTrue("Not expected exception message", expected.getMessage().contains("Direction parameter"));
        }    
    }
    
    @Test
    public void testGetNewPositionNotOnIsland() {
        assertEquals(notOnIsland.getNewPosition(MoveDirection.NORTH), null);
    }
    
    @Test
    public void testGetNewPositionValidDirection() {
        Position newPosition = onIsland.getNewPosition(MoveDirection.WEST);
        assertEquals(newPosition.getRow(), 1);
        assertEquals(newPosition.getColumn(), 1);
    }

}
