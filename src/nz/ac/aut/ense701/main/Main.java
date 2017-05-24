package nz.ac.aut.ense701.main;

import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gui.GameStartFrame;
import nz.ac.aut.ense701.gui.KiwiCountUI;

/**
 * Kiwi Count Project
 * 
 * @author AS
 * @version 2011
 */
public class Main 
{
    /**
     * Main method of Kiwi Count.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // create the game object
        //final Game game = new Game();
        // create the GUI for the game
        //final KiwiCountUI  gui  = new KiwiCountUI(game);
        // Create the Game Start Frame
        final GameStartFrame gStartFrame = new GameStartFrame();
        // make the GUI visible
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                //gui.setVisible(true);
                gStartFrame.setVisible(true);
            }
        });
    }

}
