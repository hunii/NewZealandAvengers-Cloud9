package nz.ac.aut.ense701.gameModel;

import org.junit.Test;

/**
 * The test class FoodTest.
 *
 * @author  AS
 * @version 2011
 */
public class FoodTest extends junit.framework.TestCase
{
    private Food apple;
    private Position position;
    private Island island;
    /**
     * Default constructor for test class FoodTest
     */
    public FoodTest()
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
        apple = new Food(position, "apple", "A juicy red apple", 1.0, 2.0, 1.5);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @Override
    protected void tearDown()
    {
        apple = null;
        island = null;
        position = null;
    }

    @Test
    public void testGetEnergy()
    {
        assertEquals(1.5, apple.getEnergy(), 0.01);
    }
    
    @Test
    public void testGetStringRepresentation() {
        assertEquals("E",apple.getStringRepresentation());
    }
    
    // Tests for methods in herited from Item
    // These should be testing in one descendant class
    @Test   
    public void testGetWeight()
    {
        assertEquals(1.0, apple.getWeight(), 0.01);
    }

    @Test
    public void testGetSize()
    {
        assertEquals(2.0, apple.getSize(), 0.01);
    }

    @Test
    public void testIsOkToCarryCanCarry(){
        assertTrue("Should be carrable.", apple.isOkToCarry());
    }
    
    @Test
    public void testIsOkToCarryCannotCarry(){
        Food tooBig = new Food(position, "Roast pig", "A roasted giant pig", 1.0, 0.0, 1.0);
        assertFalse("Shouldn't be carrable.", tooBig.isOkToCarry());
    }        
    

}
