package nz.ac.aut.ense701.gameModel;

import org.junit.Test;

/**
 * The test class IslandTest.
 *
 * @author  AS
 * @version July 2011
 */
public class IslandTest extends junit.framework.TestCase
{
    Island testIsland;
    Position onIsland;
    Position notOnIsland;
    Predator cat; 
    /**
     * Default constructor for test class IslandTest
     */
    public IslandTest()
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
        testIsland = new Island(6,5);
        onIsland = new Position(testIsland, 1,0); 
        notOnIsland = Position.NOT_ON_ISLAND;
        cat = new Predator(onIsland, "cat", "A hunting cat");
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @Override
    protected void tearDown()
    {
      testIsland  = null;
    }
     
    /**
     * Tests of methods which are wrappers for GridSqaure or Position are omitted as 
     * methods are tested in those test classes
     */
    @Test
    public void testGetNumRows() {
        assertEquals(6, testIsland.getNumRows());
    }  
    
    @Test
    public void testGetNumColumns() {
        assertEquals(5, testIsland.getNumColumns());
    }
    
    @Test
    public void testHasPredatorNoPredator(){
        Kiwi kiwi = new Kiwi(onIsland, "Kiwi", "Little spotted kiwi");
        testIsland.addOccupant(onIsland, kiwi);
        assertFalse(testIsland.hasPredator(onIsland));
    }
    
    @Test
    public void testHasPredatorWithPredator(){
        testIsland.addOccupant(onIsland, cat);
        assertTrue(testIsland.hasPredator(onIsland));
    }
    
    @Test
    public void testAddOccupantOnIslandValidOccupant() {
        assertTrue(testIsland.addOccupant(onIsland, cat));
        assertTrue( testIsland.hasOccupant(onIsland, cat));
    }
    
    @Test
    public void testAddOccupantNotOnIsland() {
        assertFalse(testIsland.addOccupant(notOnIsland, cat));      
    } 
    
    @Test
    public void testAddOccupantNull() {
        assertFalse( testIsland.addOccupant(onIsland, null));      
    }

    @Test
    public void testRemoveOccupantOnIslandValidOccupant() {
        assertTrue(testIsland.addOccupant(onIsland, cat));
        assertTrue( testIsland.hasOccupant(onIsland, cat));
        assertTrue(testIsland.removeOccupant(onIsland, cat));
        assertFalse(cat.getPosition().isOnIsland());
    }

    @Test
    public void testRemoveOccupantPositionNotOnIsland() {
        assertTrue(testIsland.addOccupant(onIsland, cat));
        assertFalse(testIsland.removeOccupant(notOnIsland, cat));
    }

    @Test
    public void testRemoveOccupantNotAtPosition() {
        Position another = new Position(testIsland, 0,0);
        Predator rat = new Predator(another, "Rat", "A norway rat");
        assertFalse( testIsland.removeOccupant(onIsland, rat));
    }
    
    @Test
    public void testUpdatePlayerPosition(){
        Position newPos = new Position(testIsland, 2,3);
        assertFalse(testIsland.isExplored(newPos));
        Player player = new Player(newPos ,"Ada Lovelace",25.0, 15.0, 20.0);
        player.moveToPosition(newPos, Terrain.SAND);
        
        testIsland.updatePlayerPosition(player);
        //new position should now be explored
        assertTrue("Should be explored.", testIsland.isExplored(newPos));
        //Surrounding positions should be visible
        //North
        Position north = new Position(testIsland,1,3);
        assertTrue("Should be visible.", testIsland.isVisible(north));
        
        //South
        Position south = new Position(testIsland,3,3);
        assertTrue("Should be visible.", testIsland.isVisible(south));

        //East
        Position east = new Position(testIsland,2,2);
        assertTrue("Should be visible.", testIsland.isVisible(east));
        
        //West
        Position west = new Position(testIsland,2,4);
        assertTrue("Should be visible.", testIsland.isVisible(west));
    }
    
    @Test
    public void testGetPredator(){
        testIsland.addOccupant(onIsland, cat);
        assertEquals(testIsland.getPredator(onIsland), cat);
    }


}
