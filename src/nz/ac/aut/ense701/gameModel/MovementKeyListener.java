/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author mcc7514
 */
public class MovementKeyListener implements KeyListener  {
    private Game game;
    
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
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
