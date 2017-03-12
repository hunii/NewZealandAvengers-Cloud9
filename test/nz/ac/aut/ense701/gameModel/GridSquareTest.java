package nz.ac.aut.ense701.gameModel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;


/**
 * The test class GridSquareTest.
 *
 * @author  AS
 * @version 2011
 */
public class GridSquareTest extends junit.framework.TestCase
{
    GridSquare emptySquare;
    GridSquare occupiedSquare;
    Island island;
    Position position;
    Food apple;

    /**
     * Default constructor for test class GridSquareTest
     */
    public GridSquareTest()
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
        emptySquare = new GridSquare(Terrain.SAND);
        occupiedSquare = new GridSquare(Terrain.FOREST);
        island = new Island(5,5);
        position = new Position(island, 0,0);
        apple = new Food(position, "apple", "A juicy red apple", 1.0, 2.0, 1.5);
        occupiedSquare.addOccupant(apple);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @Override
    protected void tearDown()
    {
        emptySquare = null;
        occupiedSquare = null;
        island = null;
        position = null;
        apple = null;
    }
    
    @Test
    public void testIsVisibleNewSquare() {
        assertFalse( emptySquare.isVisible());
    } 
    
    @Test
    public void testIsExploredNewSquare() {
        assertFalse( emptySquare.isExplored());
    }
    
    
    @Test
    public void testSetVisible() {
        emptySquare.setVisible();
        assertTrue( emptySquare.isVisible());
    } 
    
    @Test
    public void testsetExplored() {
        emptySquare.setExplored();
        assertTrue(emptySquare.isExplored());
    } 
    
    @Test   
    public void testGetTerrain() {
        assertEquals(Terrain.SAND, emptySquare.getTerrain());
    }
    
    @Test
    public void testSetTerrain() {
        emptySquare.setTerrain(Terrain.FOREST);
        assertEquals(Terrain.FOREST, emptySquare.getTerrain());
    }
    
    @Test
    public void testGetTerrainString(){
        assertEquals(emptySquare.getTerrainStringRepresentation(), ".");
    }
    @Test
    public void testHasPlayerNoPlayer() {
        assertFalse( emptySquare.hasPlayer());
    }
    
    @Test
    public void testHasPlayerWithPlayer() {
        Player player = new Player(position,"",25.0, 10.0, 12.0);
        occupiedSquare.setPlayer(player);
        assertTrue( occupiedSquare.hasPlayer());
    }  
    
    @Test
    public void testHasOccupantPresent(){
        assertTrue(occupiedSquare.hasOccupant(apple));
    }
    
    @Test
    public void testHasOccupantNotPresent(){
        assertFalse(emptySquare.hasOccupant(apple));
    } 
    
    @Test
    public void testGetOccupantStringRepresentationNoOccupants(){         
        assertEquals("",emptySquare.getOccupantStringRepresentation() );    
    }

    @Test
    public void testGetOccupantStringRepresentationSingle(){
        assertEquals("E",occupiedSquare.getOccupantStringRepresentation() );    
    }
    
    @Test
    public void testGetOccupantStringRepresentationMultipleOccupants(){
        // Add another occupant
        Tool trap = new Tool(position, "Trap", "A predator trap", 1.0, 2.0);
        occupiedSquare.addOccupant(trap); 
        // Add a third occupant
        Predator possum = new Predator(position, "Possum", "A log tailed possum"); 
        occupiedSquare.addOccupant(possum);          
        String stringRep = occupiedSquare.getOccupantStringRepresentation();
        assertEquals(3, stringRep.length());    
        assertTrue(stringRep.contains("E"));
        assertTrue(stringRep.contains("T"));
        assertTrue(stringRep.contains("P"));
    } 
    
    @Test
    public void testGetOccupants(){
        // Add another occupant
        Tool trap = new Tool(position, "Trap", "A predator trap", 1.0, 2.0);
        occupiedSquare.addOccupant(trap); 
        // Add a third occupant
        Predator possum = new Predator(position, "Possum", "A log tailed possum"); 
        occupiedSquare.addOccupant(possum);          
        Occupant[] occupants = occupiedSquare.getOccupants();
        assertEquals(3, occupants.length);    
        Set<Occupant> occupantSet = new HashSet<Occupant>( Arrays.asList(occupants));
        assertTrue(occupantSet.contains(trap));
        assertTrue(occupantSet.contains(apple));
        assertTrue(occupantSet.contains(possum));
    }
    
        
    @Test
    public void testAddOccupantWhenNotFull() {
        Tool trap = new Tool(position, "Trap", "A predator trap", 1.0, 2.0);        
        assertTrue(occupiedSquare.addOccupant(trap));        
        assertTrue(occupiedSquare.hasOccupant(trap));
    }
    
    @Test
    public void testAddOccupantNull() {
        assertFalse(occupiedSquare.addOccupant(null));
    }  
    
    @Test
    public void testAddOccupantWhenFull() {  
        // Add another occupant
        Tool trap = new Tool(position, "Trap", "A predator trap", 1.0, 2.0);
        occupiedSquare.addOccupant(trap); 
        // Add a third occupant
        Predator possum = new Predator(position, "Possum", "A log tailed possum"); 
        occupiedSquare.addOccupant(possum);        
        //Now the cave has three occupants it should not be possible to add another
        Predator rat = new Predator(position, "Rat", "A  ship rat"); 
        assertFalse(occupiedSquare.addOccupant(rat));
        assertFalse(occupiedSquare.hasOccupant(rat));
    } 
    
    @Test
    public void testAddOccupantDuplicate() {  
        // Attempt to add again
        assertFalse(occupiedSquare.addOccupant(apple));

    }
    
    @Test
    public void testRemoveOccupantWhenPresent() {
        assertTrue(occupiedSquare.removeOccupant(apple));
        assertFalse(occupiedSquare.hasOccupant(apple));
    }
    
    @Test
    public void testRemoveOccupantWhenNotPresent() {
        assertFalse(emptySquare.removeOccupant(apple));
    }

    @Test
    public void testRemoveOccupantWhenNull() {
        assertFalse(occupiedSquare.removeOccupant(null));
    }
   
}

