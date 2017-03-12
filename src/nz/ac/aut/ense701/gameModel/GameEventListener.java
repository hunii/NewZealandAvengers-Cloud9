/*
 * Interface for classes that want to be informed 
 * about changes in the game state.
 * 
 * @author AS
 * @version 1.0 - created
 */

package nz.ac.aut.ense701.gameModel;

public interface GameEventListener 
{
    public void gameStateChanged();
}
