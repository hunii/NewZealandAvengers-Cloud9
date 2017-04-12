
package nz.ac.aut.ense701.gameModel;

/**
 * Class that represents an object of message within the game.
 * @author James
 * Version 1.0 Created
 * Version 1.1 Added message on player move, use, collect and drop items
 */
public class GameMessage {
    private String msg;
    private Player player;
    
    /**
     * Constructor that takes Player object
     * @param player Player object that has player information
     */
    public GameMessage(Player player){
        msg= "";
        this.player = player;
    }
    
    /**
     * This method returns the game message
     * @return string of game message
     */
    public String getMsg(){
        return msg;
    }

    /**
     * This method sets the message according to the player moving direction.
     * @param direction MoveDirection enumeration that shows which direction the player has moved
     */
    public void playerMoveMsg(MoveDirection direction){
        String appendMsg = player.getName()+" has moved";
        
        switch(direction){
            case EAST:
                msg = appendMsg+" right.";
                break;
            case WEST:
                msg = appendMsg+" left.";
                break;
            case SOUTH:
                msg = appendMsg+" down.";
                break;
            case NORTH:
                msg = appendMsg+" up.";
                break;
            default:
                msg = "";
                break;
        }
    }
    
    /**
     * Method set when player has collected item.
     * @param item Collected Item object
     */
    public void collecItemMsg(Item item){
        msg = player.getName()+" has collected "+item.getName()+".";
    }
    
    /**
     * Method set when player use the item and if it is instance of food then also output how much stamina was recovered
     * @param item Collected Item object
     */
    public void useItemMsg(Item item){
        msg = player.getName()+" has used "+item.getName()+" item";
        if(item instanceof Food){
            Food fooditem = (Food)item;
            msg += " and recovered "+(int)fooditem.getEnergy()+"% stamina";
        }
        if(item instanceof Medicine){
            Medicine med = (Medicine)item;
            msg += " and recovered "+(int)med.getEnergy()+"% health";
        }
        msg += ".";
    }
    
    /**
     * Method set when when player drops item.
     * @param item Collected Item object
     */
    public void dropItemMsg(Item item){
        msg = player.getName()+" has dropped "+item.getName()+".";
    }
}
