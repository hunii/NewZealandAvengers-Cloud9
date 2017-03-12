package nz.ac.aut.ense701.gameModel;

import java.util.Collection;
import org.junit.Test;

/**
 * The test class PlayerTest.
 *
 * @author  AS
 * @version July 2011
 */
public class PlayerTest extends junit.framework.TestCase
{
   Player player;
   Position playerPosition;
   Island island;
   Food sandwich;
     /**
     * Default constructor for test class PlayerTest
     */
    public PlayerTest()
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
        playerPosition = new Position(island, 0,0);
        player = new Player(playerPosition,"Lisa Simpson",25.0, 15.0, 20.0);
        sandwich = new Food(playerPosition, "sandwich", "A tasty cheese sandwich", 1.0, 2.0, 1.5);        
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
        playerPosition = null;
        player = null;       
    }
    
    // Tests for accessor methods
    @Test
    public void testGetName()
    {
        assertEquals("Lisa Simpson", player.getName());
    }

    @Test
    public void testGetPosition()
    {
        assertEquals(playerPosition, player.getPosition());
    }
    
    @Test
    public void testGetMaximumBackpackSize(){
        assertEquals(player.getMaximumBackpackSize(),20.0);
    }
    
    @Test
    public void testGetCurrentBackpackSizeEmpty(){
        assertEquals(player.getCurrentBackpackSize(),0.0);
    }
    
    @Test
    public void testGetCurrentBackpackSizeNotEmpty(){
        player.collect(sandwich);
        assertEquals(player.getCurrentBackpackSize(),2.0);
    }
    
    @Test
    public void testGetMaximumBackpackWeight(){
        assertEquals(player.getMaximumBackpackWeight(),15.0);
    }
    
    @Test
    public void testGetCurrentBackpackWeightEmpty(){
        assertEquals(player.getCurrentBackpackWeight(),0.0);
    }
    @Test
    public void testGetCurrentBackpackWeightNotEmpty(){
        player.collect(sandwich);
        assertEquals(player.getCurrentBackpackWeight(),1.0);
    }
    
    @Test
    public void testGetMaximumStaminaLevel()
    {
        assertEquals(25.0, player.getMaximumStaminaLevel(), 0.01);
    }  
    
    @Test
    public void testGetStaminaLevel()
    {
        assertEquals(25.0, player.getStaminaLevel(), 0.01);
    }


    @Test
    public void testReduceStamina()
    {
        player.reduceStamina(7.0);
        assertEquals(18.0, player.getStaminaLevel(), 0.01);
    } 
    
    @Test
    public void testReduceStaminaPastZero()
    {
        player.reduceStamina(26.0);
        assertEquals(0.0, player.getStaminaLevel(), 0.01);
    } 
    
    @Test
    public void testReduceStaminaByNegative()
    {
        player.reduceStamina(-1.0);
        assertEquals(25.0, player.getStaminaLevel(), 0.01);
    }
    
    @Test 
    public void testIncreaseStamina()
    {
        player.reduceStamina(7.0);
        player.increaseStamina(4.0);
        assertEquals(22.0, player.getStaminaLevel(), 0.01);
    } 
    
    @Test
    public void testIncreaseStaminaPastZero()
    {
        player.increaseStamina(4.0);
        assertEquals(25.0, player.getStaminaLevel(), 0.01);
    } 
    
    @Test
    public void testIncreaseStaminaByNegative()
    {
        player.increaseStamina(-1.0);
        assertEquals(25.0, player.getStaminaLevel(), 0.01);
    }

    @Test
    public void testhasStaminaToMoveEnoughStamina()
    {
        assertTrue(player.hasStaminaToMove(Terrain.SAND));
    }

    @Test
    public void testhasStaminaToMoveNotEnoughStaminaForTerrain()
    {
        player.reduceStamina(23);
        assertFalse(player.hasStaminaToMove(Terrain.SCRUB));
    }

    @Test
    public void testhasStaminaToMoveNotEnoughStaminaFullBackpack()
    {
       // reduce stamina so under four required for FOREST with full backpack
        player.reduceStamina(22.0);
        Tool fullLoad = new Tool(playerPosition, "full", "A full load.", 14.5, 1.5);
        player.collect(fullLoad);
        assertFalse(player.hasStaminaToMove(Terrain.FOREST));
    }

    @Test
    public void testhasStaminaToMoveNotEnoughStaminaPartlyFullBackpack()
    {
       // reduce stamina so 50% backpack load takes over limit
        player.reduceStamina(23.0);
        Tool partLoad = new Tool(playerPosition, "part", "A part load.", 7.5, 1.5);
        player.collect(partLoad);
        assertFalse(player.hasStaminaToMove(Terrain.FOREST));
    }
  
    @Test
    public void testIsAlive()
    {
        assertTrue(player.isAlive());
    }
    
    @Test
    public void testKill()
    {
        player.kill();
        assertFalse(player.isAlive());
    }    

    @Test
    public void testhasItemWithItem()
    {
        player.collect(sandwich);
        assertTrue(player.hasItem(sandwich));
    }
    
    @Test
    public void testhasItemNoItem()
    {
        assertFalse(player.hasItem(sandwich));
    }
    
    @Test
    public void testHasTrapWithTrap()
    {
        Tool trap = new Tool(playerPosition, "Trap", "A predator trap", 1.0, 1.0);
        player.collect(trap);
        assertTrue(player.hasTrap());
    }
    
    @Test
    public void testHasTrapNoTrap()
    {
        assertFalse(player.hasTrap());
    }
    
    @Test
    public void testGetTrap()
    {
        Tool trap = new Tool(playerPosition, "Trap", "A predator trap", 1.0, 1.0);
        player.collect(trap);
        assertEquals(player.getTrap(), trap);
    }
    
    @Test
    public void testGetInventory(){
        player.collect(sandwich);
        Tool trap = new Tool(playerPosition, "Trap", "A predator trap", 1.0, 1.0);
        player.collect(trap);
        Collection inventory = player.getInventory();
        assertTrue(inventory.contains(trap));
        assertTrue(inventory.contains(sandwich));
    }
    
    @Test
    public void testCollectValidItemThatFits()
    {
        assertTrue(player.collect(sandwich));
        assertTrue(player.hasItem(sandwich));
        assertEquals(1.0, player.getCurrentBackpackWeight(),0.01);
        assertEquals(2.0, player.getCurrentBackpackSize(),0.01);
        Position newPos = sandwich.getPosition();
        assertFalse(newPos.isOnIsland());
    }
    
    @Test
    public void testCollectDuplicateItem()
    {
        assertTrue(player.collect(sandwich));
        assertFalse(player.collect(sandwich));
    }

    @Test
    public void testCollectItemMaxWeight()
    {
        Tool maxWeight = new Tool(playerPosition, "weight", "A very heavy weight", 15.0, 1.5);
        assertTrue(player.collect(maxWeight));
        assertTrue(player.hasItem(maxWeight));        
    }
    
    @Test
    public void testCollectItemTooHeavy()
    {
        Tool hugeWeight = new Tool(playerPosition, "weight", "A very heavy weight", 16.0, 1.5);
        assertFalse(player.collect(hugeWeight));
        assertFalse(player.hasItem(hugeWeight));        
    }
    
    @Test
    public void testCollectItemTooBig()
    {
        Tool largeTool = new Tool(playerPosition, "large", "A very large tool", 1.0, 20.5);
        assertFalse(player.collect(largeTool));
        assertFalse(player.hasItem(largeTool));        
    }     

     @Test  
    public void testDropValidItem()
    {
        assertTrue(player.collect(sandwich));
        assertTrue(player.hasItem(sandwich));
        assertTrue(player.drop(sandwich));
        assertEquals(0.0, player.getCurrentBackpackWeight(),0.01);
        assertEquals(0.0, player.getCurrentBackpackSize(),0.01);
        assertFalse(player.hasItem(sandwich));
    }

    @Test
    public void testDropInvalidItem()
    {
        assertFalse(player.drop(sandwich));
    }

    @Test
    public void testMoveToPositionEnoughStamina()
    {
        Position newPosition = new Position(island, 0,1);
        player.moveToPosition(newPosition, Terrain.SAND);
        assertEquals(newPosition, player.getPosition());
        assertEquals(24.0, player.getStaminaLevel(), 0.01);
    }

    @Test
    public void testMoveToPositionNotEnoughStamina()
    {
        Position newPosition = new Position(island, 0,1);
        player.reduceStamina(23);
        player.moveToPosition(newPosition, Terrain.SCRUB);
        assertEquals(playerPosition, player.getPosition());
        assertEquals(2.0, player.getStaminaLevel(), 0.01);
    }
}