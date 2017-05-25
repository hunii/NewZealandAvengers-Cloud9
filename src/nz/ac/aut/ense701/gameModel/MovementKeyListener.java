/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import nz.ac.aut.ense701.gui.GameInstruct;
import nz.ac.aut.ense701.gui.GameStory;
import nz.ac.aut.ense701.gui.KiwiCountUI;

/**
 *
 * @author mcc7514
 */
public class MovementKeyListener implements KeyListener  {
    private Game game;
    private KiwiCountUI kiwiUi;
    private GameInstruct gInstruct;
    
    public MovementKeyListener(Game game){
        this.game = game;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP){
            game.playerMove(MoveDirection.NORTH);
        }else if(code == KeyEvent.VK_DOWN){
            game.playerMove(MoveDirection.SOUTH);
        }else if(code == KeyEvent.VK_LEFT){
            game.playerMove(MoveDirection.WEST);
        }else if(code == KeyEvent.VK_RIGHT){
            game.playerMove(MoveDirection.EAST);
        }else if(code == KeyEvent.VK_F1){
            final GameInstruct gInstruct  = new GameInstruct();

            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() 
                {
                    gInstruct.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    gInstruct.setVisible(true);
                }
            });
            
        }else if(code == KeyEvent.VK_F2){
            final GameStory gameStory  = new GameStory();

            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() 
                {
                    gameStory.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    gameStory.setVisible(true);
                }
            });
        }else if(code == KeyEvent.VK_F3){
            String developer = game.getDevelopers();
            JOptionPane.showMessageDialog(kiwiUi,developer,"Version & Developers",JOptionPane.INFORMATION_MESSAGE);
        }else if(code == KeyEvent.VK_F11){
            int restartBut = JOptionPane.showConfirmDialog(kiwiUi, "Do you want to Restart the game?", "Restart?",JOptionPane.OK_CANCEL_OPTION);
            game.restartGame(restartBut);
        }else if(code == KeyEvent.VK_F12){
            int exitBut = JOptionPane.showConfirmDialog(kiwiUi, "Do you want to exit the game?", "Exit?",JOptionPane.OK_CANCEL_OPTION);
            game.exitGame(exitBut);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
