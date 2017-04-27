/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameModelNew;

import nz.ac.aut.ense701.gameModel.Food;
import nz.ac.aut.ense701.gameModel.GameMessage;
import nz.ac.aut.ense701.gameModel.Island;
import nz.ac.aut.ense701.gameModel.Item;
import nz.ac.aut.ense701.gameModel.MoveDirection;
import nz.ac.aut.ense701.gameModel.Player;
import nz.ac.aut.ense701.gameModel.Position;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author James
 */
public class GameMessageTest {
    
    private Island island;
    private GameMessage msg;
    private Player player;
    private Item foodItem;
    
    public GameMessageTest() {
    }
    
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        this.island = new Island(20,20); 
        this.player = new Player(new Position(island,3,3),"James", 100.0,100.0,100.0,100.0);
        this.foodItem = new Food(new Position(island,3,3), "Sandwich", "Sandwich", 20,20,20);
        this.msg = new GameMessage(player);
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() {
        this.island = null;
        this.player = null;
        this.foodItem = null;
        this.msg = null;
    }

    /**
     * Test correct message is printed depending on the direction of player move.
     */
    @Test
    public void testGetMessageOnPlayerMove()
    {
        msg.playerMoveMsg(MoveDirection.NORTH);
        String result = msg.getMsg();
        String expResult = player.getName()+" has moved up.";
        assertEquals(expResult, result);
    }
    
    /**
     * Test correct message is printed when item is collected.
     */
    @Test
    public void testGetMessageOnPlayerCollectItem()
    {
        msg.collecItemMsg(foodItem);
        String result = msg.getMsg();
        String expResult = player.getName()+" has collected Sandwich.";
        assertEquals(expResult, result);
    }
    
    /**
     * Test correct message is printed when item is dropped.
     */
    @Test
    public void testGetMessageOnPlayerDropItem()
    {
        msg.dropItemMsg(foodItem);
        String result = msg.getMsg();
        String expResult = player.getName()+" has dropped "+foodItem.getName()+".";
        assertEquals(expResult, result);
    }
    
    /**
     * Test correct message is printed when item is dropped.
     */
    @Test
    public void testGetMessageOnPlayerUseItem()
    {
        Food itemAsFood = (Food)foodItem;
        msg.useItemMsg(itemAsFood);
        String result = msg.getMsg();
        String expResult = player.getName()+" has used "+foodItem.getName()+" item and recovered "+(int)itemAsFood.getEnergy()+"% stamina.";
        assertEquals(expResult, result);
    }
}
